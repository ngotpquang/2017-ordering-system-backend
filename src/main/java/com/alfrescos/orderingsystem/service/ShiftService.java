/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Shift;

import java.util.List;

/**
 * Created by Liger on 19-Mar-17.
 */
public interface ShiftService {
    Shift create(Shift shift);

    Shift update(Shift shift);

    void delete(Long shiftId);

    Shift findById(Long shiftId);

    List<Shift> findAll();
}
