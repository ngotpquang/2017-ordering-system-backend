/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.Table;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 15-Mar-17.
 */
@Repository
public interface TableRepository extends CrudRepository<Table, Long> {
//    @Query(value = "SELECT TOP 1 * FROM \"table\" ORDER BY table_number DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM \"table\" ORDER BY table_number DESC LIMIT 1", nativeQuery = true)
    Table findLastTable();
}
