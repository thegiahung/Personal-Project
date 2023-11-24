package ghost;

import processing.core.PApplet;
import java.util.List;
import java.util.Random;

public class Whim extends Ghost {
    private List<AStar.Node> pathChase;
    private List<AStar.Node> pathScatter;

    private final int[] HOME = {26, 32}; // Default place it need to go to when in scatter mode.

    public Whim(PApplet pApplet, char[][] map, Waka waka, char name) {
        super(pApplet, map, waka, name);
        this.x = initXPos(name);
        this.y = initYPos(name);
        pathChase = (new AStar(getMap(), x / PIXEL_SIZE, y / PIXEL_SIZE, false)).findPathTo(waka.getWakaPixelPos()[0] / PIXEL_SIZE, waka.getWakaPixelPos()[1] / PIXEL_SIZE );
        pathScatter = (new AStar(getMap(), x / PIXEL_SIZE, y / PIXEL_SIZE, false)).findPathTo(1, 4);
    }

    /**
     * Get image from path file.
     *
     * @return  path file to the image.
     */
    public String getImageFileName() {
        return "src/main/resources/whim.png";
    }

    /**
     * The target location is Double the vector from Chaser to 2 grid spaces ahead of Waka.
     *
     * @return direction it needs to go to the target.
     */
    public Direction chase() {
        int wakaYPos = waka.getYGridPos();
        int wakaXPos = waka.getXGridPos();
        int ghostYPos = getYGridPos();
        int ghostXPos = getXGridPos();

        pathChase = (new AStar(getMap(), ghostXPos, ghostYPos, false)).findPathTo(wakaXPos, wakaYPos);

        if (pathChase != null && pathChase.size() > 1) {
            int destinationX = pathChase.get(GET_NEXT_CHASE_INDEX).x;
            int destinationY = pathChase.get(GET_NEXT_CHASE_INDEX).y;
            if (ghostXPos - destinationX > 0) {
                currentDirection = Direction.left;
            } else if (ghostXPos - destinationX < 0) {
                currentDirection = Direction.right;
            } else if (ghostYPos - destinationY > 0) {
                currentDirection = Direction.up;
            } else if (ghostYPos - destinationY < 0) {
                currentDirection = Direction.down;
            }
        }

        return currentDirection;
    }

    /**
     * The ghost target location is the bot right corner.
     *
     * @return the direction it need to go to bot right corner.
     */
    public Direction scatter() {
        int ghostYPos = getYGridPos();
        int ghostXPos = getXGridPos();

        if (isAtBotRightCorner()) {
            reachCorner = true;
        } else {
            reachCorner = false;
        }
        // The pathchase will return a list of way the ghost need to move, but we need only the first element of the list
        // to define the next direction that the ghost need to move
        // for example waka is at position (0,2) and the ghost is at (0,0) the pathchase will return a list {[0,0],[0,1],[0,2]}
        if (!reachCorner) {
            pathChase = (new AStar(getMap(), ghostXPos, ghostYPos, false)).findPathTo(HOME[0], HOME[1]);

            if (pathChase != null && pathChase.size() > 1) {
                int destinationX = pathChase.get(GET_NEXT_CHASE_INDEX).x;
                int destinationY = pathChase.get(GET_NEXT_CHASE_INDEX).y;
                if (ghostXPos - destinationX > 0) {
                    currentDirection = Direction.left;
                } else if (ghostXPos - destinationX < 0) {
                    currentDirection = Direction.right;
                } else if (ghostYPos - destinationY > 0) {
                    currentDirection = Direction.up;
                } else if (ghostYPos - destinationY < 0) {
                    currentDirection = Direction.down;
                }
                return currentDirection;
            }
        } else if (reachCorner) {
            Direction[] values = {Direction.up, Direction.left}; // To minimize the chance that the random direction is always go up or right continuously.
            int rnd = new Random().nextInt(values.length);          // I limit to only 2 direction to make the ghost never stops.
            currentDirection = values[rnd];
            return currentDirection;
        }

        return currentDirection;
    }

    /**
     * Declare whether chaser is at the bottom right corner ot not.
     *
     * @return true if it has hit its home corner
     */
    private boolean isAtBotRightCorner() {
        int ghostXPos = x;
        int ghostYPos = y;
        return ((ghostXPos == HOME[0]* PIXEL_SIZE) && (ghostYPos == HOME[1]* PIXEL_SIZE));
    }
}