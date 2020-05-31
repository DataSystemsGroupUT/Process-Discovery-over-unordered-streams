package ee.ut.cs.dsg.miner.example.source;

import ee.ut.cs.dsg.miner.events.Event;
import org.apache.flink.api.common.functions.MapFunction;

public class EventMapper implements MapFunction<String, Event> {
    @Override
    public Event map(String in) throws Exception {
        String[] elems = in.split(",");
        return new Event (elems[0], Long.parseLong(elems[1]),Long.parseLong(elems[2]));
    }

}
