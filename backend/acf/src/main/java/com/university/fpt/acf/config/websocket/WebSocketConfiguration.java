package com.university.fpt.acf.config.websocket;

import com.university.fpt.acf.config.websocket.model.UserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Value( "${acf.scross.path}" )
    private String path;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //************************************
        // config endpoint để clien có thể kết nối và đẩy sự kiện
        //************************************
        registry.addEndpoint("/wse/hello")
                .setAllowedOrigins(path)
                .withSockJS();

        registry.addEndpoint("/wse/online")
                .setAllowedOrigins(path)
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //************************************
        // cấu hình nơi để client có thể đăng ký để nhận notification
        //************************************
        registry.enableSimpleBroker("queue");
        registry.setApplicationDestinationPrefixes("/ws");
        registry.setUserDestinationPrefix("/users");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }
}
