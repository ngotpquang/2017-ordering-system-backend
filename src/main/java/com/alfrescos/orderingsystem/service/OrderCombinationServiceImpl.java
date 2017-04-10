/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.OrderCombination;
import com.alfrescos.orderingsystem.repository.OrderCombinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Liger on 08-Apr-17.
 */
@Service
public class OrderCombinationServiceImpl implements OrderCombinationService{

    @Autowired
    private OrderCombinationRepository orderCombinationRepository;

    @Override
    public List<OrderCombination> findByMainDishId(Long id) {
        return this.orderCombinationRepository.findByMainDishId(id);
    }

    @Override
    public List<OrderCombination> findByDrinkOrDesertId(Long id) {
        return this.orderCombinationRepository.findByDrinkOrDesertId(id);
    }

    @Override
    public OrderCombination updateNumberOrderedTogether(OrderCombination orderCombination) {
        return this.orderCombinationRepository.save(orderCombination);
    }

    @Override
    public OrderCombination createOrderCombination(OrderCombination orderCombination) {
        return this.orderCombinationRepository.save(orderCombination);
    }

    @Override
    public OrderCombination findByMainDishIdAndDrinkAndDesertId(Long mainDishId, Long drinkOrDesertId) {
        return this.orderCombinationRepository.findByMainDishIdAndDrinkOrDesertId(mainDishId, drinkOrDesertId);
    }

    @Override
    public List<OrderCombination> findBestCombination(Long id, boolean isMainDish) {
        if (isMainDish){
            return this.orderCombinationRepository.findByMainDishId(id);
        } else {
            return this.orderCombinationRepository.findByDrinkOrDesertId(id);
        }
    }

    @Override
    public OrderCombination updateVisible(OrderCombination orderCombination) {
        return this.orderCombinationRepository.save(orderCombination);
    }
}
