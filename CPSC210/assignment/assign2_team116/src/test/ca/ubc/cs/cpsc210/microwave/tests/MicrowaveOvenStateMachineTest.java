package ca.ubc.cs.cpsc210.microwave.tests;

import ca.ubc.cs.cpsc210.microwave.model.MicrowaveOvenState;
import ca.ubc.cs.cpsc210.microwave.model.MicrowaveOvenStateMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// unit tests for MicrowaveOvenStateMachine
public class MicrowaveOvenStateMachineTest {

    private MicrowaveOvenStateMachine testM;

    @BeforeEach
    public void setup() {
        testM = new MicrowaveOvenStateMachine();
    }

    @Test
    public void testSetTimeSetPowLvlStart() {
        assertEquals(MicrowaveOvenState.IDLE, testM.getCurrentState());
        assertEquals(MicrowaveOvenState.PROGRAMMED, testM.setTime(1, 59));
        assertEquals(MicrowaveOvenState.PROGRAMMED, testM.setPowerLevel(9));
        assertEquals(MicrowaveOvenState.COOKING, testM.start());
    }

    @Test
    public void testSetTimeCancel() {
        assertEquals(MicrowaveOvenState.PROGRAMMED, testM.setTime(7, 0));
        assertEquals(MicrowaveOvenState.IDLE, testM.cancel());
    }

    @Test
    public void testSetTimeStartPauseCancel(){
        assertEquals(MicrowaveOvenState.PROGRAMMED, testM.setTime(0,1));
        assertEquals(MicrowaveOvenState.COOKING, testM.start());
        assertEquals(MicrowaveOvenState.PAUSED, testM.pause());
        assertEquals(MicrowaveOvenState.IDLE, testM.cancel());
    }

    @Test
    public void testSetTimeStartPauseResume() {
        assertEquals(MicrowaveOvenState.PROGRAMMED, testM.setTime(0,30));
        assertEquals(MicrowaveOvenState.COOKING, testM.start());
        assertEquals(MicrowaveOvenState.PAUSED, testM.pause());
        assertEquals(MicrowaveOvenState.COOKING, testM.resume());
    }

    @Test
    public void testSetTimeNullPaths() {
        goToIdle();
        assertNotNull(testM.setTime(7, 7));

        goToProgrammed();
        assertNull(testM.setTime(2, 4));

        goToCooking();
        assertNull(testM.setTime(9, 8));

        goToPaused();
        assertNull(testM.setTime(8, 7));
    }

    @Test
    public void testCancelNullPaths() {
        goToIdle();
        assertNull(testM.cancel());

        goToProgrammed();
        assertNotNull(testM.cancel());

        goToCooking();
        assertNull(testM.cancel());

        goToPaused();
        assertNotNull(testM.cancel());
    }

    @Test
    public void testSetPowLvlNullPaths() {
        goToIdle();
        assertNull(testM.setPowerLevel(5));

        goToProgrammed();
        assertNotNull(testM.setPowerLevel(5));

        goToCooking();
        assertNull(testM.setPowerLevel(5));

        goToPaused();
        assertNull(testM.setPowerLevel(5));
    }

    @Test
    public void testSetStartNullPaths() {
        goToIdle();
        assertNull(testM.start());

        goToProgrammed();
        assertNotNull(testM.start());

        goToCooking();
        assertNull(testM.start());

        goToPaused();
        assertNull(testM.start());
    }

    @Test
    public void testPauseNullPaths() {
        goToIdle();
        assertNull(testM.pause());

        goToProgrammed();
        assertNull(testM.pause());

        goToCooking();
        assertNotNull(testM.pause());

        goToPaused();
        assertNull(testM.pause());
    }

    @Test
    public void testResumeNullPaths() {
        goToIdle();
        assertNull(testM.resume());

        goToProgrammed();
        assertNull(testM.resume());

        goToCooking();
        assertNull(testM.resume());

        goToPaused();
        assertNotNull(testM.resume());
    }

    // MODIFIES: this
    // EFFECTS: reset microwave, and bring microwave from idle to a generic idle state
    private void goToIdle() {
        testM = new MicrowaveOvenStateMachine();
    }

    // MODIFIES: this
    // EFFECTS: reset microwave, and bring microwave from idle to a generic programmed state
    private void goToProgrammed() {
        testM = new MicrowaveOvenStateMachine();
        testM.setTime(0,30);
    }

    // MODIFIES: this
    // EFFECTS: reset microwave, and bring microwave from idle to a generic cooking state
    private void goToCooking() {
        testM = new MicrowaveOvenStateMachine();
        testM.setTime(0,30);
        testM.start();
    }

    // MODIFIES: this
    // EFFECTS: reset microwave, and bring microwave from idle to a generic paused state
    private void goToPaused() {
        testM = new MicrowaveOvenStateMachine();
        testM.setTime(0,30);
        testM.start();
        testM.pause();
    }

}
