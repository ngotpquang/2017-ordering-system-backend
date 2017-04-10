/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.RateType;

/**
 * Created by Liger on 16-Mar-17.
 */
public interface RateTypeService {
    RateType findOne(Long rateTypeId);

    RateType create(RateType rateType);

    RateType update(RateType rateType);

    void delete(Long rateTypeId);
}
