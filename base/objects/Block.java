// 209379239 Tom Sasson
package base.objects;
import biuoop.DrawSurface;
import game.objects.Game;
import geometry.primitives.Point;
import geometry.primitives.Rectangle;
import geometry.primitives.Velocity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Block implements Collidable, Sprite, HitNotifier {
    //fields
    private final Rectangle rectangle;
    private final List<HitListener> hitListeners;
    private Color color = Color.BLACK; //default color
    /**
     * Constructor method.
     * @param rectangle is the rectangle to make a copy from.
     */
    public Block(Rectangle rectangle) {
        this.rectangle = new Rectangle(rectangle.getUpperLeft(), rectangle.getWidth(), rectangle.getHeight());
        this.hitListeners = new ArrayList<>();
    }
    /**
     * Method that returns a copy of the object we collided with.
     * @return copy of the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        //return a copy of the rectangle
        return this.rectangle;
    }
    /**
     * using the collision point and the current velocity, return the new velocity.
     * @param collisionPoint the point of collision.
     * @param currentVelocity the velocity the object is hit with.
     * @return new velocity after collision.
     */
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
        //check if color matches
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        //return new velocity
        return new Velocity(dx, dy);
    }
    /**
     * Method to draw object on surface.
     * @param d is the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draw the block
        d.setColor(this.color);
        d.fillRectangle((int) this.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.getCollisionRectangle().getWidth(),
                (int) this.getCollisionRectangle().getHeight());
    }
    /**
     * Method to let the block know time has passed, and do something.
     */
    @Override
    public void timePassed() {
        //do nothing
        //for now
    }
    /**
     * Method that add the block to a given game object.
     * @param g is the game to add to.
     */
    @Override
    public void addToGame(Game g) {
        //add to both lists
        g.addSprite(this);
        g.addCollidable(this);
    }
    @Override
    public void addHitListener(HitListener hl) {
        //add to list
        this.hitListeners.add(hl);
    }
    @Override
    public void removeHitListener(HitListener hl) {
        //remove from list
        this.hitListeners.remove(hl);
    }
    /**
     * Getter method.
     * @return color.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * Setter method to set color to block.
     * @param color is the color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Method that checks if the block matches the color of a given ball.
     * @param ball is the ball we check.
     * @return boolean.
     */
    public boolean ballColorMatch(Ball ball) {
        //check the color
        return ball.getColor().equals(this.color);
    }
    /**
     * Method that removes the block from a given game object.
     * @param game is the game to add to.
     */
    public void removeFromGame(Game game) {
        //remove the block from the game
        game.removeSprite(this);
        game.removeCollidable(this);
    }
    /**
     * Method that receives a ball that hit the block and notifies the listeners of the hit.
     * @param hitter is the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
