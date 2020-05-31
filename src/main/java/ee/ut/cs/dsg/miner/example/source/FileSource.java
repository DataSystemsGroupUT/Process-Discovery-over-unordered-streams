package ee.ut.cs.dsg.miner.example.source;


import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FileSource implements SourceFunction<Event> {

    private static final long serialVersionUID = -2873892890991630938L;
    private boolean running = true;
    private String filePath;
    private int numRecordsToEmit=Integer.MAX_VALUE;
    public FileSource(String filePath) {
        this.filePath = filePath;
    }

    public FileSource(String filePath, int numRecordsToEmit)
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

            while (running && line != null && recordsEmitted <= numRecordsToEmit) {
                String[] data = line.replace("[","").replace("]","").split(",");


                Long ts = Long.parseLong(data[2].trim());

                sourceContext.collect(new Event(data[1].trim(),Long.parseLong(data[0].trim()),ts));

//                sourceContext.emitWatermark(new Watermark(ts));
                recordsEmitted++;
                line=reader.readLine();
            }
            reader.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        running = false;

    }
}
