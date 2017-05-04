/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.security.controller;

import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.security.JwtAuthenticationResponse;
import com.alfrescos.orderingsystem.security.JwtTokenUtil;
import com.alfrescos.orderingsystem.service.PermissionService;
import com.alfrescos.orderingsystem.service.RoleService;
import com.alfrescos.orderingsystem.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.google.api.Google;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by Liger on 28-Feb-17.
 */
@RestController
@RequestMapping(value = "/api/auth/social")
public class SocialAccountController {

    private Facebook facebook;

    private Google google;

    private HttpTransport transport = new NetHttpTransport();

    private JacksonFactory jsonFactory = new JacksonFactory();

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/facebook")
    private ResponseEntity<?> signUpByFacebook(@RequestBody Map<String, String> data) {

        String accessToken = data.get("accessToken");
        facebook = new FacebookTemplate(accessToken);
        String[] fields = {"id", "email", "name", "gender"};
        org.springframework.social.facebook.api.User userSocial = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
        if (userService.findByAccountCode(userSocial.getId()) != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userSocial.getId());
            String token = this.jwtTokenUtil.generateToken(userDetails);
            System.out.println("Facebook: Registered already");
            return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
        } else if ((userSocial.getEmail() != null && userService.findByEmail(userSocial.getEmail()) != null)){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userSocial.getEmail());
            String token = this.jwtTokenUtil.generateToken(userDetails);
            System.out.println("Facebook: Registered already");
            return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
        }
        else {
            User user = new User();
            user.setAccountCode(userSocial.getId());
            if (userSocial.getEmail() != null) {
                user.setEmail(userSocial.getEmail());
            } else {
                user.setEmail(user.getAccountCode());
            }
            user.setGender((byte) (userSocial.getGender().equals("male") ? 0 : 1));
            user.setName(userSocial.getName());
            user.setAvatar("http://graph.facebook.com/" + userSocial.getId() + "/picture?width=961");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(userSocial.getId());
            user.setPassword(password);
            User newUser = userService.create(user);
            Role role = roleService.findById(new Long(4));
            permissionService.create(newUser, role);
            UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getAccountCode());
            String token = this.jwtTokenUtil.generateToken(userDetails);
            return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/google")
    private ResponseEntity<?> signUpByGoogle(@RequestBody Map<String, String> accessTokenClient) {
        String idTokenString = accessTokenClient.get("idTokenString");
        String CLIENT_ID = "841843481336-uaje8r0pj66c5g4dj099o9vqa1lmb78g.apps.googleusercontent.com";
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(this.transport, this.jsonFactory).setAudience(Collections.singletonList(CLIENT_ID)).build();
        UserDetails userDetails = null;
        String token = "";

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            if (this.userService.findByAccountCode(payload.getEmail()) != null || this.userService.findByEmail(payload.getEmail()) != null) {
                String accountCode = this.userService.findByEmail(payload.getEmail()).getAccountCode();
                userDetails = userDetailsService.loadUserByUsername(accountCode);
                token = this.jwtTokenUtil.generateToken(userDetails);
                System.out.println("Google: Registered already");
            } else {
                System.out.println(payload.getEmail());
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String password = bCryptPasswordEncoder.encode(payload.getEmail());
                User user = new User(payload.getEmail(), (String) payload.get("name"), password);
                user.setAccountCode(user.getEmail());
                String pictureUrl = (String) payload.get("picture");
                if (pictureUrl == null){
                    pictureUrl = "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ8SO68-LJq2RJdIs7EQAPP1OFbO7Fo50YO_2TLx11iNK8pdYLO";
                }
                user.setAvatar(pictureUrl);
                System.out.println(user.getAccountCode() + " - " + user.getEmail());
                User newUser = this.userService.create(user);
                System.out.println(newUser.getAccountCode() + " - " + newUser.getEmail() + " - " + newUser.getPassword());
                Role role = roleService.findById(new Long(4));
                permissionService.create(newUser, role);
                userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
                token = this.jwtTokenUtil.generateToken(userDetails);
            }
        }
        return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }
}
