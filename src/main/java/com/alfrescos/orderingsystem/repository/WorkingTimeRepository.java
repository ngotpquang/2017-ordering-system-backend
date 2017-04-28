/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.WorkingTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 19-Mar-17.
 */
@Repository
public interface WorkingTimeRepository extends CrudRepository<WorkingTime, Long>{
    @Query(value = "SELECT * FROM working_time WHERE user_id = ?1", nativeQuery = true)
    List<WorkingTime> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM working_time WHERE (user_id = ?1 AND CONVERT(VARCHAR(25), date, 126) LIKE ?2%)", nativeQuery = true)
    List<WorkingTime> findAllByUserIdAndDate(Long userId, String date);

    @Query(value = "SELECT * FROM working_time WHERE CONVERT(VARCHAR(25), date, 126) LIKE ?1%", nativeQuery = true)
    List<WorkingTime> findAllByDate(String date);

    @Query(value = "SELECT * FROM working_time WHERE shift_id = ?1", nativeQuery = true)
    List<WorkingTime> findAllByShiftId(Long shiftId);

    @Query(value = "SELECT * FROM working_time WHERE (shift_id = ?1 AND CONVERT(VARCHAR(25), date, 126) LIKE ?2%)", nativeQuery = true)
    List<WorkingTime> findAllByShiftIdAndDate(Long shiftId, String date);

    @Query(value = "SELECT * FROM working_time WHERE user_id = ?1 ORDER BY date DESC LIMIT 1", nativeQuery = true)
    WorkingTime findLastWorkingTimeByUserId(Long userId);
}
