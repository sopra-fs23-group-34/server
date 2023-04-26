package ch.uzh.ifi.hase.soprafs23.model;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerMessageTest {
    @Test
    void testGetRoundLimit() {
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setRoundLimit(10);
        assertEquals(10, playerMessage.getRoundLimit());
    }
    @Test
    void testGetFoodCategory() {
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setFoodCategory(FoodCategory.FRUITS);
        assertEquals(FoodCategory.FRUITS, playerMessage.getFoodCategory());
    }
    @Test
    void testSetRoundLimit() {
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setRoundLimit(10);
        assertEquals(10, playerMessage.getRoundLimit());
        playerMessage.setRoundLimit(20);
        assertEquals(20, playerMessage.getRoundLimit());
    }
    @Test
    void testSetFoodCategory() {
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setFoodCategory(FoodCategory.FRUITS);
        assertEquals(FoodCategory.FRUITS, playerMessage.getFoodCategory());
        playerMessage.setFoodCategory(FoodCategory.MEAT);
        assertEquals(FoodCategory.MEAT, playerMessage.getFoodCategory());
    }
}
