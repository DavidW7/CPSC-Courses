package ca.ubc.cpsc210.helpdesk.test;


import ca.ubc.cpsc210.helpdesk.model.Incident;
import ca.ubc.cpsc210.helpdesk.model.IncidentQueue;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ubc.cpsc210.helpdesk.model.IncidentQueue.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.*;


class IncidentQueueTest {

    private IncidentQueue testQueue;
    private Incident test1;
    private Incident test2;
    private Incident test3;
    private Incident test4;
    private Incident test5;

    @BeforeEach
    public void setup() {
        testQueue = new IncidentQueue();
        test1 = new Incident(1, "test1");
        test2 = new Incident(2, "test2");
        test3 = new Incident(3, "test3");
        test4 = new Incident(4, "test4");
        test5 = new Incident(5, "test5");
    }

    @Test
    public void testAddAndGetNextAndGetPosIncident() {
        testQueue.addIncident(test1);
        testQueue.addIncident(test3);
        testQueue.addIncident(test2);

        assertEquals(3, testQueue.length());
        assertEquals(1, testQueue.getPositionInQueueOfCaseNumber(1));
        assertEquals(3, testQueue.getPositionInQueueOfCaseNumber(2));

        assertEquals(test1, testQueue.getNextIncident());
        assertEquals(test3, testQueue.getNextIncident());

        testQueue.addIncident(test5);
        testQueue.addIncident(test4);
        testQueue.addIncident(test3);

        assertEquals(4, testQueue.length());
        assertEquals(-1, testQueue.getPositionInQueueOfCaseNumber(1));
        assertEquals(1, testQueue.getPositionInQueueOfCaseNumber(2));

        assertEquals(test2, testQueue.getNextIncident());
        assertEquals(test5, testQueue.getNextIncident());
    }

    @Test
    public void testIsEmptyAndIsFullAndAddFalse() {
        assertFalse(testQueue.isFull());
        assertTrue(testQueue.isEmpty());

        testQueue.addIncident(test1);
        testQueue.addIncident(test3);
        testQueue.addIncident(test2);
        testQueue.addIncident(test5);
        testQueue.addIncident(test4);
        testQueue.addIncident(test1);

        assertFalse(testQueue.isFull());
        assertFalse(testQueue.isEmpty());

        testQueue.addIncident(test2);
        testQueue.addIncident(test5);
        testQueue.addIncident(test4);
        assertTrue(testQueue.addIncident(test1));

        assertTrue(testQueue.isFull());
        assertFalse(testQueue.isEmpty());

        assertFalse(testQueue.addIncident(test1));
        assertEquals(MAX_SIZE, testQueue.length());
    }

}