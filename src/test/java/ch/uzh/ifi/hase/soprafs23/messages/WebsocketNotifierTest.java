package ch.uzh.ifi.hase.soprafs23.messages;
import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.model.WebsocketNotifier;

import org.testng.annotations.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;

import static org.mockito.Mockito.*;

public class WebsocketNotifierTest {

/*
        @Mock
        private SimpMessagingTemplate messagingTemplate;
        private String gameCode = "1";
        private String lobbies = "2";
        private WebsocketNotifier myNotifier;
        private Map<String, Double> foodStats;


        public void setup() {
            MockitoAnnotations.initMocks(this);
            myNotifier = new WebsocketNotifier(messagingTemplate,gameCode);
        }

        @Test
        public void testPublishFood() {
            // Create a Food object to publish
            Food food = new Food("Pizza",foodStats,"myPizzaPicture");

            // Call the publishFood method
            myNotifier.publishFood(food);

            // Verify that the SimpMessagingTemplate's convertAndSend method was called
            //verify(messagingTemplate).convertAndSend(WebsocketConfig.LOBBIES + gameCode, food);

    }*/

}
