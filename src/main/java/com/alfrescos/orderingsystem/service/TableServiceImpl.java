/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Table;
import com.alfrescos.orderingsystem.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Liger on 15-Mar-17.
 */
@Service
public class TableServiceImpl implements TableService{

    @Autowired
    private TableRepository tableRepository;

    @Override
    public Table findById(Long id) {
        Table table = this.tableRepository.findOne(id);
        return table.isVisible() ? table : null;
    }

    @Override
    public List<Table> findAll() {
        List<Table> tableList = (List<Table>) this.tableRepository.findAll();
        return tableList.stream().filter(table -> table.isVisible()).collect(Collectors.toList());
    }

    @Override
    public boolean switchVisible(Long id) {
        Table table = this.tableRepository.findOne(id);
        table.setVisible(!table.isVisible());
        return this.tableRepository.save(table).isVisible();
    }
}
