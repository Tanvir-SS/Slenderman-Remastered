package CMPT276.Group12.UI;

import javax.swing.*;
import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.Actions.UIAction;
import java.awt.*;


public class GameOverMenu extends Menu {
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private float elapsedTime;
    private Image backgroundImage;

    public GameOverMenu(Controller controller, int score, int windowWidth, int windowHeight, float time) {
        this.controller = controller;
        this.elapsedTime = time;
        this.backgroundImage = new ImageIcon(getClass().getResource("/UI/gameover.jpg")).getImage();
        initializeUI(score, windowWidth, windowHeight);
    }

    private void initializeUI(int score, int windowWidth, int windowHeight) {
        setLayout(new GridLayout(3, 1, 10, 10)); // Adjusted for button

        // Score label
        scoreLabel = new JLabel("Final Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        // Time label
        timeLabel = new JLabel("Total Time: " + String.format("%.2f", elapsedTime) + "s");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        // Return to Main Menu button
        initBtns("Return to Main Menu");

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(firstButton);

        // Add components to panel
        add(scoreLabel);
        add(timeLabel);
        add(buttonPanel);

        // Set preferred size
        setPreferredSize(new Dimension(992, 768));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Dynamically scales background
    }
    @Override
    public void firstButtonPressed() {
     
            controller.uiAction(UIAction.MENU); // Exit to main menu
       
    }
}
