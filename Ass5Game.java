// 209379239 Tom Sasson
import game.objects.Game;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Ass5Game {
    //static values
    private static final int COUNTER = 42, BALLS = 3;
    /**
     * main method.
     * @param args is the given arguments.
     */
    public static void main(String[] args) {
        Game game = new Game(COUNTER, BALLS);
        game.initialize();
        game.run();
    }
}
