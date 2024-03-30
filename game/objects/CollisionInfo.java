// 209379239 Tom Sasson
package game.objects;
import base.objects.Collidable;
import geometry.primitives.Point;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class CollisionInfo {
    //fields
    private Point collisionPoint;
    private Collidable collisionObject;
    /**
     * Constructor method.
     * @param collisionPoint is the point of collision.
     * @param collisionObject is the object we collide with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        //create a copy
        this.collisionPoint = collisionPoint;
        this.collisionObject =  collisionObject;
    }
    /**
     * Getter method.
     * @return copy of collision point.
     */
    public Point collisionPoint() {
        //return the collision point
        return this.collisionPoint;
    }
    /**
     * Getter method.
     * @return copy of collision object.
     */
    public Collidable collisionObject() {
        //return copy
        return this.collisionObject;
    }
}
