package ghost;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class WakaTest {
    public PApplet pApplet;

    // Test if set lives can give output as expected.
    @Test
    public void testSetLives() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setLives(2);
        assertTrue(waka.getLives() == 2);
    }

    // Test if it can give the number of lives as expected.
    @Test
    public void testGetLives() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assert waka.getLives() == 3;
    }

    // Test if get lives is not null.
    @Test
    public void testGetLivesNotNull() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assertNotNull(waka.getLives());
    }

    // Test if the initial X position not null.
    @Test
    public void testInitXPos() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assertNotNull(waka.initXPos());
    }

    // Test if the initial Y position not null.
    @Test
    public void testInitYPos() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assertNotNull(waka.initYPos());
    }

    // Test if the waka positon in pixel is not null.
    @Test
    public void testGetWakaPos() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assertNotNull(waka.getWakaPixelPos());
    }

    // Test if the waka position in grid not null
    @Test
    public void testGetXGridPos() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assertNotNull(waka.getXGridPos());
    }

    // Test if the waka position in grid not null
    @Test
    public void testGetYGridPos() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assertNotNull(waka.getYGridPos());
    }

    // Test if waka collect all the fruit on map
    @Test
    public void testCollectAll() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '7') {
                    map[i][j] = '0';
                }
            }
        }
        assertTrue(waka.collectedAll());
    }

    // Test if waka collect all the fruit on map
    @Test
    public void testNotcollectAll() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        map[1][4] = '0';
        assertFalse(waka.collectedAll());
    }

    // Test if the current direction of waka is as expected.
    @Test
    public void testGetDir() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        assertTrue(waka.getDir().equals("right"));
    }

    // Test if set position method can give the X positon as expected.
    @Test
    public void testSetXPostion() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(209);
        assertTrue(waka.getWakaPixelPos()[0] == 209);
    }

    // Test if set position method can give the Y positon as expected.
    @Test
    public void testSetYPostion() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setYPosition(64);
        assertTrue(waka.getWakaPixelPos()[1] == 64);
    }

    // Test it can reset the waka to initial state.
    @Test
    public void testReset() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.reset();
        assertTrue(waka.getDir().equals("right"));
        assertTrue(waka.getWakaPixelPos()[0] == waka.initXPos());
        assertTrue(waka.getWakaPixelPos()[1] == waka.initYPos());
    }

    // Test waka move down when pressing key.
    @Test
    public void testKeyPressDown() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(0*16);
        waka.setYPosition(23*16);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x28, true));
        waka.tick(1);
        waka.tick(1);
        assertTrue(waka.getDir().equals("down"));
        assertNotNull(waka.getXGridPos());
        assertNotNull(waka.getYGridPos());
    }

    // Test waka move up when pressing key.
    @Test
    public void testKeyPressUp() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(0*16);
        waka.setYPosition(23*16);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x26, true));
        waka.tick(1);
        waka.tick(1);
        assertTrue(waka.getDir().equals("up"));
        assertNotNull(waka.getXGridPos());
        assertNotNull(waka.getYGridPos());
    }

    // Test waka move up when pressing key.
    @Test
    public void testKeyPressUp2() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(6*16);
        waka.setYPosition(17*16);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x26, true));
        waka.tick(1);
        waka.tick(1);
        assertTrue(waka.getDir().equals("up"));
        assertNotNull(waka.getXGridPos());
        assertNotNull(waka.getYGridPos());
    }

    // Test waka move left when pressing key.
    @Test
    public void testKeyPressLeft() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(5*16);
        waka.setYPosition(22*16);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x25, true));
        waka.tick(1);
        waka.tick(1);
        assertTrue(waka.getDir().equals("left"));
        assertNotNull(waka.getXGridPos());
        assertNotNull(waka.getYGridPos());
    }

    // Test waka move left when pressing key.
    @Test
    public void testKeyPressLeft2() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(6*16);
        waka.setYPosition(17*16);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x25, true));
        waka.tick(1);
        waka.tick(1);
        assertTrue(waka.getDir().equals("left"));
        assertNotNull(waka.getXGridPos());
        assertNotNull(waka.getYGridPos());
    }

    // Test waka move right when pressing key.
    @Test
    public void testKeyPressRight() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(7*16);
        waka.setYPosition(15*16);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x27, true));
        waka.tick(1);
        waka.tick(1);
        assertTrue(waka.getDir().equals("right"));
        assertNotNull(waka.getXGridPos());
        assertNotNull(waka.getYGridPos());
    }

    // Test waka move right when pressing key.
    @Test
    public void testKeyPressRight2() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.setXPosition(6*16);
        waka.setYPosition(17*16);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x27, true));
        waka.tick(1);
        waka.tick(1);
        assertTrue(waka.getDir().equals("right"));
        assertNotNull(waka.getXGridPos());
        assertNotNull(waka.getYGridPos());
    }

    // Test if waka can collect food when pressing right key
    @Test
    public void testCollectFood() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x27, true));
        waka.setXPosition(213);
        waka.tick(1);
        waka.tick(1);
        assertTrue(map[20][14] == '0');
    }

    // Test if waka can collect food when pressing left key
    @Test
    public void testCollectFood2() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x25, true));
        waka.setXPosition(203);
        waka.tick(1);
        waka.tick(1);
        assertTrue(map[20][12] == '0');
    }

    // Test if waka can collect food when pressing up key
    @Test
    public void testCollectFood3() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x26, true));
        waka.setXPosition(144);
        waka.setYPosition(315);
        waka.tick(1);
        waka.tick(1);
        assertTrue(map[19][9] == '0');
    }

    // Test if waka can collect food when pressing down key
    @Test
    public void testCollectFood4() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        waka.keyPressed(new KeyEvent(null, 1, 1, 1, (char) 1, 0x28, true));
        waka.setXPosition(144);
        waka.setYPosition(325);
        waka.tick(1);
        waka.tick(1);
        assertTrue(map[21][9] == '0');
    }
}