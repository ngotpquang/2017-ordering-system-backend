/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.OrderCombination;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liger on 08-Apr-17.
 */
@Repository
public interface OrderCombinationRepository extends CrudRepository<OrderCombination, Long>{

    @Query(value = "SELECT * FROM order_combination WHERE main_dish_id = ?1", nativeQuery = true)
    List<OrderCombination> findByMainDishId(Long id);

    @Query(value = "SELECT * FROM order_combination WHERE drink_or_desert_id = ?1", nativeQuery = true)
    List<OrderCombination> findByDrinkOrDesertId(Long id);
}
