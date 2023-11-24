package ghost;

import processing.core.PApplet;
import processing.event.KeyEvent;

public class App extends PApplet {
    private static final int WIDTH = 448;
    private static final int HEIGHT = 576;
    GameManager gameManager;

    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }

    public App() {
        gameManager = new GameManager(this);
    }

    @Override
    public void setup() {
        frameRate(60);
        gameManager.setup();
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void draw() {
        background(0, 0, 0);
        gameManager.draw(frameCount);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        gameManager.getWaka().keyPressed(event);
    }
}
