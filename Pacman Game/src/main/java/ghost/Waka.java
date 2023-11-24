package ghost;

import processing.core.PImage;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class Waka {
    private final int LEFT = 37;
    private final int RIGHT = 39;
    private final int UP = 38;
    private final int DOWN = 40;

    public final int PIXEL_SIZE = 16;
    private final long SPEED = Config.load().getSpeed();

    private final int X_OFFSET = 5; // Offset that make waka image fit the maze
    private final int Y_OFFSET = 5;

    private final int COLLECT_FOOD_OFFSET = 10; // This offset makes waka eat the fruit right after his mouth place on the fruit
    private final int CLOSED_MOUTH_FRAME = 8;

    public PImage rightSprite;
    public PImage leftSprite;
    public PImage downSprite;
    public PImage upSprite;
    public PImage closedSprite;
    public PImage sprite;

    private PApplet pApplet;
    private char[][] map;
    private long lives;
    private int x;
    private int y;
    private String dir;

    private int lastKeycode = 39; // Make it as a default to go to the right when start the game

    public Waka(PApplet pApplet, char[][] map) {
        this.pApplet = pApplet;
        this.map = map;
        this.x = initXPos();
        this.y = initYPos();
        this.lives = Config.load().getLives();
        this.dir = "right"; // start at the right the direction
    }

    /**
     * Load the image to draw.
     *
     */
    public void setup() {
        this.rightSprite = pApplet.loadImage("src/main/resources/playerRight.png");
        this.leftSprite = pApplet.loadImage("src/main/resources/playerLeft.png");
        this.upSprite = pApplet.loadImage("src/main/resources/playerUp.png");
        this.downSprite = pApplet.loadImage("src/main/resources/playerDown.png");
        this.closedSprite = pApplet.loadImage("src/main/resources/playerClosed.png");
        this.sprite = pApplet.loadImage("src/main/resources/playerRight.png"); // Waka start at the right at the beginning of the game
    }

    /**
     * Draw the waka on screen.
     *
     */
    public void draw(int frameCount) {
        tick(frameCount);
        pApplet.image(sprite, x - X_OFFSET, y - Y_OFFSET);
    }

    public void tick(int frameCount) {
        // Waka only collects when it hits the food
        collectFood();

        if (frameCount % SPEED == 0) {
            move();
        }

        if (frameCount % CLOSED_MOUTH_FRAME == 0) {
            pApplet.image(closedSprite, x - X_OFFSET, y - Y_OFFSET);
        }
    }

    /**
     * Waka will move based on the last key was pressed by the player
     *
     */
    private void move() {
        if (lastKeycode == RIGHT) {
            goRight();
        } else if (lastKeycode == LEFT) {
            goLeft();
        } else if (lastKeycode == UP) {
            goUp();
        } else if (lastKeycode == DOWN) {
            goDown();
        }
    }

    /**
     * Move waka according to the key.
     * @param event
     */
    public void keyPressed(KeyEvent event) {
        this.lastKeycode = event.getKeyCode();
    }

    /**
     * Give the direction for the waka according to the key pressed and switch the sprite appropriately to the key.
     */
    private void goDown() {
        x = Math.round((float) x / PIXEL_SIZE) * PIXEL_SIZE; // We need to round up the column before going down.
                                                             // It means it can go pass when go pass the mid point of the block
        y += 1;
        if (wallCollide()) {
            y -= 1;
        }
        dir = "down";
        sprite = downSprite;
    }

    private void goUp() {
        x = Math.round((float) x / PIXEL_SIZE) * PIXEL_SIZE;

        y -= 1;
        if (wallCollide()) {
            y += 1;
        }
        dir = "up";
        sprite = upSprite;
    }

    private void goLeft() {
        y = Math.round((float) y / PIXEL_SIZE) * PIXEL_SIZE;

        x -= 1;
        if (wallCollide()) {
            x += 1;
        }
        dir = "left";
        sprite = leftSprite;
    }

    private void goRight() {
        y = Math.round((float) y / PIXEL_SIZE) * PIXEL_SIZE;

        x += 1;
        if (wallCollide()) {
            this.x -= 1;
        }
        dir = "right";
        sprite = rightSprite;
    }

    /**
     * Get the beginning horizontal position for Waka
     *
     * @return horizontal position
     */
    public int initXPos() {
        int xPos = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'p') {
                    xPos = j * PIXEL_SIZE;
                }
            }
        }

        return xPos;
    }

    /**
     * Get the beginning vertical position for Waka
     *
     * @return vertical position
     */
    public int initYPos() {
        int yPos = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'p') {
                    yPos = i * PIXEL_SIZE;
                }
            }
        }

        return yPos;
    }

    /**
     * Get Waka's pixel position on the map in two direction
     *
     * @return an array of current pixel positions.
     */
    public int[] getWakaPixelPos() {
        int[] wakaPosition = new int[2];
        wakaPosition[0] = x;
        wakaPosition[1] = y;
        return wakaPosition;
    }

    /**
     * Get Waka X-grid position.
     *
     * @return the position of Waka on txt file.
     */
    public int getXGridPos() {
        int wakaXPos = getWakaPixelPos()[0];
        return getDir().equals("left")
                ? (int) Math.ceil(((float) wakaXPos) / PIXEL_SIZE)
                : wakaXPos/ PIXEL_SIZE;
    }

    /**
     * Get Waka Y-grid position.
     *
     * @return the position of Waka on txt file.
     */
    public int getYGridPos() {
        int wakaYPos = getWakaPixelPos()[1];
        return getDir().equals("up")
                ? (int) Math.ceil(((float) wakaYPos) / PIXEL_SIZE)
                : wakaYPos/ PIXEL_SIZE;
    }

    /**
     * Adjust the X position of Waka.
     *
     * @param position
     */
    public void setXPosition(int position) {
        this.x = position;
    }

    /**
     * Adjust the Y position of Waka.
     * @param position
     */
    public void setYPosition(int position) {
        this.y = position;
    }

    /**
         * Check if Waka hit the wall or not.
     *
     * @return true if it hits the wall, otherwise false.
     */
    private boolean wallCollide() {
        int[] wakaPos = getWakaPixelPos();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int row = dir.equals("down")
                    ? (int) Math.ceil(((float) wakaPos[1])  / PIXEL_SIZE) // We need to ceil the row otherwise when go down
                    : wakaPos[1] / PIXEL_SIZE;                            // Otherwise it will overlap the wall.

                int col = dir.equals("right")
                    ? (int) Math.ceil(((float) wakaPos[0]) / PIXEL_SIZE)
                    : wakaPos[0] / PIXEL_SIZE;

                char wallObj = map[row][col];
                if (wallObj == '1' || wallObj == '2' || wallObj == '3' || wallObj == '4' || wallObj == '5' || wallObj == '6') {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Waka will collect the food if Waka hits the food on the map.
     *
     * @return
     */
    private void collectFood() {
        int[] wakaPos = getWakaPixelPos();

        ArrayList<int[]> fruitPos = getFruitPos();
        for (int i = 0; i < fruitPos.size(); i++) {
            // Collect the fruit when waka's mouth hit the food on the map.
            if (getDir().equals("right")) {
                if (wakaPos[0] + COLLECT_FOOD_OFFSET == getFruitPos().get(i)[0] && wakaPos[1] == getFruitPos().get(i)[1]) {
                    int col = (int) Math.ceil(((float) wakaPos[0]) / PIXEL_SIZE);
                    int row = wakaPos[1] / PIXEL_SIZE;
                    map[row][col] = '0';
                    break;
                }
            }

            else if (getDir().equals("left")) {
                if (wakaPos[0] - COLLECT_FOOD_OFFSET == getFruitPos().get(i)[0] && wakaPos[1] == getFruitPos().get(i)[1]) {
                    int col = wakaPos[0] / PIXEL_SIZE;
                    int row = wakaPos[1] / PIXEL_SIZE;
                    map[row][col] = '0';
                    break;
                }
            }

            else if (getDir().equals("up")) {
                if (wakaPos[0] == getFruitPos().get(i)[0] && wakaPos[1] - COLLECT_FOOD_OFFSET == getFruitPos().get(i)[1]) {
                    int col = wakaPos[0] / PIXEL_SIZE;
                    int row = wakaPos[1] / PIXEL_SIZE;
                    map[row][col] = '0';
                    break;
                }
            }

            else if (getDir().equals("down")) {
                if (wakaPos[0] == getFruitPos().get(i)[0] && wakaPos[1] + COLLECT_FOOD_OFFSET == getFruitPos().get(i)[1]) {
                    int col = wakaPos[0] / PIXEL_SIZE;
                    int row = (int) Math.ceil(((float) wakaPos[1]) / PIXEL_SIZE); // We want to round the round up the row to the next int divisible by 16
                    map[row][col] = '0';
                    break;
                }
            }
        }
    }

    /**
     * Check if Waka has collected all the fruit on the map
     *
     * @return true if all the fruit has been collected.
     */
    public boolean collectedAll() {
        char[][] mapCheck = map;
        for (int i = 0; i < mapCheck.length; i++) {
            for (int j = 0; j < mapCheck[i].length; j++) {
                if (mapCheck[i][j] == '7') {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Find all the position of fruit on the map
     *
     * @return a list of array of fruit positions.
     */
    private ArrayList<int[]> getFruitPos() {
        ArrayList<int[]> fruitPosition = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int[] addFruit = new int[2];
                if (map[i][j] == '7' || map[i][j] == '8') {
                    addFruit[0] = j * PIXEL_SIZE;
                    addFruit[1] = i * PIXEL_SIZE;
                    fruitPosition.add(addFruit);
                }
            }
        }

        return fruitPosition;
    }

    /**
     * Get the current direction of Waka.
     *
     * @return the current direction that Waka is moving.
     */
    public String getDir() {
        return dir;
    }

    /**
     * Get the number of lives left of Waka
     *
     * @return number of lives Waka has
     */
    public long getLives() {
        return lives;
    }

    /**
     * Adjust the number of lives of Waka.
     *
     * @param lives
     */
    public void setLives(long lives) {
        this.lives = lives;
    }

    /**
     * Reset the Waka state to initial state.
     */
    public void reset() {
        lastKeycode = 39;
        dir = "right";
        setXPosition(initXPos());
        setYPosition(initYPos());
    }
}
