package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Liger on 27-Feb-17.
 */
@Entity
@javax.persistence.Table(name="\"table\"")
public class Table {
    private Long id;
    private int size;
    private Set<Invoice> invoices;

    public Table() {
    }

    public Table(Long id, int size) {
        this.id = id;
        this.size = size;
    }

    public Table(Long id, int size, Set<Invoice> invoices) {
        this.id = id;
        this.size = size;
        this.invoices = invoices;
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

    @OneToMany(mappedBy = "table")
    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}


