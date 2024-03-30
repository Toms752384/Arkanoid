//209379239 Tom Sasson
package base.objects;
import game.objects.Game;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class BallRemover implements HitListener {
    //members
    private Game game;
    private Counter remainingBalls;
    /**
     * Constructor.
     * @param game game.
     * @param remainingBalls counter.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        //apply values
        this.game = game;
        this.remainingBalls = remainingBalls;
    }
    /**
     * Blocks that are hit should be removed from the game.
     * @param beingHit is the block.
     * @param hitter is the ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        /*
        remove objects and update counter
         */
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
