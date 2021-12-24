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
        // cấu hình prefix của các endpoint mà các client có thể subscribe vào để nhận mess từ serve
        registry.enableSimpleBroker("queue");
        // cấu hình prefix của các destination  mà client sẽ gửi thông báo cho serve
        registry.setApplicationDestinationPrefixes("/ws");
        // cấu hình prefix end point nhận mess theo user mặc định là users
        registry.setUserDestinationPrefix("/users");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }
}
