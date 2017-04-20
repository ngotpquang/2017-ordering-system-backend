/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrinkType;

import java.util.List;

/**
 * Created by Liger on 16-Mar-17.
 */
public interface FoodAndDrinkTypeService {
    FoodAndDrinkType findById(Long foodAndDrinkTypeId);

    FoodAndDrinkType create(FoodAndDrinkType foodAndDrinkType);

    FoodAndDrinkType update(FoodAndDrinkType foodAndDrinkType);

    boolean switchVisible(Long foodAndDrinkTypeId);

    List<FoodAndDrinkType> findAll();

    List<FoodAndDrinkType> findAllDrinkOrDesert();

    List<FoodAndDrinkType> findAllMainDish();
}
