/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Liger on 27-Feb-17.
 */
@Entity
@javax.persistence.Table(name="\"table\"")
public class Table {
    private Long id;
    private int size;
    private int tableNumber;
    private Set<Invoice> invoices;
    private boolean isVisible = true;
    private Date createdDate;

    public Table() {
    }

    public Table(int size, int tableNumber) {
        this.size = size;
        this.tableNumber = tableNumber;
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

    @Column(name = "size", nullable = false)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "table")
    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
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

    @Column(name = "table_number", nullable = false)
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}


