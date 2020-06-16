package ee.ut.cs.dsg.miner.unorderedstream;

import ee.ut.cs.dsg.miner.dfg.DirectlyFollowsGraph;
import ee.ut.cs.dsg.miner.dfg.Edge;
import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BufferedOOOUnawareProcessor extends ProcessWindowFunction<Event, DirectlyFollowsGraph, Long, TimeWindow> {

    // We need to store in the state the least seen timestamp with each firing
    private ValueStateDescriptor<Event> leastSeenTimestampPerFiring;

    public BufferedOOOUnawareProcessor()
    {
        leastSeenTimestampPerFiring = new ValueStateDescriptor<>("lastSeenTimestampPerFiring", Event.class);

    }
    @Override
    public void process(Long caseID, Context context, Iterable<Event> iterable, Collector<DirectlyFollowsGraph> collector) throws Exception {

        ValueState<Event>  lastTimestampVS = context.globalState().getState(leastSeenTimestampPerFiring);


        System.out.println("Handling window from: "+context.window().getStart() +" to: "+context.window().getEnd()+ " for key: "+
                caseID);
        Event lastEvent = lastTimestampVS.value();

        long lastTS = lastEvent != null?  lastEvent.getTimestamp(): Long.MIN_VALUE;


        List<Event> unorderedList = new ArrayList<>();

        if (lastEvent != null)
            unorderedList.add(lastEvent);


        for (Event e: iterable)
        {
            // Ignore elements that arrive too late
            if (e.getTimestamp() >= lastTS)
            {
                unorderedList.add(e);

            }
            else
                System.out.println("Ignoring event: "+e.toString());
        }



        if (unorderedList.size() > 0)
            lastEvent = unorderedList.get(unorderedList.size()-1);

        DirectlyFollowsGraph dfg = new DirectlyFollowsGraph();


        for (int i = 0; i < unorderedList.size() -1; i++)
        {
            Edge e = new Edge(unorderedList.get(i).getActivity(), unorderedList.get(i+1).getActivity());
            dfg.add(e,1);
        }
//        if (caseID == -1)
//            System.out.println("Dummy graph\n"+ dfg.toString());

        dfg.setComputingTimeStart(context.window().getStart());
        dfg.setComputingTimeEnd(context.window().getEnd());
        collector.collect(dfg);

        lastTimestampVS.update(lastEvent);
    }
}
