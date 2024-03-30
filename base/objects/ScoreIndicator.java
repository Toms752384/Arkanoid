// 209379239 Tom Sasson
package base.objects;
import biuoop.DrawSurface;
import game.objects.Game;
import java.awt.Color;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class ScoreIndicator implements Sprite {
    //members
    private Counter score = new Counter(0);
    /**
     * Constructor.
     * @param score is the counter to add.
     */
    public ScoreIndicator(Counter score) {
        //apply
        this.score = score;
    }
    /**
     * Method to draw object on surface.
     * @param d is the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draw text
        d.setColor(Color.BLACK);
        d.drawText(350, 20, "Score: " + score.getValue(), 16);
    }

    /**
     * Method to let the object know time has passed, and do something.
     */
    @Override
    public void timePassed() {

    }

    /**
     * Method that adds the object to the game.
     * @param g is the game to add to.
     */
    @Override
    public void addToGame(Game g) {
        //add to sprite list of game
        g.addSprite(this);
    }
}
