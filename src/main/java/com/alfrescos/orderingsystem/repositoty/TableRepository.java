package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 15-Mar-17.
 */
@Repository
public interface TableRepository extends CrudRepository<Table, Long>{
}
