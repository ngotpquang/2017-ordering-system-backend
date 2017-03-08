package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Liger on 06-Mar-17.
 */
@Entity
@Table(name = "permission")
public class Permission {
    private Integer id;
    private Role role;
    private User user;

    public Permission() {
    }

    public Permission(Integer id, Role role, User user) {
        this.id = id;
        this.role = role;
        this.user = user;
    }

    public Permission(Role role, User user) {
        this.role = role;
        this.user = user;
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
}
