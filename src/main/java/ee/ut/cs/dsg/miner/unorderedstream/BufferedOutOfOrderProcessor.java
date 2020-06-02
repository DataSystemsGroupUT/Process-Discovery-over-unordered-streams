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

public class BufferedOutOfOrderProcessor extends ProcessWindowFunction<Event, DirectlyFollowsGraph, Long, TimeWindow> {

    // We need to store in the state the least seen timestamp with each firing
    private ValueStateDescriptor<Long> leastSeenTimestampPerFiring;

    public BufferedOutOfOrderProcessor()
    {
        leastSeenTimestampPerFiring = new ValueStateDescriptor<>("lastSeenTimestampPerFiring", Long.class);

    }
    @Override
    public void process(Long aLong, Context context, Iterable<Event> iterable, Collector<DirectlyFollowsGraph> collector) throws Exception {

        ValueState<Long>  lastTimestampVS = context.globalState().getState(leastSeenTimestampPerFiring);

        long lastTS = lastTimestampVS.value() != null?  lastTimestampVS.value().longValue(): Long.MIN_VALUE;


        List<Event> orderedList = new ArrayList<>();


        for (Event e: iterable)
        {
            // Ignore elements that arrive too late
            if (e.getTimestamp() >= lastTS)
            {
                orderedList.add(e);

            }
            else
                System.out.println("Ignoring event: "+e.toString());
        }

        orderedList.sort(Comparator.comparingLong(Event::getTimestamp));
       // System.out.println(orderedList.toString());

        if (orderedList.size() > 0)
            lastTS = orderedList.get(orderedList.size()-1).getTimestamp();

        DirectlyFollowsGraph dfg = new DirectlyFollowsGraph();


        for (int i = 0; i < orderedList.size() -1; i++)
        {
            Edge e = new Edge(orderedList.get(i).getActivity(), orderedList.get(i+1).getActivity());
            dfg.add(e,1);
        }

        collector.collect(dfg);

        lastTimestampVS.update(lastTS);
    }
}
