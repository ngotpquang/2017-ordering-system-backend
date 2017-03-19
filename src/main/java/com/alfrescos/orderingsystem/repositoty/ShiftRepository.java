package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.Shift;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 19-Mar-17.
 */
@Repository
public interface ShiftRepository extends CrudRepository<Shift, Long>{
}
