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
            return new ResponseEntity<Object>("Created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Can't create due to some error", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody FoodAndDrinkType foodAndDrinkType){
        FoodAndDrinkType foodAndDrinkType1 = this.foodAndDrinkTypeService.findById(foodAndDrinkType.getId());
        if (foodAndDrinkType1 != null && !foodAndDrinkType.getName().isEmpty() && !foodAndDrinkType.getDetail().isEmpty()){
            foodAndDrinkType1 = this.foodAndDrinkTypeService.update(foodAndDrinkType);
            return new ResponseEntity<Object>(foodAndDrinkType1, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Can't update due to some error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/delete/{foodAndDrinkTypeId}")
    public ResponseEntity<?> delete(@PathVariable Long foodAndDrinkTypeId){
        FoodAndDrinkType foodAndDrinkType = this.foodAndDrinkTypeService.findById(foodAndDrinkTypeId);
        if (foodAndDrinkType != null){
            this.foodAndDrinkTypeService.delete(foodAndDrinkTypeId);
            return new ResponseEntity<Object>("Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Can't delete due to some error.", HttpStatus.NO_CONTENT);
    }
}
