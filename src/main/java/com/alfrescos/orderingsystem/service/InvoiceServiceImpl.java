/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.repositoty.InvoiceRepository;
import com.alfrescos.orderingsystem.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Liger on 15-Mar-17.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

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
    public boolean switchVisible(String invoiceId) {
        Invoice invoice = this.invoiceRepository.findOne(invoiceId);
        invoice.setVisible(!invoice.isVisible());
        return this.invoiceRepository.save(invoice).isVisible();
    }

    @Override
    public Invoice findById(String invoiceId) {
        return this.invoiceRepository.findOne(invoiceId);
    }

    @Override
    public boolean setPaid(Long staffId, String invoiceId) {
        Invoice invoice = this.invoiceRepository.findOne(invoiceId);
        if (invoice != null){
            Date payTime = new Date();
            invoice.setPaid(true);
            invoice.setStaffUser(this.userRepository.findOne(staffId));
            invoice.setPayingTime(payTime);
            System.out.println(invoiceId + " - " + invoice.isPaid() + " - " + invoice.getStaffUser().getAccountCode());
            return this.invoiceRepository.save(invoice).isPaid();
        } else {
            return false;
        }
    }

    @Override
    public List<String> findOrderedTable() {
        return this.invoiceRepository.findAllOrderedTable();
    }

    @Override
    public List<Invoice> findAllInvoicesByDate(String date) {
        return this.invoiceRepository.findAllInvoicesByDate(date);
    }

    @Override
    public List<Invoice> findAllInvoicesBetweenDates(String beginningDate, String endDate) {
        beginningDate = beginningDate + " 00:00:00";
        endDate = endDate + " 23:59:59";
        return this.invoiceRepository.findAllInvoicesBetweenDates(beginningDate, endDate);
    }
}
