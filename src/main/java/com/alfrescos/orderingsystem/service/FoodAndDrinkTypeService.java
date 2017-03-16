package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrinkType;

/**
 * Created by Liger on 16-Mar-17.
 */
public interface FoodAndDrinkTypeService {
    FoodAndDrinkType findById(Long foodAndDrinkTypeId);
    FoodAndDrinkType create(FoodAndDrinkType foodAndDrinkType);
    FoodAndDrinkType update(FoodAndDrinkType foodAndDrinkType);
    void delete(Long foodAndDrinkTypeId);
}
