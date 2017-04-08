/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.FoodAndDrinkType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 16-Mar-17.
 */
@Repository
public interface FoodAndDrinkTypeRepository extends CrudRepository<FoodAndDrinkType, Long>{
    @Query(value = "SELECT * FROM food_and_drink_type WHERE is_main_dish = '1'", nativeQuery = true)
    List<FoodAndDrinkType> findAllMainDish();

    @Query(value = "SELECT * FROM food_and_drink_type WHERE is_main_dish = '0'", nativeQuery = true)
    List<FoodAndDrinkType> findAllDrinkOrDesert();
}
