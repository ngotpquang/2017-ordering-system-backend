/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
public interface FoodAndDrinkService {
    FoodAndDrink create(FoodAndDrink foodAndDrink);

    FoodAndDrink update(FoodAndDrink foodAndDrink);

    FoodAndDrink findById(Long id);

    List<FoodAndDrink> findByName(String name);

    List<FoodAndDrink> findByFoodAndDrinkTypeId(Long typeId);

    List<FoodAndDrink> findAll();

    boolean switchVisible(Long id);
}
