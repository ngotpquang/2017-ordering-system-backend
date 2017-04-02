/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Rate;

import java.util.List;

/**
 * Created by Liger on 16-Mar-17.
 */
public interface RateService {
    Rate create(Rate rate);
    Rate update(Rate rate);
    void delete(Long rateId);
    List<Rate> findAllRatesByUserId(long userId);
    Iterable<Rate> findAllRates();
    List<Rate> findAllRatesByRateTypeId(long rateTypeId);
    List<Rate> findAllRatesByUserIdAndRateTypeId(long userId, long rateTypeId);
    Rate findRateByInvoiceId(String invoiceId);
    Rate findById(Long rateId);
    float getAverageScoreByRateTypeId(Long rateTypeId);
    int getNumOfPeopleByTypeAndScore(int score, int type);
}
