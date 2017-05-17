/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.socket;

import com.alfrescos.orderingsystem.common.CommonUtil;
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
    public String sendAdmin(String message) throws Exception{
        return CommonUtil.getLocalTime() + " - Al Fresco's channel for staff: " + message;
    }
}
