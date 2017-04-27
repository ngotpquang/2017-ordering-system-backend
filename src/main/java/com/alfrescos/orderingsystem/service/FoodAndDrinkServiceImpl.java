/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.common.CommonUtil;
import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.repository.FoodAndDrinkRepository;
import com.google.api.client.util.Lists;
import com.google.common.collect.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

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
    public List<FoodAndDrink> findAll() {
        List<FoodAndDrink> result = (List<FoodAndDrink>) this.foodAndDrinkRepository.findAll();
        return result.stream().filter(foodAndDrink -> foodAndDrink.isVisible()).collect(Collectors.toList());
    }

    @Override
    public boolean switchVisible(Long id) {
        FoodAndDrink foodAndDrink = this.foodAndDrinkRepository.findOne(id);
        foodAndDrink.setVisible(!foodAndDrink.isVisible());
        return this.foodAndDrinkRepository.save(foodAndDrink).isVisible();
    }

    @Override
    public List<FoodAndDrink> findByTag(String tag) {
        String[] tagList = tag.trim().split(" ");
        Map<FoodAndDrink, Integer> data = new HashMap<>();
        List<FoodAndDrink> allFAD = (List<FoodAndDrink>) this.foodAndDrinkRepository.findAll();
        for (FoodAndDrink fad : allFAD) {
            if(fad.isVisible()){
                data.put(fad, 1);
            }
        }
        for (String s : tagList) {
            List<FoodAndDrink> foodAndDrinkList = this.foodAndDrinkRepository.findByTags(s.trim().toLowerCase());
            for (FoodAndDrink fad : foodAndDrinkList) {
                data.put(fad, data.get(fad) + 1);
            }
        }
        List<Map.Entry<FoodAndDrink, Integer>> collect = data.entrySet().stream().sorted(Map.Entry.<FoodAndDrink, Integer>comparingByValue().reversed()).collect(Collectors.toList());
        List<FoodAndDrink> result = new ArrayList<>();
        for (Map.Entry<FoodAndDrink, Integer> entry : collect) {
            if(entry.getValue() > 1){
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
