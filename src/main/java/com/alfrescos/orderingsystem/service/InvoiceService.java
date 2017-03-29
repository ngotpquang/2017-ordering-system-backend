/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Invoice;

import java.util.List;

/**
 * Created by Liger on 15-Mar-17.
 */
public interface InvoiceService {
    public List<Invoice> findAllInvoicesByCustomerId(Long customerId);
    public List<Invoice> findAllInvoicesByStaffId(Long staffId);
    public Invoice create(Invoice invoice);
    public Iterable<Invoice> findAll();
    public boolean switchVisible(String invoiceId);
    public Invoice findById(String invoiceId);
    public boolean setPaid(Long staffId, String invoiceId);
    public List<String> findOrderedTable();
    public List<Invoice> findAllInvoicesByDate(String date);
    public List<Invoice> findAllInvoicesBetweenDates(String beginningDate, String endDate);

    public Invoice update(Invoice invoice);
}
