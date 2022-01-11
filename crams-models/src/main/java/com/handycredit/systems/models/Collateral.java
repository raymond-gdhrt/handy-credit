/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import com.handycredit.systems.constants.CollateralStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author RayGdhrt
 */
@Entity
@Table(name = "collaterals")
public class Collateral extends BaseEntity {

    private String name;
    private Business business;
    private String otherDetails;
    private CollateralStatus status = CollateralStatus.free;
    private float estimatedValue;
    private byte[] attachement;
    private String attachementLink;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "estimated_value",length = 20)
    public float getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(float estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    @Lob
    @Column(name = "attachement", columnDefinition = "BLOB")
    public byte[] getAttachement() {
        return attachement;
    }

    public void setAttachement(byte[] attachement) {
        this.attachement = attachement;
    }

     @Column(name = "attachement_link",length = 200)
    public String getAttachementLink() {
        return attachementLink;
    }

    public void setAttachementLink(String attachementLink) {
        this.attachementLink = attachementLink;
    }

    @ManyToOne
    @JoinColumn(name = "business_id")
    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    public CollateralStatus getStatus() {
        return status;
    }

    public void setStatus(CollateralStatus status) {
        this.status = status;
    }

     @Column(name = "other_details", columnDefinition = "TEXT")
    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }
    
      @Override
    public boolean equals(Object object) {
        return object instanceof Collateral && (super.getId() != null) ? super.getId().equals(((Collateral) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return  name + " (" + estimatedValue +")";
    }
    
    
    
}
