package CMPT276.Group12.UI;

import javax.swing.*;

import java.awt.*;
import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.Utility.Time;
import CMPT276.Group12.Actions.UIAction;

/*
 * PlayerHUD overlays on top of the game
 * Gives the player option to pause, shows the number of pages and playtime
 */

public class PlayerHUD extends JPanel {

    private PlayerHUDDimensions dimensions;
    private Controller controller;
    private int score;
    private Time timer;
    private JLabel pagesLabel, timeLabel, scoreLabel;
    private JButton pauseButton;

    // Constructor for PlayerHUD - intializes controller, starts time and pages from
    // 0
    public PlayerHUD(Controller con, Time timer) {
        this.controller = con;
        this.timer = timer;
        dimensions = new PlayerHUDDimensions(382, 24, 80, 16);
        initializeUI();
        timer.startTime();
        // startTimer();
        setBackground(Color.BLACK);
    }

    /**
     * Sets the ui display for number of keys found
     * 
     * @param found
     * @param total
     */
    public void setPages(int found, int total) {
        pagesLabel.setText(String.format(PlayerHUDDimensions.getPagesFormat(), found, total));
        repaint();
    } 

    /**
     * Sets the current score in hud display
     * 
     * @param num
     */
    public void setScore(int num) {
        score = num;
        scoreLabel.setText(String.format(PlayerHUDDimensions.getScoreFormat(), score));
        repaint();
    }

    /*
     * initializeUI displays playerHUD overtop of game
     */
    private void initializeUI() {
        setPreferredSize(new Dimension(dimensions.getWidth(), dimensions.getHeight()));
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        pagesLabel = new JLabel("Keys: 0/0");
        pagesLabel.setForeground(PlayerHUDDimensions.getPagesColor());
        timeLabel = new JLabel("time: 0.00s");
        timeLabel.setForeground(PlayerHUDDimensions.getTextColor());
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(PlayerHUDDimensions.getScoreColor());

        pauseButton = new JButton("pause");
        pauseButton.setBackground(PlayerHUDDimensions.getButtonColor());
        pauseButton.setForeground(PlayerHUDDimensions.getTextColor());
        pauseButton.setPreferredSize(new Dimension(dimensions.getPauseWidth(), dimensions.getPauseHeight()));
        pauseButton.addActionListener(e -> {
            controller.uiAction(UIAction.PAUSE);
            timer.stopTime();
        });

        timer.initializeTime();
        timer.setLabelToUpdate(timeLabel);
        repaint();
        timer.startTrackingTime();

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.add(timeLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(pagesLabel);

        add(infoPanel, BorderLayout.WEST);
        add(pauseButton, BorderLayout.EAST);
    }

}
