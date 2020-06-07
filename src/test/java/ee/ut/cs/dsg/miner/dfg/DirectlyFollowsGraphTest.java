package ee.ut.cs.dsg.miner.dfg;

import org.junit.Assert;
import org.junit.Test;

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

        System.out.println(graph1.compareTo(graph2));

        graph2 = DirectlyFollowsGraph.buildFromStrings(input3);

        System.out.println(graph1.compareTo(graph2));

        System.out.println(graph1.compareTo(graph1));
    }
    @Test
    public void getEditDistanceRealData()
    {
        String orderedSpeculative = "Edge from: Appraise property to: Assess loan risk  Frequency: 69\n" +
                "Edge from: Check credit history to: Appraise property  Frequency: 69\n" +
                "Edge from: Appraise property to: Check credit history  Frequency: 121\n" +
                "Edge from: Receive updated application to: Check  application  form completeness  Frequency: 11\n" +
                "Edge from: Check  application  form completeness to: Appraise property  Frequency: 121\n" +
                "Edge from: Verify repayment agreement to: Cancel application  Frequency: 68\n" +
                "Edge from: Assess eligibility to: Send home insurance quote  Frequency: 6\n" +
                "Edge from: Check credit history to: Assess loan risk  Frequency: 181\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 125\n" +
                "Edge from: Prepare acceptance pack to: Check if home insurance quote is requested  Frequency: 109\n" +
                "Edge from: Check if home insurance quote is requested to: Send acceptance pack  Frequency: 44\n" +
                "Edge from: Appraise property to: Assess eligibility  Frequency: 60\n" +
                "Edge from: Cancel application to: Loan  application canceled  Frequency: 68\n" +
                "Edge from: Assess eligibility to: Reject application  Frequency: 125\n" +
                "Edge from: Check if home insurance quote is requested to: Send home insurance quote  Frequency: 65\n" +
                "Edge from: Return application back to applicant to: Receive updated application  Frequency: 11\n" +
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 71\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 60\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 250\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 190\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 129\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 54\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 10\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 109\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 57\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 11\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 57";

        String unorderedSpeculative_10_percent = "Edge from: Appraise property to: Assess loan risk  Frequency: 69\n" +
                "Edge from: Check credit history to: Appraise property  Frequency: 69\n" +
                "Edge from: Appraise property to: Check credit history  Frequency: 121\n" +
                "Edge from: Receive updated application to: Check  application  form completeness  Frequency: 11\n" +
                "Edge from: Check  application  form completeness to: Appraise property  Frequency: 121\n" +
                "Edge from: Verify repayment agreement to: Cancel application  Frequency: 68\n" +
                "Edge from: Assess eligibility to: Send home insurance quote  Frequency: 6\n" +
                "Edge from: Check credit history to: Assess loan risk  Frequency: 181\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 125\n" +
                "Edge from: Prepare acceptance pack to: Check if home insurance quote is requested  Frequency: 109\n" +
                "Edge from: Check if home insurance quote is requested to: Send acceptance pack  Frequency: 44\n" +
                "Edge from: Appraise property to: Assess eligibility  Frequency: 60\n" +
                "Edge from: Cancel application to: Loan  application canceled  Frequency: 68\n" +
                "Edge from: Assess eligibility to: Reject application  Frequency: 125\n" +
                "Edge from: Check if home insurance quote is requested to: Send home insurance quote  Frequency: 65\n" +
                "Edge from: Return application back to applicant to: Receive updated application  Frequency: 11\n" +
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 71\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 60\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 250\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 190\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 129\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 54\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 10\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 109\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 57\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 11\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 57";

        String orderedBuffered ="Edge from: Appraise property to: Assess loan risk  Frequency: 46\n" +
                "Edge from: Check credit history to: Appraise property  Frequency: 53\n" +
                "Edge from: Appraise property to: Check credit history  Frequency: 78\n" +
                "Edge from: Send home insurance quote to: Loan  application received  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Verify repayment agreement  Frequency: 1\n" +
                "Edge from: Receive updated application to: Check  application  form completeness  Frequency: 5\n" +
                "Edge from: Check  application  form completeness to: Appraise property  Frequency: 92\n" +
                "Edge from: Verify repayment agreement to: Cancel application  Frequency: 50\n" +
                "Edge from: Assess eligibility to: Send home insurance quote  Frequency: 3\n" +
                "Edge from: Loan  application approved to: Appraise property  Frequency: 1\n" +
                "Edge from: Check credit history to: Assess loan risk  Frequency: 124\n" +
                "Edge from: Loan  application canceled to: Loan  application received  Frequency: 1\n" +
                "Edge from: Loan  application approved to: Loan  application received  Frequency: 1\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 111\n" +
                "Edge from: Prepare acceptance pack to: Check if home insurance quote is requested  Frequency: 62\n" +
                "Edge from: Appraise property to: Assess eligibility  Frequency: 36\n" +
                "Edge from: Check if home insurance quote is requested to: Send acceptance pack  Frequency: 26\n" +
                "Edge from: Cancel application to: Loan  application canceled  Frequency: 64\n" +
                "Edge from: Check credit history to: Approve application  Frequency: 1\n" +
                "Edge from: Loan  application canceled to: Check  application  form completeness  Frequency: 1\n" +
                "Edge from: Verify repayment agreement to: Check credit history  Frequency: 1\n" +
                "Edge from: Check if home insurance quote is requested to: Send home insurance quote  Frequency: 38\n" +
                "Edge from: Assess eligibility to: Reject application  Frequency: 87\n" +
                "Edge from: Loan  application canceled to: Check credit history  Frequency: 1\n" +
                "Edge from: Return application back to applicant to: Receive updated application  Frequency: 9\n" +
                "Edge from: Verify repayment agreement to: Loan  application received  Frequency: 3\n" +
                "Edge from: Loan  application received to: Cancel application  Frequency: 1\n" +
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 53\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 37\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 161\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 98\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 120\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 35\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 7\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 70\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 41\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 10\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 54\n" +
                "Edge from: Appraise property to: Cancel application  Frequency: 1";



        DirectlyFollowsGraph graph1, graph2, graph3, graph4;
        graph1 = DirectlyFollowsGraph.buildFromStrings(orderedBuffered);
        graph2 = DirectlyFollowsGraph.buildFromStrings(orderedSpeculative);
        graph3 = DirectlyFollowsGraph.buildFromStrings(unorderedSpeculative_10_percent);
//        graph4 = DirectlyFollowsGraph.buildFromStrings(unordered60Percent);

        // from unordered 50% to the ordered
   //     System.out.println("Accuracy of ordered speculative DFG compared to ordered buffered DFG "+graph2.compareTo(graph1));



        // from unordered 60% to ordered

        System.out.println("Accuracy of unordered speculative 10 % DFG compared to ordered speculativeDFG "+graph3.compareTo(graph2));

        // from unordered 20% to ordered
        System.out.println("Accuracy of unordered 20 % DFG compared to ordered DFG "+graph3.compareTo(graph1));



     //   System.out.println("Accuracy of ordered buffered DFG compared to ordered speculative DFG "+graph1.compareTo(graph2));
    }
}
