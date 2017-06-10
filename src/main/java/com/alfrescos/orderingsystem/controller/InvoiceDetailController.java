/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.InvoiceUtil;
import com.alfrescos.orderingsystem.common.TableStatus;
import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import com.alfrescos.orderingsystem.entity.Table;
import com.alfrescos.orderingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Liger on 16-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/invoice-detail")
public class InvoiceDetailController {

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private FoodAndDrinkService foodAndDrinkService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{invoiceId}")
    public ResponseEntity<?> getInvoiceDetailByInvoiceId(@PathVariable String invoiceId){
        List<InvoiceDetail> invoiceDetailList = invoiceDetailService.findAllInvoiceDetailsByInvoiceId(invoiceId);
        if (invoiceDetailList != null && !invoiceDetailList.isEmpty()){
            return new ResponseEntity<Object>(invoiceDetailList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("No content for invoice id: " + invoiceId, HttpStatus.NO_CONTENT);
        }
    }

//    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/update")
    public ResponseEntity<?> addInvoiceDetailToInvoice(@RequestBody Map<String, String> data){
        String invoiceId = data.get("invoiceId").trim();
        Invoice invoice = invoiceService.findById(invoiceId);
        String accountCode = UserUtil.getAccountCodeByAuthorization();
        if (accountCode.equals("anonymousUser")){
            accountCode = this.userService.findById(1L).getAccountCode();
        }
        if (invoice == null){
            return new ResponseEntity<Object>("Can't find invoice with id: " + invoiceId, HttpStatus.NOT_ACCEPTABLE);
        } else if (!invoice.getCustomerUser().getAccountCode().equals(accountCode)){
            return new ResponseEntity<Object>("You can't modify this invoice as you're not the owner.", HttpStatus.NOT_ACCEPTABLE);
        } else if(invoice.isPaid()){
            return new ResponseEntity<Object>("Can't modify invoice after paid", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            Date timeOrdered = new Date();
            if (InvoiceUtil.addInvoiceDetail(data, invoice, timeOrdered, foodAndDrinkService, invoiceDetailService)){
                invoice.setMade(false);
                invoice = this.invoiceService.update(invoice);
                Table table = invoice.getTable();
                table.setTableStatus(TableStatus.ORDERING);
                this.tableService.update(table);
                if (invoice != null) {
                    return new ResponseEntity<Object>("Added successfully.", HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Object>("Couldn't update is made status.", HttpStatus.NOT_MODIFIED);
                }
            }
        } catch (Exception e){
            return new ResponseEntity<Object>("Couldn't add new invoice detail due to errors.", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>("Couldn't add new invoice detail due to errors.", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/delete/{invoiceDetailId}")
    public ResponseEntity<?> delete(@PathVariable Long invoiceDetailId){
        InvoiceDetail invoiceDetail = this.invoiceDetailService.findById(invoiceDetailId);
        if (invoiceDetail != null){
            this.invoiceDetailService.delete(invoiceDetailId);
            return new ResponseEntity<Object>("Invoice detail has id: " + invoiceDetailId + " was deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't delete due to some error.", HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllInvoiceDetail(){
        Long customerId = UserUtil.getIdByAuthorization();
        List<Invoice> invoiceList = this.invoiceService.findAllInvoicesByCustomerId(customerId);
        List<InvoiceDetail> result = new ArrayList<>();
        for (Invoice i: invoiceList) {
            result.addAll(this.invoiceDetailService.findAllInvoiceDetailsByInvoiceId(i.getId()));
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/favorite")
    public ResponseEntity<?> getAllInvoiceDetailSortByFavorite(){
        List<Map> result = new ArrayList<>();
        Long customerId = UserUtil.getIdByAuthorization();
        List<Invoice> invoiceList = this.invoiceService.findAllInvoicesByCustomerId(customerId);
        List<InvoiceDetail> invoiceDetailList = new ArrayList<>();
        for (Invoice i: invoiceList) {
            invoiceDetailList.addAll(this.invoiceDetailService.findAllInvoiceDetailsByInvoiceId(i.getId()));
        }
        List<FoodAndDrink> foodAndDrinkList = (List<FoodAndDrink>) this.foodAndDrinkService.findAll();
        for (FoodAndDrink fad: foodAndDrinkList) {
            int quantity = 0;
            for (InvoiceDetail id: invoiceDetailList) {
                if(id.getFoodAndDrink().getId().equals(fad.getId())){
                    quantity += id.getQuantity();
                }
            }
            if (quantity > 0){
                Map<String, String> favouriteEntity = new HashMap<>();
                favouriteEntity.put("price", fad.getPrice() + "");
                favouriteEntity.put("quantity", quantity + "");
                favouriteEntity.put("name", fad.getName());
                result.add(favouriteEntity);
            }
        }
        Comparator<Map> comparator = (o1, o2) -> (Integer.parseInt((String) o2.get("quantity")) - Integer.parseInt((String) o1.get("quantity")));
        result.sort(comparator);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

}
