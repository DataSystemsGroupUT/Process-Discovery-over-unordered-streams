package ee.ut.cs.dsg.miner.dfg;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectlyFollowsGraph implements Serializable {

    protected Set<String> nodes;
    protected Map<Edge, Integer> edges;

    public static DirectlyFollowsGraph buildFromStrings(String s)
    {
        // We assume that the string is \n separated where each line
        // is on the form Edge from: node to: node Frequency: n
        String[] lines = s.split("\n");

        DirectlyFollowsGraph result = new DirectlyFollowsGraph();

        for (String line : lines)
        {
            String[] info = line.split(":");
            String source, destination;
            source = info[1].replace(" to","").trim();
            destination = info[2].replace(" Frequency","").trim();
            int frequency = Integer.parseInt(info[3].trim());
            result.add(new Edge(source,destination),frequency);
        }

        return result;
    }

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

    public int getEditDistance(DirectlyFollowsGraph other)
    {
        /*
        How many edit operations, add node, remove node, add edge (weight), or remove edge (weight) to transform the
        current graph to other graph
        */
        Set<String> otherNodes = other.getNodes();
        Map<Edge, Integer> otherEdges = other.getEdges();
        int distance = 0;
        for (String node :nodes)
            if (!otherNodes.contains(node))
                distance++;
        for (String node: otherNodes)
            if (!this.nodes.contains(node))
                distance++;
        for (Edge e: edges.keySet())
        {
            Integer weightOther = otherEdges.get(e);
            if (weightOther == null)
                distance++;
            else
                distance+= Math.abs(edges.get(e)-weightOther.intValue());
        }

        return distance;
    }

}
