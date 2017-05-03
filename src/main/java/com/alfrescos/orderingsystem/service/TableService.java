/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Table;

import java.util.List;

/**
 * Created by Liger on 15-Mar-17.
 */
public interface TableService {
    Table findById(Long id);

    List<Table> findAll();

    boolean switchVisible(Long id);

    Table create(Table table);

    Table findLastTable();
}
