package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import javax.persistence.Table;

import java.util.Date;
import java.util.Set;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@Table (name="\"user\"")
public class User {
    private String id;
    private String name;
    private String password;
    private byte gender;
    private Date dateOfBirth;
    private String avatar;
    private String detail;
    private float membershipPoint;
    private Role role;
    private String token;
    private Set<WorkingTime> workingTimes;

    public User() {
    }

    public User(String id, String name, String password, byte gender, Date dateOfBirth, String avatar, String detail, float membershipPoint, Role role, String token) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.detail = detail;
        this.membershipPoint = membershipPoint;
        this.role = role;
        this.token = token;
    }

    public User(String id, String name, String password, byte gender, Date dateOfBirth, String avatar, String detail, float membershipPoint, Role role, String token, Set<WorkingTime> workingTimes) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.detail = detail;
        this.membershipPoint = membershipPoint;
        this.role = role;
        this.workingTimes = workingTimes;
        this.token = token;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Column(name = "detail")
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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @OneToMany(mappedBy = "user")
    public Set<WorkingTime> getWorkingTimes() {
        return workingTimes;
    }

    public void setWorkingTimes(Set<WorkingTime> workingTimes) {
        this.workingTimes = workingTimes;
    }
}
