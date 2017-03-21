package com.alfrescos.orderingsystem.security.controller;

import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.security.JwtAuthenticationResponse;
import com.alfrescos.orderingsystem.security.JwtTokenUtil;
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
            return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
        } else {
            User user = new User();
            user.setAccountCode(userSocial.getId());
            if (userSocial.getEmail() != null) {
                user.setEmail(userSocial.getEmail());
            } else {
                user.setEmail(user.getAccountCode());
            }
            user.setGender((byte) (userSocial.getGender().equals("male") ? 0 : 1));
            user.setName(userSocial.getName());
            user.setAvatar("http://graph.facebook.com/" + userSocial.getId() + "/picture?type=square");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(userSocial.getId());
            user.setPassword(password);
            User newUser = userService.create(user);
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
                userDetails = userDetailsService.loadUserByUsername(payload.getEmail());
                token = this.jwtTokenUtil.generateToken(userDetails);
            } else {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String password = bCryptPasswordEncoder.encode(payload.getEmail());
                User user = new User(new Long(1), payload.getEmail(), (String) payload.get("name"), password);
                User newUser = this.userService.create(user);
                userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
                token = this.jwtTokenUtil.generateToken(userDetails);
            }
        }
        return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }
}
