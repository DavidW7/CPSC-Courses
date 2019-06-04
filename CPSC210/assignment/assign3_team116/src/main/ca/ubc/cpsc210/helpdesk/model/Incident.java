package ca.ubc.cpsc210.helpdesk.model;

// Represents an incident having a case number and description
public class Incident {
    private int caseNum;
    private String description;
    private boolean isClosed;

    // Effects: incident has given case number and description, and is not closed
    public Incident(int caseNum, String description) {
        this.caseNum = caseNum;
        this.description = description;
        isClosed = false;
    }

    // Effects: returns case number
    public int getCaseNum() {
        return caseNum;
    }

    // Effects: returns description
    public String getDescription() {
        return description;
    }

    // Effects: returns true if case is closed, false otherwise
    public boolean isClosed() {
        return isClosed;
    }

    // Modifies: this
    // Effects: closes the incident
    public void close() {
        isClosed = true;
    }

    // Requires: isClosed()
    // Modifies: this
    // Effects: re-opens the incident
    public void reopen() {
        isClosed = false;
    }
}
