//209379239 Tom Sasson
package base.objects;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class ScoreTrackingListener implements HitListener {
    //members
    private Counter currentScore;
    /**
     * Constructor.
     * @param scoreCounter is the counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    /**
     * Update score when hit.
     * @param beingHit is the block.
     * @param hitter is the ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
       //add points to score
        this.currentScore.increase(5);
    }
}
