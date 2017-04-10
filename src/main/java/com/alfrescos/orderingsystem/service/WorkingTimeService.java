/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.WorkingTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Liger on 19-Mar-17.
 */
public interface WorkingTimeService {
    WorkingTime create(WorkingTime workingTime);

    WorkingTime update(WorkingTime workingTime);

    void delete(Long workingTimeId);

    WorkingTime findById(Long workingTimeId);

    List<WorkingTime> findAllByUserId(Long userId);

    List<WorkingTime> findAllByDate(String date);

    List<WorkingTime> findAllByShiftId(Long shiftId);

    List<WorkingTime> findAllByShiftIdAndDate(Long shiftId, String date);

    List<WorkingTime> findAllByUserIdAndDate(Long userId, String date);
}
