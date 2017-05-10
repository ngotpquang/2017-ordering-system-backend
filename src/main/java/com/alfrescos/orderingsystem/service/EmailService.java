/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Liger on 28-Feb-17.
 */
public interface EmailService {

    void sendWelcomeMailNewMember(String receptorEmail, String memberName);

    void sendForgotPasswordMail(String receptorEmail, String memberName, String newPassword, String link) throws IOException;

}
