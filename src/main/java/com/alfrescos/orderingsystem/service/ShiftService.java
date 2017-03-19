package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Shift;

/**
 * Created by Liger on 19-Mar-17.
 */
public interface ShiftService {
    public Shift create(Shift shift);
    public Shift update(Shift shift);
    public void delete(Long shiftId);
    public Shift findById(Long shiftId);
    public Iterable<Shift> findAll();
}
