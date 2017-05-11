
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
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import com.sendgrid.*;

/**
 * Created by Liger on 28-Feb-17.
 */

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private VelocityEngine velocityEngine;
    private Properties properties;

    @Autowired
    public EmailServiceImpl() {
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
    public void sendWelcomeMailNewMember(String recipientEmail, String memberName) throws IOException {
        veInit();
        VelocityContext context = new VelocityContext();
        context.put("name", memberName);
        Template t = velocityEngine.getTemplate("templates/email/newRegister_html.vm", "UTF-8");
        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        System.out.println("Sending email...");
        SendGrid sendGrid = new SendGrid("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");
        Email from = new Email("no-reply.ordering@alfrescos.com");
        from.setName("Alfresco's Restaurant Ordering System");
        String subject = "Thank you for using our Ordering System";
        Email to = new Email(recipientEmail);
        Content content = new Content("text/html", writer.toString());
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sendGrid.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
            System.out.println("Sent welcome email successfully!");
        } catch (IOException ex) {
            throw new IOException(ex);
        }
//        try {
//            Session session = getSession();
//            MimeMessage mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress("noreply.orderingsystem@alfrescos.com", "Alfresco's Restaurant Ordering System"));
//            mimeMessage.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientEmail));
//            mimeMessage.setSubject("Thank you for using our Ordering System");
//            mimeMessage.setContent(writer.toString(), "text/html; charset=utf-8");
//            javaMailSender.send(mimeMessage);
//            System.out.println("Email Sent!");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    @Async
    public void sendForgotPasswordMail(String recipientEmail, String memberName, String newPassword, String link) throws IOException {
        veInit();
        VelocityContext context = new VelocityContext();
        context.put("name", memberName);
        context.put("link", link);
        Template t = velocityEngine.getTemplate("templates/email/forgotPassword_html.vm", "UTF-8");
        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        System.out.println("Sending email...");
        SendGrid sendGrid = new SendGrid("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");
        Email from = new Email("no-reply.ordering@alfrescos.com");
        from.setName("Alfresco's Restaurant Ordering System");
        String subject = "Reset your account's password in Alfresco's Restaurant Ordering System";
        Email to = new Email(recipientEmail);
        Content content = new Content("text/html", writer.toString());
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sendGrid.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
            System.out.println("Sent successfully reset password email!");
        } catch (IOException ex) {
            throw new IOException(ex);
        }
//        try {
//            Session session = getSession();
//            MimeMessage mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress("noreply.orderingsystem@alfrescos.com", "Alfresco's Restaurant Ordering System"));
//            mimeMessage.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientEmail));
//            mimeMessage.setSubject("Reset your account's password in Alfresco's Restaurant Ordering System");
//            mimeMessage.setContent(writer.toString(), "text/html; charset=utf-8");
//            Transport.send(mimeMessage);
//            System.out.println("Email Sent!");

//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

//    private Session getSession() {
//
////        final String username = "alfrescos.res.ordering@gmail.com";
////        final String password = "NGOquang20575485";
//        final String username = "quangghost262@gmail.com";
//        final String password = "ngoquang20575485";
////        final String username = "ngotruongphamquang@yahoo.com";
////        final String password = "NGO46478264";
//
//        Properties props = new Properties();
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.socketFactory.port","465");
////        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
////        props.put("mail.smtp.host","smtp.mail.yahoo.com");
////        props.put("mail.smtp.port","465");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        return session;
//    }

}