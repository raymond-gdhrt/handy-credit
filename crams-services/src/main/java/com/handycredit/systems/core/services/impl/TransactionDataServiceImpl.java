/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.TransactionDataService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessTransaction;
import com.handycredit.systems.models.LoanProvider;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RayGdhrt
 */
@Service
@Transactional
public class TransactionDataServiceImpl extends GenericServiceImpl<BusinessTransaction> implements TransactionDataService {

    @Override
    public boolean isDeletable(BusinessTransaction entity) throws OperationFailedException {
        return false;
    }

    @Override
    public BusinessTransaction saveInstance(BusinessTransaction instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }

}
