/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.repository;

import com.alfrescos.orderingsystem.entity.FoodAndDrinkType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 16-Mar-17.
 */
@Repository
public interface FoodAndDrinkTypeRepository extends CrudRepository<FoodAndDrinkType, Long>{
}
