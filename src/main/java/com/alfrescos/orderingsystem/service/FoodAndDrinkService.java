package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
public interface FoodAndDrinkService {
    public  FoodAndDrink create(FoodAndDrink foodAndDrink);
    public  FoodAndDrink update(FoodAndDrink foodAndDrink);
    public FoodAndDrink findById(Long id);
    public List<FoodAndDrink> findByName(String name);
    public List<FoodAndDrink> findByFoodAndDrinkTypeId(Long typeId);
    public Iterable<FoodAndDrink> findAll();
    boolean switchVisible(Long id);
}
