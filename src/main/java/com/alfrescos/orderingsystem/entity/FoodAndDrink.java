package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Liger on 27-Feb-17.
 */
@Entity
@Table(name = "food_and_drink")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FoodAndDrink {
    private Long id;
    private String name;
    private float price;
    private String detail;
    private FoodAndDrinkType foodAndDrinkType;
    private boolean isVisible = true;

    public FoodAndDrink() {
    }

    public FoodAndDrink(Long id, String name, float price, String detail, FoodAndDrinkType foodAndDrinkType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.foodAndDrinkType = foodAndDrinkType;
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
}
