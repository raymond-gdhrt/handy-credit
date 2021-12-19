/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.models.LoanApplication;
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
public class LoanApplicationServiceImpl extends GenericServiceImpl<LoanApplication> implements LoanApplicationService {

   

    @Override
    public boolean isDeletable(LoanApplication entity) throws OperationFailedException {
        return false;
    }

    @Override
    public LoanApplication saveInstance(LoanApplication instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }
}
