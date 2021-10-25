/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pahappa.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "company_sizes")
@Inheritance(strategy = InheritanceType.JOINED)
public class CompanySize extends BaseEntity{
    private Manufacturer manufacturer;
    private int year=2021;
    private int turnOver;
    private long numberOfEmployees;
    

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Column(name = "year",  length = 10)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "turn_over",  length = 100)
    public int getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(int turnOver) {
        this.turnOver = turnOver;
    }

     @Column(name = "number_of_employees",  length = 100)
    public long getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(long numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }
    
    
    @Override
    public boolean equals(Object object) {
        return object instanceof Sector && (super.getId() != null) ? super.getId().equals(((CompanySize) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }
    
}
