/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.constants;

/**
 * Represents different status of the workflow of a loan application
 * @author RayGdhrt
 */
public enum LoanApplicationStatus {
    /**
     * Represents a loan application which has just been filed by an SME
     */
    Submitted, 
    
     /**
     * Represents a loan application which has just accepted/approved  by the loan owner
     */
    Approved, 
    
    /**
     * Represents a loan application which has just rejected by the loan owner
     */
    Rejected, 
    
    /**
     * Represents a loan application which has been disbursed or given out to the applicant
     */
    Running, 
    
     /**
     * Represents a loan application which has been cleared/paid back to the lender by the borrower/applicant
     */
    Cleared, 
    
    /**
     * Represents a loan application which has been defaulted by borrower/applicant
     */
    Defaulted,
    
    /**
     * Represents a loan application which has been closed/withdrawn by borrower/applicant
     */
    Closed;
}
