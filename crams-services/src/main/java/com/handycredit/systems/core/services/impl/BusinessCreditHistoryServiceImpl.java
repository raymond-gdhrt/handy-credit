/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.BusinessCreditHistoryService;
import com.handycredit.systems.models.BusinessCreditHistory;
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
public class BusinessCreditHistoryServiceImpl extends GenericServiceImpl<BusinessCreditHistory> implements BusinessCreditHistoryService {

   

    @Override
    public boolean isDeletable(BusinessCreditHistory entity) throws OperationFailedException {
        return false;
    }

    @Override
    public BusinessCreditHistory saveInstance(BusinessCreditHistory instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }
}
