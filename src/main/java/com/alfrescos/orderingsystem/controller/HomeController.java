/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Liger on 28-Feb-17.
 */
@RestController
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> testGet() {
        return new ResponseEntity<>("Ordering System Web Service is running. Time now is: " + new Date(), HttpStatus.OK);
    }

    @GetMapping(value = "/location")
    public ResponseEntity<?> getLocation(){
        return new ResponseEntity<Object>("16.0653281,108.2212667", HttpStatus.OK);
    }

    @GetMapping(value = "/location-test")
    public ResponseEntity<?> getLocationTest(){
        return new ResponseEntity<Object>("16.0749896,108.1606557", HttpStatus.OK);
    }
}
