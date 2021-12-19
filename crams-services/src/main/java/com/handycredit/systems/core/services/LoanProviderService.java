package com.handycredit.systems.core.services;

import com.handycredit.systems.models.LoanProvider;
import org.sers.webutils.model.security.User;

/**
 * Responsible for CRUD operations on {@Link Loan}
 * @author RayGdhrt
 */
public interface LoanProviderService extends GenericService<LoanProvider>  {
    LoanProvider saveOutsideContext(LoanProvider loanProvider);
    
    LoanProvider getLoanProviderByUserAccount(User user);
    

}
