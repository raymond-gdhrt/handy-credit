/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views.utils;

import com.handycredit.systems.constants.InterestRateInterval;
import com.handycredit.systems.constants.LoanSource;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditHistory;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.util.Date;

/**
 *
 * @author RayGdhrt
 */
public class UploadedCreditHistory {

    @CsvBindByName(column = "Subject", required = true)
    private String subject;

    @CsvBindByName(column = "Source", required = true)
    private String source;

    @CsvBindByName(column = "Amount", required = true)
    private String amountBorrowed;

    @CsvDate(value = "dd/MM/yyyy")
    @CsvBindByName(column = "ExpectedClearanceDate", required = true)
    private Date expectedClearanceDate;

    @CsvDate(value = "dd/MM/yyyy")
    @CsvBindByName(column = "ActualClearanceDate", required = true)
    private Date actualClearDate;

    @CsvBindByName(column = "InterestRate", required = true)
    private String interestRate;

    @CsvBindByName(column = "RateType", required = true)
    private String rateType;

    public BusinessCreditHistory obtainCreditHistory(Business business) {
        try {
            BusinessCreditHistory transaction = new BusinessCreditHistory();

            transaction.setBusiness(business);
            transaction.setInterestRate(Float.valueOf(interestRate));
            transaction.setAmountBorrowed(Float.valueOf(amountBorrowed));
            transaction.setExpectedClearanceDate(expectedClearanceDate);
            transaction.setActualClearDate(actualClearDate);
            transaction.setName(subject);
            transaction.setSource(LoanSource.valueOf(source));
            transaction.setRateType(InterestRateInterval.valueOf(rateType.toUpperCase()));

            System.out.println("Added " + this.toString() + "...");

            return transaction;
        } catch (Exception e) {
            System.out.println("Skipped " + this.toString() + "..." + e.getLocalizedMessage());
            return null;
        }

    }

}
