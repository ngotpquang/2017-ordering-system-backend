
/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String ApiUrl = "https://api1.27hub.com/api/emh/a/v2";
    private static final String QueryFormatString = "%1$s?e=%2$s&k=%3$s";
    // API Key of verifying email of Hippo
    private static final String YourAPIKey = "xxx";

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
        // API Key of SendGrid
        SendGrid sendGrid = new SendGrid("API_KEY");
        Email from = new Email("no-reply.ordering@alfrescos.com");
        from.setName("Alfresco's Restaurant Ordering System");
        String subject = "Thank you for using our Ordering System";

        Email to = new Email(recipientEmail);
        Content content = new Content("text/html", writer.toString());
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            URL requestUrl = new URL(String.format(QueryFormatString, ApiUrl, recipientEmail, YourAPIKey));
            System.out.println(requestUrl.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));

            String inputLine;
            StringBuilder responseHippo = new StringBuilder();

            // Read in the response line from the server
            while ((inputLine = reader.readLine()) != null) {
                responseHippo.append(inputLine);
                System.out.println(inputLine);
            }

            // Close the reader
            reader.close();

            // Output the result to console
            System.out.println("Response from Hippo: " + responseHippo.toString());
            if(!responseHippo.toString().contains("Bad")){
                request.method = Method.POST;
                request.endpoint = "mail/send";
                request.body = mail.build();
                Response response = sendGrid.api(request);
                System.out.println(response.statusCode);
                System.out.println(response.body);
                System.out.println(response.headers);
                System.out.println("Sent welcome email successfully!");
            } else {
                throw new IOException();
            }
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
        // API Key of SendGrid
        SendGrid sendGrid = new SendGrid("API_KEY");
        Email from = new Email("no-reply.ordering@alfrescos.com");
        from.setName("Alfresco's Restaurant Ordering System");
        String subject = "Reset your account's password in Alfresco's Restaurant Ordering System";
        Email to = new Email(recipientEmail);
        Content content = new Content("text/html", writer.toString());
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            URL requestUrl = new URL(String.format(QueryFormatString, ApiUrl, recipientEmail, YourAPIKey));
            HttpURLConnection httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));

            String inputLine;
            StringBuilder responseHippo = new StringBuilder();

            // Read in the response line from the server
            while ((inputLine = reader.readLine()) != null) {
                responseHippo.append(inputLine);
            }

            // Close the reader
            reader.close();

            // Output the result to console
            System.out.println("Response from Hippo: " + responseHippo.toString());
            if (!responseHippo.toString().contains("Bad")) {
                request.method = Method.POST;
                request.endpoint = "mail/send";
                request.body = mail.build();
                Response response = sendGrid.api(request);
                System.out.println(response.statusCode);
                System.out.println(response.body);
                System.out.println(response.headers);
                System.out.println("Sent successfully reset password email!");
            } else {
                throw new IOException();
            }
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