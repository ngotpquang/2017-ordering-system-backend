/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.InvoiceUtil;
import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.*;
import com.alfrescos.orderingsystem.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Liger on 15-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/invoice")
public class InvoiceController {

    @Autowired
    private UserService userService;

    @Autowired
    private FoodAndDrinkService foodAndDrinkService;

    @Autowired
    private TableService tableService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private OrderCombinationService orderCombinationService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, String> data) {
        String invoiceId = "INV" + new Date().getTime();
        try {
            Long tableId = Long.parseLong(data.get("tableId").trim());
            List<Invoice> invoiceList = this.invoiceService.findAllUnpaidInvoice();
            for (Invoice invoice: invoiceList){
                if (invoice.getTable().equals(tableId)){
                    return new ResponseEntity<Object>("Table is in ordering process. You can't create invoice with this table", HttpStatus.NOT_ACCEPTABLE);
                }
            }
            String customerAccountCode = UserUtil.getAccountCodeByAuthorization();
            User customer;
            if (!customerAccountCode.equals("anonymousUser")) {
                customer = userService.findByAccountCode(customerAccountCode);
            } else {
                //TODO: Fix id of default customer
                customer = userService.findById(new Long(1));
            }
            Table table = tableService.findById(tableId);
            Invoice invoice = invoiceService.create(new Invoice(invoiceId, customer, customer, table));
            Date timeOrdered = new Date();
            if (invoice != null && InvoiceUtil.addInvoiceDetail(data, invoice, timeOrdered, foodAndDrinkService, invoiceDetailService)) {
                return new ResponseEntity<>(invoice.getId(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed when created something. Please check again!", HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Failed when created something. Please check again!", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllInvoicesForUserLoggedIn() {
        Long loggedInUserId = UserUtil.getIdByAuthorization();
        List<Invoice> invoiceList;
        if (UserUtil.checkStaffAccount()) {
            invoiceList = invoiceService.findAllInvoicesByStaffId(loggedInUserId);
        } else {
            invoiceList = invoiceService.findAllInvoicesByCustomerId(loggedInUserId);
        }
        if (invoiceList != null) {
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{invoiceId}")
    public ResponseEntity<?> getInvoiceByInvoiceId(@PathVariable String invoiceId) {
        Long loggedInUserId = UserUtil.getIdByAuthorization();
        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice != null && loggedInUserId.equals(invoice.getCustomerUser().getId())) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all-invoices")
    public ResponseEntity<?> getAllInvoices() {
        Iterable<Invoice> invoiceList = invoiceService.findAll();
        if (invoiceList != null) {
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/delete/{invoiceId}")
    public ResponseEntity<?> deleteInvoiceByInvoiceId(@PathVariable String invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice != null) {
            invoiceService.switchVisible(invoiceId);
            return new ResponseEntity<Object>("Invoice id: " + invoiceId + " has been invisible successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't find invoice with id: " + invoiceId, HttpStatus.NO_CONTENT);
        }

    }

    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    @PutMapping(value = "/confirm-paid")
    public ResponseEntity<?> confirmPaidInvoice(@RequestBody Map<String, String> data) {
        Long staffId = UserUtil.getIdByAuthorization();
        String invoiceId = data.get("invoiceId");
        String paymentType = data.get("paymentType").toUpperCase();
        System.out.println(staffId + " - " + invoiceId + " - " + paymentType);
//        return null;
        if (this.invoiceService.findById(invoiceId).isPaid()) {
            return new ResponseEntity<Object>("Invoice has been paid already!", HttpStatus.NOT_ACCEPTABLE);
        }
        List<InvoiceDetail> invoiceDetailList = this.invoiceDetailService.findAllInvoiceDetailsByInvoiceId(invoiceId);
        List<InvoiceDetail> mainDishDetailList = invoiceDetailList.stream().filter(invoiceDetail -> invoiceDetail.getFoodAndDrink().getFoodAndDrinkType().isMainDish()).collect(Collectors.toList());
        List<InvoiceDetail> drinkOrDesertDetailList = invoiceDetailList.stream().filter(invoiceDetail -> !invoiceDetail.getFoodAndDrink().getFoodAndDrinkType().isMainDish()).collect(Collectors.toList());
        if (!mainDishDetailList.isEmpty() && !drinkOrDesertDetailList.isEmpty()) {
            for (InvoiceDetail id : mainDishDetailList) {
                for (InvoiceDetail id1 : drinkOrDesertDetailList) {
                    System.out.println(id.getFoodAndDrink().getId() + " - " + id1.getFoodAndDrink().getId());
                    OrderCombination orderCombination = this.orderCombinationService.findByMainDishIdAndDrinkAndDesertId(id.getFoodAndDrink().getId(), id1.getFoodAndDrink().getId());
                    if (orderCombination != null) {
                        orderCombination.setNumOfOrderedTogether(orderCombination.getNumOfOrderedTogether() + 1);
                        this.orderCombinationService.updateNumberOrderedTogether(orderCombination);
                    } else {
                        System.out.println("Error somewhere I don't know!");
                        return new ResponseEntity<Object>("Error somewhere I don't know!", HttpStatus.NOT_ACCEPTABLE);
                    }
                }
            }
        }
        User customer = this.userService.findById(this.invoiceService.findById(invoiceId).getCustomerUser().getId());
        customer.setMembershipPoint(customer.getMembershipPoint() + Math.round((this.invoiceDetailService.calculateTotalAmountForInvoice(invoiceId) / 1000 * 0.05f)));
        customer = this.userService.save(customer);
        Invoice invoice = this.invoiceService.findById(invoiceId);
        invoice.setTotalAmount(this.invoiceDetailService.calculateTotalAmountForInvoice(invoiceId));
        invoice = this.invoiceService.update(invoice);
        if (customer != null && invoice != null) {
            return new ResponseEntity<Object>(this.invoiceService.setPaid(staffId, invoiceId, paymentType), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Error when update membership point for customer!", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    @PutMapping(value = "/update-table")
    public ResponseEntity<?> updateTableId(@RequestBody Map<String, String> data) {
        Invoice invoice = this.invoiceService.findById(data.get("invoiceId").trim());
        if (invoice != null) {
            try {
                Long tableId = Long.parseLong(data.get("tableId").trim());
                Table table = this.tableService.findById(tableId);
                if (table != null) {
                    invoice.setTable(table);
                    invoice = this.invoiceService.update(invoice);
                    return new ResponseEntity<Object>(invoice, HttpStatus.CREATED);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<Object>("Can't update due to error", HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<Object>("Can't update due to error", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'STAFF')")
    @GetMapping(value = "/unpaid-invoice")
    public ResponseEntity<?> getUnpaidInvoice() {
        List<Invoice> invoices = this.invoiceService.findAllUnpaidInvoice();
        List<Table> tableList = this.tableService.findAll();
        List<Invoice> invoiceList = new ArrayList<>();
        for (Table table: tableList){
            for (Invoice invoice: invoices){
                if (invoice.getTable().getId().equals(table.getId())){
                    invoiceList.add((invoice));
                    break;
                }
            }
        }
        return new ResponseEntity<Object>(invoiceList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/date/{date}")
    public ResponseEntity<?> getInvoiceByDate(@PathVariable String date) {
        List<Invoice> invoiceList = invoiceService.findAllInvoicesByDate(date);
        if (invoiceList != null) {
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/date/{beginningDate}/to/{endDate}")
    public ResponseEntity<?> getInvoiceBetweenDates(@PathVariable String beginningDate, @PathVariable String endDate) {
        List<Invoice> invoiceList = invoiceService.findAllInvoicesBetweenDates(beginningDate, endDate);
        if (invoiceList != null) {
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/total-monthly")
    public ResponseEntity<?> getTotalMonthly() {
        List<?> result = invoiceService.getTotalAmountByMonthAndYear();
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(value = "/is-made/{invoiceId}")
    public ResponseEntity<?> updateIsMadeStatus(@PathVariable String invoiceId){
        Invoice invoice = this.invoiceService.findById(invoiceId);
        if (invoice != null){
            if (invoice.isMade()){
                return new ResponseEntity<Object>("All drinks are already made", HttpStatus.NOT_MODIFIED);
            } else {
                invoice.setMade(true);
                this.invoiceService.update(invoice);
                return new ResponseEntity<Object>("Invoice id: " + invoiceId + " is made", HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<Object>("Couldn't find invoice with id: " + invoiceId, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/table/{tableId}")
    public ResponseEntity<?> getTheLastUnpaidInvoiceOfTable(@PathVariable long tableId){
        System.out.println("Table Id: " + tableId);
        List<Invoice> invoiceList = this.invoiceService.findAllUnpaidInvoice();
        for (Invoice invoice: invoiceList){
            System.out.println("Table Id in Invoice: " + invoice.getTable().getId());
            if(invoice.getTable().getId().equals(tableId)){
                return new ResponseEntity<Object>(invoice, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Object>("Can't find any unpaid invoice for table id: " + tableId, HttpStatus.NO_CONTENT);
    }
}
