/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

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
    List<Rate> findAllRatesByUserId(long userId);

    @Query(value = "SELECT * FROM rate WHERE user_id = ?1 AND rate_type_id = ?2", nativeQuery = true)
    List<Rate> findAllRatesByUserIdAndRateTypeId(long userId, long rateTypeId);

    @Query(value = "SELECT * FROM rate WHERE rate_type_id = ?1 AND score = ?2 ORDER BY rate_time DESC", nativeQuery = true)
    List<Rate> findAllRatesByRateTypeAndScore(int typeId, float score);

    @Query(value = "SELECT * FROM rate WHERE invoice_id = ?1 AND rate_type_id = ?2", nativeQuery = true)
    Rate findRateByInvoiceIdAndRateTypeId(String invoiceId, long rateTypeId);

    @Query(value = "SELECT * FROM rate WHERE rate_type_id = ?1", nativeQuery = true)
    List<Rate> findAllRatesByRateTypeId(long rateTypeId);

    @Query(value = "SELECT AVG(R.score) FROM rate as R WHERE R.rate_type_id = ?1", nativeQuery = true)
    float getAverageScoreByRateTypeId(long rateTypeId);

    @Query(value = "SELECT COUNT(invoice_id) FROM rate WHERE score = ?1 AND rate_type_id = ?2", nativeQuery = true)
    int countNumOfPeopleByTypeAndScore(int score, int typeId);
}
