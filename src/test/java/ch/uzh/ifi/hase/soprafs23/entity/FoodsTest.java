package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodsTest {

    @Test
    void setId_setsIdSuccessfully() {
        // Arrange
        Long expectedId = 1L;
        Foods foods = new Foods();

        // Act
        foods.setId(expectedId);
        Long actualId = foods.getId();

        // Assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void setName_setsNameSuccessfully() {
        // Arrange
        String expectedName = "Pizza";
        Foods foods = new Foods();

        // Act
        foods.setName(expectedName);
        String actualName = foods.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void setCategory_setsCategorySuccessfully() {
        // Arrange
        FoodCategory expectedCategory = FoodCategory.FRUITS;
        Foods foods = new Foods();

        // Act
        foods.setCategory(expectedCategory);
        FoodCategory actualCategory = foods.getCategory();

        // Assert
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void setHighres_setsHighresSuccessfully() {
        // Arrange
        boolean expectedHighres = true;
        Foods foods = new Foods();

        // Act
        foods.setHighres(expectedHighres);
        boolean actualHighres = foods.isHighres();

        // Assert
        assertEquals(expectedHighres, actualHighres);
    }
}

