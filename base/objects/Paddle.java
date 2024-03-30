// 209379239 Tom Sasson
package base.objects;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.objects.Game;
import geometry.primitives.Point;
import geometry.primitives.Rectangle;
import geometry.primitives.Velocity;

import java.awt.Color;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Paddle implements Sprite, Collidable {
    //static values
    private static final int WIDTH = 800;
    //fields
    private Block block;
    private Rectangle rectangle;
    private biuoop.KeyboardSensor keyboard;
    private Color color;
    private double partLength;
    /**
     * Constructor method.
     * @param rectangle is the rectangle.
     * @param gui is the gui object to create keyboard sensor from.
     * @param color is the color of the paddle.
     */
    public Paddle(Rectangle rectangle, GUI gui, Color color) {
        //apply values
        this.rectangle = new Rectangle(rectangle.getUpperLeft(), rectangle.getWidth(), rectangle.getHeight());
        this.keyboard = gui.getKeyboardSensor();
        this.color = color;
        this.block =  new Block(this.rectangle);
        this.partLength = 20;
    }
    /**
     * Move the rectangle left.
     */
    public void moveLeft() {
        //if we reached the end
        if ((this.rectangle.getUpperRight().getX() - 5) < 0) {
            this.rectangle = new Rectangle(new Point((this.rectangle.getUpperLeft().getX() - 5 + WIDTH),
                    this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
            return;
        }
        //move the rectangle left
        this.rectangle = new Rectangle(new Point((this.rectangle.getUpperLeft().getX() - 5),
                this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
    }
    /**
     * Move the rectangle right.
     */
    public void moveRight() {
        //move the rectangle right
        this.rectangle = new Rectangle(new Point((this.rectangle.getUpperLeft().getX() + 5) % WIDTH,
                this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
    }
    /**
     * Check if keys were pressed, and move the rectangle accordingly.
     */
    @Override
    public void timePassed() {
        //check if keys were pressed, and move accordingly
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }
    /**
     * Draw the paddle on a given surface.
     * @param d is the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draw the paddle
        d.setColor(this.color);
        d.fillRectangle((int) this.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.getCollisionRectangle().getWidth(),
                (int) this.getCollisionRectangle().getHeight());
    }
    /**
     * Getter method of rectangle.
     * @return a copy of rectangle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        //return copy
        return new Rectangle(this.rectangle.getUpperLeft(), this.rectangle.getWidth(),
                this.rectangle.getHeight());
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //find the collision point coordinates
        double pointX = collisionPoint.getX();
        double pointY = collisionPoint.getY();
        //find the current velocity values
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        //find the minimum and maximum values of x and y
        double minX = getCollisionRectangle().getUpperLeft().getX();
        double maxX = getCollisionRectangle().getUpperLeft().getX() + getCollisionRectangle().getWidth();
        double minY = getCollisionRectangle().getUpperLeft().getY();
        double maxY = getCollisionRectangle().getUpperLeft().getY() + getCollisionRectangle().getHeight();
        //check if object is hit from below or above
        if (pointY == minY || pointY == maxY) {
            dy = -dy;
        }
        //check if object is hit from left or right
        if (pointX == minX || pointX == maxX) {
            dx = -dx;
        }
        //return new velocity
        return new Velocity(dx, dy);
    }
    /**
     * Add the paddle to a given game.
     * @param g is the game to add to.
     */
    @Override
    public void addToGame(Game g) {
        //add to both lists of game
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * find the impact angle of the ball after hitting the paddle.
     * @param partNumber int.
     * @return the impact angle.
     */
    public int findImpactAngle(int partNumber) {
        return -60 + (30 * partNumber);
    }
    /**
     * finds the part of the paddle that collision happened on him.
     * @param x the x coordinate of the collision point
     * @return the part of the paddle.
     */
    public int partNumber(double x) {
        for (int i = 0; i <= 4; i++) {
            if (x >= upperLeftX() + i * partLength && x <= upperLeftX() + (i + 1) * partLength) {
                return i;
            }
        }
        return -1;
    }
    /**
     * returns the upper left point of the paddle.
     * @return the upper left Point.
     */
    public double upperLeftX() {
        return this.block.getCollisionRectangle().getUpperLeft().getX();
    }
}
