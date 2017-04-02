/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Permission;
import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Liger on 13-Mar-17.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission create(User user, Role role) {
        Permission permission = new Permission();
        permission.setRole(role);
        permission.setUser(user);
        return this.permissionRepository.save(permission);
    }
}
