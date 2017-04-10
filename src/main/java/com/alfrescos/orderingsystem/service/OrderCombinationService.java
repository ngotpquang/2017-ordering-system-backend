/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.OrderCombination;

import java.util.List;

/**
 * Created by Liger on 08-Apr-17.
 */
public interface OrderCombinationService {
    List<OrderCombination> findByMainDishId(Long id);

    List<OrderCombination> findByDrinkOrDesertId(Long id);

    OrderCombination updateNumberOrderedTogether(OrderCombination orderCombination);

    OrderCombination createOrderCombination(OrderCombination orderCombination);

    OrderCombination findByMainDishIdAndDrinkAndDesertId(Long mainDishId, Long drinkOrDesertId);

    List<OrderCombination> findBestCombination(Long id, boolean isMainDish);

    OrderCombination updateVisible(OrderCombination orderCombination);
}
