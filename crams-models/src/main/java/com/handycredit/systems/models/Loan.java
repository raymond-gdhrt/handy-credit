/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import com.handycredit.systems.constants.AccountStatus;
import com.handycredit.systems.constants.BusinessCategory;
import com.handycredit.systems.constants.InterestRateInterval;
import com.handycredit.systems.constants.LoanRequestReason;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author RayGdhrt
 */
@Entity
@Table(name = "loans")
public class Loan extends BaseEntity {

    private LoanProvider loanProvider;
    private float interestRate;
    private InterestRateInterval interestRateInterval;
    private float maximumAmount;
    private float minimumAmount;
    private float minimumCapitalScore;
    private float minimumCapacityScore;
    private float minimumCharacterScore;
    private float minimumConditionsScore;
    private float minimumCollateralScore;
    private Set<DynamicField> dynamicFields;
    private BusinessCategory targetBusinessCategory;
    private LoanRequestReason targetRequestReason;
    private AccountStatus targetAccountStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_provider")
    public LoanProvider getLoanProvider() {
        return loanProvider;
    }

    public void setLoanProvider(LoanProvider loanProvider) {
        this.loanProvider = loanProvider;
    }

    @Column(name = "interest_rate", length = 5)
    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "interest_rate_interval",length = 20)
    public InterestRateInterval getInterestRateInterval() {
        return interestRateInterval;
    }

    public void setInterestRateInterval(InterestRateInterval interestRateInterval) {
        this.interestRateInterval = interestRateInterval;
    }

    @Column(name = "maximum_amount", length = 20)
    public float getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(float maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    @Column(name = "minimum_amount", length = 20)
    public float getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(float minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    @Column(name = "minimum_capital_score", length = 20)
    public float getMinimumCapitalScore() {
        return minimumCapitalScore;
    }

    public void setMinimumCapitalScore(float minimumCapitalScore) {
        this.minimumCapitalScore = minimumCapitalScore;
    }

    @Column(name = "minimum_capacity_score", length = 20)
    public float getMinimumCapacityScore() {
        return minimumCapacityScore;
    }

    public void setMinimumCapacityScore(float minimumCapacityScore) {
        this.minimumCapacityScore = minimumCapacityScore;
    }

    @Column(name = "minimum_character_score", length = 20)
    public float getMinimumCharacterScore() {
        return minimumCharacterScore;
    }

    public void setMinimumCharacterScore(float minimumCharacterScore) {
        this.minimumCharacterScore = minimumCharacterScore;
    }

    @Column(name = "minimum_condition_score", length = 20)
    public float getMinimumConditionsScore() {
        return minimumConditionsScore;
    }

    public void setMinimumConditionsScore(float minimumConditionsScore) {
        this.minimumConditionsScore = minimumConditionsScore;
    }

    @Column(name = "minimum_collateral_score", length = 20)
    public float getMinimumCollateralScore() {
        return minimumCollateralScore;
    }

    public void setMinimumCollateralScore(float minimumCollateralScore) {
        this.minimumCollateralScore = minimumCollateralScore;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "loan_dynamic_fields", joinColumns = @JoinColumn(name = "loan_id"), inverseJoinColumns = @JoinColumn(name = "dynamic_field_id"))
    public Set<DynamicField> getDynamicFields() {
        return dynamicFields;
    }

    public void setDynamicFields(Set<DynamicField> dynamicFields) {
        this.dynamicFields = dynamicFields;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "target_business_category")
    public BusinessCategory getTargetBusinessCategory() {
        return targetBusinessCategory;
    }

    public void setTargetBusinessCategory(BusinessCategory targetBusinessCategory) {
        this.targetBusinessCategory = targetBusinessCategory;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "target_request_reason")
    public LoanRequestReason getTargetRequestReason() {
        return targetRequestReason;
    }

    public void setTargetRequestReason(LoanRequestReason targetRequestReason) {
        this.targetRequestReason = targetRequestReason;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "target_account_status")
    public AccountStatus getTargetAccountStatus() {
        return targetAccountStatus;
    }

    public void setTargetAccountStatus(AccountStatus targetAccountStatus) {
        this.targetAccountStatus = targetAccountStatus;
    }

}
