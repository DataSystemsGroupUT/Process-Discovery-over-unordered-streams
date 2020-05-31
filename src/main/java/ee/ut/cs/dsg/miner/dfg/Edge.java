package ee.ut.cs.dsg.miner.dfg;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable {
    protected String source;
    protected String destination;
//    protected int frequency;

    public Edge(String source, String destination)
    {
        this.source = source;
        this.destination = destination;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString()

    {
        return "Edge from: "+ source +" to: "+destination+" ";
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

//    public int getFrequency() {
//        return frequency;
//    }
//
//    public void setFrequency(int frequency) {
//        this.frequency = frequency;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Objects.equals(getSource(), edge.getSource()) &&
                Objects.equals(getDestination(), edge.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination());
    }
}
