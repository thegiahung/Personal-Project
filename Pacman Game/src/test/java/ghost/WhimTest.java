package ghost;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

public class WhimTest {
    PApplet pApplet = new PApplet();

    // Test if whim is chasing to the right.
    @Test
    public void testChaseRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(144);
        whim.setYPosition(304);
        whim.tick(1);
        assertNotNull(whim.chase());

    }

    // Test if whim is chasing to the left.
    @Test
    public void testChaseLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(288);
        whim.setYPosition(320);
        whim.tick(1);
        assertNotNull(whim.chase());
    }

    // Test if whim is chasing upwards.
    @Test
    public void testChaseUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(144);
        waka.setXPosition(288);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(144);
        whim.setYPosition(320);
        whim.tick(1);
        assertNotNull(whim.chase());
    }

    // Test if whim is chasing downwards.
    @Test
    public void testChaseDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(144);
        waka.setXPosition(320);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(144);
        whim.setYPosition(224);
        whim.tick(1);
        assertNotNull(whim.chase());
    }

    // Test if whim is scatter to the right
    @Test
    public void testScatterRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(1*16);
        whim.setYPosition(23*16);
        whim.tick(1);
        assertNotNull(whim.scatter());
    }

    // Test if whim is scatter to the left
    @Test
    public void testScatterLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(12*16);
        whim.setYPosition(4*16);
        whim.tick(1);
        assertNotNull(whim.scatter());
    }

    // Test if whim is scatter upwards
    @Test
    public void testScatterUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(1*16);
        whim.setYPosition(8*16);
        whim.tick(1);
        assertNotNull(whim.scatter());
    }

    // Test if whim is scatter downwards.
    @Test
    public void testScatterDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(15*16);
        whim.setYPosition(4*16);
        whim.tick(1);
        assertNotNull(whim.scatter());
    }

    // Test what if whim do after reaching corner.
    @Test
    public void testScatterCorner() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Whim whim = new Whim(pApplet, map, waka, 'w' );
        whim.setXPosition(26*16);
        whim.setYPosition(32*16);
        whim.tick(1);
        assertNotNull(whim.scatter());
    }

}