/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 15-Mar-17.
 */
@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

    @Query(value = "SELECT * FROM invoice_detail WHERE invoice_id = ?1 AND is_visible = 1", nativeQuery = true)
//    @Query(value = "SELECT * FROM invoice_detail WHERE invoice_id = ?1 AND is_visible = TRUE", nativeQuery = true)
    List<InvoiceDetail> findAllInvoiceDetailsByInvoiceId(String invoiceId);

    @Query(value = "SELECT * FROM invoice_detail WHERE food_and_drink_id = ?1 AND invoice_id = ?2 AND is_visible = 1", nativeQuery = true)
//    @Query(value = "SELECT * FROM invoice_detail WHERE food_and_drink_id = ?1 AND invoice_id = ?2 AND is_visible = TRUE", nativeQuery = true)
    InvoiceDetail findByFoodAndDrinkId(Long foodAndDrinkId, String invoiceId);
}
