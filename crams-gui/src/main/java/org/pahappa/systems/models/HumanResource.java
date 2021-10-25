/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pahappa.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.Gender;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "human_resource")
public class HumanResource extends BaseEntity {

    private String profession;
    private Gender gender;
    private String number="1";

    @Column(name = "profession", nullable = true)
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", nullable = true)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    
    @Column(name = "number", nullable = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    
    
    
    

    @Override
    public boolean equals(Object object) {
        return object instanceof Sector && (super.getId() != null) ? super.getId().equals(((HumanResource) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }
}
