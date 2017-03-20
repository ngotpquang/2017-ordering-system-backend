package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.WorkingTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Liger on 19-Mar-17.
 */
public interface WorkingTimeService {
    public WorkingTime create(WorkingTime workingTime);
    public WorkingTime update(WorkingTime workingTime);
    public void delete(Long workingTimeId);
    public WorkingTime findById(Long workingTimeId);
    public List<WorkingTime> findAllByUserId(Long userId);
    public List<WorkingTime> findAllByDate(String date);
    public List<WorkingTime> findAllByShiftId(Long shiftId);
    public List<WorkingTime> findAllByShiftIdAndDate(Long shiftId, String date);
    public List<WorkingTime> findAllByUserIdAndDate(Long userId, String date);
}
