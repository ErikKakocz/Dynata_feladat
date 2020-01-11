package model;

public class Participation {

    private long memberId;
    
    private long surveyId;
    
    private Status status;
    
    private int length;

    /**
     * 
     */
    public Participation() {
        super();
    }

    /**
     * @param memberId
     * @param surveyId
     * @param status
     * @param length
     */
    public Participation(long memberId, long surveyId, Status status, int length) {
        super();
        this.memberId = memberId;
        this.surveyId = surveyId;
        this.status = status;
        this.length = length;
    }

    /**
     * @return the memberId
     */
    public long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the surveyId
     */
    public long getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }
    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Participation [memberId=" + memberId + ", surveyId=" + surveyId + ", status=" + status + ", length="
                + length + "]";
    }
    
    
}
