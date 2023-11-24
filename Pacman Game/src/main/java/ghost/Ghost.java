package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import static java.lang.Math.*;

enum Direction {
    up, down, left, right;
}

public abstract class Ghost {
    protected final int PIXEL_SIZE = 16;
    private final long SPEED = Config.load().getSpeed();
    private final int X_OFFSET = 5; // Offset that make the image of ghost fit the maze
    private final int Y_OFFSET = 5;
    private final int HIT_OFFSET = 20;
    protected final int GET_NEXT_CHASE_INDEX = 1; // The next direction that the ghost will move

    protected PApplet pApplet;
    protected char[][] map;
    protected Waka waka;
    private char name;
    private String dir;

    private PImage sprite;
    protected int x;
    protected int y;
    protected boolean reachCorner = false;

    protected Direction currentDirection = Direction.right; // Let the ghost move to the right as default at start
    private int modeIndex = 0;
    private long Timer;
    private long tickCount = 0;

    protected abstract Direction chase();
    protected abstract Direction scatter();
    protected abstract String getImageFileName();

    public Ghost(PApplet pApplet, char[][] map, Waka waka, char name) {
        this.pApplet = pApplet;
        this.map = map;
        this.waka = waka;
        this.name = name;
        dir = "right";
    }

    /**
     * Load the image to draw.
     *
     */
    public void setup() {
        this.sprite = pApplet.loadImage(getImageFileName());
    }

    /**
     * Draw the ghost on screen.
     *
     */
    public void draw(int frameCount) {
        pApplet.image(sprite, x - X_OFFSET, y - Y_OFFSET);

        tick(frameCount);
    }

    /**
     * Handling game logic and choose the mode accordingly to the mode length.
     * @param frameCount
     *
     */
    protected void tick(int frameCount) {
        if (frameCount % SPEED == 0) {
            currentDirection = move();
            switch (currentDirection) {
                case up:
                    goUp();
                    break;

                case down:
                    goDown();
                    break;

                case left:
                    goLeft();
                    break;

                case right:
                    goRight();
                    break;
            }
            chooseMode(); // Always check the timer whether it should swap the mode or not.
            tickCount++;
        }
    }

    /**
     * Give the ghost the direction it need to move. Only define the new direction again when it see the intersection.
     *
     * @return next direction ghost need to move.
     */
    private Direction move() {
        if (intersection()) {
            currentDirection = modeIndex % 2 == 0 ? scatter() : chase();
        }

        return currentDirection;
    }

    /**
     * This will declare whether the ghost hit the Waka or not.
     *
     * @return true if the ghost hit Waka.
     */
    protected boolean hitWaka() {
        int wakaXPos = waka.getWakaPixelPos()[0];
        int wakaYPos = waka.getWakaPixelPos()[1];
        if (sqrt(pow(x - wakaXPos, 2) + pow(y - wakaYPos, 2)) <= HIT_OFFSET) {
            return true;
        }
        return false;
    }

    /**
     * Go down.
     *
     */
    private void goDown() {
        x = Math.round((float) x / PIXEL_SIZE) * PIXEL_SIZE; // We need to round up the column before going down.

        y += 1;
        if (wallCollide()) {
            y -= 1;
        }
        dir = "down";
    }

    /**
     * Go up.
     *
     */
    private void goUp() {
        x = Math.round((float) x / PIXEL_SIZE) * PIXEL_SIZE;

        y -= 1;
        if (wallCollide()) {
            y += 1;
        }
        dir = "up";
    }

    /**
     * Go left.
     *
     */
    private void goLeft() {
        y = Math.round((float) y / PIXEL_SIZE) * PIXEL_SIZE;

        x -= 1;
        if (wallCollide()) {
            x += 1;
        }
        dir = "left";
    }

    /**
     * Go right.
     *
     */
    private void goRight() {
        y = Math.round((float) y / PIXEL_SIZE) * PIXEL_SIZE;

        x += 1;
        if (wallCollide()) {
            this.x -= 1;
        }
        dir = "right";
    }

    /**
     * Check if the ghost hit the wall or not.
     *
     * @return true if it hit the wall, otherwise false.
     */
    public boolean wallCollide() {
        int[] ghostPos = getGhostPixelPos();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int row = getDir().equals("down")
                        ? (int) Math.ceil(((float) ghostPos[1]) / PIXEL_SIZE)
                        : ghostPos[1] / PIXEL_SIZE;

                int col = getDir().equals("right")
                        ? (int) Math.ceil(((float) ghostPos[0]) / PIXEL_SIZE)
                        : ghostPos[0] / PIXEL_SIZE;

                char wallObj = map[row][col];
                if (wallObj == '1' || wallObj == '2' || wallObj == '3' || wallObj == '4' || wallObj == '5' || wallObj == '6') {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Get the current position of the ghost on the map.
     *
     * @return an array of pixel position of the ghost.
     */
    protected int[] getGhostPixelPos() {
        int[] ghostPosition = new int[2];
        ghostPosition[0] = x;
        ghostPosition[1] = y;
        return ghostPosition;
    }

    /**
     * Get ghost X-grid position.
     *
     * @return the position of ghost on txt file.
     */
    public int getXGridPos() {
        int wakaXPos = getGhostPixelPos()[0];
        return getDir().equals("left")
                ? (int) Math.ceil(((float) wakaXPos) / PIXEL_SIZE)
                : wakaXPos/ PIXEL_SIZE;
    }

    /**
     * Get ghost Y-grid position.
     *
     * @return the position of ghost on txt file.
     */
    public int getYGridPos() {
        int wakaYPos = getGhostPixelPos()[1];
        return getDir().equals("up")
                ? (int) Math.ceil(((float) wakaYPos) / PIXEL_SIZE)
                : wakaYPos/ PIXEL_SIZE;
    }

    /**
     * Get the X position of the ghost base on the character given on the map when starting the game.
     * @param c
     *
     * @return the initial X position of the ghost.
     */
    public int initXPos(char c) {
        int xPos = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == c) {
                    xPos = j * PIXEL_SIZE;
                }
            }
        }

        return xPos;
    }

    /**
     * Get the Y position of the ghost base on the character given on the map when starting the game.
     * @param c
     *
     * @return the initial Y position of the ghost.
     */
    public int initYPos(char c) {
        int yPos = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == c) {
                    yPos = i * PIXEL_SIZE;
                }
            }
        }

        return yPos;
    }

    /**
     * Adjust the X position of the ghost.
     * @param position
     *
     */
    public void setXPosition(int position) {
        this.x = position;
    }

    /**
     * Adjust the Y position of the ghost.
     * @param position
     *
     */
    public void setYPosition(int position) {
        this.y = position;
    }

    /**
     * Define whether the ghost has gone into the intersection or not.
     *
     * @return true if the ghost is at the intersection.
     */
    public boolean intersection() {
        return ((canTurnLeft() && canTurnRight() && canGoUp() && canGoDown()) || (canTurnLeft() && canTurnRight() && canGoUp()) || (canTurnLeft() && canTurnRight() && canGoDown()) || (canTurnLeft() && canGoDown()) || (canTurnRight() && canGoDown()) || (canTurnLeft() && canGoUp()) || (canTurnRight() && canGoUp()));
    }

    /**
     * Define whether the ghost can turn left at current position.
     *
     * @return true if it can turn left.
     */
    public boolean canTurnLeft() {
        if (map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) - 1] == '1' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) - 1] == '2' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) - 1] == '3' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) - 1] == '4' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) - 1] == '5' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) - 1] == '6') {
            return false;
        }

        return true;
    }

    /**
     * Define whether the ghost can turn right at current position.
     *
     * @return true if it can turn right.
     */
    public boolean canTurnRight() {
        if (map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) + 1] == '1' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) + 1] == '2' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) + 1] == '3' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) + 1] == '4' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) + 1] == '5' || map[y/ PIXEL_SIZE][(x/ PIXEL_SIZE) + 1] == '6') {
            return false;
        }

        return true;
    }

    /**
     * Define whether the ghost can go up at current position.
     *
     * @return true if it can go up.
     */
    public boolean canGoUp() {
        if (map[(y/ PIXEL_SIZE) - 1][x/ PIXEL_SIZE] == '1' || map[(y/ PIXEL_SIZE) - 1][x/ PIXEL_SIZE] == '2' || map[(y/ PIXEL_SIZE) - 1][x/ PIXEL_SIZE] == '3' || map[(y/ PIXEL_SIZE) - 1][x/ PIXEL_SIZE] == '4' || map[(y/ PIXEL_SIZE) - 1][x/ PIXEL_SIZE] == '5' || map[(y/ PIXEL_SIZE) - 1][x/ PIXEL_SIZE] == '6') {
            return false;
        }

        return true;
    }

    /**
     * Define whether the ghost can go down at current position.
     *
     * @return true if it can go down.
     */
    public boolean canGoDown() {
        if (map[(y/ PIXEL_SIZE) + 1][x/ PIXEL_SIZE] == '1' || map[(y/ PIXEL_SIZE) + 1][x/ PIXEL_SIZE] == '2' || map[(y/ PIXEL_SIZE) + 1][x/ PIXEL_SIZE] == '3' || map[(y/ PIXEL_SIZE) + 1][x/ PIXEL_SIZE] == '4' || map[(y/ PIXEL_SIZE) + 1][x/ PIXEL_SIZE] == '5' || map[(y/ PIXEL_SIZE) + 1][x/ PIXEL_SIZE] == '6') {
            return false;
        }

        return true;
    }

    /**
     * Convert map from 2 dimension char array to 2 dimension int array.
     *
     * @return 2 dimension int array.
     */
    protected int[][] getMap() {
        int[][] intMap = new int[36][28];
        for (int i = 0; i < intMap.length; i++) {
            for (int j = 0; j < intMap[i].length; j++) {
                if (Character.getNumericValue(map[i][j]) == 1 || Character.getNumericValue(map[i][j]) == 2 || Character.getNumericValue(map[i][j]) == 3 || Character.getNumericValue(map[i][j]) == 4 || Character.getNumericValue(map[i][j]) == 5 || Character.getNumericValue(map[i][j]) == 6) {
                    intMap[i][j] = -1;
                } else {
                    intMap[i][j] = 1;
                }
            }
        }
        return intMap;
    }

    /**
     * Get the current moving direction of ghost.
     *
     * @return current direction.
     */
    public String getDir() {
        return dir;
    }

    /**
     * Get the name of the ghost.
     *
     * @return name of ghost.
     */
    public char getName() {
        return name;
    }

    /**
     * Reset the ghost state to initial state.
     *
     */
    protected void reset() {
        currentDirection = Direction.right;
        setXPosition(initXPos(getName()));
        setYPosition(initYPos(getName()));
        reachCorner = false;
        dir = "right";
    }

    /**
     * Change the current mode according to the modeLength.
     *
     */
    private void chooseMode() {
        Long[] mode = Config.load().getModeLengths();
        Timer = tickCount;
        if (modeIndex == mode.length - 1 && Timer == mode[mode.length - 1] * 60) {
            modeIndex = 0;
            tickCount = 0;
            return;
        }

        if (Timer == mode[modeIndex] * 60) {
            modeIndex += 1;
            tickCount = 0;
        }
    }
}
