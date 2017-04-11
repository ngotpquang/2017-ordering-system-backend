/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Shift;
import com.alfrescos.orderingsystem.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Liger on 19-Mar-17.
 */
@Service
public class ShiftServiceImpl implements ShiftService{

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public Shift create(Shift shift) {
        return this.shiftRepository.save(shift);
    }

    @Override
    public Shift update(Shift shift) {
        return this.shiftRepository.save(shift);
    }

    @Override
    public void delete(Long shiftId) {
        this.shiftRepository.delete(shiftId);
    }

    @Override
    public Shift findById(Long shiftId) {
        Shift shift = this.shiftRepository.findOne(shiftId);
        return shift != null ? (shift.isVisible() ? shift : null) : null;
    }

    @Override
    public List<Shift> findAll() {
        List<Shift> shiftList = (List<Shift>) this.shiftRepository.findAll();
        return shiftList.stream().filter(shift -> shift.isVisible()).collect(Collectors.toList());
    }
}
