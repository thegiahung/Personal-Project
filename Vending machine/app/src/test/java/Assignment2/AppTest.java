package Assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // Test the constructor
    @Test
    public void testAppConstructor() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest);
    }
}
