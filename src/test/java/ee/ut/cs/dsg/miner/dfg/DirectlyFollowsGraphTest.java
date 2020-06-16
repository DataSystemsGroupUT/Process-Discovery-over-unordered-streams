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

        String orderedBuffered ="Edge from: Appraise property to: Assess loan risk  Frequency: 69\n" +
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
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 70\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 60\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 250\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 190\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 129\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 54\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 10\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 109\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 56\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 11\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 57\n" +
                "Edge from: Send home insurance quote to: Approve application  Frequency: 1";

        String bufferedUnordered = "Edge from: Appraise property to: Assess loan risk  Frequency: 69\n" +
                "Edge from: Check credit history to: Appraise property  Frequency: 69\n" +
                "Edge from: Appraise property to: Check credit history  Frequency: 120\n" +
                "Edge from: Receive updated application to: Check  application  form completeness  Frequency: 11\n" +
                "Edge from: Check  application  form completeness to: Appraise property  Frequency: 119\n" +
                "Edge from: Verify repayment agreement to: Cancel application  Frequency: 67\n" +
                "Edge from: Assess eligibility to: Send home insurance quote  Frequency: 7\n" +
                "Edge from: Check credit history to: Assess loan risk  Frequency: 181\n" +
                "Edge from: Send acceptance pack to: Cancel application  Frequency: 1\n" +
                "Edge from: Loan  application received to: Appraise property  Frequency: 1\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 125\n" +
                "Edge from: Prepare acceptance pack to: Check if home insurance quote is requested  Frequency: 107\n" +
                "Edge from: Check if home insurance quote is requested to: Send acceptance pack  Frequency: 44\n" +
                "Edge from: Appraise property to: Assess eligibility  Frequency: 60\n" +
                "Edge from: Cancel application to: Loan  application canceled  Frequency: 67\n" +
                "Edge from: Assess eligibility to: Check if home insurance quote is requested  Frequency: 1\n" +
                "Edge from: Assess eligibility to: Reject application  Frequency: 125\n" +
                "Edge from: Check if home insurance quote is requested to: Send home insurance quote  Frequency: 64\n" +
                "Edge from: Return application back to applicant to: Receive updated application  Frequency: 11\n" +
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 71\n" +
                "Edge from: Assess loan risk to: Prepare acceptance pack  Frequency: 1\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 60\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 246\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 189\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 128\n" +
                "Edge from: Loan  application received to: Check credit history  Frequency: 1\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 53\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 10\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 106\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 57\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 11\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 57\n\n";

        DirectlyFollowsGraph graph1, graph2, graph3, graph4;
        graph1 = DirectlyFollowsGraph.buildFromStrings(orderedBuffered);
        graph2 = DirectlyFollowsGraph.buildFromStrings(orderedSpeculative);
        graph3 = DirectlyFollowsGraph.buildFromStrings(unorderedSpeculative_10_percent);
        graph4 = DirectlyFollowsGraph.buildFromStrings(bufferedUnordered);

        // from unordered 50% to the ordered
   //     System.out.println("Accuracy of ordered speculative DFG compared to ordered buffered DFG "+graph2.compareTo(graph1));



        // from unordered 60% to ordered

        System.out.println("Accuracy of unordered speculative 10 % DFG compared to ordered speculativeDFG "+graph3.compareTo(graph2));

        // from unordered 20% to ordered
        System.out.println("Accuracy of unordered 20 % DFG compared to ordered DFG "+graph3.compareTo(graph1));



       System.out.println("Accuracy of buffered unordered DFG compared to buffered ordered DFG "+graph4.compareTo(graph1));
    }
}
