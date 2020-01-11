package model;

public class Survey {

    private long surveyId;
    
    private String name;
    
    private int expectedCompletes;
    
    private int completedPoints;
    
    private int filteredPoints;

    /**
     * 
     */
    public Survey() {
        super();
    }

    /**
     * @param surveyId
     * @param name
     * @param expectedCompletes
     * @param completedPoints
     * @param filteredPoints
     */
    public Survey(long surveyId, String name, int expectedCompletes, int completedPoints, int filteredPoints) {
        super();
        this.surveyId = surveyId;
        this.name = name;
        this.expectedCompletes = expectedCompletes;
        this.completedPoints = completedPoints;
        this.filteredPoints = filteredPoints;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the expectedCompletes
     */
    public int getExpectedCompletes() {
        return expectedCompletes;
    }

    /**
     * @param expectedCompletes the expectedCompletes to set
     */
    public void setExpectedCompletes(int expectedCompletes) {
        this.expectedCompletes = expectedCompletes;
    }

    /**
     * @return the completedPoints
     */
    public int getCompletedPoints() {
        return completedPoints;
    }

    /**
     * @param completedPoints the completedPoints to set
     */
    public void setCompletedPoints(int completedPoints) {
        this.completedPoints = completedPoints;
    }

    /**
     * @return the filteredPoints
     */
    public int getFilteredPoints() {
        return filteredPoints;
    }

    /**
     * @param filteredPoints the filteredPoints to set
     */
    public void setFilteredPoints(int filteredPoints) {
        this.filteredPoints = filteredPoints;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Survey [surveyId=" + surveyId + ", name=" + name + ", expectedCompletes=" + expectedCompletes
                + ", completedPoints=" + completedPoints + ", filteredPoints=" + filteredPoints + "]";
    }
    
    
}
