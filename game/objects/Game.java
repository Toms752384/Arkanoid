// 209379239 Tom Sasson
package game.objects;
import base.objects.Ball;
import base.objects.BallRemover;
import base.objects.Block;
import base.objects.BlockRemover;
import base.objects.Collidable;
import base.objects.Counter;
import base.objects.HitListener;
import base.objects.Paddle;
import base.objects.ScoreIndicator;
import base.objects.ScoreTrackingListener;
import base.objects.Sprite;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import geometry.primitives.Point;
import geometry.primitives.Rectangle;
import geometry.primitives.Velocity;
import java.awt.Color;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Game {
    //static values
    private static final int WIDTH = 800, HEIGHT = 600, CENTER = 500, BLOCK_WIDTH = 50, BLOCK_HEIGHT = 30;
    private static final int FIRST_ROW = 12, SECOND_ROW = 10, THIRD_ROW = 8,
            FORTH_ROW = 6, FIFTH_ROW = 4, LAST_ROW = 2;
    //members
    private GUI gui;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainingBlocks = new Counter(0);
    private Counter remainingBalls = new Counter(0);
    private Counter score = new Counter(0);
    /**
     * Constructor method.
     */
    public Game() {
        //initialize fields
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }
    /**
     * Constructor method.
     * @param blockCounter is the counter of blocks.
     * @param ballCounter is the counter of balls.
     */
    public Game(int blockCounter, int ballCounter) {
        //initialize fields
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(blockCounter);
        this.remainingBalls = new Counter(ballCounter);
    }
    /**
     * Method that adds a collidable to a list.
     * @param c the collidable to add.
     */
    public void addCollidable(Collidable c) {
        //add to list
        this.environment.addCollidable(c);
    }
    /**
     * Method that adds a sprite to a list.
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        //add to list
        this.sprites.addSprite(s);
    }
    /**
     * Method that removes a collidable from a list.
     * @param c the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        //remove from list
        this.environment.removeCollidable(c);
    }
    /**
     * Method that removes a sprite from a list.
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        //remove from list
        this.sprites.removeSprite(s);
    }
    /**
     * Initialize a new game: create the Blocks and balls, and adds them to the game.
     */
    public void initialize() {
        //create gui
        GUI gui = new GUI("Arkanoid", WIDTH, HEIGHT);
        this.gui = gui;
        //initialize border blocks
        initializeBorderBlocks();
        initializeDeathRegion();
        //initialize balls
        initializeBalls();
        //initialize game blocks
        initializeGameBlocks();
        //initialize paddle
        Paddle paddle = new Paddle(new Rectangle(new Point(500, 551), 100, 23), gui, Color.ORANGE);
        paddle.addToGame(this);
        //initialize score indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(score);
        scoreIndicator.addToGame(this);
    }
    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                Sleeper sleeper = new Sleeper();
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (this.remainingBlocks.getValue() == 0 || this.remainingBalls.getValue() == 0) {
                break;
            }
        }
        if (this.remainingBlocks.getValue() == 0) {
            this.remainingBalls.increase(100);
            System.out.println("Good job!");
        } else {
            System.out.println("Sucks to be you...");
        }
        gui.close();
    }
    /**
     * Method to initialize all the blocks that are playable.
     */
    private void initializeGameBlocks() {
        //create block remover and score counter
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        //create first row of blocks
        Point firstRowPoint = new Point(100, 50);
        for (int i = 0; i < FIRST_ROW; i++) {
            Block yellowBlock = new Block(new Rectangle(firstRowPoint, BLOCK_WIDTH - 1, BLOCK_HEIGHT));
            yellowBlock.setColor(Color.YELLOW);
            yellowBlock.addHitListener(blockRemover);
            yellowBlock.addHitListener(scoreTrackingListener);
            yellowBlock.addToGame(this);
            firstRowPoint = new Point(firstRowPoint.getX() + 50, firstRowPoint.getY());
        }
        //create second row of blocks
        Point secondRowPoint = new Point(150, 80);
        for (int i = 0; i < SECOND_ROW; i++) {
            Block cyanBlock = new Block(new Rectangle(secondRowPoint, BLOCK_WIDTH - 1, BLOCK_HEIGHT));
            cyanBlock.setColor(Color.CYAN);
            cyanBlock.addHitListener(blockRemover);
            cyanBlock.addHitListener(scoreTrackingListener);
            cyanBlock.addToGame(this);
            secondRowPoint = new Point(secondRowPoint.getX() + 50, secondRowPoint.getY());
        }
        //create third row of blocks
        Point thirdRowPoint = new Point(200, 110);
        for (int i = 0; i < THIRD_ROW; i++) {
            Block redBlock = new Block(new Rectangle(thirdRowPoint, BLOCK_WIDTH - 1, BLOCK_HEIGHT));
            redBlock.setColor(Color.RED);
            redBlock.addHitListener(blockRemover);
            redBlock.addHitListener(scoreTrackingListener);
            redBlock.addToGame(this);
            thirdRowPoint = new Point(thirdRowPoint.getX() + 50, thirdRowPoint.getY());
        }
        //create forth row of blocks
        Point forthRowPoint = new Point(250, 140);
        for (int i = 0; i < FORTH_ROW; i++) {
            Block pinkBlock = new Block(new Rectangle(forthRowPoint, BLOCK_WIDTH - 1, BLOCK_HEIGHT));
            pinkBlock.setColor(Color.PINK);
            pinkBlock.addHitListener(blockRemover);
            pinkBlock.addHitListener(scoreTrackingListener);
            pinkBlock.addToGame(this);
            forthRowPoint = new Point(forthRowPoint.getX() + 50, forthRowPoint.getY());
        }
        //create fifth row of blocks
        Point fifthRowPoint = new Point(300, 170);
        for (int i = 0; i < FIFTH_ROW; i++) {
            Block greenBlock = new Block(new Rectangle(fifthRowPoint, BLOCK_WIDTH - 1, BLOCK_HEIGHT));
            greenBlock.setColor(Color.GREEN);
            greenBlock.addHitListener(blockRemover);
            greenBlock.addHitListener(scoreTrackingListener);
            greenBlock.addToGame(this);
            fifthRowPoint = new Point(fifthRowPoint.getX() + 50, fifthRowPoint.getY());
        }
        //create last row of blocks
        Point lastRowPoint = new Point(350, 200);
        for (int i = 0; i < LAST_ROW; i++) {
            Block grayBlock = new Block(new Rectangle(lastRowPoint, BLOCK_WIDTH - 1, BLOCK_HEIGHT));
            grayBlock.setColor(Color.GRAY);
            grayBlock.addHitListener(blockRemover);
            grayBlock.addHitListener(scoreTrackingListener);
            grayBlock.addToGame(this);
            lastRowPoint = new Point(lastRowPoint.getX() + 50, lastRowPoint.getY());
        }
    }
    /**
     * Method to initialize all the border blocks.
     */
    private void initializeBorderBlocks() {
        //create borders block and add to game
        Block borders = new Block(new Rectangle(new Point(0, 0), WIDTH, HEIGHT));
        borders.setColor(Color.WHITE);
        borders.addToGame(this);
        //create gray borders
        Block leftBlock = new Block(new Rectangle(new Point(0, 50), 30, 500));
        leftBlock.setColor(Color.GRAY);
        leftBlock.addToGame(this);
        Block rightBlock = new Block(new Rectangle(new Point(770, 50), 30, 500));
        rightBlock.setColor(Color.GRAY);
        rightBlock.addToGame(this);
        Block upperBlock = new Block(new Rectangle(new Point(0, 25), WIDTH, 25));
        upperBlock.setColor(Color.GRAY);
        upperBlock.addToGame(this);
    }
    /**
     * Initialize death block.
     */
    private void initializeDeathRegion() {
        //create block
        Block deathRegion = new Block(new Rectangle(new Point(0, 575), WIDTH, 25));
        deathRegion.setColor(Color.WHITE);
        deathRegion.addToGame(this);
        //add listener to block
        HitListener ballRemover = new BallRemover(this, this.remainingBalls);
        deathRegion.addHitListener(ballRemover);
    }
    /**
     * Method to initialize balls and add to game.
     */
    private void initializeBalls() {
        Ball ball1 = new Ball(new Point(CENTER, CENTER), 5, Color.GRAY);
        Velocity velocity1 = new Velocity(2, -2);
        ball1.setVelocity(velocity1);
        ball1.setGameEnvironment(this.environment);
        ball1.addToGame(this);
        Ball ball2 = new Ball(new Point(CENTER, CENTER), 5, Color.YELLOW);
        Velocity velocity2 = new Velocity(3, -3);
        ball2.setVelocity(velocity2);
        ball2.setGameEnvironment(this.environment);
        ball2.addToGame(this);
        Ball ball3 = new Ball(new Point(CENTER, CENTER), 5, Color.BLUE);
        Velocity velocity3 = new Velocity(-3, -3);
        ball3.setVelocity(velocity3);
        ball3.setGameEnvironment(this.environment);
        ball3.addToGame(this);
    }
}