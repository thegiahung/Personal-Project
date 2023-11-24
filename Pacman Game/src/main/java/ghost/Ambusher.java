package ghost;

import processing.core.PApplet;
import java.util.List;
import java.util.Random;

public class Ambusher extends Ghost {
    private List<AStar.Node> pathChase;
    private List<AStar.Node> pathScatter;

    private final int[] HOME = {26,4}; // Default place it need to go to when in scatter mode.

    public Ambusher(PApplet pApplet, char[][] map, Waka waka, char name) {
        super(pApplet, map, waka, name);
        this.x = initXPos(name);
        this.y = initYPos(name);
        pathChase = (new AStar(getMap(), x / PIXEL_SIZE, y / PIXEL_SIZE, false)).findPathTo(destination()[0], destination()[1]);
        pathScatter = (new AStar(getMap(), x / PIXEL_SIZE, y / PIXEL_SIZE, false)).findPathTo(1, 4);
    }

    /**
     * Get image from path file.
     *
     * @return  path file to the image.
     */
    public String getImageFileName() {
        return "src/main/resources/ambusher.png";
    }

    /**
     * The ghost's target location is four grid spaces ahead of Waka (based on Waka’s current direction)
     *
     * @return direction it needs to go to target.
     */
    public Direction chase() {
        int desX = destination()[0];
        int desY = destination()[1];
        int ghostXPos = getXGridPos();
        int ghostYPos = getYGridPos();

        pathChase = (new AStar(getMap(), ghostXPos, ghostYPos, false)).findPathTo(desX, desY);

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

        return currentDirection;
    }

    /**
     * The ghost target location is the top right corner.
     *
     * @return the direction it need to go to top right corner.
     */
    public Direction scatter() {
        int ghostYPos = getYGridPos();
        int ghostXPos = getXGridPos();

        if (isAtTopRightCorner()) {
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
            Direction[] values = {Direction.down, Direction.left};
            int rnd = new Random().nextInt(values.length);
            currentDirection = values[rnd];
            return currentDirection;
        }

        return currentDirection;
    }

    /**
     * Declare whether chaser is at the top right corner ot not.
     *
     * @return true if it has hit its home corner
     */
    private boolean isAtTopRightCorner() {
        int ghostXPos = x;
        int ghostYPos = y;
        return ((ghostXPos == HOME[0]* PIXEL_SIZE) && (ghostYPos == HOME[1]* PIXEL_SIZE));
    }

    /**
     * Find the destination ahead waka 4 grid space that the ghost need to move to based on waka current direction.
     * If the there is a wall, just move to a maximum position it can get to with the maximum is 4 grid space ahead Waka.
     *
     * @return an array of position that the ghost need to go to.
     */
    private int[] destination() {
        int[] position = new int[2];
        int wakaXPos = waka.getXGridPos();
        int wakaYPos = waka.getYGridPos();

        // First we need to check what is the current direction of waka
        // We need to find the position that the ghost should move, maximum is 4 grid ahead waka.
        // For example, if ahead waka 1 grid space is a wall then the expected position for ghost is the current position of waka.
        // If we pass the first condition, then we check if ahead waka 2 grid space is a wall so the expected position will be waka current direction plus 1 and so on.
        if (waka.getDir().equals("up")) {
            for (int i = 1; i <= 4; i++) {
                if (checkWall(waka, i, "up")) {
                    position[0] = wakaXPos;
                    position[1] = wakaYPos - i + 1;
                    return position;
                }
            }
            position[0] = wakaXPos;
            position[1] = wakaYPos - 4;
            return position;
        }

        if (waka.getDir().equals("down")) {
            for (int i = 1; i <= 4; i++) {
                if (checkWall(waka, i, "down")) {
                    position[0] = wakaXPos;
                    position[1] = wakaYPos + i - 1;
                    return position;
                }
            }
            position[0] = wakaXPos;
            position[1] = wakaYPos + 4;
            return position;
        }

        if (waka.getDir().equals("left")) {
            for (int i = 1; i <= 4; i++) {
                if (checkWall(waka, i, "left")) {
                    position[0] = wakaXPos - i + 1;
                    position[1] = wakaYPos;
                    return position;
                }
            }
            position[0] = wakaXPos - 4;
            position[1] = wakaYPos;
            return position;
        }

        // Right
        for (int i = 1; i <= 4; i++) {
            if (checkWall(waka, i, "right")) {
                position[0] = wakaXPos + i - 1;
                position[1] = wakaYPos;
                return position;
            }
        }
        position[0] = wakaXPos + 4;
        position[1] = wakaYPos;
        return position;
    }

    /**
     * Check if there is a wall ahead based on the Waka current direction with a distance   .
     *
     * @param waka
     * @param distance
     * @param direction
     *
     * @return true if there is a wall with given distance, false otherwise.
     */
    private boolean checkWall (Waka waka, int distance, String direction) {
        int wakaXPos = waka.getXGridPos();
        int wakaYPos = waka.getYGridPos();
        if (direction.equals("up")) {
            if (map[wakaYPos - distance][wakaXPos] == '1' || map[wakaYPos - distance][wakaXPos] == '2' || map[wakaYPos - distance][wakaXPos] == '3' || map[wakaYPos - distance][wakaXPos] == '4' || map[wakaYPos - distance][wakaXPos] == '5' || map[wakaYPos - distance][wakaXPos] == '6') {
                return true;
            }
        } else if (direction.equals("down")) {
            if (map[wakaYPos + distance][wakaXPos] == '1' || map[wakaYPos + distance][wakaXPos] == '2' || map[wakaYPos + distance][wakaXPos] == '3' || map[wakaYPos + distance][wakaXPos] == '4' || map[wakaYPos + distance][wakaXPos] == '5' || map[wakaYPos + distance][wakaXPos] == '6') {
                return true;
            }
        } else if (direction.equals("left")) {
            if (map[wakaYPos][wakaXPos - distance] == '1' || map[wakaYPos][wakaXPos - distance] == '2' || map[wakaYPos][wakaXPos - distance] == '3' || map[wakaYPos][wakaXPos - distance] == '4' || map[wakaYPos][wakaXPos - distance] == '5' || map[wakaYPos][wakaXPos - distance] == '6') {
                return true;
            }
        } else if (direction.equals("right")) {
            if (map[wakaYPos][wakaXPos + distance] == '1' || map[wakaYPos][wakaXPos + distance] == '2' || map[wakaYPos][wakaXPos + distance] == '3' || map[wakaYPos][wakaXPos + distance] == '4' || map[wakaYPos][wakaXPos + distance] == '5' || map[wakaYPos][wakaXPos + distance] == '6') {
                return true;
            }
        }

        return false;
    }
}