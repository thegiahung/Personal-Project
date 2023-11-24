package ghost;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    // Test whether the map is null.
    @Test
    public void testGetMap() {
        assertNotNull(Config.load().getMap());
    }

    // Test whether if lives is equals as expected.
    @Test
    public void testGetLives() {
        int lives = 3;
        assertTrue(Config.load().getLives() == lives);
    }

    // Test if the array of mode length is the same as expected.
    @Test
    public void testGetModeLength() {
        Long[] expectedModelength = {7L, 20L, 7L, 20L, 5L, 20L, 5L, 1000L};
        Assert.assertArrayEquals(expectedModelength, Config.load().getModeLengths());
        assertNotNull(Config.load().getModeLengths());
    }

    // Test whether the speed is as expected.
    @Test
    public void testGetSpeed() {
        int speed = 1;
        assertTrue(Config.load().getSpeed() == speed);
    }

}