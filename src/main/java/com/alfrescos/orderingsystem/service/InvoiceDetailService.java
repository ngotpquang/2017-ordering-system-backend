/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.InvoiceDetail;

import java.util.List;

/**
 * Created by Liger on 14-Mar-17.
 */
public interface InvoiceDetailService {
    List<InvoiceDetail> findAllInvoiceDetailsByInvoiceId(String invoiceId);

    InvoiceDetail create(InvoiceDetail invoiceDetail);

    void delete(Long invoiceDetailId);

    void deleteByInvoiceId(String invoiceId);

    boolean setMade(Long invoiceDetailId);

    InvoiceDetail findById(Long invoiceDetailId);

    InvoiceDetail findByDrinkAndFoodId(Long foodAndDrinkId, String invoiceId);

    InvoiceDetail update(InvoiceDetail invoiceDetail);
}
