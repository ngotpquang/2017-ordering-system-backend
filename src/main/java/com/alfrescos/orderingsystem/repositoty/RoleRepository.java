package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 09-Mar-17.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
    Role findByName(String roleName);
}
