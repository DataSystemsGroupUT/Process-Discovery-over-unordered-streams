package ee.ut.cs.dsg.miner.example.source;

import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class EventLogSource implements SourceFunction<Event> {

    private static final long serialVersionUID = -2873892890991630938L;
    private final long tailPadding; // in minutes to be converted to seconds
    private boolean running = true;
    private final String filePath;
    private final int numRecordsToEmit;
    private final long interArrivalTime;
    public EventLogSource(String filePath) {
        this(filePath, Integer.MAX_VALUE, 100, 1000 );
    }

    public EventLogSource(String filePath, int numRecordsToEmit)
    {
        this(filePath,numRecordsToEmit, 100, 1000);
    }

    public EventLogSource(String filePath, int numRecordsToEmit, long interArrivalTime)
    {
        this(filePath,numRecordsToEmit, interArrivalTime, 1000);
    }

    public EventLogSource(String filePath, int numRecordsToEmit, long interArrivalTime, long tailPadding)
    {
        this.filePath = filePath;
        this.numRecordsToEmit = numRecordsToEmit;
        this.interArrivalTime = interArrivalTime;
        this.tailPadding = tailPadding;
    }

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        try {
            int recordsEmitted=0;
            BufferedReader reader;
            if (filePath.startsWith("http")) {
                URL url = new URL(filePath);
                InputStreamReader is = new InputStreamReader(url.openStream());

//            BufferedReader reader = new BufferedReader(new FileReader(filePath));
                reader = new BufferedReader(is);
            }
            else
            {
                reader = new BufferedReader(new FileReader(filePath));
            }
            String line;
 //           reader.readLine();//skip the header line
            line = reader.readLine();
            int offset = 0;
//            List<String> uniqueKeys = new ArrayList<>();

            while (running && line != null && recordsEmitted <= numRecordsToEmit) {
                String[] data = line.replace("[","").replace("]","").split(",");

                if (data.length == 5)// this is to handle the extra two timestamps added by the OOO generator
                    offset=2;

                Long ts = Long.parseLong(data[2+offset].trim());
                Event ev = new Event(data[1+offset].trim(),Long.parseLong(data[0+offset].trim()),ts, recordsEmitted);
          //      System.out.println(ev.toString());
                sourceContext.collect(ev);
//              Thread.sleep(interArrivalTime);
                Thread.sleep(1);
                recordsEmitted++;
                line=reader.readLine();
            }
            System.out.println("Records emitted "+recordsEmitted);
            reader.close();

            // just keep sending dummy records for the sake of allowing the pipeline to flush all the remaining
            // computations
//            sourceContext.collect(new Event("DUMMY", -1, System.currentTimeMillis()));
//            sourceContext.collect(new Event("DUMMY", -1, System.currentTimeMillis()+1));
//            Thread.sleep(3* 60 * 1000 * tailPadding);
//            for (int secs = 1; secs <= 3 * 60 * tailPadding; secs++)
//            {
//                sourceContext.collect(new Event("DUMMY", -1, System.currentTimeMillis()));
//                Thread.sleep(interArrivalTime);
//            }
//            for (String key: uniqueKeys)
////                        sourceContext.collectWithTimestamp(new SpeedEvent(key, Long.MAX_VALUE, new Double(-100)), Long.MAX_VALUE);
            // sourceContext.emitWatermark(new Watermark(Long.MAX_VALUE));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        running = false;

    }
}