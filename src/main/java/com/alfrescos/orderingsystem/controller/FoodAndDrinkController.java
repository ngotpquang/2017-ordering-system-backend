package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.service.FoodAndDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
@RestController
@RequestMapping(value = "/api/food-and-drink")
public class FoodAndDrinkController {

    @Autowired
    private FoodAndDrinkService foodAndDrinkservice;

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
}
