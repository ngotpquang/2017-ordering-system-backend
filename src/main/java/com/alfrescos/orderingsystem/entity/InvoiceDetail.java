/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@Table(name = "invoice_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDetail {
    private Long id;
    private Invoice invoice;
    private FoodAndDrink foodAndDrink;
    private int quantity;
    private float price;
//    private boolean isMade = false;
    private Date orderingTime;
    private boolean isVisible = true;

    public InvoiceDetail() {
    }

    public InvoiceDetail(Invoice invoice, FoodAndDrink foodAndDrink, int quantity, Date orderingTime, float price) {
        this.invoice = invoice;
        this.foodAndDrink = foodAndDrink;
        this.quantity = quantity;
        this.orderingTime = orderingTime;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne
    @JoinColumn(name = "food_and_drink_id", nullable = false)
    public FoodAndDrink getFoodAndDrink() {
        return foodAndDrink;
    }

    public void setFoodAndDrink(FoodAndDrink foodAndDrink) {
        this.foodAndDrink = foodAndDrink;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "price", nullable = false)
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name = "ordering_time", nullable = false)
    public Date getOrderingTime() {
        return orderingTime;
    }

    public void setOrderingTime(Date orderingTime) {
        this.orderingTime = orderingTime;
    }

//    @Column(name = "is_made", nullable = false)
//    public boolean isMade() {
//        return isMade;
//    }
//
//    public void setMade(boolean made) {
//        isMade = made;
//    }

    @Column(name = "is_visible", nullable = false)
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return this.getId() + "\t" + this.getFoodAndDrink().getName() + "\t"
                + this.getQuantity() + "\t" + this.getInvoice().getId() + "\t"
                + this.getOrderingTime();
    }
}
