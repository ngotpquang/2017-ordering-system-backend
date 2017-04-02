/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.repository.FoodAndDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
@Service
public class FoodAndDrinkServiceImpl implements FoodAndDrinkService {

    @Autowired
    private FoodAndDrinkRepository foodAndDrinkRepository;

    @Override
    public FoodAndDrink create(FoodAndDrink foodAndDrink) {
        return this.foodAndDrinkRepository.save(foodAndDrink);
    }

    @Override
    public FoodAndDrink update(FoodAndDrink foodAndDrink) {
        return this.foodAndDrinkRepository.save(foodAndDrink);
    }

    @Override
    public FoodAndDrink findById(Long id) {
        return foodAndDrinkRepository.findOne(id);
    }

    @Override
    public List<FoodAndDrink> findByName(String name) {
        return foodAndDrinkRepository.findByName(name);
    }

    @Override
    public List<FoodAndDrink> findByFoodAndDrinkTypeId(Long typeId) {
        return this.foodAndDrinkRepository.findByFoodAndDrinkTypeId(typeId);
    }

    @Override
    public Iterable<FoodAndDrink> findAll() {
        return foodAndDrinkRepository.findAll();
    }

    @Override
    public boolean switchVisible(Long id) {
        FoodAndDrink foodAndDrink = this.foodAndDrinkRepository.findOne(id);
        foodAndDrink.setVisible(!foodAndDrink.isVisible());
        return this.foodAndDrinkRepository.save(foodAndDrink).isVisible();
    }
}
