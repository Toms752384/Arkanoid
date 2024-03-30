//209379239 Tom Sasson
package base.objects;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit is the block.
     * @param hitter is the ball.
     */
    void hitEvent(Block beingHit, Ball hitter);
}

