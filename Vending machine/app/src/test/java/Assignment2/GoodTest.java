package Assignment2;

import org.junit.jupiter.api.Test;

public class GoodTest {
    Goods g1 = new Goods("Coca", 0, "Drink", 0, 4 );
    Goods g2 = new Goods("Kitkat", 0, "Chocolates", 0, 10 );
    
    // Test if we can edit quantity
    @Test
    public void TestEditQ() {
        g1.editQuantity(10);
    }

    // Test if we can edit quantity
    @Test
    public void TestGetType() {
        g1.getType();
    }
}
