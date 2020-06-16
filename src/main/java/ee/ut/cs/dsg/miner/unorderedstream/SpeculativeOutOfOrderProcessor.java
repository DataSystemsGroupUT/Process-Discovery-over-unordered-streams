package ee.ut.cs.dsg.miner.unorderedstream;

import ee.ut.cs.dsg.miner.dfg.DirectlyFollowsGraph;
import ee.ut.cs.dsg.miner.dfg.Edge;
import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;

import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;

import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Comparator;

public class SpeculativeOutOfOrderProcessor extends KeyedProcessFunction<Long, Event, DirectlyFollowsGraph> {

    private ListState<Event> bufferedEvents;
    private ValueState<Long> minTS;
  //  private ValueState<DirectlyFollowsGraph> speculativeDFG;

    private long slackTime; // this is the buffering time window K
    // I need to store also the earliest possible, within the K limit, seen timestamp to drop the element if it was received after.
    private long minAcceptedEventTime;

    public SpeculativeOutOfOrderProcessor()
    {}

    public SpeculativeOutOfOrderProcessor(long slackTime) {
        this.slackTime = slackTime;
        this.minAcceptedEventTime = 0;

    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        bufferedEvents = getRuntimeContext().getListState(new ListStateDescriptor<>("bufferedEvents",Event.class));
//        minTS = getRuntimeContext().getState(new ValueStateDescriptor<>("minTS", Long.class));
    //    speculativeDFG = getRuntimeContext().getState(new ValueStateDescriptor<DirectlyFollowsGraph>("speculativeDFG", DirectlyFollowsGraph.class));


    }




    @Override
    public void processElement(Event event, Context context, Collector<DirectlyFollowsGraph> collector) throws Exception {

        // we do not process too late elements
//        if (minTS.value() != null)
//            minAcceptedEventTime = minTS.value().longValue();
////        if (minAcceptedEventTime == 0)
////            minAcceptedEventTime = event.getTimestamp();
//        if (event.getTimestamp() < minAcceptedEventTime) {
//            System.out.println("Ignoring event: "+event.toString());
//            return;
//        }
        ArrayList<Event> insertionSortedList = new ArrayList<>();

        bufferedEvents.get().forEach(insertionSortedList::add);
        //insertionSortedList.sort(Comparator.comparingLong(Event::getTimestamp));
        DirectlyFollowsGraph dfgSoFar;// = speculativeDFG.value();

//        if (dfgSoFar == null)
//        {
        dfgSoFar = new DirectlyFollowsGraph();
//        }
//        for (Event e: bufferedEvents.get())
//        {
//            insertionSortedList.addAll(bufferedEvents.get());
//        }

        // We need to check if elements are added sorted or not.
        // assuming it is sorted
        if (insertionSortedList.size() > 0) {
            Event prev;
            Event next;
            if (event.getTimestamp() >= insertionSortedList.get(insertionSortedList.size() - 1).getTimestamp()) {
                prev = insertionSortedList.get(insertionSortedList.size() - 1);
                insertionSortedList.add(event);
                dfgSoFar.add(new Edge(prev.getActivity(), event.getActivity()),1);
            } else if (event.getTimestamp() < insertionSortedList.get(0).getTimestamp()) {
                System.out.println("Processing a late event at the head of the list for process instance "+context.getCurrentKey());
                prev = insertionSortedList.get(0);
                insertionSortedList.add(0, event);
                dfgSoFar.add(new Edge(event.getActivity(),prev.getActivity()),1);
            } else // iterate to find its location
            {
                for (int i = 0; i < insertionSortedList.size()-1; i++)
                {
                    prev = insertionSortedList.get(i);
                    next = insertionSortedList.get(i+1);

                    if (prev.getTimestamp() <= event.getTimestamp() && event.getTimestamp() <= next.getTimestamp())
                    {
                        // send a negative DFG edge
                        System.out.println("Processing a late element at the middle of the list for process instance "+context.getCurrentKey());
                        insertionSortedList.add(i+1,event);
                        Edge toBreak, toAdd1, toAdd2;

                        toBreak = new Edge(prev.getActivity(), next.getActivity());
                        toAdd1 = new Edge(prev.getActivity(), event.getActivity());
                        toAdd2 = new Edge(event.getActivity(), next.getActivity());
                        dfgSoFar.add( toBreak,-1);
                        dfgSoFar.add( toAdd1,1);
                        dfgSoFar.add( toAdd2,1);
                        System.out.println("Breaking the edge "+toBreak+ ". Adding edge "+toAdd1+" and edge "+toAdd2+
                                " in case "+context.getCurrentKey());
                        break;
                    }
                }
            }
        }
        else {
            insertionSortedList.add(event);
//            System.out.println("Seeding list for process instance "+context.getCurrentKey());
        }
        bufferedEvents.update(insertionSortedList);
        // The setting of the time stamp is used to control the emission of updated global DFG later on.
        long ts = event.getSequenceNumber();
        dfgSoFar.setComputingTimeStart(ts);
        dfgSoFar.setComputingTimeEnd(ts);

        collector.collect(dfgSoFar);
     //   System.out.println("Number of events buffered for case "+context.getCurrentKey()+ " is "+insertionSortedList.size());

//        if (minTS.value() ==null)
//            context.timerService().registerProcessingTimeTimer(context.timerService().currentProcessingTime()+slackTime);
       // speculativeDFG.update(dfgSoFar);
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<DirectlyFollowsGraph> out) throws Exception {
        super.onTimer(timestamp, ctx, out);
        //minAcceptedEventTime = minAcceptedEventTime+slackTime;
        ArrayList<Event> insertionSortedList = new ArrayList<>();

        bufferedEvents.get().forEach(insertionSortedList::add);

        if (minTS.value() == null)
        {
            // this is the first timer firing, we need to get the min timestamp and add to it slack
            minAcceptedEventTime = insertionSortedList.get(0).getTimestamp()+slackTime;
        }
        else
        {
            minAcceptedEventTime = minTS.value().longValue()+slackTime;
          //  System.out.println("New minimum event time: "+minAcceptedEventTime);
        }
        insertionSortedList.removeIf( item -> item.getTimestamp() < minAcceptedEventTime);
//        for (Event e: insertionSortedList)
//            if (e.getTimestamp() < minAcceptedEventTime)
//                insertionSortedList.remove(e);
        minTS.update(minAcceptedEventTime);
        bufferedEvents.update(insertionSortedList);

        ctx.timerService().registerProcessingTimeTimer(ctx.timerService().currentProcessingTime()+slackTime);
    }
}
