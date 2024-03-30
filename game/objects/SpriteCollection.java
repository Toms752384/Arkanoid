// 209379239 Tom Sasson
package game.objects;
import base.objects.Sprite;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class SpriteCollection {
    //fields
    private List<Sprite> spriteList;
    /**
     * Constructor method.
     */
    public SpriteCollection() {
        //initialize list
        this.spriteList = new ArrayList<>();
    }
    /**
     * Method that adds a given sprite to the list of the object.
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        //add sprite to list
        this.spriteList.add(s);
    }

    /**
     * Method that removes a given sprite from the list.
     * @param s sprite to remove.
     */
    public void removeSprite(Sprite s) {
        //remove sprite
        this.spriteList.remove(s);
    }
    /**
     *  call timePassed on all sprites.
     */
    public void notifyAllTimePassed() {
        //create copy of list and traverse it
        List<Sprite> sprites = new ArrayList<>(this.spriteList);
        for (Sprite sprite : sprites) {
            sprite.timePassed();
        }
    }
    /**
     * call drawOn(d) on all sprites.
     * @param d is the surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        //go on all the list and draw
        for (Sprite sprite : this.spriteList) {
            sprite.drawOn(d);
        }
    }

    /**
     * Getter method.
     * @return list of sprites.
     */
    public List<Sprite> getSpriteList() {
        //return the list
        return this.spriteList;
    }
}