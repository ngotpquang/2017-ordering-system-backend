/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 16-Mar-17.
 */
@Repository
public interface RateRepository extends CrudRepository<Rate, Long>{

    @Query(value = "SELECT * FROM rate WHERE user_id = ?1", nativeQuery = true)
    public List<Rate> findAllRatesByUserId(long userId);

    @Query(value = "SELECT * FROM rate WHERE user_id = ?1 AND rate_type_id = ?2", nativeQuery = true)
    public List<Rate> findAllRatesByUserIdAndRateTypeId(long userId, long rateTypeId);

    @Query(value = "SELECT * FROM rate WHERE invoice_id = ?1", nativeQuery = true)
    public Rate findRateByInvoiceId(String invoiceId);

    @Query(value = "SELECT * FROM rate WHERE rate_type_id = ?1", nativeQuery = true)
    public List<Rate> findAllRatesByRateTypeId(long rateTypeId);

    @Query(value = "SELECT AVG(R.score) FROM rate as R WHERE R.rate_type_id = ?1", nativeQuery = true)
    public float getAverageScoreByRateTypeId(long rateTypeId);
}
