package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Table;

/**
 * Created by Liger on 15-Mar-17.
 */
public interface TableService {
    public Table findById(Long id);
    boolean switchVisible(Long id);
}
