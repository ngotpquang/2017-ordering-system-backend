/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.ReservedTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 16-May-17.
 */
@Repository
public interface ReservedTableRepository extends CrudRepository<ReservedTable, Long>{
    @Query(value = "SELECT * FROM reserved_table WHERE table_id = ?1", nativeQuery = true)
    List<ReservedTable> findByTableId(Long tableId);

    @Query(value = "SELECT * FROM reserved_table WHERE user_id = ?1", nativeQuery = true)
    List<ReservedTable> findByUserId(Long userId);

}
