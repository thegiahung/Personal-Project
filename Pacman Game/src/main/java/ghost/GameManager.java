package ghost;

import processing.core.PApplet;
import processing.core.PFont;

public class GameManager {
    private Config config;
    private Background map;
    private Waka waka;
    private Fruit fruit;
    private Ghost ambusher;
    private Ghost chaser;
    private Ghost ignorant;
    private Ghost whim;

    private int Timer;
    private PFont gameFont;
    private boolean gameEnded = false;

    PApplet pApplet;
    // Mutable
    private char[][] gameMap;
    private final int[] LIVES_DISPLAY = {26, 544};

    public GameManager(PApplet pApplet) {
        this.pApplet = pApplet;
        config = Config.load();
        gameMap = config.getMap();
        map = new Background(pApplet, gameMap);
        waka = new Waka(pApplet, gameMap);
        fruit = new Fruit(pApplet, gameMap);
        ambusher = new Ambusher(pApplet, gameMap, waka, 'a');
        ignorant = new Ignorant(pApplet, gameMap, waka, 'i');
        chaser = new Chaser(pApplet, gameMap, waka, 'c');
        whim = new Whim(pApplet, gameMap, waka, 'w');
    }

    public void setup() {
        map.setup();
        waka.setup();
        fruit.setup();
        ambusher.setup();
        ignorant.setup();
        chaser.setup();
        whim.setup();
        gameFont = pApplet.createFont("src/main/resources/PressStart2P-Regular.ttf", 32);//font resource parsed here
        pApplet.textFont(gameFont);
    }

    public void draw(int frameCount) {
        tick();
        if (!gameEnded) {
            setup(); // This is to make when the gameRestart again, they draw method will have the setup for the image.
            map.draw();
            waka.draw(frameCount);
            fruit.draw();
            ambusher.draw(frameCount);
            ignorant.draw(frameCount);
            chaser.draw(frameCount);
            whim.draw(frameCount);
            liveDisplay();
        }
    }

    /**
     * Display the Waka lives on the screen.
     *
     */
    private void liveDisplay() {
        long lives = waka.getLives();
        for (int k = 0; k < lives; k++) {
            pApplet.image(waka.rightSprite, k * LIVES_DISPLAY[0], LIVES_DISPLAY[1]);
        }
    }

    /**
     * Process the logic of the game.
     *
     */
    public void tick() {
        // It only resets when ghost hit waka.
        reset();
        // It only ends game when Waka is out of lives.
        if (endGameLose()) {
            gameEnded = true;
            pApplet.background(0,0,0);
            pApplet.text("Game Over", 100, 100);
        }
        if (endGameWin()) {
            gameEnded = true;
            pApplet.background(0,0,0);
            pApplet.text("You Win", 100, 100);
        }
        if (gameEnded) {
            Timer += 1;
        }
        if (Timer / 60 > 10) {
            gameEnded = false;
            Timer = 0;
            restartGame();
        }
    }

    /**
     * Reset the position of waka and 4 ghosts when one of them hit Waka.
     * Also, reduce the amount of lives of Waka by 1.
     *
     */
    private void reset() {
        if (ambusher.hitWaka() || chaser.hitWaka() || ignorant.hitWaka() || whim.hitWaka()) {
            waka.setLives(waka.getLives() - 1);
            waka.reset();
            ambusher.reset();
            ignorant.reset();
            chaser.reset();
            whim.reset();
        }
    }

    /**
     * Reset the game whenever player lose or win.
     *
     */
    private void restartGame() {
        gameMap = Config.load().getMap();
        map = new Background(pApplet, gameMap);
        waka = new Waka(pApplet, gameMap);
        fruit = new Fruit(pApplet, gameMap);
        ambusher = new Ambusher(pApplet, gameMap, waka, 'a');
        ignorant = new Ignorant(pApplet, gameMap, waka, 'i');
        chaser = new Chaser(pApplet, gameMap, waka, 'c');
        whim = new Whim(pApplet, gameMap, waka, 'w');
    }

    /**
     * Get Waka object on the map.
     *
     * @return Waka.
     */
    public Waka getWaka() {
        return waka;
    }

    /**
     * If Waka has not collected all the fruit in the map before out of lives, then lose.
     *
     * @return true Waka can not get all the fruit before out of lives.
     */
    private boolean endGameLose() {
        if (waka.getLives() < 0) {
            return true;
        }
        return false;

    }

    /**
     * If Waka has collected all the fruit in the map, it will win.
     *
     * @return true if Waka can collect all the fruit before out of lives.
     */
    private boolean endGameWin() {
        if (waka.collectedAll()) {
            return true;
        }
        return false;

    }
}
