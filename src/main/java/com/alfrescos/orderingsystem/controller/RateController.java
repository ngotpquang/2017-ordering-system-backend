/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.entity.Rate;
import com.alfrescos.orderingsystem.entity.RateType;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.service.InvoiceService;
import com.alfrescos.orderingsystem.service.RateService;
import com.alfrescos.orderingsystem.service.RateTypeService;
import com.alfrescos.orderingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by Liger on 16-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/rate")
public class RateController {

    @Autowired
    private RateService rateService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private RateTypeService rateTypeService;

//    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Map<String, String> data) {
        try {
            Long userId = UserUtil.getAccountCodeByAuthorization().equals("anonymousUser") ? 1L : UserUtil.getIdByAuthorization();
            User user = this.userService.findById(userId);
            String invoiceId = data.get("invoiceId").trim();
            Invoice invoice = this.invoiceService.findById(invoiceId);
            if (!user.getId().equals(invoice.getCustomerUser().getId())) {
                return new ResponseEntity<Object>("You can't rate for this invoice which you don't own!", HttpStatus.NOT_ACCEPTABLE);
            } else if (this.rateService.findRateByInvoiceIdAndRateTypeId(invoiceId, 1L) != null
                    || this.rateService.findRateByInvoiceIdAndRateTypeId(invoiceId, 2L) != null) {
                return new ResponseEntity<Object>("You've already rated for this invoice.", HttpStatus.NOT_ACCEPTABLE);
            }
            if (invoice == null) {
                return new ResponseEntity<Object>("Can't create new rate due to error.", HttpStatus.NOT_ACCEPTABLE);
            }
            String floatString = data.get("score").trim();
            float scoreForFood = Float.parseFloat(floatString.split(",")[0]);
            float scoreForService = Float.parseFloat(floatString.split(",")[1]);
            Date rateTime = new Date();
            if (scoreForFood > 0) {
                RateType rateType = this.rateTypeService.findOne(1L);
                Rate rateForFood = this.rateService.create(new Rate(user, rateType, scoreForFood, invoice, rateTime));
                if (rateForFood == null) {
                    return new ResponseEntity<Object>("Can't create new rate due to error.", HttpStatus.NOT_ACCEPTABLE);
                }
            }
            if (scoreForService > 0) {
                RateType rateType = this.rateTypeService.findOne(2L);
                Rate rateForService = this.rateService.create(new Rate(user, rateType, scoreForService, invoice, rateTime));
                if (rateForService == null) {
                    return new ResponseEntity<Object>("Can't create new rate due to error.", HttpStatus.NOT_ACCEPTABLE);
                }
            }
            return new ResponseEntity<Object>("Rate was created successfully.", HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            return new ResponseEntity<Object>("Can't create new rate due to error.", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping(value = "/{invoiceId}/{rateTypeId}")
    public ResponseEntity<?> getRateByRateTypeId(@PathVariable String invoiceId, @PathVariable long rateTypeId) {
        Rate rate = this.rateService.findRateByInvoiceIdAndRateTypeId(invoiceId, rateTypeId);
        if (rate != null) {
            return new ResponseEntity<Object>(rate, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("No rate was found for this invoice", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllRatesForUserLoggedIn() {
        User user = this.userService.findById(UserUtil.getIdByAuthorization());
        List<Rate> rateList = this.rateService.findAllRatesByUserId(user.getId());
        if (rateList != null) {
            return new ResponseEntity<>(rateList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No rate was found for current user", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all/{typeId}/{score}")
    public ResponseEntity<?> getAllRatesByScore(@PathVariable int typeId, @PathVariable float score) {
        List<Rate> rateList = this.rateService.findAllRatesByRateTypeAndScore(typeId, score);
        if (rateList != null) {
            return new ResponseEntity<>(rateList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No rate was found!", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all-rates")
    public ResponseEntity<?> getAllRates() {
        Iterable<Rate> rateList = this.rateService.findAllRates();
        if (rateList != null) {
            return new ResponseEntity<>(rateList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No rates", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all-rates/average-score/{rateTypeId}")
    public ResponseEntity<?> getAverageScore(@PathVariable Long rateTypeId) {
        float averageScore = this.rateService.getAverageScoreByRateTypeId(rateTypeId);
        return new ResponseEntity<>(averageScore, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/delete/{rateId}")
    public ResponseEntity<?> delete(@PathVariable Long rateId) {
        User user = this.userService.findById(UserUtil.getIdByAuthorization());
        Rate rate = this.rateService.findById(rateId);
        if (rate != null && rate.getUser().getAccountCode().equals(user.getAccountCode())) {
            this.rateService.delete(rateId);
            return new ResponseEntity<>("Rate has id: " + rateId + " was deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can't delete due to some error", HttpStatus.NO_CONTENT);
    }

//    @PreAuthorize("isAuthenticated()")
//    @PutMapping(value = "/update")
//    public ResponseEntity<?> update(@RequestBody Map<String, String> data) {
//        User user = this.userService.findById(UserUtil.getIdByAuthorization());
//        String invoiceId = data.get("invoiceId").trim();
//        Rate rate = this.rateService.findRateByInvoiceIdAndRateTypeId(invoiceId, rateTypeId);
//        if (!rate.getUser().getAccountCode().equals(user.getAccountCode())) {
//            return new ResponseEntity<>("You don't have right to modify the rate for this invoice id: " + invoiceId, HttpStatus.NOT_ACCEPTABLE);
//        }
//        try {
//            float score = Float.parseFloat(data.get("score").trim());
//            Date rateTime = new Date();
//            if (rate != null) {
//                rate.setScore(score);
//                rate.setRateTime(rateTime);
//                rate = this.rateService.update(rate);
//                if (rate != null) {
//                    return new ResponseEntity<Object>("Rating was updated successfully.", HttpStatus.CREATED);
//                }
//            }
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<Object>("Can't update rate for invoice id: " + invoiceId + " due to error.", HttpStatus.NOT_ACCEPTABLE);
//        }
//        return new ResponseEntity<Object>("Can't update rate for invoice id: " + invoiceId + " due to error.", HttpStatus.NOT_ACCEPTABLE);
//    }

    @GetMapping(value = "/{rateTypeId}/num-of-people")
    public ResponseEntity<?> getNumOfPeople(@PathVariable int rateTypeId) {
        List<Map<String, String>> returnList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, String> data = new HashMap<>();
            String id = i + "";
            String numOfPeople = this.rateService.getNumOfPeopleByTypeAndScore(i, rateTypeId) + "";
            data.put("numOfPeople", numOfPeople);
            data.put("id", id);
            returnList.add(data);
        }
        return new ResponseEntity<Object>(returnList, HttpStatus.OK);
    }
}
