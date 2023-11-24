package Assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {
    VendingMachine v1 = new VendingMachine();
    Goods g1 = new Goods("Coca", 0, "Drink", 1002, 4 );
    Goods g2 = new Goods("Kitkat", 0, "Chocolates", 1001, 10 );

    // Cant put in cuz not appropriate amount
    @Test
    public void TestinOutCart1() {
        v1.goods.add(g1);
        v1.goods.add(g2);
        v1.inOutCart(1001, 10);
    }

    // @Test
    // public void TestCalculator() {
    //     v1.goods.add(g1);
    //     v1.goods.add(g2);
    //     v1.calculator();
    // }

    @Test
    public void TestClearCart () {
        v1.goods.add(g1);
        v1.goods.add(g2);
        v1.clearCart();
    }

    @Test
    public void TestInVending() {
        v1.goods.add(g1);
        v1.goods.add(g2);
        v1.inVendingmachine();
    }


    // Cant put in cuz not appropriate amount
    // @Test
    // public void TestinOutCart1() {
    //     v1.goods.add(g1);
    //     v1.goods.add(g2);
    //     v1.inOutCart(1001, 10);
    // }

    @Test
    public void TestSold() {
        v1.goods.add(g1);
        v1.goods.add(g2);
        v1.getsold();
    }
  
  
}
