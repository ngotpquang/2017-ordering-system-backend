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
        User user = userService.findByEmail(authenticationRequest.getEmail());
        if (user == null || !passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Account not found!");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getAccountCode());
        final String token = this.jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity<?> createForgotPasswordToken(@Valid @RequestBody JwtAuthenticationRequest authenticationRequest) throws javax.naming.AuthenticationException {
        User user = userService.findByEmail(authenticationRequest.getEmail());
        if (user == null) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }

        String password = RandomStringUtils.randomAlphanumeric(10);
        final String token = this.jwtTokenUtil.generateToken(authenticationRequest.getEmail(), password, authenticationRequest.getUrlPath());

        try {
            emailService.sendForgotPasswordMail(authenticationRequest.getEmail(), user.getName(), password, authenticationRequest.getUrlPath() + "/api/auth/forgotPassword?token=" + token);
        } catch (Exception e) {
            // catch error
            System.out.println("Error while sending email: " + e.getMessage());
        }
        return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    void generatePassword(HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        System.out.println(token);
        String email = this.jwtTokenUtil.getEmailFromToken(token);
        String password = this.jwtTokenUtil.getPasswordFromToken(token);
        String urlPath = this.jwtTokenUtil.getUrlFromToken(token);

        if (email == null || password == null || urlPath == null || oldUsing.containsKey(email)) {
            response.sendRedirect(urlPath + "?mod=login&act=fail");
            System.out.println("Can't reset password");
        } else {
            oldUsing.put(email, token);
            User user = userService.findByAccountCode(email);
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            System.out.println(user.getAccountCode());
            response.sendRedirect(urlPath + "?mod=login&act=success&email=" + email);
        }
    }
}
