package ee.ut.cs.dsg.miner.dfg;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectlyFollowsGraph implements Serializable {

    protected Set<String> nodes;
    protected Map<Edge, Integer> edges;

    private long computingTimeStart;
    private long computingTimeEnd;

    public static DirectlyFollowsGraph buildFromStrings(String s)
    {
        // We assume that the string is \n separated where each line
        // is on the form Edge from: node to: node Frequency: n
        String[] lines = s.split("\n");

        DirectlyFollowsGraph result = new DirectlyFollowsGraph();

        for (String line : lines)
        {

            if (line.length() ==0)
                continue;

            if (line.startsWith("start"))
            {

                continue;
            }
            String[] info = line.split(":");
            String source, destination;
            try {
                if (info.length < 2)
                    System.out.println(line);
                source = info[1].replace(" to", "").trim();
                destination = info[2].replace(" Frequency", "").trim();
                int frequency = Integer.parseInt(info[3].trim());
                result.add(new Edge(source, destination), frequency);
            }
            catch  (Exception e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }

    public DirectlyFollowsGraph()
    {
        nodes = new HashSet<>();
        edges = new HashMap<>();
        computingTimeStart = Long.MIN_VALUE;
        computingTimeEnd = Long.MIN_VALUE;

    }
    public void add(Edge e, int frequency)
    {
        if (frequency == 0)
            return;
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
        if (freq == 0)
        {
            edges.remove(e);
            return;
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
        sb.append("start TS:"+this.getComputingTimeStart()+"\n");
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
//        allEdges = allEdges.stream().filter(e -> this.getEdgeFrequency(e) > 0 || base.getEdgeFrequency(e) > 0).collect(Collectors.toSet());
        for (Edge e: allEdges)
        {
            int localFrequency = this.getEdgeFrequency(e);
            int baseGraphEdgeFrequency = base.getEdgeFrequency(e);
            int difference = baseGraphEdgeFrequency - localFrequency;
            if (difference < 0) // we have more frequency in the current graph than the base one, so we have excess
            {
//                System.out.println(e.toString()+" from current graph with frequency "+localFrequency+" \n " +
//                        " exceeds the frequency from the base model with "+-1*difference+"\n");
                excess += -1 * difference;
            }
            else if (difference >0 ){
//                System.out.println(e.toString()+" from current graph with frequency "+localFrequency+" \n " +
//                        " is lower than base model the frequency from the base model with "+difference+"\n");
                loss += difference; //even if there is no difference, we are adding zeros.
            }
        }




        return (2d - ((double) loss/ totalFrequencyBase) - ((double) excess/totalFrequencyThis ))/2;
    }

    public void setComputingTimeEnd(long computingTimeEnd) {
        this.computingTimeEnd = computingTimeEnd;
    }

    public void setComputingTimeStart(long computingTimeStart) {
        this.computingTimeStart = computingTimeStart;
    }

    public long getComputingTimeStart(){
        return computingTimeStart;
    }
    public long getComputingTimeEnd()
    {
        return  computingTimeEnd;
    }
}
