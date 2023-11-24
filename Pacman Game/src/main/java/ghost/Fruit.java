package ghost;

import processing.core.PApplet;
import processing.core.PImage;

public class Fruit {
    private final int PIXEL_SIZE = 16;

    private PApplet pApplet;
    private char[][] map;
    private PImage fruit;
    private PImage bigFruit;

    public Fruit(PApplet pApplet, char[][] map) {
        this.pApplet = pApplet;
        this.map = map;
    }

    /**
     * Load the image to draw.
     *
     */
    public void setup() {
        this.fruit = pApplet.loadImage("src/main/resources/fruit.png");
        this.bigFruit = pApplet.loadImage("src/main/resources/specialFruit.png");
    }

    /**
     * Draw the fruit on screen.
     *
     */
    public void draw() {
        tick();
    }

    /**
     * Handling draw logic.
     *
     */
    public void tick() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '7') {
                    pApplet.image(this.fruit, PIXEL_SIZE * j, PIXEL_SIZE * i);
                } else if (map[i][j] == '8') {
                    pApplet.image(this.bigFruit, PIXEL_SIZE * j, PIXEL_SIZE * i);
                }
            }
        }
    }
}