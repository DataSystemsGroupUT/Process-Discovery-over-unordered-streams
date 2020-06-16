package ee.ut.cs.dsg.miner.unorderedstream;

import ee.ut.cs.dsg.miner.dfg.DirectlyFollowsGraph;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class FullDFGProcessor extends KeyedProcessFunction<String, DirectlyFollowsGraph, DirectlyFollowsGraph> {

    ValueState<DirectlyFollowsGraph> dfgState;

    private IntCounter intCounter = new IntCounter();
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);


        dfgState = getRuntimeContext().getState(new ValueStateDescriptor<>("dfgState", DirectlyFollowsGraph.class));
        getRuntimeContext().addAccumulator("ChangeDFGCount",intCounter);

    }
    @Override
    public void processElement(DirectlyFollowsGraph updatesToDFG, Context context, Collector<DirectlyFollowsGraph> collector)
            throws Exception {

//        System.out.println(" Current graph key is "+context.getCurrentKey());
//        System.out.println("Input graph computation window "+updatesToDFG.getComputingTimeStart()+ " to "+updatesToDFG.getComputingTimeEnd());
        intCounter.add(1);
        DirectlyFollowsGraph dfgSofar = dfgState.value();


        if (dfgSofar == null)
            dfgSofar = new DirectlyFollowsGraph();

        if (updatesToDFG.getComputingTimeStart() > dfgSofar.getComputingTimeStart()
                && updatesToDFG.getComputingTimeEnd() > dfgSofar.getComputingTimeEnd()) {

//            System.out.println("A time progress happened and we should emit the global DFG");
            collector.collect(dfgSofar);
            dfgSofar.setComputingTimeEnd(updatesToDFG.getComputingTimeEnd());
            dfgSofar.setComputingTimeStart(updatesToDFG.getComputingTimeStart());
        }

        if (updatesToDFG.getNodes().size() > 0) {
//            System.out.println("DFG updated!");


            dfgSofar.merge(updatesToDFG);


            dfgState.update(dfgSofar);
        }
//        else
//            System.out.println("No update to DFG");
    }
}
