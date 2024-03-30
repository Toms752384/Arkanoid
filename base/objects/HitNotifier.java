//209379239 Tom Sasson
package base.objects;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public interface HitNotifier {
    /**
     * Add hl as a listener to hit events list.
     * @param hl a hitListener object.
     */
    void addHitListener(HitListener hl);
    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl a hitListener object.
     */
    void removeHitListener(HitListener hl);
}
