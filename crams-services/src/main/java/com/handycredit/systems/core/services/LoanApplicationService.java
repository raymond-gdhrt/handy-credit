package com.handycredit.systems.core.services;

import com.handycredit.systems.models.LoanApplication;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;

/**
 * Responsible for CRUD operations on {@Link LoanApplication}
 *
 * @author RayGdhrt
 */
public interface LoanApplicationService extends GenericService<LoanApplication> {

    /**
     * Marks a supplied loan application as approved.
     * @param loanApplication
     * @return
     * @throws ValidationFailedException 
     */
    public LoanApplication approveLoan(LoanApplication loanApplication) throws ValidationFailedException;

    /**
     * Marks a supplied loan application as rejected.
     * @param loanApplication
     * @param reason
     * @return
     * @throws ValidationFailedException 
     */
    public LoanApplication rejectLoan(LoanApplication loanApplication, String reason) throws ValidationFailedException;

    /**
     * Marks a supplied loan application as disbursed/given-out
     * @param loanApplication
     * @return
     * @throws ValidationFailedException 
     */
    public LoanApplication disburseLoan(LoanApplication loanApplication) throws ValidationFailedException;

    /**
     * Marks a supplied loan application as cleared/paid back.
     * @param loanApplication
     * @return
     * @throws ValidationFailedException 
     * @throws org.sers.webutils.model.exception.OperationFailedException 
     */
    public LoanApplication clearLoan(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException;

    /**
     * Marks a supplied loan application as defaulted.
     * @param loanApplication
     * @return
     * @throws ValidationFailedException 
     */
    public LoanApplication defaultLoan(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException;

    
    /**
     * Marks a supplied loan application as closed/withdrawn.
     * @param loanApplication
     * @return
     * @throws ValidationFailedException 
     */
    LoanApplication withdrawLoan(LoanApplication loanApplication) throws ValidationFailedException;
}
