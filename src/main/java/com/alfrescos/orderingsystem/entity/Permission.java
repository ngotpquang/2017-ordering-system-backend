/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liger on 06-Mar-17.
 */
@Entity
@Table(name = "permission")
public class Permission {
    private Integer id;
    private Role role;
    private User user;
    private Date createdDate;

    public Permission() {
    }

    public Permission(Role role, User user) {
        this.role = role;
        this.user = user;
        this.createdDate = new Date();
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getPermissionId() {
        return id;
    }

    public void setPermissionId(Integer permissionId) {
        this.id = permissionId;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
