/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.alfrescos.orderingsystem.common.TableStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Liger on 16-May-17.
 */
@Entity
@javax.persistence.Table(name = "reserved_table")
public class ReservedTable {
    private Long id;
    private Table table;
    private User user;
    private int finalStatus;
    private Date reservingTime;
    private String detail;
    private int travelingTime;

    public ReservedTable(){

    }

    public ReservedTable(Table table, User user, String detail, int travelingTime) {
        this.table = table;
        this.user = user;
        this.finalStatus = TableStatus.RESERVING;
        this.reservingTime = new Date();
        this.detail = detail;
        this.travelingTime = travelingTime;
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
    @JoinColumn(name = "table_id", nullable = false)
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "final_status", nullable = false)
    public int getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(int finalStatus) {
        this.finalStatus = finalStatus;
    }

    @Column(name = "reserving_time", nullable = false)
    public Date getReservingTime() {
        return reservingTime;
    }

    public void setReservingTime(Date reservingTime) {
        this.reservingTime = reservingTime;
    }

    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Column(name = "traveling_time")
    public int getTravelingTime() {
        return travelingTime;
    }

    public void setTravelingTime(int travelingTime) {
        this.travelingTime = travelingTime;
    }
}
