/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.constants;

/**
 *
 * @author RayGdhrt
 */
public enum InterestRateInterval {
    weekly("week"), monthly("month"), yearly("year"), quaterly("quater");

    String name;

    InterestRateInterval(String uiName) {
        this.name = uiName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;
    }
    
    

}
