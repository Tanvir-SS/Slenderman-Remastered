package CMPT276.Group12.UI;
import java.awt.Color;
/*
 * PlayerHUDDimensions takes care of the data clump in PlayerHUD by 
 * holding the dimensions required for its formatting
 */
public class PlayerHUDDimensions {
    private static final Color TEXT_COLOR;
    private static final Color SCORE_COLOR;
    private static final Color PAGES_COLOR;
    private static final Color BUTTON_COLOR;

    private static final String PAGES_FORMAT;
    private static final String SCORE_FORMAT;

    private final int WIDTH;
    private final int HEIGHT;
    private final int PAUSE_WIDTH;
    private final int PAUSE_HEIGHT;

    // Static initialization block
    static {
        TEXT_COLOR = Color.WHITE;
        SCORE_COLOR = Color.ORANGE;  
        PAGES_COLOR = Color.YELLOW;
        BUTTON_COLOR = Color.BLACK;

        PAGES_FORMAT = "Keys: %d / %d";
        SCORE_FORMAT = "Score: %d";
    }

    // Constructor for instance-specific fields
    public PlayerHUDDimensions(int width, int height, int pauseW, int pauseH) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.PAUSE_WIDTH = pauseW;
        this.PAUSE_HEIGHT = pauseH;
    }
    public static Color getTextColor() {
        return TEXT_COLOR;
    }

    public static Color getScoreColor() {
        return SCORE_COLOR;
    }

    public static Color getPagesColor() {
        return PAGES_COLOR;
    }

    public static Color getButtonColor() {
        return BUTTON_COLOR;
    }

    public static String getPagesFormat() {
        return PAGES_FORMAT;
    }

    public static String getScoreFormat() {
        return SCORE_FORMAT;
    }

    // Instance getters
    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getPauseWidth() {
        return PAUSE_WIDTH;
    }

    public int getPauseHeight() {
        return PAUSE_HEIGHT;
    }
}
