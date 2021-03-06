/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 05-Mar-17.
 */
@Repository
public interface FoodAndDrinkRepository extends CrudRepository<FoodAndDrink, Long> {

    @Query(value = "SELECT * FROM food_and_drink AS fad WHERE fad.name LIKE %:name% OR fad.name = :name OR fad.detail LIKE %:name% AND is_visible = 1", nativeQuery = true)
//    @Query(value = "SELECT * FROM food_and_drink AS fad WHERE fad.name LIKE %:name% OR fad.name = :name OR fad.detail like %:name% AND is_visible = TRUE", nativeQuery = true)
    List<FoodAndDrink> findByName(@Param("name") String name);

    @Query(value = "SELECT * FROM food_and_drink AS fad WHERE fad.tags LIKE %:tag% AND is_visible = 1", nativeQuery = true)
//    @Query(value = "SELECT * FROM food_and_drink AS fad WHERE fad.tags LIKE %:tag% AND is_visible = TRUE", nativeQuery = true)
    List<FoodAndDrink> findByTags(@Param("tag") String tag);

    @Query(value = "SELECT * FROM food_and_drink AS fad WHERE fad.food_and_drink_type_id = ?1 AND is_visible = 1", nativeQuery = true)
//    @Query(value = "SELECT * FROM food_and_drink AS fad WHERE fad.food_and_drink_type_id = ?1 AND is_visible = TRUE", nativeQuery = true)
    List<FoodAndDrink> findByFoodAndDrinkTypeId(Long foodAndDrinkTypeId);
}
