package CMPT276.Group12.UITests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CMPT276.Group12.Utility.Time;

import javax.swing.*;

public class TimeTest {
    private Time timer;
    private JLabel label;

    @BeforeEach
    public void setup() {
        timer = new Time();  
        label = new JLabel();
        timer.setLabelToUpdate(label);
        timer.startTrackingTime();
    }

    @Test
    public void testElapsedTimeIncreases() throws InterruptedException {
        float time1 = timer.getElaspedTime();
        Thread.sleep(300); // Give timer time to tick
        float time2 = timer.getElaspedTime();
        assertTrue(time2 > time1, "Elapsed time should increase.");
    }

    @Test
    public void testPauseStopsTime() throws InterruptedException {
        Thread.sleep(300);
        timer.stopTime();
        float timePaused = timer.getElaspedTime();
        Thread.sleep(300);
        float timeAfterPause = timer.getElaspedTime();
        assertEquals(timePaused, timeAfterPause, 0.05, "Time should not change while paused.");
    }

    @Test
    public void testResumeContinuesTime() throws InterruptedException {
        Thread.sleep(300);
        timer.stopTime();
        float pausedTime = timer.getElaspedTime();
        Thread.sleep(300);  // Time while paused (should not count)
        timer.startTime();  // Resume
        Thread.sleep(300);  // Time after resuming
        float resumedTime = timer.getElaspedTime();
        assertTrue(resumedTime > pausedTime, "Timer should continue after being resumed.");
    }

    @Test
    public void testLabelUpdates() throws InterruptedException {
        Thread.sleep(200);  // Let timer tick
        String text = label.getText();
        assertNotNull(text, "Label should not be null.");
        assertTrue(text.startsWith("time: "), "Label should be updated with time.");
    }

    @Test
    public void testStopTimeWhileStopped() {
        timer.stopTime();  // Stop once
        float time1 = timer.getElaspedTime();
        timer.stopTime();  // Try stopping again
        float time2 = timer.getElaspedTime();
        assertEquals(time1, time2, 0.01, "Time should not change while already stopped.");
    }

    @Test
    public void testPauseTime() throws InterruptedException {
        timer.stopTime();
        assertTrue(timer.PausedStatus(), "Timer should be paused.");
    }
}
