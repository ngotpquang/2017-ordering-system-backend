/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.ReservedTable;

import java.util.List;

/**
 * Created by Liger on 16-May-17.
 */
public interface ReservedTableService {
    List<ReservedTable> findByTableId(Long tableId);

    List<ReservedTable> findByUserId(Long userId);

    List<ReservedTable> findAll();

    ReservedTable create(ReservedTable reservedTable);

    ReservedTable update(ReservedTable reservedTable);

    ReservedTable findById(long reservedTableId);
}
