// 209379239 Tom Sasson
package geometry.primitives;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Velocity {
    //static values
    private static final int WIDHT = 200, HEIGHT = 200;
    //fields
    private double dx;
    private double dy;
    /**
     * Constructor method of velocity class.
     * @param dx is the change in the x-axis.
     * @param dy is the change in the y-axis.
     */
    public Velocity(double dx, double dy) {
        //apply values
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * getter method that returns dx value.
     * @return dx value.
     */
    public double getDx() {
        //return dx value
        return dx;
    }
    /**
     * getter method that returns dy value.
     * @return dy value.
     */
    public double getDy() {
        //return dy value
        return dy;
    }
    /**
     * Method that takes a point with position (x,y) and returns a new point
     * with position (x+dx, y+dy).
     * @param p is the point to change.
     * @return new point after change.
     */
    public Point applyToPoint(Point p) {
        //apply values and return it
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
    /**
     * Method that changes dx of velocity.
     * @param dx is the new value.
     */
    public void setDx(double dx) {
        //change dx
        this.dx = dx;
    }
    /**
     * Method that changes dy of velocity.
     * @param dy is the new value.
     */
    public void setDy(double dy) {
        //change dx
        this.dy = dy;
    }
    /**
     * chenges the degree of a velocity by the rules of "More fun paddle" from ass 3.
     * @param degree the degree we want to apply.
     * @return a new Velocity created with degree, while kipping her size equal..
     */
    public Velocity changeDegree(int degree) {
        return fromAngleAndSpeed(degree, Math.sqrt(dx * dx + dy * dy));
    }
    /**
     * Method that takes an angle and speed, and calculates the change in velocity.
     * @param angle is the change in degrees.
     * @param speed is the change in units.
     * @return new velocity after change.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //calculate using the formula
        double angleRadians = Math.toRadians(angle);
        double dx = speed * Math.sin(angleRadians);
        double dy = speed * Math.cos(angleRadians);
        return new Velocity(dx, dy);
    }
}