package CMPT276.Group12.Utility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * New Time class to refactor timer out of playerhud
 */
public class Time {
    private float elapsedTime;
    private long pauseStartTime;
    private long totalPausedTime;
    private boolean isPaused;
    private Timer timer;
    private long startTime;
    private JLabel timeLabelToUpdate;

    public Time() {
        this.timeLabelToUpdate = null;
        initializeTime();
        startTime();
    }

    public boolean PausedStatus() {
        return isPaused;
    }

    /*
     * startTimer begins at zero, or whenever the player paused the game
     */
    public void startTime() {
        if (!timer.isRunning()) {
            long pauseDuration = System.currentTimeMillis() - pauseStartTime; // Time spent paused
            totalPausedTime += pauseDuration; // Add to total pause time
            timer.start();
            isPaused = false;
        }
    }

    /*
     * stopTimer halts the timer and stores the time when pause was initiated
     */
    public void stopTime() {
        if (timer.isRunning()) {
            pauseStartTime = System.currentTimeMillis(); // Store when paused
            timer.stop();
            isPaused = true;
        }
    }

    public float getElaspedTime() {
        return elapsedTime;
    }

    public void setLabelToUpdate(JLabel label) {
        this.timeLabelToUpdate = label;
    }

    public void initializeTime() {
        // Initialize new timer that updates every 100ms and checks if game is not
        // paused; deliver changes on gamescreen
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    elapsedTime = (System.currentTimeMillis() - startTime - totalPausedTime) / 1000.0f;
                    if (timeLabelToUpdate != null) {
                        timeLabelToUpdate.setText("time: " + String.format("%.2f", elapsedTime) + "s");
                    }
                    // repaint();
                }
            }
        });
    }

    public void startTrackingTime() {
        startTime = System.currentTimeMillis(); // Start tracking when game starts
        totalPausedTime = 0; // Reset total pause time
        timer.start();
    }
}
