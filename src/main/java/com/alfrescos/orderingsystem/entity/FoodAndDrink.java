/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * Created by Liger on 27-Feb-17.
 */
@Entity
@Table(name = "food_and_drink")
public class FoodAndDrink {
    private Long id;
    private String name;
    private float price;
    private String detail;
    private int numOrdered = 0;
    private FoodAndDrinkType foodAndDrinkType;
    private Set<OrderCombination> mainDishCombination;
    private Set<OrderCombination> drinkOrDesertCombination;
    private String tags;
    private Date createdDate;
    private boolean isVisible = true;

    public FoodAndDrink() {
    }

    public FoodAndDrink(String name, float price, String detail, FoodAndDrinkType foodAndDrinkType) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.foodAndDrinkType = foodAndDrinkType;
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

    @Column(name = "price", nullable = false)
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @ManyToOne
    @JoinColumn(name = "food_and_drink_type_id", nullable = false)
    public FoodAndDrinkType getFoodAndDrinkType() {
        return foodAndDrinkType;
    }

    @Column(name = "is_visible", nullable = false)
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setFoodAndDrinkType(FoodAndDrinkType foodAndDrinkType) {
        this.foodAndDrinkType = foodAndDrinkType;
    }

    @Column(name = "num_ordered", nullable = false)
    public int getNumOrdered() {
        return numOrdered;
    }

    public void setNumOrdered(int numOrdered) {
        this.numOrdered = numOrdered;
    }

    @OneToMany(mappedBy = "mainDish")
    @JsonIgnore
    public Set<OrderCombination> getMainDishCombination() {
        return mainDishCombination;
    }

    public void setMainDishCombination(Set<OrderCombination> mainDishCombination) {
        this.mainDishCombination = mainDishCombination;
    }

    @OneToMany(mappedBy = "drinkOrDesert")
    @JsonIgnore
    public Set<OrderCombination> getDrinkOrDesertCombination() {
        return drinkOrDesertCombination;
    }

    public void setDrinkOrDesertCombination(Set<OrderCombination> drinkOrDesertCombination) {
        this.drinkOrDesertCombination = drinkOrDesertCombination;
    }

    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
