package com.handycredit.systems.core.services;

import com.handycredit.systems.models.Loan;
import com.handycredit.systems.models.LoanProvider;

/**
 * Responsible for CRUD operations on {@Link Loan}
 * @author RayGdhrt
 */
public interface LoanProviderService extends GenericService<LoanProvider>  {
    void saveOutsideContext(LoanProvider loanProvider);

}
