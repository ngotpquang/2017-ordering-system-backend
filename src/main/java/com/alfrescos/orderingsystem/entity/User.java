/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
//@JsonFilter("filter.User")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "\"user\"")
public class User {
    private Long id;
    private String email;
    private String name;
    private String password;
    private byte gender;
    private Date dateOfBirth;
    private String avatar;
    private String detail;
    private float membershipPoint = 0;
    private Set<WorkingTime> workingTimes;
    private List<Permission> permissionList = new ArrayList<>();
    private String accountCode;
//    private byte[] avatarContent;
    private Set<Invoice> customerInvoices;
    private Set<Invoice> staffInvoices;
    private Date createdDate;
    private boolean isDeleted = false;
    private Date lastAccess;
    private Set<ReservedTable> reservedTableSet;
//    private boolean ignoreJoin;

    public User() {
        this.createdDate = new Date();
    }

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdDate = new Date();
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "account_code", nullable = false)
    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "gender")
    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "detail", columnDefinition = "varchar(255)")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Column(name = "membership_point", nullable = false)
    public float getMembershipPoint() {
        return membershipPoint;
    }

    public void setMembershipPoint(float membershipPoint) {
        this.membershipPoint = membershipPoint;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<WorkingTime> getWorkingTimes() {
        return workingTimes;
    }

    public void setWorkingTimes(Set<WorkingTime> workingTimes) {
        this.workingTimes = workingTimes;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

//    @Lob
//    For PostgreSQL
//    @Column(name = "avatar_content", columnDefinition = "oid")
//    For SQL Server
//    @Column(name = "avatar_content", columnDefinition = "varbinary(max)")
//    public byte[] getAvatarContent() {
//        return avatarContent;
//    }
//
//    public void setAvatarContent(byte[] avatarContent) {
//        this.avatarContent = avatarContent;
//    }

//    @JsonIgnore
//    public boolean isIgnoreJoin() {
//        return ignoreJoin;
//    }
//    public void setIgnoreJoin(boolean ignoreJoin) {
//        this.ignoreJoin = ignoreJoin;
//    }

    @Column(name = "is_deleted", nullable = false)
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @OneToMany(mappedBy = "customerUser")
    @JsonIgnore
    public Set<Invoice> getCustomerInvoices() {
        return customerInvoices;
    }

    public void setCustomerInvoices(Set<Invoice> customerInvoices) {
        this.customerInvoices = customerInvoices;
    }

    @OneToMany(mappedBy = "staffUser")
    @JsonIgnore
    public Set<Invoice> getStaffInvoices() {
        return staffInvoices;
    }

    public void setStaffInvoices(Set<Invoice> staffInvoices) {
        this.staffInvoices = staffInvoices;
    }

    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "lass_access")
    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    public Set<ReservedTable> getReservedTableSet() {
        return reservedTableSet;
    }

    public void setReservedTableSet(Set<ReservedTable> reservedTableSet) {
        this.reservedTableSet = reservedTableSet;
    }

    @Override
    public String toString() {
        return this.id + "\t" + this.email + "\t" + this.name + "\t" + this.gender + "\t"
                + this.dateOfBirth + "\t" + this.password + "\t" + this.accountCode + "\t" + this.lastAccess + "\n";
    }
}
