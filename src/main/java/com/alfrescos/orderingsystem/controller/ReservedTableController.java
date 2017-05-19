/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.common.TableStatus;
import com.alfrescos.orderingsystem.common.UserUtil;
import com.alfrescos.orderingsystem.entity.ReservedTable;
import com.alfrescos.orderingsystem.entity.Table;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.service.ReservedTableService;
import com.alfrescos.orderingsystem.service.TableService;
import com.alfrescos.orderingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Liger on 16-May-17.
 */
@RestController
    @RequestMapping(value = "/api/reserved-table")
public class ReservedTableController {

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @Autowired
    private ReservedTableService reservedTableService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CUSTOMER')")
    @PostMapping
    ResponseEntity<?> create(@RequestBody Map<String, String> data){
        Long userId = UserUtil.getIdByAuthorization();
        User user = this.userService.findById(userId);
        try {
            long tableId = Long.parseLong(data.get("tableId").trim());
            Table table = this.tableService.findById(tableId);
            String detail = data.get("detail");
            int travelingTime = Integer.parseInt(data.get("travelingTime").trim());
            if(user != null && table != null && table.getTableStatus() != TableStatus.ORDERING){
                ReservedTable reservedTable = new ReservedTable(table, user, detail, travelingTime);
                reservedTable = this.reservedTableService.create(reservedTable);
                if (reservedTable != null){
                    table.setTableStatus(TableStatus.RESERVED);
                    table = this.tableService.update(table);
                    if(table != null){
                        return new ResponseEntity<Object>("Table number: " + table.getTableNumber() + " is reserved by user: " + user.getName() + ".", HttpStatus.CREATED);
                    }
                } else {
                    return new ResponseEntity<Object>("Can't reserve a table due to error", HttpStatus.NOT_ACCEPTABLE);
                }
            }

        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't reserve a table due to error", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Object>("Can't reserve a table due to error", HttpStatus.NOT_ACCEPTABLE);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CUSTOMER')")
    @PutMapping(value = "/update")
    ResponseEntity<?> update(@RequestBody Map<String, String> data){
        Long userId = UserUtil.getIdByAuthorization();
        User user = this.userService.findById(userId);
        try {
            String detail = data.get("detail");
            long reservedTableId = Long.parseLong(data.get("reservedTableId").trim());
            ReservedTable reservedTable = this.reservedTableService.findById(reservedTableId);
            if(reservedTable != null){
                int finalStatus = Integer.parseInt(data.get("finalStatus").trim());
                Table table = this.tableService.findById(reservedTable.getTable().getId());
                boolean isManager = UserUtil.checkAdminOrManagerAccount();
                switch (finalStatus){
                    case TableStatus.USED:
                        reservedTable.setFinalStatus(TableStatus.USED);
                        break;
                    case TableStatus.USER_CANCEL:
                        reservedTable.setFinalStatus(TableStatus.USER_CANCEL);
                        table.setTableStatus(TableStatus.FREE);
                        break;
                    case TableStatus.TIME_OUT:
                        reservedTable.setFinalStatus(TableStatus.TIME_OUT);
                        table.setTableStatus(TableStatus.FREE);
                        break;
                    case TableStatus.ADMIN_CANCEL:
                        if(isManager){
                            reservedTable.setFinalStatus(TableStatus.ADMIN_CANCEL);
                            table.setTableStatus(TableStatus.FREE);
                        } else {
                            return new ResponseEntity<Object>("You are not ADMIN/MANAGER to edit this record", HttpStatus.FORBIDDEN);
                        }
                        break;
                }
                this.tableService.update(table);
            } else {
                return new ResponseEntity<Object>("Can't find reserved table with id: " + reservedTableId, HttpStatus.NO_CONTENT);
            }
            reservedTable.setDetail(detail);
            reservedTable = this.reservedTableService.update(reservedTable);
            if(reservedTable != null){
                return new ResponseEntity<Object>("Update successfully", HttpStatus.OK);
            }
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't update reserved table due to error", HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<Object>("Can't update reserved table due to error", HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CUSTOMER')")
    @GetMapping
    ResponseEntity<?> getReservedTable(){
        System.out.println(UserUtil.checkAdminOrManagerAccount());
        if(UserUtil.checkAdminOrManagerAccount()){
            List<ReservedTable> reservedTableList = this.reservedTableService.findAll();
            System.out.println(reservedTableList.size());
            if (reservedTableList != null){
                reservedTableList = reservedTableList.stream().filter(reservedTable -> reservedTable.getFinalStatus() == TableStatus.RESERVING).collect(Collectors.toList());
                return new ResponseEntity<Object>(reservedTableList, HttpStatus.OK);
            }
        } else {
            Long userId = UserUtil.getIdByAuthorization();
            List<ReservedTable> reservedTableList = this.reservedTableService.findByUserId(userId);
            if (reservedTableList != null){
                reservedTableList = reservedTableList.stream().filter(reservedTable -> reservedTable.getFinalStatus() == TableStatus.RESERVING).collect(Collectors.toList());
                return new ResponseEntity<Object>(reservedTableList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Object>("Can't find due to error", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CUSTOMER')")
    @GetMapping(value = "/history")
    ResponseEntity<?> getHistoryReservedTable(){
        if(UserUtil.checkAdminOrManagerAccount()){
            List<ReservedTable> reservedTableList = this.reservedTableService.findAll();
            return new ResponseEntity<Object>(reservedTableList, HttpStatus.OK);
        } else {
            Long userId = UserUtil.getIdByAuthorization();
            List<ReservedTable> reservedTableList = this.reservedTableService.findByUserId(userId);
            return new ResponseEntity<Object>(reservedTableList, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/table")
    ResponseEntity<?> getReservedTableByTableId(@RequestParam long tableId){
        List<ReservedTable> reservedTableList = this.reservedTableService.findByTableId(tableId);
        if (reservedTableList != null){
            reservedTableList = reservedTableList.stream().filter(reservedTable -> reservedTable.getFinalStatus() == TableStatus.RESERVING).collect(Collectors.toList());
            return new ResponseEntity<Object>(reservedTableList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't find data due to error", HttpStatus.NO_CONTENT);
        }
    }

}
