package model;

public enum Status {

    NOTASKED,REJECTED,FILTERED,COMPLETED;
    
    public static Status getStatus(int statusNumber) {
        switch(statusNumber) {
            case 1: return NOTASKED;
            case 2: return REJECTED;
            case 3: return FILTERED;
            case 4: return COMPLETED;
            default: return null;
        }
    }
}

