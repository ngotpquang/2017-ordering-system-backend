/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Liger on 22-Apr-17.
 */
@RestController
@RequestMapping(value = "/api/table")
public class TableController {
    @Autowired
    private TableService tableService;

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllTable(){
        return new ResponseEntity<Object>(this.tableService.findAll(), HttpStatus.OK);
    }
}
