package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 13-Mar-17.
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
