package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@Table(name = "rate")
public class Rate {
    private Long id;
    private User user;
    private RateType rateType;
    private float score;
    private Invoice invoice;
    private Date rateTime;

    public Rate() {
    }

    public Rate(Long id, User user, RateType rateType, float score, Invoice invoice, Date rateTime) {
        this.id = id;
        this.user = user;
        this.rateType = rateType;
        this.score = score;
        this.invoice = invoice;
        this.rateTime = rateTime;
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
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "rate_type_id", nullable = false)
    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    @Column(name = "score", nullable = false)
    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Column(name = "rate_time", nullable = false)
    public Date getRateTime() {
        return rateTime;
    }

    public void setRateTime(Date rateTime) {
        this.rateTime = rateTime;
    }
}
