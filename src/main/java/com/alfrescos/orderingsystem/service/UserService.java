/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;

import java.util.List;

/**
 * Created by Liger on 06-Mar-17.
 */
public interface UserService {
    User create(User user);

    User findById(Long id);

    User findByAccountCode(String accountCode);

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    User update(User user);

    User updateLastAccess(User user);

    boolean switchDeletedStatus(Long id);

    List<User> getAll();

    User save(User user);

    List<Role> getAllRoleByUserId(Long id);

    User updatePassword(User user);

}
