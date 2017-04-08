/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * Created by Liger on 27-Feb-17.
 */
@Entity
@Table(name = "rate_type")
public class RateType {
    private Long id;
    private String name;
    private Set<Rate> rates;
    private Date createdDate;

    public RateType() {
    }

    public RateType(String name, Set<Rate> rates) {
        this.name = name;
        this.rates = rates;
        this.createdDate = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "rateType")
    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
