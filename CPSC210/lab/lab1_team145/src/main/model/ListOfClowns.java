package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListOfClowns {
    List<Clown> clowns = new ArrayList<Clown>();

    //TODO: Implement this method
    //REQUIRES: boolean value isStanding, indicating whether the clown should be constructed sitting or standing
    //MODIFIES: the list clowns
    //EFFECTS:  creates a new clown, passing in the isStanding parameter, and adds that clown to the list of clowns
    public void addClown(boolean isStanding) {
        Clown newClown = new Clown(isStanding);
        clowns.add(newClown);
    }

    //TODO: Implement this method
    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS:  checks all the clowns in the list clowns, and returns true if every clown is standing
    public boolean allClownsStanding() {
//        for (i = 0; i <  clowns.length() ; i++) {
//            if (allStanding) {
//                allStanding = clowns.get(i).getIsStanding();
//            } else {
//                break;
//            }
//        }
//        return allStanding;
        for (int i = 0; i < clowns.size(); i++) {
            if (!(clowns.get(i).getIsStanding())) {
                return false;
            }
        }
        return true;
    }

    //TODO: Implement this method
    //REQUIRES: an integer value for the clown's index in the list clowns (position)
    //MODIFIES: the clown at the position, and the clown to either side (position+1 and position-1) of the clown
    //EFFECTS:  calls clown.flip() to make a clown sit if standing, or stand if sitting.
    public void flip(int position) {
        if (position >= clowns.size()) {
            System.out.print("Position out of bound");
        } else if (clowns.size() == 1) {
            clowns.get(position).flip();
        } else if (position == 0){
            clowns.get(position).flip();
            clowns.get(position+1).flip();
        } else if (position == clowns.size() - 1) {
            clowns.get(position - 1).flip();
            clowns.get(position).flip();
        } else {
            clowns.get(position - 1).flip();
            clowns.get(position).flip();
            clowns.get(position + 1).flip();
        }
    }

    //TODO: Implement this method
    //REQUIRES: an integer value for the clown's index in the list of clowns (position)
    //MODIFIES: nothing
    //EFFECTS:  returns true if the clown in the list clowns at index position is standing, false otherwise
    public boolean isClownStanding(int position) {
        if (position >= clowns.size()) {
            System.out.print("Position out of bound");
            return false;
        } else {
            return clowns.get(position).getIsStanding();
        }
    }

    //Do not modify this method
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Clown c : clowns) {
            if (c.getIsStanding()) {
                sb.append("  UP  ");
            } else {
                sb.append(" DOWN ");
            }
        }
        return sb.toString();
    }

    //Do not modify this method
    public List<Integer> solve() {
        return solve(new HashSet<String>());
    }

    //Do not modify this method
    public List<Integer> solve(Set<String> seen) {
        return null;
    }
}