/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Liger on 06-Mar-17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private boolean checkString(String value) {
        if (value != null) {
            if (!value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findOne(id);
        return user.isDeleted() ? null : user;
    }

    @Override
    public User findByAccountCode(String accountCode) {
        User user = userRepository.findByAccountCode(accountCode);
        return user.isDeleted() ? null : user;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return user.isDeleted() ? null : user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user.isDeleted() ? null : user;
    }

    @Override
    public User update(User user) {
        String accountCode = SecurityContextHolder.getContext().getAuthentication().getName();
        User oldUser = this.userRepository.findByAccountCode(accountCode);
        if (checkString(user.getName())) {
            oldUser.setName(user.getName());
        }
        if (checkString(user.getPassword())) {
            oldUser.setPassword(user.getPassword());
        }
        if (checkString(user.getEmail())) {
            oldUser.setEmail(user.getEmail());
        }
        if (checkString(user.getDetail())) {
            oldUser.setDetail(user.getDetail());
        }
        return this.userRepository.save(oldUser);
    }

    @Override
    public boolean switchDeletedStatus(Long id) {
        User user = this.userRepository.findOne(id);
        user.setDeleted(!user.isDeleted());
        return this.userRepository.save(user).isDeleted();
    }

    @Override
    public List<User> getAll() {
        List<User> userList = (List<User>) this.userRepository.findAll();
        return userList.stream().filter(user -> !user.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<Role> getAllRoleByUserId(Long id) {
        return this.userRepository.findOne(id).getPermissionList().stream().map(x -> x.getRole()).collect(Collectors.toList());
    }

    @Override
    public User updatePassword(User user) {
        User oldUser = this.userRepository.findByAccountCode(user.getAccountCode());
        if(checkString(user.getPassword())) {
            oldUser.setPassword(user.getPassword());
        }
//        oldUser.setIgnoreJoin(user.isIgnoreJoin());
        return this.userRepository.save(oldUser);
    }
}
