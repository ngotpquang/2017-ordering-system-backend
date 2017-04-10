/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 14-Mar-17.
 */
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String>{
    @Query(value = "SELECT * FROM invoice WHERE customer_user_id = ?1 AND is_visible = 1", nativeQuery = true)
    public List<Invoice> findAllInvoicesByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM invoice WHERE staff_user_id = ?1 AND is_visible = 1", nativeQuery = true)
    public List<Invoice> findAllInvoicesByStaffId(Long customerId);

    @Query(value = "SELECT I.table_id FROM invoice as I WHERE I.paying_time IS NULL", nativeQuery = true)
    public List<String> findAllOrderedTable();

    @Query(value = "SELECT * FROM invoice WHERE CONVERT(VARCHAR(25), paying_time, 126) LIKE ?1% AND is_visible = 1", nativeQuery = true)
    public List<Invoice> findAllInvoicesByDate(String date);

    @Query(value = "SELECT * FROM invoice WHERE paying_time >= ?1 AND paying_time <= ?2 AND is_visible = 1", nativeQuery = true)
    public List<Invoice> findAllInvoicesBetweenDates(String beginningDate, String endDate);
}
