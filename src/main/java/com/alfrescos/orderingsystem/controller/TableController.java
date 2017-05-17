/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.TableStatus;
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
    public ResponseEntity<?> getAllTable() {
        return new ResponseEntity<Object>(this.tableService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> create (@RequestBody Map<String, String> data) {
        try {
            Table table = this.tableService.findLastTable();
            int tableNumber = 1;
            if(table != null){
                tableNumber = table.getTableNumber() + 1;
            }
            int size = Integer.parseInt(data.get("size").trim());
            Table table1 = new Table(size, tableNumber);
            table1 = this.tableService.create(table1);
            if (table != null) {
                return new ResponseEntity<Object>(table1, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Object>("Can't create due to error.", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't create due to error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'STAFF')")
    @PutMapping(value = "/update-status")
    public ResponseEntity<?> updateTableStatus(@RequestBody Map<String, String> data) {
        try {
            Long tableId = Long.parseLong(data.get("tableId").trim());
            int statusNumber = Integer.parseInt(data.get("statusNumber").trim());
            Table table = this.tableService.findById(tableId);
            if (table != null) {
                switch (statusNumber) {
                    case TableStatus.FREE:
                        table.setTableStatus(TableStatus.FREE);
                        break;
                    case TableStatus.ORDERING:
                        table.setTableStatus(TableStatus.ORDERING);
                        break;
                    case TableStatus.FOOD_IS_MADE:
                        table.setTableStatus(TableStatus.FOOD_IS_MADE);
                        break;
                    case TableStatus.CLEANING:
                        table.setTableStatus(TableStatus.CLEANING);
                        break;
                }
                table = this.tableService.update(table);
                if (table.getTableStatus() == statusNumber){
                    return new ResponseEntity<Object>("Update status for table number: " + table.getTableNumber() + " successfully!", HttpStatus.OK);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't update due to some errors!", HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<Object>("Can't update due to some errors!", HttpStatus.NOT_MODIFIED);
    }
}
