package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@Table(name = "food_and_drink_type")
public class FoodAndDrinkType {
    private Long id;
    private String name;
    private String detail;
    private Set<FoodAndDrink> foodAndDrinks;

    public FoodAndDrinkType() {
    }

    public FoodAndDrinkType(Long id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public FoodAndDrinkType(Long id, String name, String detail, Set<FoodAndDrink> foodAndDrinks) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.foodAndDrinks = foodAndDrinks;
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

    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @OneToMany(mappedBy = "food_and_drink_type")
    public Set<FoodAndDrink> getFoodAndDrinks() {
        return foodAndDrinks;
    }

    public void setFoodAndDrinks(Set<FoodAndDrink> foodAndDrinks) {
        this.foodAndDrinks = foodAndDrinks;
    }
}
