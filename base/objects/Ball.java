// 209379239 Tom Sasson

package base.objects;
import biuoop.DrawSurface;
import game.objects.Game;
import game.objects.GameEnvironment;
import geometry.primitives.Line;
import geometry.primitives.Point;
import geometry.primitives.Velocity;
import java.awt.Color;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Ball implements Sprite {
    //static values
    private static final double THRESHOLD = 0.00001;
    private static final int WIDHT = 200, HEIGHT = 200;
    //fields
    private Point center;
    private double r;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;
    /**
     * Constructor method of a ball.
     * @param center is the center of the ball.
     * @param r is the radius.
     * @param color is the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        //apply values
        this.center = center;
        this.r = r;
        this.color = color;
    }
    /**
     * Constructor method of a ball.
     * @param x is the x value center of the ball.
     * @param y is the y value center of the ball.
     * @param r is the radius.
     * @param color is the color of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        //apply values
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
    }
    /**
     * Access method.
     * @return x value of the center.
     */
    public int getX() {
        //return to x value of the center
        return (int) this.center.getX();
    }
    /**
     * Access method.
     * @return y value of the center.
     */
    public int getY() {
        //return to y value of the center
        return (int) this.center.getY();
    }
    /**
     * Access method, that calculates the size of the circle.
     * @return y value of the center.
     */
    public double getSize() {
        //return radius
        return this.r;
    }
    /**
     * Access method.
     * @return radius of the ball.
     */
    public double getRadious() {
        //return radious value
        return this.r;
    }
    /**
     * Access method.
     * @return color of the circle.
     */
    public java.awt.Color getColor() {
        //return the color
        return this.color;
    }
    /**
     * Access method, returns a copy of game environment.
     * @return copy of game environment.
     */
    public GameEnvironment getGameEnvironment() {
        //return game environment
        return this.gameEnvironment;
    }
    /**
     * Setting method, sets game environment reference,.
     * @param gameEnvironment is a reference to game.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        //apply value
        this.gameEnvironment = gameEnvironment;
    }
    /**
     * Remove from given game.
     * @param game is the game to remove from.
     */
    public void removeFromGame(Game game) {
        //remove from the given game
        game.removeSprite(this);
    }
    /**
     * method that draws a circle.
     * @param surface is the surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        //set color and draw the circle
        surface.setColor(this.color);
        surface.fillCircle(getX(), getY(), (int) r);
    }
    /**
     * Method to let the ball know time has passed, and move a step.
     */
    @Override
    public void timePassed() {
        //move the ball
        this.moveOneStep();
    }
    /**
     * Method to add the ball to a given game list of sprites.
     * @param g is the game to add to.
     */
    @Override
    public void addToGame(Game g) {
        //add the ball to the game object
        g.addSprite(this);
    }
    /**
     * method that sets velocity.
     * @param v is the velocity to set.
     */
    public void setVelocity(Velocity v) {
        //apply values
        this.velocity = v;
    }
    /**
     * method that sets velocity.
     * @param dx is change in x-axis.
     * @param dy is change in y-axis.
     */
    public void setVelocity(double dx, double dy) {
        //apply values to velocity
        this.velocity = new Velocity(dx, dy);
    }
    /**
     * Setter method.
     * @param color is the new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Access method.
     * @return velocity of the ball.
     */
    public Velocity getVelocity() {
        //return to velocity of the ball
        return this.velocity;
    }
    /**
     * method that sets velocity to the center of the ball.
     */
    public void moveOneStep() {
        //create trajectory line
        Line trajectory = new Line(new Point(this.center.getX(), this.center.getY()),
                            new Point(this.center.getX() + this.getVelocity().getDx(),
                            this.center.getY() + this.getVelocity().getDy()));
        //check if there is an intersection
        if (this.gameEnvironment.getClosestCollision(trajectory) == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            //create center near the intersection point
            this.center = findClosePointToIntersection(new Line(trajectory.start(),
                    this.gameEnvironment.getClosestCollision(trajectory).collisionPoint()));
           //find the new velocity and apply
            Velocity newVelocity = this.gameEnvironment.getClosestCollision(trajectory).collisionObject().
                    hit(this, this.gameEnvironment.getClosestCollision(trajectory).collisionPoint(), this.velocity);
            this.setVelocity(newVelocity);
        }
    }
    /**
     * method that sets velocity to the center of the ball, according to given borders.
     * @param upperBorder is the upper border of the ball.
     * @param lowerBorder is the lower border of the ball.
     * @param rightBorder is the right border of the ball.
     * @param leftBorder is the left border of the ball.
     */
    public void moveOneStep(double upperBorder, double lowerBorder, double rightBorder, double leftBorder) {
        //calculate the new center after applying velocity
        Point newCenter = this.getVelocity().applyToPoint(this.center);

        //check for collisions of the left or right borders
        if (newCenter.getX() + r >= rightBorder || newCenter.getX() - r <= leftBorder) {
            setVelocity(-this.velocity.getDx(), this.velocity.getDy());
        }

        //check for collisions of the upper or bottom borders
        if (newCenter.getY() + r >= lowerBorder || newCenter.getY() - r <= upperBorder) {
            setVelocity(this.velocity.getDx(), -this.velocity.getDy());
        }

        // Apply the updated velocity to the center
        this.center = this.getVelocity().applyToPoint(this.center);
    }
    /**
     * method that sets velocity to the center of the ball, according to given borders of a rectangle.
     * @param minX is the upper left x value of rectangle.
     * @param minY is the upper left y value of rectangle.
     * @param maxX is the down right x value of rectangle.
     * @param maxY is the down right y value of rectangle.
     */
    public void moveOneStepWhiteSpace(double minX, double minY, double maxX, double maxY) {
        //check for collision from the left side of the rectangle
        if ((this.center.getX() + r >= minX) && (this.center.getX() <= minX)
                && ((this.center.getY() >= minY) && (this.center.getY() <= maxY))) {
            setVelocity(-this.velocity.getDx(), this.velocity.getDy());
            //check precision
            double newX = this.center.getX() + r - minX;
            this.center = new Point(this.center.getX() - newX, this.center.getY());
        }

        //check for collision from the right side of the rectangle
        if ((this.center.getX() - r <= maxX) && (this.center.getX() >= maxX)
                && ((this.center.getY() >= minY) && (this.center.getY() <= maxY))) {
            setVelocity(-this.velocity.getDx(), this.velocity.getDy());
            //check precision
            double newX = maxX - this.center.getX() + r;
            this.center = new Point(this.center.getX() + newX, this.center.getY());
        }

        //check for collision from the upper side of the rectangle
        if ((this.center.getY() + r >= minY) && (this.center.getY() <= minY)
                && this.center.getX() >= minX && (this.center.getX() <= maxX)) {
            setVelocity(this.velocity.getDx(), -this.velocity.getDy());
            //check precision
            double newY = this.center.getY() + r - minY;
            this.center = new Point(this.center.getX(), this.center.getY() - newY);
        }

        //check for collision from the lower side of the rectangle
        if ((this.center.getY() - r <= maxY) && (this.center.getY() >= maxY)
                && this.center.getX() >= minX && (this.center.getX() <= maxX)) {
            setVelocity(this.velocity.getDx(), -this.velocity.getDy());
            //check precision
            double newY = maxY - this.center.getY() + r;
            this.center = new Point(this.center.getX(), this.center.getY() + newY);
        }

        //apply the updated velocity to the center
        this.center = this.getVelocity().applyToPoint(this.center);
    }
    /**
     * method that sets velocity to the center of the ball, according to given borders of a rectangle.
     * @param minX is the upper left x value of rectangle.
     * @param minY is the upper left y value of rectangle.
     * @param maxX is the down right x value of rectangle.
     * @param maxY is the down right y value of rectangle.
     */
    public void moveOneStepAdvanced(double minX, double minY, double maxX, double maxY) {
        //check if x is inside the right border and the circle isn't
        if (this.center.getX() + r >= maxX) {
            setVelocity(-this.velocity.getDx(), this.velocity.getDy());
            double correctX = this.center.getX() + r - maxX;
            this.center = new Point(this.getX() - correctX, this.center.getY());
        }
        //check if x is inside the left border and the circle isn't
        if (this.center.getX() - r <= minX) {
            setVelocity(-this.velocity.getDx(), this.velocity.getDy());
            double correctX = minX - this.center.getX() + r;
            this.center = new Point(this.getX() + correctX, this.center.getY());
        }
        //check if y is inside the lower border and the circle isn't
        if (this.center.getY() + r >= maxY) {
            setVelocity(this.velocity.getDx(), -this.velocity.getDy());
            double correctY = this.center.getY() + r - maxY;
            this.center = new Point(this.getX(), this.center.getY() - correctY);
        }
        //check if y is inside the upper border and the circle isn't
        if (this.center.getY() - r <= minY) {
            setVelocity(this.velocity.getDx(), -this.velocity.getDy());
            double correctY = minY - this.center.getY() + r;
            this.center = new Point(this.getX(), this.center.getY() + correctY);
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }
    /**
     * method that sets velocity to the center of the ball, according to given borders of a rectangle,
     * specific to the vertexes.
     * @param minX is the upper left x value of rectangle.
     * @param minY is the upper left y value of rectangle.
     * @param maxX is the down right x value of rectangle.
     * @param maxY is the down right y value of rectangle.
     */
    public void moveOneStepCorners(double minX, double minY, double maxX, double maxY) {
        //find the vertexes
        Point topLeft = new Point(minX, minY);
        Point topRight = new Point(maxX, minY);
        Point lowLeft = new Point(minX, maxY);
        Point lowRight = new Point(maxX, maxY);
        //check for collisions top left
        if (this.center.distance(topLeft) < r) {
            setVelocity(-this.velocity.getDx(), -this.velocity.getDy());
        }
        //check for collision top right
        if (this.center.distance(topRight) < r) {
            setVelocity(-this.velocity.getDx(), -this.velocity.getDy());
        }
        //check for collision lower left
        if (this.center.distance(lowLeft) < r) {
            setVelocity(-this.velocity.getDx(), -this.velocity.getDy());
        }
        //check for collision lower right
        if (this.center.distance(lowRight) < r) {
            setVelocity(-this.velocity.getDx(), -this.velocity.getDy());
        }
        //apply the updated velocity to the center
        this.center = this.getVelocity().applyToPoint(this.center);
    }
    /**
     * Method that checks if a value is within range of
     * 2 other values.
     * @param checkValue is the number we check.
     * @param max is the max value.
     * @param min is the min value.
     * @param threshold is the value that helps check
     * accuracy with doubles.
     * @return true if in range, false if not.
     */
    private boolean withinRange(double checkValue, double max, double min, double threshold) {
        //check if within range using the threshold
        if ((checkValue >= min - threshold) && (checkValue <= max + threshold)) {
            return true;
        }
        return false;
    }
    /**
     * Method that finds a point close to the end of a given line.
     * @param line is the line to check.
     * @return a point in distance of radius from the end.
     */
    private Point findClosePointToIntersection(Line line) {
        //calculate ratio of radius to total distance
        double ratio = this.getRadious() / line.length();
        //find new coordiantes
        double newX = line.end().getX() - ratio * (line.end().getX() - line.start().getX());
        double newY = line.end().getY() - ratio * (line.end().getY() - line.start().getY());
        //return new point close to end of line
        return new Point(newX, newY);
    }
}
