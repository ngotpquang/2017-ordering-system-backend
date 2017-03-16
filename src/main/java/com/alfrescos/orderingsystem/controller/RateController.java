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
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Map<String, String> data){
        User user = this.userService.findById(UserUtil.getIdByAuthorization());
        String invoiceId = data.get("invoiceId").trim();
        Invoice invoice = this.invoiceService.findInvoiceByInvoiceId(invoiceId);
        float score = Float.parseFloat(data.get("score").trim());
        long rateTypeId = Long.parseLong(data.get("rateTypeId").trim());
        RateType rateType = this.rateTypeService.findOne(rateTypeId);
        Date rateTime = new Date();
        Rate rate = this.rateService.create(new Rate(user, rateType, score, invoice, rateTime));
        if (rate != null){
            return new ResponseEntity<Object>("Rate was created successfully.", HttpStatus.CREATED);
        }
        return new ResponseEntity<Object>("Can't create new rate due to error.", HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(value = "/{invoiceId}")
    public ResponseEntity<?> getRateByRateId(@PathVariable String invoiceId){
        Rate rate = this.rateService.findRateByInvoiceId(invoiceId);
        if (rate != null){
            return new ResponseEntity<Object>(rate, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("No rate was found for this invoice", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllRatesForUserLoggedIn(){
        User user = this.userService.findById(UserUtil.getIdByAuthorization());
        List<Rate> rateList = this.rateService.findAllRatesByUserId(user.getId());
        if (rateList != null){
            return new ResponseEntity<>(rateList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No rate was found for current user", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody Map<String, String> data){
        User user = this.userService.findById(UserUtil.getIdByAuthorization());
        String invoiceId = data.get("invoiceId").trim();
        Rate rate = this.rateService.findRateByInvoiceId(invoiceId);
        if (!rate.getUser().getAccountCode().equals(user.getAccountCode())){
            return new ResponseEntity<>("You don't have right to modify the rate for this invoice id: " + invoiceId, HttpStatus.NOT_ACCEPTABLE);
        }
        float score = Float.parseFloat(data.get("score").trim());
        System.out.println("Score:" + score);
        Date rateTime = new Date();
        if (rate != null){
            rate.setScore(score);
            rate.setRateTime(rateTime);
            rate = this.rateService.update(rate);
            if (rate != null) {
                return new ResponseEntity<Object>("Rating was updated successfully.", HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Object>("Can't update rate for invoice id: " + invoiceId + " due to error.", HttpStatus.NOT_ACCEPTABLE);
    }
}
