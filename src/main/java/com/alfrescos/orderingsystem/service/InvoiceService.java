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
    public void delete(String invoiceId);
    Invoice findInvoiceByInvoiceId(String invoiceId);
}
