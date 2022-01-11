/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import com.handycredit.systems.constants.LoanApplicationStatus;
import com.handycredit.systems.constants.LoanRequestReason;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author RayGdhrt
 */
@Entity
@Table(name = "loan_applications")
public class LoanApplication extends BaseEntity {

    private Loan loan;
    private float amount;
    private int proposedPaymentPeriodInDays;
    private LoanRequestReason loanRequestReason;
    private String reasonDescription;
    private Business business;
    private float neededAmount;
    private LoanApplicationStatus status = LoanApplicationStatus.Submitted;
    private Date dateSubmitted;
    private Date dateApproved;
    private Date dateRejected;
    private Date dateCleared;
    private Date dateGivenOut;
    private Date dateDefaulted;
    private String rejectionNotes;
    private String defaultedNotes;
    private BusinessCreditProfile creditRiskProfile;
    private Set<FilledDynamicField> filledFields;
    private Set<Collateral> attachedCollaterals;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Column(name = "amount", length = 50)
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Column(name = "proposed_payment_period_in_days")
    public int getProposedPaymentPeriodInDays() {
        return proposedPaymentPeriodInDays;
    }

    public void setProposedPaymentPeriodInDays(int proposedPaymentPeriodInDays) {
        this.proposedPaymentPeriodInDays = proposedPaymentPeriodInDays;
    }

    @ManyToOne
    @JoinColumn(name = "risk_profile_id")
    public BusinessCreditProfile getCreditRiskProfile() {
        return creditRiskProfile;
    }

    public void setCreditRiskProfile(BusinessCreditProfile creditRiskProfile) {
        this.creditRiskProfile = creditRiskProfile;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", length = 100)
    public LoanRequestReason getLoanRequestReason() {
        return loanRequestReason;
    }

    public void setLoanRequestReason(LoanRequestReason loanRequestReason) {
        this.loanRequestReason = loanRequestReason;
    }

    @Column(name = "reason_description", columnDefinition = "TEXT")
    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_submitted")
    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_approved")
    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_rejected")
    public Date getDateRejected() {
        return dateRejected;
    }

    public void setDateRejected(Date dateRejected) {
        this.dateRejected = dateRejected;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_cleared")
    public Date getDateCleared() {
        return dateCleared;
    }

    public void setDateCleared(Date dateCleared) {
        this.dateCleared = dateCleared;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_given_out")
    public Date getDateGivenOut() {
        return dateGivenOut;
    }

    public void setDateGivenOut(Date dateGivenOut) {
        this.dateGivenOut = dateGivenOut;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_defualted")
    public Date getDateDefaulted() {
        return dateDefaulted;
    }

    public void setDateDefaulted(Date dateDefaulted) {
        this.dateDefaulted = dateDefaulted;
    }

    @Column(name = "rejection_notes")
    public String getRejectionNotes() {
        return rejectionNotes;
    }

    public void setRejectionNotes(String rejectionNotes) {
        this.rejectionNotes = rejectionNotes;
    }

    @Column(name = "defaulted_loan_notes")
    public String getDefaultedNotes() {
        return defaultedNotes;
    }

    public void setDefaultedNotes(String defaultedNotes) {
        this.defaultedNotes = defaultedNotes;
    }

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "loan_application_filled_dynamic_fields", joinColumns = @JoinColumn(name = "loan_application_id"), inverseJoinColumns = @JoinColumn(name = "filled_dynamic_field_id"))
    public Set<FilledDynamicField> getFilledFields() {
        return filledFields;
    }

    public void setFilledFields(Set<FilledDynamicField> filledFields) {
        this.filledFields = filledFields;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "loan_application_collaterals", joinColumns = @JoinColumn(name = "loan_application_id"), inverseJoinColumns = @JoinColumn(name = "collateral_id"))
    public Set<Collateral> getAttachedCollaterals() {
        return attachedCollaterals;
    }

    public void setAttachedCollaterals(Set<Collateral> attachedCollaterals) {
        this.attachedCollaterals = attachedCollaterals;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100)
    public LoanApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(LoanApplicationStatus status) {
        this.status = status;
    }

    @Column(name = "needed_amount", length = 100)
    public float getNeededAmount() {
        return neededAmount;
    }

    public void setNeededAmount(float neededAmount) {
        this.neededAmount = neededAmount;
    }
    
      @Override
    public boolean equals(Object object) {
        return object instanceof LoanApplication && (super.getId() != null) ? super.getId().equals(((LoanApplication) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }

}
