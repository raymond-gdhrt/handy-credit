/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import com.handycredit.systems.constants.InterestRateInterval;
import com.handycredit.systems.constants.LoanApplicationStatus;
import com.handycredit.systems.constants.LoanSource;
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
@Table(name = "business_credit_histories")
public class BusinessCreditHistory extends BaseEntity {

    private Business business;
    private String name;
    private LoanSource source;
    private float amountBorrowed;
    private Date expectedClearanceDate;
    private Date actualClearDate;
    private float interestRate;
    private float actualAmountPaid;
    private float expectedAmountToPay;
    private LoanApplicationStatus loanApplicationStatus;
    private InterestRateInterval rateType;

    @ManyToOne(optional = true)
    @JoinColumn(name = "business_id")
    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "source", length = 20)
    public LoanSource getSource() {
        return source;
    }

    public void setSource(LoanSource source) {
        this.source = source;
    }

    @Column(name = "amount_borrowed", length = 50)
    public float getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(float amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "expected_clearance_date", length = 50)
    public Date getExpectedClearanceDate() {
        return expectedClearanceDate;
    }

    public void setExpectedClearanceDate(Date expectedClearanceDate) {
        this.expectedClearanceDate = expectedClearanceDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "actual_clearance_date", length = 50)
    public Date getActualClearDate() {
        return actualClearDate;
    }

    public void setActualClearDate(Date actualClearDate) {
        this.actualClearDate = actualClearDate;
    }

    @Column(name = "interest_rate", length = 5)
    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @Column(name = "amount_paid", length = 50)
    public float getActualAmountPaid() {
        return actualAmountPaid;
    }

    public void setActualAmountPaid(float actualAmountPaid) {
        this.actualAmountPaid = actualAmountPaid;
    }

    @Column(name = "amount_payable", length = 5)
    public float getExpectedAmountToPay() {
        return expectedAmountToPay;
    }

    public void setExpectedAmountToPay(float expectedAmountToPay) {
        this.expectedAmountToPay = expectedAmountToPay;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "interest_rate_interval", length = 100)
    public InterestRateInterval getRateType() {
        return rateType;
    }

    public void setRateType(InterestRateInterval rateType) {
        this.rateType = rateType;
    }

    @Column(name = "loan_name", length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", length = 200)
    public LoanApplicationStatus getLoanApplicationStatus() {
        return loanApplicationStatus;
    }

    public void setLoanApplicationStatus(LoanApplicationStatus loanApplicationStatus) {
        this.loanApplicationStatus = loanApplicationStatus;
    }

}
