/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 09-Mar-17.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
    Role findByName(String roleName);
}
