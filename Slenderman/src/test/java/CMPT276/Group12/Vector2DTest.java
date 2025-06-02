package CMPT276.Group12;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CMPT276.Group12.Utility.Vector2D;

public class Vector2DTest {
    private Vector2D vector1;
    private Vector2D vector2;
    private Vector2D vector3;

    @BeforeEach
    void setUp() {
        vector1 = new Vector2D(1, 2);
        vector2 = new Vector2D(3, 4);
        vector3 = new Vector2D(1, 2);
    }

    @Test
    void testConstructor() {
        assertEquals(1, vector1.getX());
        assertEquals(2, vector1.getY());
    }

    @Test
    void testAdd() {
        vector1.add(vector2);
        assertEquals(4, vector1.getX());
        assertEquals(6, vector1.getY());
    }

    @Test
    void testSubtract() {
        vector1.subtract(vector2);
        assertEquals(-2, vector1.getX());
        assertEquals(-2, vector1.getY());
    }

    @Test
    void testMultiply() {
        vector1.multiply(2);
        assertEquals(2, vector1.getX());
        assertEquals(4, vector1.getY());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void testEquals() {
        assertTrue(vector1.equals(vector3));
        assertFalse(vector1.equals(vector2));

        assertFalse(vector1.equals(null));
        assertTrue(vector1.equals(vector1));

        Vector2D vector4 = new Vector2D(1, 3);
        assertFalse(vector1.equals(vector4));

        assertFalse(vector1.equals("not a vector"));
    }
}
