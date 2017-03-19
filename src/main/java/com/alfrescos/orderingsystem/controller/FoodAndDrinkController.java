package com.alfrescos.orderingsystem.controller;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.service.FoodAndDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FoodAndDrink foodAndDrink){
        FoodAndDrink createdFoodAndDrink = this.foodAndDrinkservice.create(foodAndDrink);
        if (createdFoodAndDrink != null){
            return new ResponseEntity<Object>("Created FAD name: " + createdFoodAndDrink.getName() + " successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Can't create due to error.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@Valid @RequestBody FoodAndDrink foodAndDrink){
        FoodAndDrink oldFoodAndDrink = this.foodAndDrinkservice.findById(foodAndDrink.getId());
        if (oldFoodAndDrink != null && !foodAndDrink.getName().isEmpty() && foodAndDrink.getPrice() != 0 && foodAndDrink.getFoodAndDrinkType() != null){
            return new ResponseEntity<Object>("Updated FAD id: " + foodAndDrink.getId() + " successfully.", HttpStatus.CREATED);
        } else {
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
