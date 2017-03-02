package com.alfrescos.orderingsystem.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Liger on 28-Feb-17.
 */
@Entity
@javax.persistence.Table(name = "invoice")
public class Invoice {
    private String id;
    private User customerUser;
    private User staffUser;
    private Table table;
    private boolean isPaid;
    private Date payingTime;
    private Set<InvoiceDetail> invoiceDetails;

    public Invoice() {
    }

    public Invoice(String id, User customerUser, User staffUser, Table table, boolean isPaid, Date payingTime, Set<InvoiceDetail> invoiceDetails) {
        this.id = id;
        this.customerUser = customerUser;
        this.staffUser = staffUser;
        this.table = table;
        this.isPaid = isPaid;
        this.payingTime = payingTime;
        this.invoiceDetails = invoiceDetails;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "customer_user_id", nullable = false)
    public User getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(User customerUser) {
        this.customerUser = customerUser;
    }

    @ManyToOne
    @JoinColumn(name = "staff_user_id", nullable = false)
    public User getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(User staffUser) {
        this.staffUser = staffUser;
    }

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Column(name = "is_paid", nullable = false)
    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Column(name = "paying_time", nullable = false)
    public Date getPayingTime() {
        return payingTime;
    }

    public void setPayingTime(Date payingTime) {
        this.payingTime = payingTime;
    }

    @OneToMany(mappedBy = "invoice")
    public Set<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
