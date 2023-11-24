package ghost;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GhostTest {
    PApplet pApplet = new PApplet();

    // Test if the image is not null.
    @Test
    public void testGetFileImage() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertNotNull(ambusher.getImageFileName());
    }

    // Test if the direction is not null.
    @Test
    public void testGetDir() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertNotNull(ambusher.getDir());
    }

    // Test if the set position can set the position as expected.
    @Test
    public void testSetPosition() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(180);
        ambusher.setYPosition(210);
        int[] expectedPosition = {180, 210};
        Assert.assertArrayEquals(expectedPosition, ambusher.getGhostPixelPos());
    }

    // Test if the the name of the ghost can return as expected for particular ghost.
    @Test
    public void testGetName() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertEquals('a', ambusher.getName());
    }

    // Test if the current position has hit waka or not
    @Test
    public void testNotHitWaka() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertFalse(ambusher.hitWaka());
    }

    // Another test if the current position has hit waka or not
    @Test
    public void testHitWaka() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(waka.initXPos() - 20);
        ambusher.setYPosition(waka.initYPos());
        assertTrue(ambusher.hitWaka());
    }

    // Test if the position is not the same as waka.
    @Test
    public void testGetGridPos() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertNotEquals(waka.getXGridPos(), ambusher.getXGridPos());
        assertNotEquals(waka.getYGridPos(), ambusher.getYGridPos());
    }

    // Test if the ghost is at the intersection or not
    @Test
    public void testNotIntersection() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertFalse(ambusher.intersection());
    }

    // Test if the ghost is at the intersection or not
    @Test
    public void testIntersection() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(9*16);
        ambusher.setYPosition(14*16);
        assertTrue(ambusher.intersection());
    }

    // Test if the ghost is at the intersection or not
    @Test
    public void testIntersection2() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(6*16);
        ambusher.setYPosition(17*16);
        assertTrue(ambusher.intersection());
    }

    // Test if the ghost is at the intersection or not
    @Test
    public void testIntersection3() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(18*16);
        ambusher.setYPosition(14*16);
        assertTrue(ambusher.intersection());
    }

    // Test if the reset method can reset the intial state of the ghost.
    @Test
    public void testReset() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(9*16);
        ambusher.setYPosition(14*16);
        int[] beforeReset = ambusher.getGhostPixelPos();
        ambusher.reset();
        int[] afterReset = ambusher.getGhostPixelPos();
        assertFalse(Arrays.equals(beforeReset, afterReset));
    }

    // Test logic if the tick method can make the ghost move.
    @Test
    public void testTick() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ghost ambusher = new Ambusher(pApplet, map, waka, 'a' );
        int[] beforeMovePos = ambusher.getGhostPixelPos();
        ambusher.tick(1);
        int[] afterMovePos = ambusher.getGhostPixelPos();
        assertFalse(Arrays.equals(beforeMovePos, afterMovePos));
    }

}