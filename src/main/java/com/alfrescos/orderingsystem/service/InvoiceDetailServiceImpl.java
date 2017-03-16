package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import com.alfrescos.orderingsystem.repositoty.InvoiceDetailRepository;
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

}
