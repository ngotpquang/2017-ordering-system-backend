/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.entity.OrderCombination;
import com.alfrescos.orderingsystem.service.FoodAndDrinkService;
import com.alfrescos.orderingsystem.service.OrderCombinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liger on 09-Apr-17.
 */
@RestController
@RequestMapping(value = "/api/order-combination")
public class OrderCombinationController {

    @Autowired
    private OrderCombinationService orderCombinationService;

    @Autowired
    private FoodAndDrinkService foodAndDrinkService;

    @GetMapping(value = "/{foodAndDrinkId}")
    public ResponseEntity<?> getOrderCombination(@PathVariable Long foodAndDrinkId){
        List<OrderCombination> result = new ArrayList<>();
        FoodAndDrink foodAndDrink = this.foodAndDrinkService.findById(foodAndDrinkId);
        List<OrderCombination> orderCombinationList = this.orderCombinationService.findBestCombination(foodAndDrinkId, foodAndDrink.getFoodAndDrinkType().isMainDish());
        result.add(orderCombinationList.get(0));
        result.add(orderCombinationList.get(1));
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
