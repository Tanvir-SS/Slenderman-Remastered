package CMPT276.Group12.UI;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import CMPT276.Group12.Mechanics.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;

/* 
 * Menu extends Jpanel to act as ancestor of MainMenu and PauseMenu
 * topPanel and bottomPanel display different buttons depending on type of menu
 * controller updates state of game (menu,game,pause,exit)
 * 
 */
public class Menu extends JPanel {
    protected JPanel topPanel; // in pause will show controls, in mainmenu will say main menu or game name
    protected JPanel bottomPanel;// pause will show resume, exit to mainmenu or close game
    // main menu will show start controls and options
    protected JButton firstButton; // Start game button
    protected JButton secondButton; // Exit game button

    // use method uiAction to update this method when a button is clicked
    protected Controller controller;

    
    protected Image backgroundImage;

    /**
     * Creates a new JButton
     * @param title of button
     * @return JButton
     */
    private JButton btnFactory(String title) {
        JButton btn = new JButton(title);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    /**
     * Inits this Menu's Buttons
     * @param title1 title of button 1
     * @param title2 title of button 2
     */
    private void initBtns(String title1, String title2) {
        firstButton = btnFactory(title1);
        secondButton = btnFactory(title2);

        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstButtonPressed();
            }
        });

        secondButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondButtonPressed();
            }
        });
    }
    protected void initBtns(String title1) {
        firstButton = btnFactory(title1);

        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstButtonPressed();
            }
        });

    }


    /**
     * Shared layout logic for menu UI using a header component (label or image).
     */
    private void layoutUIComponents(Component header) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        bottomPanel = new JPanel(new GridBagLayout());

        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        topPanel.add(header);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        bottomPanel.add(firstButton, gbc);

        gbc.gridy = 1;
        bottomPanel.add(secondButton, gbc);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    /**
     * Initialize menu UI with a text title.
     */
    protected void initializeUI(String titleText, String firstText, String secondText) {
        initBtns(firstText, secondText);

        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        layoutUIComponents(titleLabel);
    }

    /**
     * Initialize menu UI with an image instead of a text label.
     */
    protected void initializeUI(String title, String imagePath, String firstText, String secondText) {
        initBtns(firstText, secondText);

        ImageIcon img = new ImageIcon(getClass().getResource(imagePath));
        JLabel imageLabel = new JLabel(img);

        layoutUIComponents(imageLabel);
    }
    // to be overwrote in subclasses eg first button in main menu starts game
    // these methods will update controller which updates game's listener
    public void firstButtonPressed() {
    }

    public void secondButtonPressed() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Dynamically scales background
    }

}
