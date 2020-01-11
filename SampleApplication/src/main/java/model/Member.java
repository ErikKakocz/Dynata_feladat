package model;

public class Member {

    private long id;
    
    private String fullName;
    
    private String email;
    
    private boolean active;

    /**
     * 
     */
    public Member() {
        super();
    }

    /**
     * @param id
     * @param fullName
     * @param email
     * @param active
     */
    public Member(long id, String fullName, String email, boolean active) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.active = active;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Member [id=" + id + ", fullName=" + fullName + ", email=" + email + ", active=" + active + "]";
    }
    
    
}
