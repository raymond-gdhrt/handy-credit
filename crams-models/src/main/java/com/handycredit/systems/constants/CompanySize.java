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
public enum CompanySize {
    zerototen("0 to 10 employees"),
    tento50("11 to 50 employees"),
    fiftyto100("51 to 100 employees"),
    hundredto500("101 to 500 employees"),
    fivehundredto500("500+ employees");

    private String uiName;

    CompanySize(String name) {
        this.uiName = name;
    }

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

}
