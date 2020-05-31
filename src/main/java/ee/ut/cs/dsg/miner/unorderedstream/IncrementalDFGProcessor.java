package ee.ut.cs.dsg.miner.unorderedstream;

import ee.ut.cs.dsg.miner.dfg.DirectlyFollowsGraph;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class IncrementalDFGProcessor extends KeyedProcessFunction<String, DirectlyFollowsGraph, DirectlyFollowsGraph> {


    @Override
    public void processElement(DirectlyFollowsGraph updatesToDFG, Context context, Collector<DirectlyFollowsGraph> collector)
            throws Exception {



        collector.collect(updatesToDFG);


    }

}
