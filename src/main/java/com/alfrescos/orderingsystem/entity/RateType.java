package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Table;
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

    public RateType() {
    }

    public RateType(Long id, String name, Set<Rate> rates) {
        this.id = id;
        this.name = name;
        this.rates = rates;
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
}
