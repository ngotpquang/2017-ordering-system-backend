package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Table;
import com.alfrescos.orderingsystem.repositoty.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liger on 15-Mar-17.
 */
@Service
public class TableServiceImpl implements TableService{

    @Autowired
    private TableRepository tableRepository;

    @Override
    public Table findById(Long id) {
        return tableRepository.findOne(id);
    }
}
