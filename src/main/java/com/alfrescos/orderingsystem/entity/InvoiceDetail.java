package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@Table (name = "invoice_detail")
public class InvoiceDetail {
    private Long id;
    private Invoice invoice;
    private FoodAndDrink foodAndDrink;
    private int quantity;
    private float amount;
    private Date orderingTime;

    public InvoiceDetail() {
    }

    public InvoiceDetail(Long id, Invoice invoice, FoodAndDrink foodAndDrink, int quantity, float amount, Date orderingTime) {
        this.id = id;
        this.invoice = invoice;
        this.foodAndDrink = this.foodAndDrink;
        this.quantity = quantity;
        this.amount = amount;
        this.orderingTime = orderingTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setFoodAndDrink (FoodAndDrink foodAndDrink) {
        this.foodAndDrink = foodAndDrink;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "amount", nullable = false)
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Column(name = "ordering_time", nullable = false)
    public Date getOrderingTime() {
        return orderingTime;
    }

    public void setOrderingTime(Date orderingTime) {
        this.orderingTime = orderingTime;
    }
}
