/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrinkType;
import com.alfrescos.orderingsystem.repository.FoodAndDrinkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liger on 16-Mar-17.
 */
@Service
public class FoodAndDrinkTypeServiceImpl implements FoodAndDrinkTypeService{

    @Autowired
    private FoodAndDrinkTypeRepository foodAndDrinkTypeRepository;

    @Override
    public FoodAndDrinkType findById(Long foodAndDrinkTypeId) {
        return this.foodAndDrinkTypeRepository.findOne(foodAndDrinkTypeId);
    }

    @Override
    public FoodAndDrinkType create(FoodAndDrinkType foodAndDrinkType) {
        return this.foodAndDrinkTypeRepository.save(foodAndDrinkType);
    }

    @Override
    public FoodAndDrinkType update(FoodAndDrinkType foodAndDrinkType) {
        return this.foodAndDrinkTypeRepository.save(foodAndDrinkType);
    }

    @Override
    public boolean switchVisible(Long foodAndDrinkTypeId) {
        FoodAndDrinkType foodAndDrinkType = this.foodAndDrinkTypeRepository.findOne(foodAndDrinkTypeId);
        foodAndDrinkType.setVisible(!foodAndDrinkType.isVisible());
        return this.foodAndDrinkTypeRepository.save(foodAndDrinkType).isVisible();
    }
}
