package ghost;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

public class IgnorantTest {
    PApplet pApplet = new PApplet();

    // Test if ignorant is chasing to the right.
    @Test
    public void testChaseRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(143);
        ignorant.setYPosition(20*16);
        ignorant.tick(1);
        assertNotNull(ignorant.chase());

    }

    // Test if ignorant is chasing to the left.
    @Test
    public void testChaseLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(18*16);
        ignorant.setYPosition(20*16);
        ignorant.tick(1);
        assertNotNull(ignorant.chase());
    }

    // Test if ignorant is chasing upwards.
    @Test
    public void testChaseUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(1*16);
        waka.setYPosition(29*16);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(1*16);
        ignorant.setYPosition(32*16);
        ignorant.tick(1);
        assertNotNull(ignorant.chase());
    }

    // Test if ignorant is chasing downwards.
    @Test
    public void testChaseDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(1*16);
        waka.setYPosition(32*16);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(1*16);
        ignorant.setYPosition(29*16);
        ignorant.tick(1);
        assertNotNull(ignorant.chase());
    }

    // Test if ignorant is scatter upwards.
    @Test
    public void testScatterUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(21*16);
        ignorant.setYPosition(29*16);
        ignorant.tick(1);
        assertNotNull(ignorant.scatter());
    }

    // Test if ignorant is scatter to the right.
    @Test
    public void testScatterRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(1*16);
        ignorant.setYPosition(17*16);
        ignorant.tick(1);
        assertNotNull(ignorant.scatter());
    }

    // Test if ignorant is scatter downwards.
    @Test
    public void testScatterDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(1*16);
        ignorant.setYPosition(29*16);
        ignorant.tick(1);
        assertNotNull(ignorant.scatter());
    }

    // Test if ignorant is scatter to the left.
    @Test
    public void testScatterLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ignorant ignorant = new Ignorant(pApplet, map, waka, 'c' );
        ignorant.setXPosition(12*16);
        ignorant.setYPosition(4*16);
        ignorant.tick(1);
        assertNotNull(ignorant.scatter());
    }

    // Test what if ignorant do when reaching corner
    @Test
    public void testScatterCorner() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(1*16);
        chaser.setYPosition(32*16);
        chaser.tick(1);
        assertNotNull(chaser.scatter());
    }
}