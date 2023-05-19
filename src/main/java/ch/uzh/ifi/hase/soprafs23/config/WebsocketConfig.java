package ch.uzh.ifi.hase.soprafs23.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(SIMPLEBROKERDESTINATION);
        config.setApplicationDestinationPrefixes(APPLICATIONDESTINATION);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("http://localhost:3000", "https://sopra-fs23-group-34-client.oa.r.appspot.com").withSockJS();
    }

    public static final String APPLICATIONDESTINATION = "/app";
    public static final String SIMPLEBROKERDESTINATION = "/topic";
    public static final String LOBBIES = SIMPLEBROKERDESTINATION + "/lobbies/";
    public static final String LOBBYSDESTINATION = LOBBIES + "{gameCode}";
    public static final String ERRORDESTINATION = SIMPLEBROKERDESTINATION + "/error/"  ;
}