package ee.ut.cs.dsg.miner.example.pipeline;

import ee.ut.cs.dsg.miner.dfg.DirectlyFollowsGraph;
import ee.ut.cs.dsg.miner.example.source.DFGGraphSource;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

public class GraphAccurracyComparator {

    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);


        ParameterTool parameters = ParameterTool.fromArgs(args);

        String baseGraphPath = parameters.getRequired("baseGraphPath");
        String baseGraphFile = parameters.getRequired("baseGraphFile");
        String oooGraphPath = parameters.getRequired("oooGraphPath");
        String oooGraphFile = parameters.getRequired("oooGraphFile");

        DataStream<Tuple2<Integer, String>> baseGraphStream  = env.addSource(new DFGGraphSource(baseGraphPath+"\\"+baseGraphFile));
        DataStream<Tuple2<Integer, String>> oooGraphStream = env.addSource(new DFGGraphSource(oooGraphPath+"\\"+oooGraphFile));

        baseGraphStream.keyBy(0).connect(oooGraphStream.keyBy(0)).process(new CoProcessFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple2<Integer, Double>>() {

            class GraphState
            {
                String orderedOrOOO;
                String graphString;
            }
            private ValueState<GraphState> storedGraph;
            @Override
            public void open(Configuration parameters) throws  Exception
            {

                storedGraph = getRuntimeContext().getState(new ValueStateDescriptor<>("storedGraph",GraphState.class));
            }

            @Override
            // this should be the ordered DFG
            public void processElement1(Tuple2<Integer, String> element, Context context, Collector<Tuple2<Integer, Double>> collector) throws Exception {
                GraphState current = storedGraph.value();

                if (current == null)
                {
                    current = new GraphState();
                    current.orderedOrOOO="Base";
                    current.graphString = element.f1;
                    storedGraph.update(current);
                }
                else
                {
                    DirectlyFollowsGraph oooDFG = DirectlyFollowsGraph.buildFromStrings(current.graphString);
                    DirectlyFollowsGraph orderedDFG = DirectlyFollowsGraph.buildFromStrings(element.f1);

                    collector.collect(new Tuple2<>(element.f0, oooDFG.compareTo(orderedDFG)));
                }
            }

            @Override
            //This should be the unordered DFG
            public void processElement2(Tuple2<Integer, String> element, Context context, Collector<Tuple2<Integer, Double>> collector) throws Exception {
                GraphState current = storedGraph.value();

                if (current == null)
                {
                    current = new GraphState();
                    current.orderedOrOOO="OOO"; // this piece of information is redundant
                    current.graphString = element.f1;
                    storedGraph.update(current);
                }
                else
                {
                    DirectlyFollowsGraph orderedDFG = DirectlyFollowsGraph.buildFromStrings(current.graphString);
                    DirectlyFollowsGraph oooDFG = DirectlyFollowsGraph.buildFromStrings(element.f1);

                    collector.collect(new Tuple2<>(element.f0, oooDFG.compareTo(orderedDFG)));
                }
            }
        }).writeAsText(baseGraphFile+" compared to "+oooGraphFile+".csv", FileSystem.WriteMode.OVERWRITE).setParallelism(1);

        env.execute("Graph comparison of base DFG "+baseGraphFile+ "and OOO DFG "+oooGraphFile);

    }
}
