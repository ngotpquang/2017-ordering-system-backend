package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.RateType;
import com.alfrescos.orderingsystem.repositoty.RateTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liger on 16-Mar-17.
 */
@Service
public class RateTypeServiceImpl implements RateTypeService {

    @Autowired
    private RateTypeRepository rateTypeRepository;

    @Override
    public RateType findOne(Long rateTypeId) {
        return this.rateTypeRepository.findOne(rateTypeId);
    }

    @Override
    public RateType create(RateType rateType) {
        return this.rateTypeRepository.save(rateType);
    }

    @Override
    public RateType update(RateType rateType) {
        return this.rateTypeRepository.save(rateType);
    }

    @Override
    public void delete(Long rateTypeId) {
        this.rateTypeRepository.delete(rateTypeId);
    }
}
