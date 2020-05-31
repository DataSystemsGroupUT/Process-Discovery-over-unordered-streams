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
    private boolean running = true;
    private String filePath;
    private int numRecordsToEmit=Integer.MAX_VALUE;
    public EventLogSource(String filePath) {
        this.filePath = filePath;
    }

    public EventLogSource(String filePath, int numRecordsToEmit)
    {
        this.filePath = filePath;
        this.numRecordsToEmit = numRecordsToEmit;
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
            reader.readLine();//skip the header line
            line = reader.readLine();
            int offset = 0;
//            List<String> uniqueKeys = new ArrayList<>();
            //TODO: This part needs to be fixed to adapt to the nature of XES files (event logs)
            while (running && line != null && recordsEmitted <= numRecordsToEmit) {
                String[] data = line.replace("[","").replace("]","").split(",");

                if (data.length == 5)
                    offset=2;

                Long ts = Long.parseLong(data[2+offset].trim());
                sourceContext.collectWithTimestamp(new Event(data[1+offset].trim(),Long.parseLong(data[0+offset].trim()),ts),ts);
                Thread.sleep(100);
                recordsEmitted++;
                line=reader.readLine();
            }
            reader.close();
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