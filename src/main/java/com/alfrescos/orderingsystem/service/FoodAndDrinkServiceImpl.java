package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.repositoty.FoodAndDrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Liger on 05-Mar-17.
 */
public class FoodAndDrinkServiceImpl implements FoodAndDrinkService {
    @Autowired
    private FoodAndDrinkRepository foodAndDrinkRepository;

    @Override
    public FoodAndDrink getFoodAndDrink(Long id) {
        return foodAndDrinkRepository.findOne(id);
    }
}
