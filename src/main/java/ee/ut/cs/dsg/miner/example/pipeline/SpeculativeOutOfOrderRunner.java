package ee.ut.cs.dsg.miner.example.pipeline;

import ee.ut.cs.dsg.miner.dfg.DirectlyFollowsGraph;
import ee.ut.cs.dsg.miner.events.Event;
import ee.ut.cs.dsg.miner.example.source.EventLogSource;
import ee.ut.cs.dsg.miner.example.source.EventMapper;
import ee.ut.cs.dsg.miner.example.source.FixedUnorderedSource;
import ee.ut.cs.dsg.miner.unorderedstream.FullDFGProcessor;
import ee.ut.cs.dsg.miner.unorderedstream.IncrementalDFGProcessor;
import ee.ut.cs.dsg.miner.unorderedstream.SpeculativeOutOfOrderProcessor;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

public class SpeculativeOutOfOrderRunner {

    private static long windowLength = 10L;

    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);


        ParameterTool parameters = ParameterTool.fromArgs(args);
        DataStream<Event> rawEventStream;
        String source = parameters.getRequired("source");
        String kafka;
        String fileName;
        String topic;

        String numRecordsToEmit;
        String interArrivalTime;

        String generateOutput = parameters.get("generateOutput");
        if (generateOutput == null)
            generateOutput="No";

        String winLen = parameters.get("windowSize");

        if (winLen != null)
        {
            try
            {
                windowLength = Long.parseLong(winLen);
            }
            catch(Exception e)
            {

            }
        }

        numRecordsToEmit = parameters.get("numRecordsToEmit");
        interArrivalTime = parameters.get("interArrivalTime");

        int iNumRecordsToEmit=Integer.MAX_VALUE;

        if (numRecordsToEmit != null)
            iNumRecordsToEmit = Integer.parseInt(numRecordsToEmit);

        long iInterArrivalTime = 1000;

        if (interArrivalTime != null)
            iInterArrivalTime = Long.parseLong(interArrivalTime);


        if (source.toLowerCase().equals("kafka")) {
            kafka = parameters.get("kafka");
            topic = parameters.get("topic");
//            zooKeeper = parameters.get("zookeeper");
            Properties properties = new Properties();
            properties.setProperty("bootstrap.servers", kafka);
//            // only required for Kafka 0.8
//            properties.setProperty("zookeeper.connect", "localhost:2181");
//            properties.setProperty("group.id", "test");
            FlinkKafkaConsumer011<String> consumer = new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(),properties);
            consumer.setStartFromEarliest();
            rawEventStream = env.addSource(consumer).setParallelism(1).map(new EventMapper());
        } else if (source.equalsIgnoreCase("file")){
            fileName = parameters.get("filePath")+"\\"+parameters.get("fileName");
            rawEventStream = env.addSource(new EventLogSource(fileName, iNumRecordsToEmit, iInterArrivalTime*1000));//.setParallelism(1);
        }
        else
//            rawEventStream = env.addSource(new FixedInOrderSource());
            rawEventStream = env.addSource(new FixedUnorderedSource());
        rawEventStream
              //  .filter(e -> e.getCaseID() == 175).setParallelism(1)
                .keyBy(Event::getCaseID)
                .process(new SpeculativeOutOfOrderProcessor()).setParallelism(1)//(windowLength*1000))
                .keyBy((KeySelector<DirectlyFollowsGraph, String>) directlyFollowsGraph -> "1")
                .process(new FullDFGProcessor()).setParallelism(1)
               // .process(new IncrementalDFGProcessor()).setParallelism(1)
                .writeAsText(parameters.get("fileName")+"-GlobalDFG-Speculative.txt", FileSystem.WriteMode.OVERWRITE).setParallelism(1);

       JobExecutionResult result =  env.execute("Test Speculative Out of Order Processor");

       System.out.println("Total processed change DFGs "+result.getAccumulatorResult("ChangeDFGCount").toString());
    }
}
