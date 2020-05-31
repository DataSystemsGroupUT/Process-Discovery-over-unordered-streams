package ee.ut.cs.dsg.miner.events;

import java.io.Serializable;

public class Event  implements Serializable {

    public long getCaseID() {
        return caseID;
    }

    public void setCaseID(long caseID) {
        this.caseID = caseID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getArrivalTimestamp() {
        return arrivalTimestamp;
    }

    public void setArrivalTimestamp(long arrivalTimestamp) {
        this.arrivalTimestamp = arrivalTimestamp;
    }

    protected long caseID;

    protected String activity;

    protected long timestamp;

    protected long arrivalTimestamp;

    public Event(String activity, long caseID, long timestamp)
    {
        this.activity = activity;
        this.caseID = caseID;
        this.timestamp = timestamp;
        this.arrivalTimestamp = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Event)
        {
            Event otherEvent = (Event) other;
            return otherEvent.getTimestamp() == this.getTimestamp() && otherEvent.getCaseID()==this.getCaseID() && otherEvent.getActivity().equals(this.getActivity());
        }
        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        return sb.append("Activity: ").append(this.getActivity())
                .append(" Case: ").append(this.getCaseID())
                .append(" TS: ").append(this.getTimestamp())
                .toString();
    }

}
