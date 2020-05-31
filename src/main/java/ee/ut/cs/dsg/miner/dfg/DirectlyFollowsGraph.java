package ee.ut.cs.dsg.miner.dfg;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectlyFollowsGraph implements Serializable {

    protected Set<String> nodes;
    protected Map<Edge, Integer> edges;

    public DirectlyFollowsGraph()
    {
        nodes = new HashSet<>();
        edges = new HashMap<>();

    }
    public void add(Edge e, int frequency)
    {
        nodes.add(e.getSource());
        nodes.add(e.getDestination());

        Integer freq;
        if (edges.containsKey(e))
        {
             freq = edges.get(e);
             freq = Integer.valueOf(freq.intValue())+ frequency;
        }
        else
        {
            freq = Integer.valueOf(frequency);
        }
        edges.put(e,freq);


    }


    public int getEdgeFrequency(Edge e)
    {
        if (this.edges.containsKey(e))
            return this.edges.get(e).intValue();

        return 0;
    }

    public Set<String> getNodes()
    {
        return this.nodes;
    }
    public Map<Edge, Integer> getEdges()
    {
        return this.edges;
    }
    public void merge (DirectlyFollowsGraph other)
    {
        this.nodes.addAll(other.getNodes());
        for (Edge e: other.getEdges().keySet())
        {
            this.add(e, other.getEdgeFrequency(e));
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Edge e : getEdges().keySet())
        {
            sb.append(e.toString()).append(" Frequency: ").append(getEdgeFrequency(e)).append("\n");
        }
        return sb.toString();
    }

}
