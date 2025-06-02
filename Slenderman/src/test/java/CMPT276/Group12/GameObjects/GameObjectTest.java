package CMPT276.Group12.GameObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {
    private TestGameObject testObject;
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
        // Create mock frames
        mockFrames = new BufferedImage[sprite32pxPaths.length];
        for (int i = 0; i < mockFrames.length; i++) {
            mockFrames[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }

        // Create a concrete instance of GameObject
        testObject = new TestGameObject(32, sprite32pxPaths);
        testObject.frames = mockFrames; // Set the mock frames directly
    }

    @Test
    void testDraw() {
        // Create a real Graphics2D instance
        BufferedImage dummyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dummyImage.createGraphics();

        // Call the draw method
        testObject.draw(g2, 100, 200);

        // Verify that no exceptions are thrown
        assertNotNull(g2);
    }

    @Test
    void testSetSpriteFramesHandlesIOException() {
        // Create a GameObject with invalid sprite paths
        String[] invalidSpritePaths = {"/invalid/path1.png", "/invalid/path2.png"};
        TestGameObject testObjectWithInvalidPaths = new TestGameObject(32, invalidSpritePaths);

        // Verify that the frames array is still initialized but contains null values
        for (BufferedImage frame : testObjectWithInvalidPaths.frames) {
            assertNull(frame); // Frames should be null because loading failed
        }
    }

    @Test
    void testSetSpriteFrameValidIndex() {
        // Set a valid frame index
        testObject.setSpriteFrame(1);

        // Verify that the frame index is updated
        assertEquals(1, testObject.idx);
    }

    @Test
    void testSetSpriteFrameInvalidIndex() {
        // Try to set an invalid frame index (negative index)
        testObject.setSpriteFrame(-1);

        // Verify that the frame index is not updated
        assertEquals(0, testObject.idx); // idx should remain at its initial value

        // Try to set an invalid frame index (index out of bounds)
        testObject.setSpriteFrame(mockFrames.length);

        // Verify that the frame index is not updated
        assertEquals(0, testObject.idx); // idx should remain unchanged
    }

    // Concrete subclass for testing
    private static class TestGameObject extends GameObject {
        public TestGameObject(int spriteSize, String[] sprites) {
            super(spriteSize, sprites);
        }
    }
}