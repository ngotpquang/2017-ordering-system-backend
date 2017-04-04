
/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by Liger on 28-Feb-17.
 */

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private VelocityEngine velocityEngine;
    private Properties properties;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void veInit() {
        properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine = new VelocityEngine();
        velocityEngine.init(properties);
    }

    @Async
    public void sendWelcomeMailNewMember(String recipientEmail, String memberName) throws MailException {
        veInit();
        VelocityContext context = new VelocityContext();
        context.put("name", memberName);
        context.put("email", recipientEmail);
        Template t = velocityEngine.getTemplate("templates/email/newRegister_html.vm", "UTF-8");
        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        System.out.println("Sending email...");
        try {
            Session session = getSession();
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("orderingsystem@alfrescosrestaurant.com", "Alfresco's Restaurant Ordering System"));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            mimeMessage.setSubject("Thank you for using our Ordering System");
            mimeMessage.setContent(writer.toString(), "text/html; charset=utf-8");
            javaMailSender.send(mimeMessage);
            System.out.println("Email Sent!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendForgotPasswordMail(String recipientEmail, String memberName, String newPassword, String link) throws MailException {
        veInit();
        VelocityContext context = new VelocityContext();
        context.put("name", memberName);
        context.put("email", recipientEmail);
        context.put("password", newPassword);
        context.put("link", link);

        System.out.println("Link to reset password: " + link);

        Template t = velocityEngine.getTemplate("templates/email/forgotPassword_html.vm", "UTF-8");
        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        System.out.println("Sending email...");
        try {

            Session session = getSession();

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("orderingsystem@alfrescosrestaurant.com", "Alfresco's Restaurant Ordering System"));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            mimeMessage.setSubject("Your login details for Alfresco's Restaurant Ordering System");
            mimeMessage.setContent(writer.toString(), "text/html; charset=utf-8");
            Transport.send(mimeMessage);
            System.out.println("Email Sent!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Session getSession() {

        final String username = "quangghost262@gmail.com";
        final String password = "ngoquang20575485";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        return session;
    }

}