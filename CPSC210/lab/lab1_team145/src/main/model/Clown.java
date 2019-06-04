package model;

public class Clown {
    boolean standing = false;

    //TODO: Implement this method
    //REQUIRES: boolean value standing
    //MODIFIES: this
    //EFFECTS:  Constructs the clown initialising its status to
    //          standing if the parameter is true, and sitting if the parameter is false
    public Clown(boolean standing) {
        this.standing = standing;
    }

    //TODO: Implement this method
    //REQUIRES: boolean value standing (true means standing, false means sitting)
    //MODIFIES: this
    //EFFECTS:  toggles the value of the status of the clown from sitting to standing and vise versa
    public void flip() {
        this.standing = !(this.standing);
    }

    //TODO: Implement this method
    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS:  returns true if the clown is standing, false otherwise
    public boolean getIsStanding() {
        return standing;
    }
}