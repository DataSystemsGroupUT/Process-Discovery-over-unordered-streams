package ee.ut.cs.dsg.miner.example.source;

import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class FixedUnorderedSource implements SourceFunction<Event> {
    private boolean running = true;

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        if (running)
        {



//            sourceContext.collect(new Event("B", 1, 2000));
//            Thread.sleep(1000);
//            sourceContext.collect(new Event("C", 1, 3000));
//            Thread.sleep(1000);
//            sourceContext.collect(new Event("A", 1, 1000));
//            Thread.sleep(1000);
//
//            sourceContext.collect(new Event("C", 1, 4000));
//            Thread.sleep(1000);

//            sourceContext.collect(new Event("A", 2, 1100));
//            Thread.sleep(1000);
            sourceContext.collect(new Event("B", 2, 1900));
            Thread.sleep(1000);
            sourceContext.collect(new Event("F", 2, 3100));
            Thread.sleep(1000);
            sourceContext.collect(new Event("G", 2, 4200));
            Thread.sleep(1000);

            // now send a too late event for a slack of 10 second
            Thread.sleep(10000);
            sourceContext.collect(new Event("G", 2, 500));
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
            sourceContext.collect(new Event("Z", -1, 1000000));
            Thread.sleep(1000);
            

        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}