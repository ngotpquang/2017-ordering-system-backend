/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liger on 27-Feb-17.
 */
@Entity
@Table(name = "shift")
public class Shift {
    private Long id;
    private String detail;
    private boolean isVisible = true;
    private Date createdDate;

    public Shift() {
    }

    public Shift(String detail) {
        this.detail = detail;
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

    @Column(name = "detail", nullable = false)
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
}
