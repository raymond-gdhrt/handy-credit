/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import com.handycredit.systems.constants.Transactiontype;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author RayGdhrt
 */
@Entity
@Table(name = "business_transactions")
public class BusinessTransaction extends BaseEntity {

    private Business business;
    private String subject;
    private float amount;

    private Date date;
    private Transactiontype transactiontype ;

    @ManyToOne
    @JoinColumn(name = "business_id")
    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public Transactiontype getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(Transactiontype transactiontype) {
        this.transactiontype = transactiontype;
    }

    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date", length = 50)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
   

}
