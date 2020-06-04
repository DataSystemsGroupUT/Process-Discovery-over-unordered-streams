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
    public int getAllEdgeFrequencies()
    {
        return edges.values().stream().mapToInt(Integer::intValue).sum();
    }

    public double compareTo(DirectlyFollowsGraph base)
    {
        /*
        The result of the comparison is fulfilling the following formula
        result = (2 - (loss) - (excess))/2, where:
        loss: the frequency of edges in the base model not found in this model divided by the total frequencies in the
        base model
        excess: the frequency of edges in this model not found in the base model divided by the total frequencies in this
        model
        */
        int totalFrequencyBase = base.getAllEdgeFrequencies();
        int totalFrequencyThis = this.getAllEdgeFrequencies();

//      Compute the loss and excess

        Set<Edge>  allEdges = new HashSet<Edge>(base.getEdges().keySet().size()+this.edges.keySet().size());

        allEdges.addAll(base.getEdges().keySet());
        // for some reason this throws an supported operation exception
        allEdges.addAll(this.edges.keySet());


        int loss = 0;
        int excess = 0;
        for (Edge e: allEdges)
        {
            int localFrequency = this.getEdgeFrequency(e);
            int baseGraphEdgeFrequency = base.getEdgeFrequency(e);
            int difference = baseGraphEdgeFrequency - localFrequency;
            if (difference < 0) // we have more frequency in the current graph than the base one, so we have excess
                excess += -1*difference;
            else
                loss += difference; //even if there is no difference, we are adding zeros.
        }


//        for (Edge e: this.edges.keySet())
//        {
//            int localFrequency = this.getEdgeFrequency(e);
//            int baseGraphEdgeFrequency = base.get
//        }
//        int distance = 0;
//        for (String node :nodes)
//            if (!otherNodes.contains(node))
//                distance++;
//        for (String node: otherNodes)
//            if (!this.nodes.contains(node))
//                distance++;
//        for (Edge e: edges.keySet())
//        {
//            Integer weightOther = otherEdges.get(e);
//            if (weightOther == null)
//                distance++;
//            else
//                distance+= Math.abs(edges.get(e)-weightOther.intValue());
//        }

        return (2d - ((double) loss/ totalFrequencyBase) - ((double) excess/totalFrequencyThis ))/2;
    }

}
