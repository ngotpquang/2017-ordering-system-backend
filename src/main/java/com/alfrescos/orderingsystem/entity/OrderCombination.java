/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;

/**
 * Created by Liger on 08-Apr-17.
 */
@Entity
@javax.persistence.Table(name = "order_combination")
public class OrderCombination {
    private long id;
    private FoodAndDrink mainDish;
    private FoodAndDrink drinkOrDesert;
    private int numOfOrderedTogether = 0;
    private boolean isVisible = true;

    public OrderCombination() {
    }

    public OrderCombination(FoodAndDrink mainDish, FoodAndDrink drinkOrDesert) {
        this.mainDish = mainDish;
        this.drinkOrDesert = drinkOrDesert;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "main_dish_id", nullable = false)
    public FoodAndDrink getMainDish() {
        return mainDish;
    }

    public void setMainDish(FoodAndDrink mainDish) {
        this.mainDish = mainDish;
    }

    @ManyToOne
    @JoinColumn(name = "drink_or_desert_id", nullable = false)
    public FoodAndDrink getDrinkOrDesert() {
        return drinkOrDesert;
    }

    public void setDrinkOrDesert(FoodAndDrink drinkOrDesert) {
        this.drinkOrDesert = drinkOrDesert;
    }

    @Column(name = "num_of_ordered_together", nullable = false)
    public int getNumOfOrderedTogether() {
        return numOfOrderedTogether;
    }

    public void setNumOfOrderedTogether(int numOfOrderedTogether) {
        this.numOfOrderedTogether = numOfOrderedTogether;
    }

    @Column(name = "is_visible", nullable = false)
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
