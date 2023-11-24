package ghost;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class AmbusherTest {
    PApplet pApplet = new PApplet();

    // Test the image is existed or not
    @Test
    public void testGetFileImage() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertNotNull(ambusher.getImageFileName());
    }

    // Test the direction of waka should not be null
    @Test
    public void testGetDir() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        assertNotNull(ambusher.getDir());
    }

    // Test the set position method can give the expected position or not
    @Test
    public void testSetXPosition() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(180);
        ambusher.setYPosition(210);
        int[] expectedPosition = {180, 210};
        Assert.assertArrayEquals(expectedPosition, ambusher.getGhostPixelPos());
    }

    // Test the direction it needs to chase waka when in chase mode
    @Test
    public void testChase() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        Direction direction = ambusher.chase();
        assertNotNull(direction);
    }

    // Test the direction it needs to chase waka when in scatter mode
    @Test
    public void testScatter() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        Direction direction = ambusher.scatter();
        assertNotNull(direction);
    }

    // The test is to find the destination in maximum at 4 grid space from Waka to the right
    @Test
    public void testDestinationChaseRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        int expPos = 17;
        assertNotNull(ambusher.chase());
        waka.setXPosition(240);
        int expPosWithObj = 18;
        assertNotNull(ambusher.chase());
        waka.setXPosition(256);
        assertNotNull(ambusher.chase());
        waka.setXPosition(272);
        assertNotNull(ambusher.chase());
        waka.setXPosition(288);
        assertNotNull(ambusher.chase());
    }

    // The test is to find the destination in maximum at 4 grid space from Waka to the left
    @Test
    public void testDestinationChaseLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x25, true));
        waka.tick(1);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        int expPos = 9;
        assertNotNull(ambusher.chase());
        waka.setXPosition(12*16);
        assertNotNull(ambusher.chase());
        waka.setXPosition(11*16);
        assertNotNull(ambusher.chase());
        waka.setXPosition(10*16);
        assertNotNull(ambusher.chase());
        waka.setXPosition(9*16);
        assertNotNull(ambusher.chase());
    }

    // The test is to find the destination in maximum at 4 grid space from Waka upwards.
    @Test
    public void testDestinationChaseUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x26, true));
        waka.tick(1);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        int expPos = 4;
        waka.setXPosition(1*16);
        waka.setYPosition(8*16);
        ambusher.setXPosition(1*16);
        ambusher.setYPosition(10*16);

        assertNotNull(ambusher.chase());
        waka.setYPosition(7*16);
        assertNotNull(ambusher.chase());
        waka.setYPosition(6*16);
        assertNotNull(ambusher.chase());
        waka.setYPosition(5*16);
        assertNotNull(ambusher.chase());
        waka.setYPosition(4*16);
        assertNotNull(ambusher.chase());
    }

    // The test is to find the destination in maximum at 4 grid space from Waka to the downwards.
    @Test
    public void testDestinationChaseDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x28, true));
        waka.tick(1);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        int expPos = 17;
        waka.setXPosition(1*16);
        waka.setYPosition(13*16);
        ambusher.setXPosition(1*16);
        ambusher.setYPosition(10*16);

        assertNotNull(ambusher.chase());
        waka.setYPosition(14*16);
        assertNotNull(ambusher.chase());
        waka.setYPosition(15*16);
        assertNotNull(ambusher.chase());
        waka.setYPosition(16*16);
        assertNotNull(ambusher.chase());
        waka.setYPosition(17*16);
        assertNotNull(ambusher.chase());
    }

    // Test whether the ambusher is going to the right in scatter mode
    @Test
    public void testDestinationScatterRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(240);
        ambusher.tick(1);
        assertNotNull(ambusher.scatter());
    }

    // Test whether the ambusher is going to the left in scatter mode
    @Test
    public void testDestinationScatterLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(240);
        ambusher.setYPosition(432);
        ambusher.tick(1);
        assertNotNull(ambusher.scatter());
    }

    // Test whether the ambusher is going downwards in scatter mode
    @Test
    public void testDestinationScatterDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(192);
        ambusher.setYPosition(64);
        ambusher.tick(1);
        assertNotNull(ambusher.scatter());
    }

    // Test whether ambusher is going upwards in scatter mode
    @Test
    public void testDestinationScatterUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(416);
        ambusher.setYPosition(368);
        ambusher.tick(1);
        assertNotNull(ambusher.scatter());
    }

    // Test what if the ambusher do when reaching corner
    @Test
    public void testScatterCorner() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        Ambusher ambusher = new Ambusher(pApplet, map, waka, 'a' );
        ambusher.setXPosition(416);
        ambusher.setYPosition(64);
        assertNotNull(ambusher.scatter());
    }
}