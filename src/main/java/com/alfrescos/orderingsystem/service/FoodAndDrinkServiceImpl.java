package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.repositoty.FoodAndDrinkRepository;
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
    public FoodAndDrink findById(Long id) {
        return foodAndDrinkRepository.findOne(id);
    }

    @Override
    public List<FoodAndDrink> findByName(String name) {
        return foodAndDrinkRepository.findByName(name);
    }

    @Override
    public Iterable<FoodAndDrink> findAll() {
        return foodAndDrinkRepository.findAll();
    }
}
