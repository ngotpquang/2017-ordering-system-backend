package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.InvoiceDetailUtil;
import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.*;
import com.alfrescos.orderingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, String> data) {
        int numberOfInvoiceDetails = Integer.parseInt(data.get("numberOfInvoiceDetails").trim());
        String invoiceId = data.get("invoiceId").trim();
        try {
            Long tableId = Long.parseLong(data.get("tableId").trim());
            String customerAccountCode = UserUtil.getAccountCodeByAuthorization();
            User customer;
            if (!customerAccountCode.equals("anonymousUser")) {
                customer = userService.findByAccountCode(customerAccountCode);
            } else {
                customer = userService.findById(new Long(1));
            }
            Table table = tableService.findById(tableId);
            Invoice invoice = invoiceService.create(new Invoice(invoiceId, customer, customer, table));
            Date timeOrdered = new Date();
            if (invoice != null && InvoiceDetailUtil.addInvoiceDetail(numberOfInvoiceDetails, data, invoice, timeOrdered, foodAndDrinkService, invoiceDetailService)) {
                return new ResponseEntity<>("Ordered!", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed when created something. Please check again!", HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e){
            return new ResponseEntity<>("Failed when created something. Please check again!", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllInvoicesForUserLoggedIn(){
        Long loggedInUserId = UserUtil.getIdByAuthorization();
        List<Invoice> invoiceList = invoiceService.findAllInvoicesByCustomerId(loggedInUserId);
        if (invoiceList != null) {
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{invoiceId}")
    public ResponseEntity<?> getInvoiceByInvoiceId(@PathVariable String invoiceId){
        Long loggedInUserId = UserUtil.getIdByAuthorization();
        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice != null && loggedInUserId == invoice.getCustomerUser().getId()) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all-invoices")
    public ResponseEntity<?> getAllInvoices(){
        Iterable<Invoice> invoiceList = invoiceService.findAll();
        if (invoiceList != null) {
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/delete/{invoiceId}")
    public ResponseEntity<?> deleteInvoiceByInvoiceId(@PathVariable String invoiceId){
        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice != null){
            invoiceService.switchVisible(invoiceId);
            return new ResponseEntity<Object>("Invoice id: " + invoiceId + " has been invisible successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't find invoice with id: " + invoiceId, HttpStatus.NO_CONTENT);
        }

    }

    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    @PutMapping(value = "/confirm-paid/{invoiceId}")
    public ResponseEntity<?> confirmPaidInvoice(@PathVariable String invoiceId){
        Long staffId = UserUtil.getIdByAuthorization();
        return new ResponseEntity<Object>(this.invoiceService.setPaid(staffId, invoiceId), HttpStatus.CREATED);
    }
}