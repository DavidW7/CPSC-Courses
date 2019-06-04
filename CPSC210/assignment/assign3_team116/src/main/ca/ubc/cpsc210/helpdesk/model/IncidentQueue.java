package ca.ubc.cpsc210.helpdesk.model;

import java.util.*;

// Represents a queue of incidents to be handled by helpdesk
// with maximum size MAX_SIZE
public class IncidentQueue {
    public static final int MAX_SIZE = 10;

    private List<Incident> incidentQueue;

    // MODIFIES: this
    // EFFECTS: created a new empty queue;
    public IncidentQueue() {
        incidentQueue = new LinkedList<>();
    }

    // REQUIRES: Incident
    // MODIFIES: this
    // EFFECTS: add incident to queue and return true, if queue full then return false
    public boolean addIncident(Incident incident) {
        if (incidentQueue.size() >= MAX_SIZE) {
            return false;
        } else {
            incidentQueue.add(incident);
            return true;
        }
    }

    // REQUIRES: queue not empty
    // MODIFIES: this
    // EFFECTS: remove and return first incident in queue
    public Incident getNextIncident() {
        Incident i = incidentQueue.get(0);
        incidentQueue.remove(0);
        return i;
    }

    // REQUIRES: case number
    // EFFECTS: return queue position of given case, or -1
    public int getPositionInQueueOfCaseNumber(int caseNum) {
        int pos = 1;

        for (Incident i : incidentQueue) {
            if (i.getCaseNum() == caseNum) {
                return pos;
            } else {
                pos++;
            }
        }

        return -1;
    }

    // EFFECTS: return length of queue
    public int length() {
        return incidentQueue.size();
    }

    // EFFECTS: true if the queue is empty, false otherwise
    public boolean isEmpty() {
        return incidentQueue.isEmpty();
    }

    // EFFECTS: true if the queue is full, false otherwise
    public boolean isFull() {
        return incidentQueue.size() >= MAX_SIZE;
    }
}
