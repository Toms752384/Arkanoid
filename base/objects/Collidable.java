// 209379239 Tom Sasson
package base.objects;
import geometry.primitives.Point;
import geometry.primitives.Rectangle;
import geometry.primitives.Velocity;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return collision shape.
     */
    Rectangle getCollisionRectangle();
    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * @param collisionPoint the point of collision.
     * @param currentVelocity the velocity the object is hit with.
     * @param hitter is the ball that is hit.
     * @return the new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}

