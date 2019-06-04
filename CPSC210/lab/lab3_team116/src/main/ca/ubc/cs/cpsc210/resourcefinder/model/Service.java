package ca.ubc.cs.cpsc210.resourcefinder.model;

// Represents services offered by resources
public enum Service {
    SHELTER("Shelter"),
    FOOD("Food"),
    COUNSELLING("Counselling"),
    YOUTH("Youth services"),
    SENIOR("Senior services"),
    LEGAL("Legal advice");

    private String displayName;

    Service(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
