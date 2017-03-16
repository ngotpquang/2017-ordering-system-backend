package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.*;
import com.alfrescos.orderingsystem.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        boolean isInvoiceDetailAllCreated = true;
        for (int i = 1; i <= numberOfInvoiceDetails; i++) {
            String foodAndDrinkId = data.get("foodAndDrinkId" + i).trim();
            int quantity = Integer.parseInt(data.get("quantity" + i).trim());
            FoodAndDrink foodAndDrink = foodAndDrinkService.findById(Long.parseLong(foodAndDrinkId));
            InvoiceDetail invoiceDetail = invoiceDetailService.create(new InvoiceDetail(invoice, foodAndDrink, quantity, timeOrdered, foodAndDrink.getPrice()));
            if (invoiceDetail == null) {
                isInvoiceDetailAllCreated = false;
                break;
            }
        }
        if (isInvoiceDetailAllCreated && invoice != null) {
            return new ResponseEntity<>("Ordered!", HttpStatus.CREATED);
        } else {
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all-invoices")
    public ResponseEntity<?> getAllInvoices(){
        Iterable<Invoice> invoiceList = invoiceService.findAll();
        if (invoiceList != null) {
            return new ResponseEntity<>(invoiceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{invoiceId}")
    public ResponseEntity<?> deleteInvoiceByInvoiceId(@PathVariable String invoiceId){
        Invoice invoice = invoiceService.findInvoiceByInvoiceId(invoiceId);
        if (invoice != null){
            invoiceService.delete(invoiceId);
            return new ResponseEntity<Object>("Invoice id: " + invoiceId + " has been deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't find invoice with id: " + invoiceId, HttpStatus.NO_CONTENT);
        }

    }
}
