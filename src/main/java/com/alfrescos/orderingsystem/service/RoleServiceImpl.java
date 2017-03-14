package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.repositoty.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Liger on 09-Mar-17.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return (List<Role>) this.roleRepository.findAll();
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public Role create(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return this.roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    public Role findById(Long id) {
        return this.roleRepository.findOne(id);
    }
}
