// 209379239 Tom Sasson
package geometry.primitives;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Point {
    //static values
    private static final double THRESHOLD = 0.00001;
    //fields
    private double x;
    private double y;
    /**
     * Constructor method.
     * @param x is the x value of the point.
     * @param y is the y value of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Calculate distance method, using the Pythagorean theorem.
     * @param other is the point to measure distance to.
     * @return the distance from the given point.
     */
    public double distance(Point other) {
        //calculate sides of the triangle
        double firstSide = other.getX() - this.x;
        double secondSide = other.getY() - this.y;

        //use the Pythagorean theorem
        double distance = Math.pow(firstSide, 2) + Math.pow(secondSide, 2);
        return Math.sqrt(distance);
    }
    /**
     * Check if points are equal in value.
     * @param other is the point to check.
     * @return true if equal, false if not.
     */
    public boolean equals(Point other) {
        //check if other is null
        if (other == null) {
            return false;
        }
        //check the values and return the boolean value accordingly.
        return (Math.abs(other.getX() - this.x) < THRESHOLD)
                && (Math.abs(other.getY() - this.y) < THRESHOLD);
    }
    /**
     * Access method.
     * @return x value of the point
     */
    public double getX() {
        return this.x;
    }
    /**
     * Access method.
     * @return y value of the point
     */
    public double getY() {
        return this.y;
    }
}