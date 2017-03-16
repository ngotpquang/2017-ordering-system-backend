package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
public interface FoodAndDrinkService {
    public FoodAndDrink findById(Long id);
    public List<FoodAndDrink> findByName(String name);
    public Iterable<FoodAndDrink> findAll();
}
