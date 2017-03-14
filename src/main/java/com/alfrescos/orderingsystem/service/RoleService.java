package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Role;

import java.util.List;

/**
 * Created by Liger on 09-Mar-17.
 */
public interface RoleService {
    public List<Role> getAll();
    public boolean deleteAll();
    public Role create(String roleName);
    public Role findByName(String roleName);
    public Role findById(Long id);
}
