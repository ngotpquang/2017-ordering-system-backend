/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import com.alfrescos.orderingsystem.repository.InvoiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liger on 14-Mar-17.
 */
@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService{

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public List<InvoiceDetail> findAllInvoiceDetailsByInvoiceId(String invoiceId) {
        return invoiceDetailRepository.findAllInvoiceDetailsByInvoiceId(invoiceId);
    }

    @Override
    public InvoiceDetail create(InvoiceDetail invoiceDetail) {
        return invoiceDetailRepository.save(invoiceDetail);
    }

    @Override
    public void delete(Long invoiceDetailId) {
        this.invoiceDetailRepository.delete(invoiceDetailId);
    }

    @Override
    public void deleteByInvoiceId(String invoiceId) {
        List<InvoiceDetail> invoiceDetailList = this.invoiceDetailRepository.findAllInvoiceDetailsByInvoiceId(invoiceId);
        for (InvoiceDetail invoiceDetail:
             invoiceDetailList) {
            invoiceDetail.setVisible(false);
            this.invoiceDetailRepository.delete(invoiceDetail);
        }
    }

    @Override
    public boolean setMade(Long invoiceDetailId) {
//        InvoiceDetail invoiceDetail = this.invoiceDetailRepository.findOne(invoiceDetailId);
//        if (invoiceDetail == null){
//            return false;
//        }
//        invoiceDetail.setMade(true);
//        return this.invoiceDetailRepository.save(invoiceDetail).isMade();
        return false;
    }

    @Override
    public InvoiceDetail findById(Long invoiceDetailId) {
        return this.invoiceDetailRepository.findOne(invoiceDetailId);
    }

    @Override
    public InvoiceDetail findByDrinkAndFoodId(Long foodAndDrinkId, String invoiceId) {
        return this.invoiceDetailRepository.findByFoodAndDrinkId(foodAndDrinkId, invoiceId);
    }

    @Override
    public InvoiceDetail update(InvoiceDetail invoiceDetail) {
        return this.invoiceDetailRepository.save(invoiceDetail);
    }

    @Override
    public float calculateTotalAmountForInvoice(String invoiceId) {
        System.out.println("InvoiceId: " + invoiceId);
        float totalAmount = 0.0f;
        List<InvoiceDetail> invoiceDetailList = this.invoiceDetailRepository.findAllInvoiceDetailsByInvoiceId(invoiceId);
        for (InvoiceDetail id: invoiceDetailList) {
            System.out.println(id.getPrice() + " - " + id.getQuantity());
            totalAmount += id.getPrice() * id.getQuantity();
        }
        System.out.println(totalAmount);
        return totalAmount;
    }

}
