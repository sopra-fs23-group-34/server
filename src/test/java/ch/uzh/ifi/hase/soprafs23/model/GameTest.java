package ch.uzh.ifi.hase.soprafs23.model;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;



import java.util.HashMap;

import java.util.Map;

class GameTest {




    @Test
    void testGame() {
        // Create some test players
        Map<Long, Player> players = new HashMap<>();
        Player player1 = new Player("name1",1L,false);
        Player player2 = new Player("name2",2L, false);

        // Create a test GameConfig object
        GameConfig config = new GameConfig();
        config.setFoodCategory(FoodCategory.FRUITS);
        config.setRoundLimit(2);
        config.setTimerLength(2);

        // Mock FoodService and Notifier
        FoodService foodService = mock(FoodService.class);
        UserService userService = mock(UserService.class);
        Notifier notifier = mock(Notifier.class);

        // Create a Game object and run it
        Game game = new Game(players, config, notifier, foodService, userService);
        Game game1 = new Game(players, config,notifier, foodService, userService);
        assertEquals(game.getClass(), game1.getClass());

    }
}

