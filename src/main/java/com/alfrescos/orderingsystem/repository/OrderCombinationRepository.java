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
public interface OrderCombinationRepository extends CrudRepository<OrderCombination, Long> {

//    @Query(value = "SELECT * FROM order_combination WHERE main_dish_id = ?1 AND is_visible = 1 ORDER BY num_of_ordered_together DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM order_combination WHERE main_dish_id = ?1 AND is_visible = TRUE ORDER BY num_of_ordered_together DESC", nativeQuery = true)
    List<OrderCombination> findByMainDishId(Long id);

//    @Query(value = "SELECT * FROM order_combination WHERE drink_or_desert_id = ?1 AND is_visible = 1 ORDER BY num_of_ordered_together DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM order_combination WHERE drink_or_desert_id = ?1 AND is_visible = TRUE ORDER BY num_of_ordered_together DESC", nativeQuery = true)
    List<OrderCombination> findByDrinkOrDesertId(Long id);

//    @Query(value = "SELECT * FROM order_combination WHERE main_dish_id = ?1 AND drink_or_desert_id = ?2 AND is_visible = 1", nativeQuery = true)
    @Query(value = "SELECT * FROM order_combination WHERE main_dish_id = ?1 AND drink_or_desert_id = ?2 AND is_visible = TRUE", nativeQuery = true)
    OrderCombination findByMainDishIdAndDrinkOrDesertId(Long mainDishId, Long drinkOrDesertId);

//    @Query(value = "SELECT TOP 2 * FROM order_combination WHERE drink_or_desert_id = ?1 AND is_visible = 1 ORDER BY num_of_ordered_together DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM order_combination WHERE drink_or_desert_id = ?1 AND is_visible = TRUE ORDER BY num_of_ordered_together DESC LIMIT 2", nativeQuery = true)
    List<OrderCombination> findTop2ByDrinkOrDesertId(Long id);

//    @Query(value = "SELECT TOP 2 * FROM order_combination WHERE main_dish_id = ?1 AND is_visible = 1 ORDER BY num_of_ordered_together DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM order_combination WHERE main_dish_id = ?1 AND is_visible = TRUE ORDER BY num_of_ordered_together DESC LIMIT 2", nativeQuery = true)
    List<OrderCombination> findTop2ByMainDishId(Long id);
}
