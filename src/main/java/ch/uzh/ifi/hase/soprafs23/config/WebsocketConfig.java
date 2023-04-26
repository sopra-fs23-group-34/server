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
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    public final static String APPLICATIONDESTINATION = "/app";
    public final static String SIMPLEBROKERDESTINATION = "/topic";
    public final static String LOBBIES = SIMPLEBROKERDESTINATION + "/lobbies/";
    public final static String LOBBYSDESTINATION = LOBBIES + "{gameCode}";
    public final static String ERRORDESTINATION = SIMPLEBROKERDESTINATION + "/error/"  ;
}