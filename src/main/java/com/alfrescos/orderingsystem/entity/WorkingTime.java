package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@Table(name = "working_time")
public class WorkingTime {
    private Long id;
    private User user;
    private Shift shift;
    private Date date;

    public WorkingTime() {
    }

    public WorkingTime(Long id, User user, Shift shift, Date date) {
        this.id = id;
        this.user = user;
        this.shift = shift;
        this.date = date;
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
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "shift_id", nullable = false)
    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
