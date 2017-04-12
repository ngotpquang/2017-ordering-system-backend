/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@javax.persistence.Table(name = "invoice")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice {
    private String id;
    private User customerUser;
    private User staffUser;
    private Table table;
    private boolean isPaid = false;
    private Date createdDate;
    private Date payingTime;
    private Set<InvoiceDetail> invoiceDetails;
    private boolean isVisible = true;
    private float totalAmount;
    private String paymentType = "CASH";

    public Invoice() {
    }

    public Invoice(String id, User customerUser, User staffUser, Table table) {
        this.id = id;
        this.customerUser = customerUser;
        this.staffUser = staffUser;
        this.table = table;
        this.createdDate = new Date();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "customer_user_id", nullable = false)
    public User getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(User customerUser) {
        this.customerUser = customerUser;
    }

    @ManyToOne
    @JoinColumn(name = "staff_user_id", nullable = false)
    public User getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(User staffUser) {
        this.staffUser = staffUser;
    }

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Column(name = "is_paid", nullable = false)
    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Column(name = "paying_time")
    public Date getPayingTime() {
        return payingTime;
    }

    public void setPayingTime(Date payingTime) {
        this.payingTime = payingTime;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "invoice")
    public Set<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    @Column(name = "is_visible", nullable = false)
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "total_amount")
    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Column(name = "payment_type", nullable = false)



    @Override
    public String toString() {
        return this.getId() + "\t" + this.getCustomerUser().getAccountCode() + "\t"
                + this.getStaffUser().getAccountCode() + "\t" + this.getTable().getSize();
    }
}
