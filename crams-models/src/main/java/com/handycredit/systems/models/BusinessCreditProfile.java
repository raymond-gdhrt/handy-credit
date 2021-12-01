/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author RayGdhrt
 */
@Entity
@Table(name = "business_credit_profiles")
public class BusinessCreditProfile extends BaseEntity {

    private Business business;
    private Loan loan;
    private float capitalScore;
    private float capacityScore;
    private float characterScore;
    private float conditionsScore;
    private float collateralScore;

    @Column(name = "capital_score",length = 5)
    public float getCapitalScore() {
        return capitalScore;
    }

    public void setCapitalScore(float capitalScore) {
        this.capitalScore = capitalScore;
    }

    @Column(name = "capacity_score",length = 5)
    public float getCapacityScore() {
        return capacityScore;
    }

    public void setCapacityScore(float capacityScore) {
        this.capacityScore = capacityScore;
    }

    @Column(name = "character_score",length = 5)
    public float getCharacterScore() {
        return characterScore;
    }

    public void setCharacterScore(float characterScore) {
        this.characterScore = characterScore;
    }

    @Column(name = "conditions_score",length = 5)
    public float getConditionsScore() {
        return conditionsScore;
    }

    public void setConditionsScore(float conditionsScore) {
        this.conditionsScore = conditionsScore;
    }

    @Column(name = "collateral_score",length = 5)
    public float getCollateralScore() {
        return collateralScore;
    }

    public void setCollateralScore(float collateralScore) {
        this.collateralScore = collateralScore;
    }

    @ManyToOne
    @JoinColumn(name = "business_id")
    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @ManyToOne
    @JoinColumn(name = "loan_id")
    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

}
