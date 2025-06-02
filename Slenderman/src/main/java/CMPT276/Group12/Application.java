package CMPT276.Group12;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import CMPT276.Group12.UI.*;
import CMPT276.Group12.Utility.Time;
import CMPT276.Group12.Mechanics.*;
import CMPT276.Group12.States.ApplicationState;
import CMPT276.Group12.Actions.InputAction;
import CMPT276.Group12.Actions.UIAction;

public class Application extends JFrame implements Runnable {

    private ApplicationState state;
    private Thread thread;
    private Controller controller;

    // Panels
    private MainMenu mainMenu;
    private PauseMenu pauseMenu;
    private GameOverMenu gameOverMenu;

    private Game game;
    private PlayerHUD hud;
    private Time timer = new Time(); // Tanvir - added a new Time class

    public Application() {
        setFocusable(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        controller = new Controller(this);
        addKeyListener(controller);

        mainMenu = new MainMenu(controller);
        pauseMenu = new PauseMenu(controller);

        state = ApplicationState.MENU;
        add(mainMenu);
    }

    /**
     * Main Program Loop
     */
    @Override
    public void run() {
        while (thread != null) {
            if (state == ApplicationState.MENU) {
                // todo update cursor
                mainMenu.repaint();
            }

            if (state == ApplicationState.PAUSED) {
                // todo update cursor
                pauseMenu.repaint();
            }

            if (state == ApplicationState.GAME) {
                if (game.endGame())
                    changeState(ApplicationState.GAME_OVER);
                else {
                    hud.setPages(game.getPages(), game.getStage());
                    hud.setScore(game.getScore());
                    hud.repaint();
                    game.repaint();
                }
            }
        }
    }

    /**
     * Start this thread
     */
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Change state to newState; controls which panels are shown
     * 
     * @param newState states enum
     */
    // todo enforce size on state transition
    private void changeState(ApplicationState newState) {
        getContentPane().removeAll(); // Ensure old UI is fully cleared
        if (newState == ApplicationState.MENU) {
            if (state == ApplicationState.GAME) {
                // quit game to main menu
                remove(game);
                remove(hud);
                add(mainMenu);
            } else if (state == ApplicationState.PAUSED) {
                remove(pauseMenu);
                add(mainMenu);
            } else if (state == ApplicationState.GAME_OVER) {
                remove(gameOverMenu);
                add(mainMenu);
            }

        } else if (newState == ApplicationState.PAUSED) {
            if (state == ApplicationState.GAME) {
                // pause game
                remove(game);
                remove(hud);
                add(pauseMenu);
                timer.stopTime(); // Tanvir - added stopTimer(); to pause the game timer - automatically stores
                                  // time paused
            }
        } else if (newState == ApplicationState.GAME) {
            if (state == ApplicationState.MENU) {
                // start new game
                remove(mainMenu);
                game = new Game();
                hud = new PlayerHUD(controller, timer); // Tanvir - added controller as Param for PlayerHUD to change
                                                        // game to
                // pause
                add(hud);
                add(game);
            } else if (state == ApplicationState.PAUSED) {
                // resume game
                timer.startTime(); // Tanvir - startTimer() resumes time from when it was paused
                remove(pauseMenu);
                add(hud);
                add(game);
            }
        } else if (newState == ApplicationState.GAME_OVER && state == ApplicationState.GAME) {
            // should only be when `state == states.GAME`
            remove(game);
            remove(hud);
            gameOverMenu = new GameOverMenu(controller, game.getScore(), getWidth(), getHeight(),
                    timer.getElaspedTime());
            add(gameOverMenu);
        }
        state = newState;
        revalidate(); // forces UI layout update
        repaint(); // Redraws the frame
        pack();
    }

    /**
     * Receive InputActions, i.e. keyboard inputs to perform actions in Game
     * 
     * @param action InputAction describing keystrokes
     */
    public void inputAction(InputAction action) {
        // todo add UI nagivation
        if (action == InputAction.UP) {
            if (state == ApplicationState.GAME) {
                game.movePlayerUp();
            }
        }

        else if (action == InputAction.RIGHT) {
            if (state == ApplicationState.GAME) {
                game.movePlayerRight();
            }
        }

        else if (action == InputAction.DOWN) {
            if (state == ApplicationState.GAME) {
                game.movePlayerDown();
            }
        }

        else if (action == InputAction.LEFT) {
            if (state == ApplicationState.GAME) {
                game.movePlayerLeft();
            }
        }

        else if (action == InputAction.SELECT) {
            // todo in UI this should select the currently highlighted menu button
        }

        else if (action == InputAction.ESCAPE) {
            if (state == ApplicationState.MENU) {
                System.exit(0);
            } else if (state == ApplicationState.GAME) {
                changeState(ApplicationState.PAUSED);
            } else if (state == ApplicationState.PAUSED) {
                changeState(ApplicationState.GAME);
            }
        }
    }

    /**
     * Receive and perform actions based on UIActions from UI classes
     * 
     * @param action UIAction enum
     */
    public void uiAction(UIAction action) {

        if (action == UIAction.GAME) {
            changeState(ApplicationState.GAME);
        }

        else if (action == UIAction.EXIT) {
            System.exit(0);
        }

        else if (action == UIAction.MENU) {
            changeState(ApplicationState.MENU);
        }

        else if (action == UIAction.PAUSE) {
            changeState(ApplicationState.PAUSED);
        }
    }

    /**
     * Can be called to get a reference to this Game's Controller
     * 
     * @return Controller object use by this Game
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Returns the current state of the app
     * 
     * @return ApplicationState enumerator
     */
    public ApplicationState getApplicationState() {
        return state;
    }

    /**
     * Returns the game if initiated, null otherwise
     * 
     * @return Game or null
     */
    public Game getGame() {
        if (getApplicationState() == ApplicationState.GAME)
            return game;
        return null;
    }
}
