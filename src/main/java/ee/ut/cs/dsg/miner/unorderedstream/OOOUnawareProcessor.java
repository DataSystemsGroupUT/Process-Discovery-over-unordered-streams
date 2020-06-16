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
import org.apache.flink.util.Collector;

import java.util.ArrayList;

public class OOOUnawareProcessor extends KeyedProcessFunction<Long, Event, DirectlyFollowsGraph> {

    //   private ListState<Event> bufferedEvents;
    private ValueState<Event> lastReceivedEvent;
    //  private ValueState<DirectlyFollowsGraph> speculativeDFG;

    private long slackTime; // this is the buffering time window K
    // I need to store also the earliest possible, within the K limit, seen timestamp to drop the element if it was received after.
    private long minAcceptedEventTime;

    public OOOUnawareProcessor() {
    }


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);


        lastReceivedEvent = getRuntimeContext().getState(new ValueStateDescriptor<>("lastEvent", Event.class));


    }


    @Override
    public void processElement(Event event, Context context, Collector<DirectlyFollowsGraph> collector) throws Exception {


        Event last = lastReceivedEvent.value();

        if (last != null) {

            DirectlyFollowsGraph dfgSoFar;
            dfgSoFar = new DirectlyFollowsGraph();
            dfgSoFar.add(new Edge(last.getActivity(), event.getActivity()), 1);
            long ts = event.getSequenceNumber();
            dfgSoFar.setComputingTimeStart(ts);
            dfgSoFar.setComputingTimeEnd(ts);

            collector.collect(dfgSoFar);
        }
        lastReceivedEvent.update(event);

    }


}
