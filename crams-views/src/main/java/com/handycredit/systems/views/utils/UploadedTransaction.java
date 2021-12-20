/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views.utils;

import com.handycredit.systems.constants.Transactiontype;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessTransaction;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.util.Date;

/**
 *
 * @author RayGdhrt
 */
public class UploadedTransaction {
    
    @CsvBindByName(column = "Subject", required = true)
    private String subject;


    @CsvBindByName(column = "Amount", required = true)
    private String amount;

    @CsvBindByName(column = "Rate type", required = true)
    private String type;

    @CsvDate(value = "dd/MM/yyyy")
    @CsvBindByName(column = "Date", required = true)
    private Date date;

  

    public BusinessTransaction obtainTransaction(Business business) {
        BusinessTransaction transaction = new BusinessTransaction();

        transaction.setDateCreated(date);
        transaction.setAmount(Float.valueOf(amount));
        transaction.setBusiness(business);
        transaction.setSubject(subject);
        transaction.setTransactiontype(Transactiontype.valueOf(type.toUpperCase()));

        if (transaction.getTransactiontype() != null) {
            System.out.println("Created " + this.toString() + "...");
            return transaction;
        }

        System.out.println("Skipped " + this.toString() + "...");

        return null;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

   

}
