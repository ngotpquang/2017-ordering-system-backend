/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.common.InvoiceUtil;
import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.repository.InvoiceRepository;
import com.alfrescos.orderingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public boolean setPaid(Long staffId, String invoiceId, String paymentType) {
        Invoice invoice = this.invoiceRepository.findOne(invoiceId);
        if (invoice != null) {
            Date payTime = new Date();
            invoice.setPaid(true);
            invoice.setStaffUser(this.userRepository.findOne(staffId));
            invoice.setPayingTime(payTime);
            invoice.setPaymentType(paymentType);
            System.out.println(invoiceId + " - " + invoice.isPaid() + " - " + invoice.getStaffUser().getAccountCode() + " - " + invoice.getPaymentType());
            return this.invoiceRepository.save(invoice).isPaid();
        } else {
            return false;
        }
    }

    @Override
    public List<Invoice> findAllUnpaidInvoice() {
        return this.invoiceRepository.findAllUnpaidInvoice();
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

    @Override
    public List<?> getTotalAmountByMonthAndYear() {
        List<Map> result = new ArrayList<>();
        List<Invoice> invoiceList = this.invoiceRepository.findAllInvoicesSortByDateAscending();
        if (invoiceList.size() > 0) {
            Date beginDate = invoiceList.get(0).getPayingTime();
            Date endDate = invoiceList.get(invoiceList.size() - 1).getPayingTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beginDate);
            int beginMonth = calendar.get(Calendar.MONTH) + 1;
            int beginYear = calendar.get(Calendar.YEAR);
            calendar.setTime(endDate);
            int endMonth = calendar.get(Calendar.MONTH) + 1;
            int endYear = calendar.get(Calendar.YEAR);
            int b, e;
            for (int i = beginYear; i <= endYear; i++) {
                if (i == beginYear) {
                    b = beginMonth;
                } else {
                    b = 1;
                }
                if (i == endYear) {
                    e = endMonth;
                } else {
                    e = 12;
                }
                for (int k = b; k <= e; k++) {
                    int month = k;
                    int year = i;
                    List<Invoice> temp = invoiceList.stream().filter(invoice -> InvoiceUtil.checkMonthAndYear(invoice, month, year)).collect(Collectors.toList());
                    int total = 0;
                    for (Invoice invoice : temp) {
                        total += invoice.getTotalAmount();
                    }
                    Map<String, String> data = new HashMap<>();
                    data.put("month", month + "");
                    data.put("year", year + "");
                    data.put("total", total + "");
                    result.add(data);
                    System.out.println("Data in a month: " + data);
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public Invoice update(Invoice invoice) {
        return this.invoiceRepository.save(invoice);
    }
}
