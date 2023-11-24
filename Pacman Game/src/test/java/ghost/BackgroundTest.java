package ghost;

import org.junit.Test;
import processing.core.PApplet;

public class BackgroundTest {
    PApplet pApplet = new PApplet();

    // Test constructor of map
    @Test
    public void mapConstructor() {
        char[][] map = Config.load().getMap();
        Background background = new Background(pApplet, map);
    }

}