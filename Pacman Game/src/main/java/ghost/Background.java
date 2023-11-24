package ghost;

import processing.core.PApplet;
import processing.core.PImage;

public class Background {
    private final int PIXEL_SIZE = 16;

    private PApplet pApplet;
    private char[][] map;

    private PImage downLeft;
    private PImage downRight;
    private PImage upLeft;
    private PImage upRight;
    private PImage horizontal;
    private PImage vertical;

    public Background(PApplet pApplet, char[][] map) {
        this.pApplet = pApplet;
        this.map = map;
    }

    /**
     * Load the image to draw
     *
     */
    public void setup() {
        this.horizontal = pApplet.loadImage("src/main/resources/horizontal.png");
        this.vertical = pApplet.loadImage("src/main/resources/vertical.png");
        this.upLeft = pApplet.loadImage("src/main/resources/upLeft.png");
        this.upRight = pApplet.loadImage("src/main/resources/upRight.png");
        this.downLeft = pApplet.loadImage("src/main/resources/downLeft.png");
        this.downRight = pApplet.loadImage("src/main/resources/downRight.png");
    }

    /**
     * Draw the map on screen
     *
     */
    public void draw() {
        tick();
    }

    /**
     * Handling draw logic
     *
     */
    public void tick() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '1') {
                    pApplet.image(this.horizontal, PIXEL_SIZE * j, PIXEL_SIZE * i);
                } else if (map[i][j] == '2') {
                    pApplet.image(this.vertical, PIXEL_SIZE * j, PIXEL_SIZE * i);
                } else if (map[i][j] == '3') {
                    pApplet.image(this.upLeft, PIXEL_SIZE * j, PIXEL_SIZE * i);
                } else if (map[i][j] == '4') {
                    pApplet.image(this.upRight, PIXEL_SIZE * j, PIXEL_SIZE * i);
                } else if (map[i][j] == '5') {
                    pApplet.image(this.downLeft, PIXEL_SIZE * j, PIXEL_SIZE * i);
                } else if (map[i][j] == '6') {
                    pApplet.image(this.downRight, PIXEL_SIZE * j, PIXEL_SIZE * i);
                }
            }
        }
    }
}