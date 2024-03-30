//209379239 Tom Sasson
package base.objects;
import game.objects.Game;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class BlockRemover implements HitListener {
    //members
    private Game game;
    private Counter remainingBlocks;
    /**
     * Constructor.
     * @param game game.
     * @param remainingBlocks counter.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        //apply values
        this.game = game;
        this.remainingBlocks = remainingBlocks;
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
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        this.remainingBlocks.decrease(1);
        hitter.setColor(beingHit.getColor());
    }
}
