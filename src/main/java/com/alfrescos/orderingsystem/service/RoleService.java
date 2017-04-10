/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Role;

import java.util.List;

/**
 * Created by Liger on 09-Mar-17.
 */
public interface RoleService {
    List<Role> getAll();

    boolean deleteAll();

    Role create(String roleName);

    Role findByName(String roleName);

    Role findById(Long id);
}
