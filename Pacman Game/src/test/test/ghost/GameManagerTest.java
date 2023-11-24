package ghost;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameManagerTest {
    PApplet pApplet = new PApplet();

    // Test the draw method
    @Test
    public void testDraw() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        PApplet.runSketch(new String[]{"must be something here"}, new App());
    }

    // Test if the waka is not null.
    @Test
    public void testGetWaka() {
        char[][] map = Config.load().getMap();
        Waka waka = new Waka(pApplet, map);
        GameManager gameManager = new GameManager(pApplet);
        assertNotNull(gameManager.getWaka());
    }
}