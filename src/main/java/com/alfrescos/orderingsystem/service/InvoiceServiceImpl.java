package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.repositoty.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liger on 15-Mar-17.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findAllInvoicesByCustomerId(Long customerId) {
        return invoiceRepository.findAllInvoicesByCustomerId(customerId);
    }

    @Override
    public List<Invoice> findAllInvoicesByStaffId(Long staffId) {
        return invoiceRepository.findAllInvoicesByStaffId(staffId);
    }

    @Override
    public Invoice create(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Iterable<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public void delete(String invoiceId) {
        invoiceRepository.delete(invoiceId);
    }

    @Override
    public Invoice findInvoiceByInvoiceId(String invoiceId) {
        return invoiceRepository.findOne(invoiceId);
    }
}
