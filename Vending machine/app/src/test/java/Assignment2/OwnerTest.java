package Assignment2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest {
    Owner o = new Owner("A", "123", 0);
    String last5 [] = {"pringle", "coke", "chips", "chocolate", "water"};
    Owner o1 = new Owner("A", "123", "salt", 0, last5);
    Goods g1 = new Goods("Coca", 0, "Drink", 0, 4 );
    
    @Test
    public void TestOwnerName() {
        o.getUsername().equals("A");
    }

    @Test
    public void TestOwnerAuth() {
        assertEquals(o1.getAuthority(), 0);
    }

    @Test
    public void TestEditAmount() {
        assertFalse(o.editAmount(g1, -1));
    }

    

    @Test
    public void TestEditPrice() {
        assertFalse(o.editPrice(g1, 20.0));
    }

    @Test
    public void TestEditPrice1() {
        assertFalse(o.editPrice(g1, -1.0));
    }

    @Test
    public void TestEditName() {
        assertFalse(o.editName(g1, "Cereal"));
    }
}
