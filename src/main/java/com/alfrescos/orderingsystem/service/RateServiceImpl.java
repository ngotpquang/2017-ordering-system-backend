/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Rate;
import com.alfrescos.orderingsystem.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liger on 16-Mar-17.
 */
@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public Rate create(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public Rate update(Rate rate) {
        return this.rateRepository.save(rate);
    }

    @Override
    public void delete(Long rateId) {
        this.rateRepository.delete(rateId);
    }

    @Override
    public List<Rate> findAllRatesByUserId(long userId) {
        return this.rateRepository.findAllRatesByUserId(userId);
    }

    @Override
    public Iterable<Rate> findAllRates() {
        return this.rateRepository.findAll();
    }

    @Override
    public List<Rate> findAllRatesByRateTypeId(long rateTypeId) {
        return this.rateRepository.findAllRatesByRateTypeId(rateTypeId);
    }

    @Override
    public List<Rate> findAllRatesByUserIdAndRateTypeId(long userId, long rateTypeId) {
        return this.rateRepository.findAllRatesByUserIdAndRateTypeId(userId, rateTypeId);
    }

    @Override
    public Rate findRateByInvoiceId(String invoiceId) {
        return this.rateRepository.findRateByInvoiceId(invoiceId);
    }

    @Override
    public Rate findById(Long rateId) {
        return this.rateRepository.findOne(rateId);
    }

    @Override
    public float getAverageScoreByRateTypeId(Long rateTypeId) {
        return this.rateRepository.getAverageScoreByRateTypeId(rateTypeId);
    }

    @Override
    public int getNumOfPeopleByTypeAndScore(int score, int type) {
        return this.rateRepository.countNumOfPeopleByTypeAndScore(score, type);
    }
}
