package com.alfrescos.orderingsystem.security.controller;

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
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.google.api.Google;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by dtnhat on 12/20/2016.
 */
@RestController
@RequestMapping(value = "/api/v1/social")
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
        String[] fields = {"id", "email", "name", "first_name", "last_name", "gender", "middle_name", "cover", "hometown", "location"};
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
            }
            user.setGender((byte) (userSocial.getGender().equals("male") ? 0 : 1));
            user.setName(userSocial.getFirstName() + " " + userSocial.getMiddleName() + " " + userSocial.getLastName());
//            user.setName(userSocial.getName());
            user.setAvatar("http://graph.facebook.com/" + userSocial.getId() + "/picture?type=square");
            User newUser = userService.create(user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getAccountCode());
            String token = this.jwtTokenUtil.generateToken(userDetails);
            return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/google")
    private ResponseEntity<?> signUpByGoogle(@RequestBody Map<String, String> accessTokenClient) {
        String accessToken = accessTokenClient.get("accessToken");
        String CLIENT_ID = "597737117477-3mpfnd8v8u8gjc2mtpr0m9b1frfjbhh0.apps.googleusercontent.com";
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(this.transport, this.jsonFactory).setAudience(Collections.singletonList(CLIENT_ID)).build();

        UserDetails userDetails = null;
        String token = "";

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(accessToken);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String userId = payload.getSubject();
            if (this.userService.findByAccountCode(payload.getEmail()) != null) {
                userDetails = userDetailsService.loadUserByUsername(payload.getEmail());
                token = this.jwtTokenUtil.generateToken(userDetails);
            } else {
                User user = new User();
                user.setEmail(payload.getEmail());
                user.setAccountCode(payload.getEmail());
                user.setName((String) payload.get("name"));
                user.setAvatar((String) payload.get("picture"));
                User newUser = this.userService.create(user);

                userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
                token = this.jwtTokenUtil.generateToken(userDetails);
//                System.out.println("User ID: " + userId);
//                String email = payload.getEmail();
//                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//                String name = (String) payload.get("name");
//                String pictureUrl = (String) payload.get("picture");
//                String locale = (String) payload.get("locale");
//                String familyName = (String) payload.get("family_name");
//                String givenName = (String) payload.get("given_name");
//                System.out.println(payload.getEmail());
            }
        }
        return new ResponseEntity<Object>(new JwtAuthenticationResponse(token), HttpStatus.OK);
    }
}
