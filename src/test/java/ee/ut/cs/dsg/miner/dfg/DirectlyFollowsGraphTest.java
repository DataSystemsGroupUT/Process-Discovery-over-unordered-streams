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
        String ordered = "Edge from: Appraise property to: Assess loan risk  Frequency: 46\n" +
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
        // from log 1 with 50% OOO
        String unordered = "Edge from: Verify repayment agreement to: Loan  application canceled  Frequency: 5\n" +
                "Edge from: Check credit history to: Appraise property  Frequency: 49\n" +
                "Edge from: Assess loan risk to: Reject application  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Verify repayment agreement  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Appraise property  Frequency: 85\n" +
                "Edge from: Verify repayment agreement to: Cancel application  Frequency: 42\n" +
                "Edge from: Loan  application approved to: Appraise property  Frequency: 1\n" +
                "Edge from: Loan  application received to: Appraise property  Frequency: 3\n" +
                "Edge from: Cancel application to: Loan  application canceled  Frequency: 52\n" +
                "Edge from: Loan  application canceled to: Cancel application  Frequency: 3\n" +
                "Edge from: Loan application rejected to: Reject application  Frequency: 4\n" +
                "Edge from: Loan  application canceled to: Check  application  form completeness  Frequency: 1\n" +
                "Edge from: Loan  application approved to: Approve application  Frequency: 2\n" +
                "Edge from: Verify repayment agreement to: Check credit history  Frequency: 1\n" +
                "Edge from: Check if home insurance quote is requested to: Send home insurance quote  Frequency: 43\n" +
                "Edge from: Assess eligibility to: Reject application  Frequency: 84\n" +
                "Edge from: Loan  application canceled to: Check credit history  Frequency: 1\n" +
                "Edge from: Return application back to applicant to: Receive updated application  Frequency: 7\n" +
                "Edge from: Verify repayment agreement to: Loan  application received  Frequency: 3\n" +
                "Edge from: Loan  application received to: Cancel application  Frequency: 1\n" +
                "Edge from: Assess loan risk to: Prepare acceptance pack  Frequency: 1\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 40\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 112\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 142\n" +
                "Edge from: Loan  application received to: Check credit history  Frequency: 2\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 34\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 65\n" +
                "Edge from: Assess eligibility to: Loan application rejected  Frequency: 4\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 50\n" +
                "Edge from: Appraise property to: Assess loan risk  Frequency: 44\n" +
                "Edge from: Appraise property to: Check credit history  Frequency: 75\n" +
                "Edge from: Receive updated application to: Check  application  form completeness  Frequency: 6\n" +
                "Edge from: Assess eligibility to: Send home insurance quote  Frequency: 3\n" +
                "Edge from: Check credit history to: Assess loan risk  Frequency: 112\n" +
                "Edge from: Loan  application canceled to: Loan  application received  Frequency: 1\n" +
                "Edge from: Loan  application approved to: Loan  application received  Frequency: 2\n" +
                "Edge from: Check if home insurance quote is requested to: Send acceptance pack  Frequency: 25\n" +
                "Edge from: Prepare acceptance pack to: Check if home insurance quote is requested  Frequency: 55\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 99\n" +
                "Edge from: Appraise property to: Assess eligibility  Frequency: 34\n" +
                "Edge from: Verify repayment agreement to: Loan  application approved  Frequency: 3\n" +
                "Edge from: Check credit history to: Approve application  Frequency: 1\n" +
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 45\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 90\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 8\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 42\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 9\n" +
                "Edge from: Appraise property to: Cancel application  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Assess loan risk  Frequency: 2";

        // from 20% OOO log
        String unordered2 = "Edge from: Verify repayment agreement to: Loan  application canceled  Frequency: 8\n" +
                "Edge from: Check credit history to: Appraise property  Frequency: 43\n" +
                "Edge from: Assess loan risk to: Reject application  Frequency: 3\n" +
                "Edge from: Check  application  form completeness to: Approve application  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Verify repayment agreement  Frequency: 1\n" +
                "Edge from: Approve application to: Loan  application received  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Appraise property  Frequency: 81\n" +
                "Edge from: Verify repayment agreement to: Cancel application  Frequency: 34\n" +
                "Edge from: Loan  application approved to: Appraise property  Frequency: 1\n" +
                "Edge from: Loan  application received to: Appraise property  Frequency: 2\n" +
                "Edge from: Cancel application to: Loan  application canceled  Frequency: 39\n" +
                "Edge from: Loan  application canceled to: Cancel application  Frequency: 11\n" +
                "Edge from: Loan application rejected to: Reject application  Frequency: 12\n" +
                "Edge from: Loan  application approved to: Approve application  Frequency: 1\n" +
                "Edge from: Verify repayment agreement to: Check credit history  Frequency: 1\n" +
                "Edge from: Check if home insurance quote is requested to: Send home insurance quote  Frequency: 37\n" +
                "Edge from: Assess eligibility to: Reject application  Frequency: 71\n" +
                "Edge from: Loan  application canceled to: Check credit history  Frequency: 1\n" +
                "Edge from: Return application back to applicant to: Receive updated application  Frequency: 6\n" +
                "Edge from: Verify repayment agreement to: Loan  application received  Frequency: 2\n" +
                "Edge from: Loan  application received to: Cancel application  Frequency: 1\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 34\n" +
                "Edge from: Send home insurance quote to: Loan  application canceled  Frequency: 1\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 137\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 108\n" +
                "Edge from: Loan  application received to: Check credit history  Frequency: 2\n" +
                "Edge from: Cancel application to: Check  application  form completeness  Frequency: 1\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 37\n" +
                "Edge from: Loan  application received to: Assess loan risk  Frequency: 1\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 61\n" +
                "Edge from: Assess eligibility to: Loan application rejected  Frequency: 9\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 40\n" +
                "Edge from: Check if home insurance quote is requested to: Verify repayment agreement  Frequency: 2\n" +
                "Edge from: Appraise property to: Assess loan risk  Frequency: 42\n" +
                "Edge from: Appraise property to: Check credit history  Frequency: 66\n" +
                "Edge from: Cancel application to: Loan  application received  Frequency: 1\n" +
                "Edge from: Check credit history to: Assess eligibility  Frequency: 1\n" +
                "Edge from: Receive updated application to: Check  application  form completeness  Frequency: 7\n" +
                "Edge from: Assess eligibility to: Send home insurance quote  Frequency: 2\n" +
                "Edge from: Check credit history to: Assess loan risk  Frequency: 99\n" +
                "Edge from: Loan  application canceled to: Loan  application received  Frequency: 2\n" +
                "Edge from: Loan  application approved to: Loan  application received  Frequency: 3\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 80\n" +
                "Edge from: Prepare acceptance pack to: Check if home insurance quote is requested  Frequency: 58\n" +
                "Edge from: Appraise property to: Assess eligibility  Frequency: 29\n" +
                "Edge from: Check if home insurance quote is requested to: Send acceptance pack  Frequency: 21\n" +
                "Edge from: Verify repayment agreement to: Loan  application approved  Frequency: 3\n" +
                "Edge from: Loan  application approved to: Assess loan risk  Frequency: 1\n" +
                "Edge from: Check credit history to: Approve application  Frequency: 1\n" +
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 47\n" +
                "Edge from: Loan application rejected to: Loan  application received  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 90\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 6\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 39\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 10\n" +
                "Edge from: Appraise property to: Cancel application  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Assess loan risk  Frequency: 1";

        String unordered60Percent = "Edge from: Send acceptance pack to: Loan  application received  Frequency: 1\n" +
                "Edge from: Verify repayment agreement to: Loan  application canceled  Frequency: 23\n" +
                "Edge from: Check credit history to: Appraise property  Frequency: 37\n" +
                "Edge from: Assess loan risk to: Reject application  Frequency: 2\n" +
                "Edge from: Check  application  form completeness to: Approve application  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Verify repayment agreement  Frequency: 1\n" +
                "Edge from: Approve application to: Loan  application received  Frequency: 3\n" +
                "Edge from: Check  application  form completeness to: Appraise property  Frequency: 70\n" +
                "Edge from: Verify repayment agreement to: Cancel application  Frequency: 19\n" +
                "Edge from: Loan  application approved to: Appraise property  Frequency: 1\n" +
                "Edge from: Send acceptance pack to: Cancel application  Frequency: 1\n" +
                "Edge from: Loan  application received to: Appraise property  Frequency: 3\n" +
                "Edge from: Cancel application to: Loan  application canceled  Frequency: 22\n" +
                "Edge from: Loan  application canceled to: Cancel application  Frequency: 20\n" +
                "Edge from: Loan application rejected to: Reject application  Frequency: 28\n" +
                "Edge from: Loan  application approved to: Approve application  Frequency: 17\n" +
                "Edge from: Verify repayment agreement to: Check credit history  Frequency: 1\n" +
                "Edge from: Assess eligibility to: Reject application  Frequency: 34\n" +
                "Edge from: Check if home insurance quote is requested to: Send home insurance quote  Frequency: 32\n" +
                "Edge from: Return application back to applicant to: Receive updated application  Frequency: 5\n" +
                "Edge from: Verify repayment agreement to: Loan  application received  Frequency: 2\n" +
                "Edge from: Loan  application received to: Cancel application  Frequency: 1\n" +
                "Edge from: Loan  application received to: Return application back to applicant  Frequency: 2\n" +
                "Edge from: Check if home insurance quote is requested to: Approve application  Frequency: 1\n" +
                "Edge from: Prepare acceptance pack to: Approve application  Frequency: 2\n" +
                "Edge from: Assess loan risk to: Prepare acceptance pack  Frequency: 2\n" +
                "Edge from: Assess loan risk to: Appraise property  Frequency: 27\n" +
                "Edge from: Send home insurance quote to: Loan  application canceled  Frequency: 1\n" +
                "Edge from: Loan  application received to: Check  application  form completeness  Frequency: 117\n" +
                "Edge from: Assess loan risk to: Assess eligibility  Frequency: 91\n" +
                "Edge from: Loan  application received to: Check credit history  Frequency: 6\n" +
                "Edge from: Assess loan risk to: Loan application rejected  Frequency: 4\n" +
                "Edge from: Send acceptance pack to: Verify repayment agreement  Frequency: 20\n" +
                "Edge from: Loan  application received to: Assess loan risk  Frequency: 3\n" +
                "Edge from: Assess eligibility to: Prepare acceptance pack  Frequency: 58\n" +
                "Edge from: Assess eligibility to: Loan application rejected  Frequency: 28\n" +
                "Edge from: Approve application to: Loan  application approved  Frequency: 24\n" +
                "Edge from: Check if home insurance quote is requested to: Verify repayment agreement  Frequency: 4\n" +
                "Edge from: Appraise property to: Assess loan risk  Frequency: 30\n" +
                "Edge from: Appraise property to: Check credit history  Frequency: 64\n" +
                "Edge from: Send home insurance quote to: Loan  application received  Frequency: 1\n" +
                "Edge from: Check credit history to: Assess eligibility  Frequency: 2\n" +
                "Edge from: Receive updated application to: Check  application  form completeness  Frequency: 7\n" +
                "Edge from: Assess eligibility to: Send home insurance quote  Frequency: 2\n" +
                "Edge from: Loan  application received to: Receive updated application  Frequency: 1\n" +
                "Edge from: Prepare acceptance pack to: Send home insurance quote  Frequency: 4\n" +
                "Edge from: Check credit history to: Assess loan risk  Frequency: 99\n" +
                "Edge from: Assess eligibility to: Verify repayment agreement  Frequency: 2\n" +
                "Edge from: Loan  application canceled to: Loan  application received  Frequency: 4\n" +
                "Edge from: Appraise property to: Reject application  Frequency: 3\n" +
                "Edge from: Loan  application approved to: Loan  application received  Frequency: 1\n" +
                "Edge from: Reject application to: Loan application rejected  Frequency: 47\n" +
                "Edge from: Check if home insurance quote is requested to: Send acceptance pack  Frequency: 17\n" +
                "Edge from: Prepare acceptance pack to: Check if home insurance quote is requested  Frequency: 51\n" +
                "Edge from: Appraise property to: Assess eligibility  Frequency: 33\n" +
                "Edge from: Send home insurance quote to: Loan  application approved  Frequency: 1\n" +
                "Edge from: Verify repayment agreement to: Loan  application approved  Frequency: 11\n" +
                "Edge from: Prepare acceptance pack to: Send acceptance pack  Frequency: 2\n" +
                "Edge from: Assess eligibility to: Check if home insurance quote is requested  Frequency: 1\n" +
                "Edge from: Send home insurance quote to: Verify repayment agreement  Frequency: 38\n" +
                "Edge from: Check  application  form completeness to: Check credit history  Frequency: 81\n" +
                "Edge from: Check credit history to: Loan  application approved  Frequency: 1\n" +
                "Edge from: Assess eligibility to: Send acceptance pack  Frequency: 5\n" +
                "Edge from: Verify repayment agreement to: Approve application  Frequency: 18\n" +
                "Edge from: Check  application  form completeness to: Return application back to applicant  Frequency: 6\n" +
                "Edge from: Appraise property to: Cancel application  Frequency: 1\n" +
                "Edge from: Check  application  form completeness to: Assess loan risk  Frequency: 3\n" +
                "Edge from: Send home insurance quote to: Approve application  Frequency: 2";


        DirectlyFollowsGraph graph1, graph2, graph3, graph4;
        graph1 = DirectlyFollowsGraph.buildFromStrings(ordered);
        graph2 = DirectlyFollowsGraph.buildFromStrings(unordered);
        graph3 = DirectlyFollowsGraph.buildFromStrings(unordered2);
        graph4 = DirectlyFollowsGraph.buildFromStrings(unordered60Percent);

        // from unordered 50% to the ordered
        System.out.println("Accuracy of unordered 50 % DFG compared to ordered DFG "+graph2.compareTo(graph1));



        // from unordered 60% to ordered

        System.out.println("Accuracy of unordered 60 % DFG compared to ordered DFG "+graph4.compareTo(graph1));

        // from unordered 20% to ordered
        System.out.println("Accuracy of unordered 20 % DFG compared to ordered DFG "+graph3.compareTo(graph1));



        System.out.println("Accuracy of ordered DFG compared to ordered DFG "+graph1.compareTo(graph1));
    }
}
