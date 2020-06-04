package ee.ut.cs.dsg.miner.unorderedstream;

import ee.ut.cs.dsg.miner.dfg.DirectlyFollowsGraph;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class FullDFGProcessor extends KeyedProcessFunction<String, DirectlyFollowsGraph, DirectlyFollowsGraph> {

    ValueState<DirectlyFollowsGraph> dfgState;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);


        dfgState = getRuntimeContext().getState(new ValueStateDescriptor<>("dfgState", DirectlyFollowsGraph.class));

    }
    @Override
    public void processElement(DirectlyFollowsGraph updatesToDFG, Context context, Collector<DirectlyFollowsGraph> collector)
            throws Exception {

        System.out.println(" Current graph key is "+context.getCurrentKey());
        DirectlyFollowsGraph dfgSofar = dfgState.value();

        if (dfgSofar == null)
            dfgSofar = new DirectlyFollowsGraph();

        if (updatesToDFG.getNodes().size() > 1) {
            System.out.println("DFG updated!");
            dfgSofar.merge(updatesToDFG);


            collector.collect(dfgSofar);

            dfgState.update(dfgSofar);
        }
        else
            System.out.println("No update to DFG");
    }
}
