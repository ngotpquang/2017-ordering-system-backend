package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.repositoty.FoodAndDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
@RestController
@RequestMapping(value = "api/v1/food_and_drink")
public class FoodAndDrinkService {
    @Autowired
    private FoodAndDrinkRepository foodAndDrinkRepository;

    @GetMapping(value = "/{foodAndDrinkId}")
    public ResponseEntity<FoodAndDrink> getFoodAndDrinkById(@PathVariable Long foodAndDrinkId) {
        return new ResponseEntity<>(foodAndDrinkRepository.findById(foodAndDrinkId), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<FoodAndDrink>> getFoodAndDrinkByName(@RequestParam String name) {
        return new ResponseEntity<>(foodAndDrinkRepository.findByName(name), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<FoodAndDrink>> getAllFoodAndDrink() {
        return new ResponseEntity<>(foodAndDrinkRepository.findAll(), HttpStatus.OK);
    }
}
