/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.Table;
import com.alfrescos.orderingsystem.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> createTable(@RequestBody Map<String, String> data){
        try {
            Table table = this.tableService.findLastTable();
            int tableNumber = table.getTableNumber() + 1;
            int size = Integer.parseInt(data.get("size").trim());
            Table table1 = new Table(size, tableNumber);
            table1 = this.tableService.create(table1);
            if (table != null){
                return new ResponseEntity<Object>(table1, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Object>("Can't create due to error.", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't create due to error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
