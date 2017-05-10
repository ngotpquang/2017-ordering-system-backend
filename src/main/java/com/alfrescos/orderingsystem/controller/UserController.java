/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.Permission;
import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.security.JwtAuthenticationResponse;
import com.alfrescos.orderingsystem.security.JwtTokenUtil;
import com.alfrescos.orderingsystem.service.EmailService;
import com.alfrescos.orderingsystem.service.PermissionService;
import com.alfrescos.orderingsystem.service.RoleService;
import com.alfrescos.orderingsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Liger on 09-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all/customer")
    public ResponseEntity<?> getAllCustomer() {
        List<User> users = userService.getAll();
        List<User> result = users.stream().filter(user -> UserUtil.checkAccountType(user) == 4).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all/staff")
    public ResponseEntity<?> getAllStaff() {
        List<User> users = userService.getAll();
        List<User> result = users.stream().filter(user -> UserUtil.checkAccountType(user) == 3).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");
        String name = data.get("name");
        String roleIds = data.get("roleId");
        System.out.println(email + " - " + password + " - " + name + " - " + roleIds);
        User existUser = userService.findByEmail(email);
        if (existUser != null) {
            return new ResponseEntity<>("Email has been registered already!", HttpStatus.CONFLICT);
        } else {
            try {
                String[] roleIdList = roleIds.split(",");
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String passwordEncoded = passwordEncoder.encode(password);
                emailService.sendWelcomeMailNewMember(email, name);
                User user = new User(email, name, passwordEncoded);
                System.out.println("Created date: " + user.getCreatedDate());
                user.setAccountCode(user.getEmail());
                user.setAvatar("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ8SO68-LJq2RJdIs7EQAPP1OFbO7Fo50YO_2TLx11iNK8pdYLO");
                User newUser = userService.create(user);
                for (String roleId : roleIdList) {
                    Role role = roleService.findById(Long.parseLong(roleId.trim()));
                    permissionService.create(newUser, role);
                }
                String token = this.jwtTokenUtil.generateToken(this.userDetailsService.loadUserByUsername(user.getAccountCode()));
                return new ResponseEntity<>(new JwtAuthenticationResponse(token), HttpStatus.CREATED);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>("Can't create user due to some error.", HttpStatus.NOT_ACCEPTABLE);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>("Wrong email address.", HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }


    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@Valid @RequestBody Map<String, String> data) {
        User user = this.userService.findById(UserUtil.getIdByAuthorization());
        try {
            Long dateOfBirth = Long.parseLong(data.get("dateOfBirth"));
            String detail = data.get("detail");
            byte gender = Byte.parseByte(data.get("gender"));
            String name = data.get("name");
            user.setDateOfBirth(new Date(dateOfBirth));
            user.setDetail(detail);
            user.setGender(gender);
            user.setName(name);
            User updatedUser = userService.update(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Can't update due to error.", HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user != null) {
            userService.switchDeletedStatus(userId);
            return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can't delete due to error.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/info")
    public ResponseEntity<?> getInformation() {
        User user = this.userService.findByAccountCode(UserUtil.getAccountCodeByAuthorization());
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/info/permission")
    public ResponseEntity<?> getPermissionList() {
        User user = this.userService.findByAccountCode(UserUtil.getAccountCodeByAuthorization());
        return new ResponseEntity<Object>(user.getPermissionList(), HttpStatus.OK);
    }

    //TODO: Check later
//    @PreAuthorize("isAuthenticated()")
//    @PutMapping(value = "/profile", consumes = {"multipart/form-data"})
//    public ResponseEntity<Object> update(@RequestPart("profile") String profile, @RequestPart(name = "avatar", required = false) MultipartFile avatar) throws IOException {
//        String accountCode = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = new ObjectMapper().readValue(profile, User.class);
//        user.setAccountCode(accountCode);
//        if (avatar != null) {
//            user.setAvatarContent(avatar.getBytes());
//        }
//        User updatedUser = userService.update(user);
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(updatedUser);
//        FilterProvider filters = new SimpleFilterProvider()
//                .addFilter("filter.User", SimpleBeanPropertyFilter
//                        .serializeAllExcept("password"));
//        mappingJacksonValue.setFilters(filters);
//
//        if (updatedUser != null) {
//            return new ResponseEntity<Object>(mappingJacksonValue, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<Object>("Failed", HttpStatus.BAD_REQUEST);
//        }
//    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/profile/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody Map<String, String> passwordMap) {
        String currentPassword = passwordMap.get("currentPassword");
        String newPassword = passwordMap.get("newPassword");
//        String confirmPassword = passwordMap.get("confirmPassword");
        String accountCode = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByAccountCode(accountCode);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            if (newPassword != null) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userService.updatePassword(user);
                return new ResponseEntity<Object>("Change password successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("Confirmed password doesn't match new password", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<Object>("Current password is not correct", HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/permission")
    public ResponseEntity<?> getPermissionForLoggedInUser() {
        User user = this.userService.findById(UserUtil.getIdByAuthorization());
        return new ResponseEntity<Object>(user.getPermissionList(), HttpStatus.OK);
    }

}
