/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.security.controller;

import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.security.JwtAuthenticationRequest;
import com.alfrescos.orderingsystem.security.JwtAuthenticationResponse;
import com.alfrescos.orderingsystem.security.JwtTokenUtil;
import com.alfrescos.orderingsystem.security.service.JwtUserDetailsServiceImpl;
import com.alfrescos.orderingsystem.service.EmailService;
import com.alfrescos.orderingsystem.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liger on 28-Feb-17.
 */
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationRestController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private Map<String, String> oldUsing = new HashMap<>();

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        System.out.println(authenticationRequest.getEmail() + " - " + authenticationRequest.getPassword() + " - " + authenticationRequest.getUrlPath());
        User user = this.userService.findByEmail(authenticationRequest.getEmail());
        if (user == null || !passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Account not found!");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getAccountCode());
        final String token = this.jwtTokenUtil.generateToken(userDetails);
        user.setLastAccess(new Date());
        this.userService.updateLastAccess(user);
        return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> createForgotPasswordToken(@RequestBody Map<String, String> data) {
        String urlPath = data.get("urlPath").trim();
        String serverPath = "https://backend-os-v2.herokuapp.com/";
        String email = data.get("email").trim();
        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }

        String password = RandomStringUtils.randomAlphanumeric(10);
        final String token = this.jwtTokenUtil.generateToken(email, password, urlPath);
        try {
            emailService.sendForgotPasswordMail(email, user.getName(), password, serverPath + "api/auth/reset-password?token=" + token);
            return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
        } catch (IOException e) {
            // catch error
            System.out.println("Error while sending email: " + e.getMessage());
            return new ResponseEntity<Object>("Can't send email due to error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/reset-password")
    void generatePassword(HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        System.out.println(token);
        String email = this.jwtTokenUtil.getEmailFromToken(token);
        String password = this.jwtTokenUtil.getPasswordFromToken(token);
        String urlPath = this.jwtTokenUtil.getUrlFromToken(token);

        if (email == null || password == null || urlPath == null) {
            response.sendRedirect(urlPath);
            System.out.println("Can't reset password");
        } else {
            User user = userService.findByAccountCode(email);
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            System.out.println(user.getAccountCode());
            response.sendRedirect(urlPath + "reset-password?token=" + token);
        }
    }

    @PostMapping(value = "/reset-password")
    ResponseEntity<?> resetPassword(@RequestBody Map<String, String> data){
        String token = data.get("token").trim();
        String newPassword = data.get("newPassword").trim();
        String email = this.jwtTokenUtil.getEmailFromToken(token);
        String currentPassword = this.jwtTokenUtil.getPasswordFromToken(token);
        User user = this.userService.findByEmail(email);
        if (passwordEncoder.encode(currentPassword).equals(user.getPassword())){
            user.setPassword(newPassword);
            this.userService.save(user);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getAccountCode());
            final String tokenNew = this.jwtTokenUtil.generateToken(userDetails);
            return new ResponseEntity<Object>(new JwtAuthenticationResponse(tokenNew), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Error some where", HttpStatus.NOT_MODIFIED);
        }
    }
}
