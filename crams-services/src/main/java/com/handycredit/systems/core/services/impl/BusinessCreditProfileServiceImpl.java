/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.BusinessCreditProfileService;
import com.handycredit.systems.models.BusinessCreditProfile;
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
public class BusinessCreditProfileServiceImpl extends GenericServiceImpl<BusinessCreditProfile> implements BusinessCreditProfileService {

   

    @Override
    public boolean isDeletable(BusinessCreditProfile entity) throws OperationFailedException {
        return false;
    }

    @Override
    public BusinessCreditProfile saveInstance(BusinessCreditProfile instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }
}
