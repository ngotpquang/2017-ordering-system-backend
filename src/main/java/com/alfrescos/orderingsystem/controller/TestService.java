package com.alfrescos.orderingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Liger on 28-Feb-17.
 */
@RestController
@RequestMapping(value = "/")
public class TestService {

    @GetMapping
    public ResponseEntity<String> testGet() {
        return new ResponseEntity<>("Successfully", HttpStatus.OK);
    }
}
