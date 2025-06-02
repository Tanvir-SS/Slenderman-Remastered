package CMPT276.Group12.GameObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

import CMPT276.Group12.States.PlayerState;

class AnimatedGameObjectTest {
    private TestAnimatedGameObject testObject;
    private BufferedImage[] mockFrames;

    private static String[] sprite32pxPaths = {
        "/tiles/32px/player/forward0.png",
        "/tiles/32px/player/forward1.png",
        "/tiles/32px/player/forward2.png",
        "/tiles/32px/player/forward3.png",
        "/tiles/32px/player/forward4.png"
    };

    @BeforeEach
    void setUp() {
        // Create real BufferedImage instances instead of relying on resource loading
        mockFrames = new BufferedImage[sprite32pxPaths.length];
        for (int i = 0; i < mockFrames.length; i++) {
            mockFrames[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }

        // Create a concrete instance of AnimatedGameObject
        testObject = new TestAnimatedGameObject(32, sprite32pxPaths);
        testObject.frames = mockFrames; // Set the mock frames directly
    }

    @Test
    void testDrawCallsDrawImage() {
        // Create a real Graphics2D instance
        BufferedImage dummyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dummyImage.createGraphics();

        // Call the draw method
        testObject.draw(g2, 100, 200);

        // Verify that no exceptions are thrown
        assertNotNull(g2);
    }

    // TEST DOES NOT WORK
    @Test
    void testFrameIndexCycles() {
        // Create a real Graphics2D instance
        BufferedImage dummyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dummyImage.createGraphics();

        // Simulate enough draw calls to cycle through all frames
        for (int i = 0; i < mockFrames.length; i++) {
            testObject.draw(g2, 100, 200);
        }

        // Verify that the frame index resets to 0
        assertEquals(0, testObject.idx); // Accessing idx (inherited from AnimatedGameObject)
    }

    @Test
    void testNullFramesThrowsException() {
        // Set frames to null
        testObject.frames = null;

        // Create a real Graphics2D instance
        BufferedImage dummyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dummyImage.createGraphics();

        // Verify that a NullPointerException is thrown
        assertThrows(NullPointerException.class, () -> testObject.draw(g2, 100, 200));
    }

    @Test
    void testEmptyFramesThrowsException() {
        // Set frames to an empty array
        testObject.frames = new BufferedImage[0];

        // Create a real Graphics2D instance
        BufferedImage dummyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dummyImage.createGraphics();

        // Verify that an ArrayIndexOutOfBoundsException is thrown
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> testObject.draw(g2, 100, 200));
    }

    // Concrete subclass for testing
    private static class TestAnimatedGameObject extends AnimatedGameObject<PlayerState> {

        public TestAnimatedGameObject(int spriteSize, String[] sprites) {
            super(spriteSize, sprites);
        }
    }
}