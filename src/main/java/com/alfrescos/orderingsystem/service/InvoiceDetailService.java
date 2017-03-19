package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.InvoiceDetail;

import java.util.List;

/**
 * Created by Liger on 14-Mar-17.
 */
public interface InvoiceDetailService {
    public List<InvoiceDetail> findAllInvoiceDetailsByInvoiceId(String invoiceId);
    public InvoiceDetail create(InvoiceDetail invoiceDetail);
    public void delete(Long invoiceDetailId);
    public void deleteByInvoiceId(String invoiceId);
    public boolean setMade(Long invoiceDetailId);

    InvoiceDetail findById(Long invoiceDetailId);
}
