package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Permission;
import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;

/**
 * Created by Liger on 13-Mar-17.
 */
public interface PermissionService {
    public Permission create(User user, Role role);
}
