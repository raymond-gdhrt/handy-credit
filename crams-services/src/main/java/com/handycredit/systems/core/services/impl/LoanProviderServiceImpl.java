/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.models.LoanProvider;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RayGdhrt
 */
@Service
@Transactional
public class LoanProviderServiceImpl extends GenericServiceImpl<LoanProvider> implements LoanProviderService {

   

    @Override
    public boolean isDeletable(LoanProvider entity) throws OperationFailedException {
        return false;
    }

    @Override
    public LoanProvider saveInstance(LoanProvider instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }

    @Override
    public void saveOutsideContext(LoanProvider loanProvider) {
       super.saveBG(loanProvider);
    }
}
