package ee.ut.cs.dsg.miner.example.source;

import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DFGGraphSource implements SourceFunction<Tuple2<Integer, String>> {

    private boolean running = true;
    private String filePath;
    private int count = 1;
    public DFGGraphSource(String filePath)
    {
        this.filePath = filePath;
    }
    @Override
    public void run(SourceContext<Tuple2<Integer, String>> sourceContext) throws Exception {

        try {

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

            line = reader.readLine();
            StringBuilder graphString = new StringBuilder();
            while (running && line != null ) {

                if (line.trim().length() == 0) // we are now ready to emit a new graph string tuple
                {
                    sourceContext.collect(new Tuple2<>(count, graphString.toString()));
                    graphString.setLength(0); // reset the buffer
                    count++;
                }
                else
                {
                    graphString.append(line).append("\n");
                }

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
