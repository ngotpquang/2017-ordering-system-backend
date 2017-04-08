/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.entity.FoodAndDrinkType;
import com.alfrescos.orderingsystem.entity.OrderCombination;
import com.alfrescos.orderingsystem.service.FoodAndDrinkService;
import com.alfrescos.orderingsystem.service.FoodAndDrinkTypeService;
import com.alfrescos.orderingsystem.service.OrderCombinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Liger on 05-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/food-and-drink")
public class FoodAndDrinkController {

    @Autowired
    private FoodAndDrinkService foodAndDrinkservice;

    @Autowired
    private OrderCombinationService orderCombinationService;

    @Autowired
    private FoodAndDrinkTypeService foodAndDrinkTypeService;

    @GetMapping(value = "/{foodAndDrinkId}")
    public ResponseEntity<?> getFoodAndDrinkById(@PathVariable Long foodAndDrinkId) {
        FoodAndDrink foodAndDrink = foodAndDrinkservice.findById(foodAndDrinkId);
        if (foodAndDrink != null) {
            return new ResponseEntity<>(foodAndDrink, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> getFoodAndDrinkByName(@RequestParam String name) {
        List<FoodAndDrink> foodAndDrinkList = foodAndDrinkservice.findByName(name);
        if (foodAndDrinkList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(foodAndDrinkList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllFoodAndDrink() {
        Iterable<FoodAndDrink> foodAndDrinkList = foodAndDrinkservice.findAll();
        if (!foodAndDrinkList.iterator().hasNext()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(foodAndDrinkList, HttpStatus.OK);
        }
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FoodAndDrink foodAndDrink){
        FoodAndDrink createdFoodAndDrink = this.foodAndDrinkservice.create(foodAndDrink);
        if (createdFoodAndDrink != null){
            if (createdFoodAndDrink.getFoodAndDrinkType().isMainDish()){
                List<FoodAndDrinkType> drinkOrDesertTypeList = this.foodAndDrinkTypeService.findAllDrinkOrDesert();
                if (!drinkOrDesertTypeList.isEmpty()){
                    for (FoodAndDrinkType fadt: drinkOrDesertTypeList) {
                        List<FoodAndDrink> drinkOrDesertList = this.foodAndDrinkservice.findByFoodAndDrinkTypeId(fadt.getId());
                        for (FoodAndDrink fad: drinkOrDesertList){
                            this.orderCombinationService.createOrderCombination(new OrderCombination(createdFoodAndDrink, fad));
                        }
                    }
                }
            } else {
                List<FoodAndDrinkType> mainDishTypeList = this.foodAndDrinkTypeService.findAllMainDish();
                if(!mainDishTypeList.isEmpty()){
                    for (FoodAndDrinkType fadt: mainDishTypeList) {
                        List<FoodAndDrink> mainDishList = this.foodAndDrinkservice.findByFoodAndDrinkTypeId(fadt.getId());
                        for (FoodAndDrink fad: mainDishList){
                            this.orderCombinationService.createOrderCombination(new OrderCombination(fad, createdFoodAndDrink));
                        }
                    }
                }
            }
            return new ResponseEntity<Object>("Created FAD name: " + createdFoodAndDrink.getName() + " successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Can't create due to error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody Map<String, String> data){
        try {
            Long foodAndDrinkId = Long.parseLong(data.get("foodAndDrinkId"));
            String detail = data.get("detail");
            String tags= data.get("tags");
            FoodAndDrink oldFoodAndDrink = this.foodAndDrinkservice.findById(foodAndDrinkId);
            if (oldFoodAndDrink != null
                    && !oldFoodAndDrink.getDetail().equals(detail)
                    && !oldFoodAndDrink.getTags().equals(tags)){
                oldFoodAndDrink.setDetail(detail);
                oldFoodAndDrink.setTags(tags);
                oldFoodAndDrink = this.foodAndDrinkservice.update(oldFoodAndDrink);
                return new ResponseEntity<Object>("Updated FAD id: " + oldFoodAndDrink.getId() + " successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Object>("Can't update due to error.", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>("Can't update due to error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/delete/{foodAndDrinkId}")
    public ResponseEntity<?> delete(@PathVariable long foodAndDrinkId){
        if (this.foodAndDrinkservice.findById(foodAndDrinkId) != null){
            return new ResponseEntity<Object>("Visible status of FAD with id: " + foodAndDrinkId + " is: " + this.foodAndDrinkservice.switchVisible(foodAndDrinkId), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("Can't find FAD with id: " + foodAndDrinkId, HttpStatus.NO_CONTENT);
        }
    }
}
