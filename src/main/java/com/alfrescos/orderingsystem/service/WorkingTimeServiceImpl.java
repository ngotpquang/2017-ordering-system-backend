/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.WorkingTime;
import com.alfrescos.orderingsystem.repository.WorkingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liger on 20-Mar-17.
 */
@Service
public class WorkingTimeServiceImpl implements WorkingTimeService{

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Override
    public WorkingTime create(WorkingTime workingTime) {
        return this.workingTimeRepository.save(workingTime);
    }

    @Override
    public WorkingTime update(WorkingTime workingTime) {
        return this.workingTimeRepository.save(workingTime);
    }

    @Override
    public void delete(Long workingTimeId) {
        this.workingTimeRepository.delete(workingTimeId);
    }

    @Override
    public WorkingTime findById(Long workingTimeId) {
        return this.workingTimeRepository.findOne(workingTimeId);
    }

    @Override
    public List<WorkingTime> findAllByUserId(Long userId) {
        return this.workingTimeRepository.findAllByUserId(userId);
    }

    @Override
    public List<WorkingTime> findAllByDate(String date) {
        return this.workingTimeRepository.findAllByDate(date);
    }

    @Override
    public List<WorkingTime> findAllByShiftId(Long shiftId) {
        return this.workingTimeRepository.findAllByShiftId(shiftId);
    }

    @Override
    public List<WorkingTime> findAllByShiftIdAndDate(Long shiftId, String date) {
        return this.workingTimeRepository.findAllByShiftIdAndDate(shiftId, date);
    }

    @Override
    public List<WorkingTime> findAllByUserIdAndDate(Long userId, String date) {
        return this.workingTimeRepository.findAllByUserIdAndDate(userId, date);
    }
}
