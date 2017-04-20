/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.socket;

import com.alfrescos.orderingsystem.common.UserUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Liger on 27-Mar-17.
 */
@Controller
public class MessageController {

    /*
        Broadcast channel for:
        1. Customer sends request to admin after ordering.
        2. Admin subscribes to receive request from customers.
     */
    @MessageMapping(value = {"/admin"})
    @SendTo("/request/admin")
    public String sendAdmin(String message){
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println(time + " - Admin broadcast channel: " + message);
        return time + " - Al Fresco's channel for staff: " + message;
    }

    /*
        Broadcast channel for:
        1. Customer or Admin sends request to admin after ordering.
        2. Admin or Staff subscribes to receive request from customers.
     */
    @MessageMapping("/waiter")
    @SendTo("/request/waiter")
    public String send(String message){
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return time + " - Waiter broadcast channel: " + message;
    }
}
