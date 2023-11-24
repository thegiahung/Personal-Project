package ghost;

import processing.core.PApplet;
import java.util.List;
import java.util.Random;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Ignorant extends Ghost {
    private List<AStar.Node> pathChase;
    private List<AStar.Node> pathScatter;

    private final int[] HOME = {1,32}; // Default place it need to go to when in scatter mode.

    public Ignorant(PApplet pApplet, char[][] map, Waka waka, char name) {
        super(pApplet, map, waka, name);
        this.x = initXPos(name);
        this.y = initYPos(name);
        pathChase = (new AStar(getMap(), x / PIXEL_SIZE, y / PIXEL_SIZE, false)).findPathTo(waka.getWakaPixelPos()[0] / waka.PIXEL_SIZE, waka.getWakaPixelPos()[1] / waka.PIXEL_SIZE);
        pathScatter = (new AStar(getMap(), x / PIXEL_SIZE, y / PIXEL_SIZE, false)).findPathTo(1, 32);
    }

    /**
     * Get image from path file.
     *
     * @return  path file to the image.
     */
    public String getImageFileName() {
        return "src/main/resources/ignorant.png";
    }

    /**
     * The ghost will chase Waka if more than 8 units away from Waka (straight line distance),target location is Waka.
     * Otherwise, target location is bottom left corner.
     *
     * @return direction it needs to chase waka
     */
    public Direction chase() {
        int wakaXPos = waka.getXGridPos();
        int wakaYPos = waka.getYGridPos();
        int ghostXPos = getXGridPos();
        int ghostYPos = getYGridPos();

        // Only chase when waka position is ahead from the ghost in 8 grid space.
        if (sqrt(pow(ghostXPos - wakaXPos, 2) + pow(ghostYPos - wakaYPos, 2)) <= 8) {
            pathChase = (new AStar(getMap(), ghostXPos, ghostYPos, false)).findPathTo(wakaXPos, wakaYPos);
        } else {
            currentDirection = scatter();
            return currentDirection;
        }
        if (pathChase != null && pathChase.size() > 1) {
            int destinationX = pathChase.get(GET_NEXT_CHASE_INDEX).x;
            int destinationY = pathChase.get(GET_NEXT_CHASE_INDEX).y;
            if (ghostXPos - destinationX > 0) {
                return Direction.left;
            } else if (ghostXPos - destinationX < 0) {
                return Direction.right;
            } else if (ghostYPos - destinationY > 0) {
                return Direction.up;
            } else if (ghostYPos - destinationY < 0) {
                return Direction.down;
            }
        }

        // keep the same direction until it reaches the intersection.
        return currentDirection;
    }

    /**
     * The ghost target location is the bottom left corner.
     *
     * @return the direction it need to go to bottom left corner.
     */
    public Direction scatter() {
        int ghostYPos = getYGridPos();
        int ghostXPos = getXGridPos();

        if (isAtBotLeftCorner()) {
            reachCorner = true;
        } else {
            reachCorner = false;
        }

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
            Direction[] values = {Direction.up, Direction.right};
            int rnd = new Random().nextInt(values.length);
            currentDirection = values[rnd];
            return currentDirection;
        }

        return currentDirection;
    }

    /**
     * Declare whether chaser is at the bot left corner ot not.
     *
     * @return true if it has hit its home corner
     */
    private boolean isAtBotLeftCorner() {
        int ghostXPos = x;
        int ghostYPos = y;
        return ((ghostXPos == HOME[0]* PIXEL_SIZE) && (ghostYPos == HOME[1]* PIXEL_SIZE));
    }
}