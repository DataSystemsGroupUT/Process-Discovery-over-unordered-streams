package ee.ut.cs.dsg.miner.example.source;

import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class FixedInOrderSource implements SourceFunction<Event> {
    private boolean running = true;

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        if (running)
        {


//            sourceContext.collectWithTimestamp(new Event("A", 1, 1000),1000);
//            sourceContext.collectWithTimestamp(new Event("B", 1, 2000),2000);
//            sourceContext.collectWithTimestamp(new Event("C", 1, 3000),3000);
//            sourceContext.collectWithTimestamp(new Event("C", 1, 4000),4000);
//
//            sourceContext.collectWithTimestamp(new Event("A", 2, 1100),1100);
//            sourceContext.collectWithTimestamp(new Event("B", 2, 1900),1900);
//            sourceContext.collectWithTimestamp(new Event("F", 2, 3100),3100);
//            sourceContext.collectWithTimestamp(new Event("G", 2, 4200),4200);

            sourceContext.collect(new Event("A", 1, 1000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("B", 1, 2000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("C", 1, 3000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("C", 1, 4000));
            Thread.sleep(1000);

            sourceContext.collect(new Event("A", 2, 1100));
            Thread.sleep(1000);
            sourceContext.collect(new Event("B", 2, 1900));
            Thread.sleep(1000);
            sourceContext.collect(new Event("F", 2, 3100));
            Thread.sleep(1000);
            sourceContext.collect(new Event("G", 2, 4200));


            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);

            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);
            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);


        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}