/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import java.util.List;

/**
 * Created by Liger on 28-Feb-17.
 */
public interface EmailService {

    public void sendWelcomeMailNewMember(String receptorEmail, String memberName);

    public void sendForgotPasswordMail(String receptorEmail, String memberName, String newPassword, String link);

}
