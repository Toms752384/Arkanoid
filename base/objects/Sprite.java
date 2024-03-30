// 209379239 Tom Sasson
package base.objects;
import biuoop.DrawSurface;
import game.objects.Game;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public interface Sprite {
    /**
     * Method to draw object on surface.
     * @param d is the surface.
     */
    void drawOn(DrawSurface d);
    /**
     * Method to let the object know time has passed, and do something.
     */
    void timePassed();
    /**
     * Method that adds the object to the game.
     * @param g is the game to add to.
     */
    void addToGame(Game g);
}
