package ghost;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

public class ChaserTest {
    PApplet pApplet = new PApplet();

    // Test if chaser is chasing to the right
    @Test
    public void testChaseRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(144);
        chaser.setYPosition(304);
        chaser.tick(1);
        assertNotNull(chaser.chase());

    }

    // Test if chaser is chasing to the left
    @Test
    public void testChaseLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(288);
        chaser.setYPosition(320);
        chaser.tick(1);
        assertNotNull(chaser.chase());
    }

    // Test if chaser is chasing upwards.
    @Test
    public void testChaseUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(144);
        waka.setXPosition(288);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(144);
        chaser.setYPosition(320);
        chaser.tick(1);
        assertNotNull(chaser.chase());
    }

    // Test if chaser is chasing downwards.
    @Test
    public void testChaseDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(144);
        waka.setXPosition(320);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(144);
        chaser.setYPosition(224);
        chaser.tick(1);
        assertNotNull(chaser.chase());
    }

    // Test if chaser is scatter to the right
    @Test
    public void testScatterRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(1*16);
        chaser.setYPosition(23*16);
        chaser.tick(1);
        assertNotNull(chaser.scatter());
    }

    // Test if chaser is scatter to the left
    @Test
    public void testScatterLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(12*16);
        chaser.setYPosition(4*16);
        chaser.tick(1);
        assertNotNull(chaser.scatter());
    }

    // Test if chaser is scatter upwards
    @Test
    public void testScatterUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(1*16);
        chaser.setYPosition(8*16);
        chaser.tick(1);
        assertNotNull(chaser.scatter());
    }

    // Test if chaser is scatter downwards
    @Test
    public void testScatterDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(15*16);
        chaser.setYPosition(4*16);
        chaser.tick(1);
        assertNotNull(chaser.scatter());
    }

    // Test what if scatter go when reaching corner
    @Test
    public void testScatterCorner() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Chaser chaser = new Chaser(pApplet, map, waka, 'c' );
        chaser.setXPosition(1*16);
        chaser.setYPosition(4*16);
        chaser.tick(1);
        assertNotNull(chaser.scatter());
    }

}