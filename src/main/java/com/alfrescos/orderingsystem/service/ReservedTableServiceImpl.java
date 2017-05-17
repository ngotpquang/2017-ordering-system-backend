/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.ReservedTable;
import com.alfrescos.orderingsystem.repository.ReservedTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liger on 16-May-17.
 */
@Service
public class ReservedTableServiceImpl implements ReservedTableService {

    @Autowired
    private ReservedTableRepository reservedTableRepository;

    @Override
    public List<ReservedTable> findByTableId(Long tableId) {
        return this.reservedTableRepository.findByTableId(tableId);
    }

    @Override
    public List<ReservedTable> findByUserId(Long userId) {
        return this.reservedTableRepository.findByUserId(userId);
    }

    @Override
    public List<ReservedTable> findAll() {
//        List<ReservedTable> reservedTableList = (List<ReservedTable>) this.reservedTableRepository.findAll();
//        System.out.println("Size: " + reservedTableList.size());
        return (List<ReservedTable>) this.reservedTableRepository.findAll();
    }

    @Override
    public ReservedTable create(ReservedTable reservedTable) {
        return this.reservedTableRepository.save(reservedTable);
    }

    @Override
    public ReservedTable update(ReservedTable reservedTable) {
        return this.reservedTableRepository.save(reservedTable);
    }

    @Override
    public ReservedTable findById(long reservedTableId) {
        return this.reservedTableRepository.findOne(reservedTableId);
    }
}
