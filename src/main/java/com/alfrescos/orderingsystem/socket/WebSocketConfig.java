/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeHandler;

/**
 * Created by Liger on 24-Mar-17.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/request");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint for Admin broadcast channel
        registry.addEndpoint("/admin").setAllowedOrigins("*");
        // Endpoint for Staff broadcast channel
        registry.addEndpoint("/staff").setAllowedOrigins("*");
    }
}
