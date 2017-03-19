package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Liger on 27-Feb-17.
 */
@Entity
@Table(name = "shift")
public class Shift {
    private Long id;
    private String detail;
    private boolean isVisible = true;

    public Shift() {
    }

    public Shift(Long id, String detail) {
        this.id = id;
        this.detail = detail;
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
}
