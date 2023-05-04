package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;

public class RoundTest {

    @Test
    public void testGetFood() throws IOException {
        Notifier notifier = mock(Notifier.class);
        FoodService foodService = mock(FoodService.class);
        String name = "Apple";
        Food food = new Food(name, null, null);
        when(foodService.getFood(name)).thenReturn(food);
        Round round = new Round(notifier, foodService);
        assertEquals(food, round.getFood(name));
        verify(foodService).getFood(name);
    }

    @Test
    public void testRun() throws InterruptedException {
        Notifier notifier = mock(Notifier.class);
        FoodService foodService = mock(FoodService.class);
        String name = "Apple";
        Food food = new Food(name, null, null);
        Round round = new Round(notifier, foodService);
        round.run(food, 10);
        verify(notifier).publishRoundStart();
        verify(notifier).publishFood(food);
        for (int tick = 10; tick >= 0; tick --){
            verify(notifier).publishTimer(tick);
            Thread.sleep(1000);
        }
        verify(notifier).publishRoundScoreStart();
    }
}

