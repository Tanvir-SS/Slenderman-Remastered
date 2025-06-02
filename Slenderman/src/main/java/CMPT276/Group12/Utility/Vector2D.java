package CMPT276.Group12.Utility;

/**
 * The Vector2D class represents a 2-dimensional vector with integer components.
 */
public class Vector2D {
    private int x;
    private int y;

    /**
     * Constructs a new Vector2D with the specified x and y components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     */
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new Vector2D that is a copy of the specified vector.
     *
     * @param other the vector to copy
     */
    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Returns the x component of the vector.
     *
     * @return the x component of the vector
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y component of the vector.
     *
     * @return the y component of the vector
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x component of the vector.
     *
     * @param x the new x component of the vector
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y component of the vector.
     *
     * @param y the new y component of the vector
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Adds the specified vector to this vector.
     *
     * @param other the vector to add
     */
    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Subtracts the specified vector from this vector.
     *
     * @param other the vector to subtract
     */
    public void subtract(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    /**
     * Multiplies this vector by the specified scalar.
     *
     * @param scalar the scalar to multiply by
     */
    public void multiply(int scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Vector2D vector2D = (Vector2D) obj;
        return x == vector2D.x && y == vector2D.y;
    }
}
