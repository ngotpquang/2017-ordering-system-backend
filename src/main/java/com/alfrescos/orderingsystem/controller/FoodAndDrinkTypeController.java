/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.FoodAndDrinkType;
import com.alfrescos.orderingsystem.service.FoodAndDrinkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Liger on 16-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/food-and-drink-type")
public class FoodAndDrinkTypeController {

    @Autowired
    private FoodAndDrinkTypeService foodAndDrinkTypeService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FoodAndDrinkType foodAndDrinkType){
        FoodAndDrinkType foodAndDrinkType1 = this.foodAndDrinkTypeService.create(foodAndDrinkType);
        if (foodAndDrinkType1 != null){
            return new ResponseEntity<Object>("Created successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Can't create due to some error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllFoodAndDrinkType(){
        return new ResponseEntity<Object>(this.foodAndDrinkTypeService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody FoodAndDrinkType foodAndDrinkType){
        FoodAndDrinkType foodAndDrinkType1 = this.foodAndDrinkTypeService.findById(foodAndDrinkType.getId());
        if (foodAndDrinkType1 != null && !foodAndDrinkType.getName().isEmpty() && !foodAndDrinkType.getDetail().isEmpty()){
            foodAndDrinkType1 = this.foodAndDrinkTypeService.update(foodAndDrinkType);
            return new ResponseEntity<Object>("Updated FADT with id: " + foodAndDrinkType1.getId() + " successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Can't update due to some error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/delete/{foodAndDrinkTypeId}")
    public ResponseEntity<?> delete(@PathVariable Long foodAndDrinkTypeId){
        FoodAndDrinkType foodAndDrinkType = this.foodAndDrinkTypeService.findById(foodAndDrinkTypeId);
        if (foodAndDrinkType != null){
            return new ResponseEntity<Object>("Visible status of FADT with id: " + foodAndDrinkTypeId + " is: " + this.foodAndDrinkTypeService.switchVisible(foodAndDrinkTypeId), HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Can't find FADT with id: " + foodAndDrinkTypeId, HttpStatus.NO_CONTENT);
    }
}
