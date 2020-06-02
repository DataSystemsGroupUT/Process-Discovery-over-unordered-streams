package ee.ut.cs.dsg.miner.dfg;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class DirectlyFollowsGraphTest {

    @Test
    public void buildFromStrings()
    {
        String input = "Edge from: Check  application  form completeness to: Appraise property  Frequency: 1\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 1\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 1";

        DirectlyFollowsGraph result = DirectlyFollowsGraph.buildFromStrings(input);
        System.out.println(result.toString());
        Assert.assertTrue(result.getNodes().contains("Check  application  form completeness")
                && result.getNodes().contains("Appraise property")
                && result.getNodes().contains("Loan  application received")
                && result.getNodes().contains("Reject application")
                && result.getNodes().contains("Loan application rejected"));
    }

    @Test
    public void getEditDistance() {

        String input1 = "Edge from: Check  application  form completeness to: Appraise property  Frequency: 1\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 1\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 1";

        String input2 = "Edge from: Check  application  form completeness to: Appraise property  Frequency: 1\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 1\n";

        String input3 = "Edge from: Check  application  form completeness to: Appraise property  Frequency: 1\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 1\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 5";

        DirectlyFollowsGraph graph1, graph2;

        graph1 = DirectlyFollowsGraph.buildFromStrings(input1);
        graph2 = DirectlyFollowsGraph.buildFromStrings(input2);

        System.out.println(graph1.getEditDistance(graph2));

        graph2 = DirectlyFollowsGraph.buildFromStrings(input3);

        System.out.println(graph1.getEditDistance(graph2));

        System.out.println(graph1.getEditDistance(graph1));
    }
}
