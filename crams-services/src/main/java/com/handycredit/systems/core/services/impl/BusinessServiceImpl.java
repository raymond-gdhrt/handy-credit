/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.models.Business;
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
public class BusinessServiceImpl extends GenericServiceImpl<Business> implements BusinessService {

    @Override
    public boolean isDeletable(Business entity) throws OperationFailedException {
        return false;
    }

    @Override
    public Business saveInstance(Business instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }

    @Override
    public Business saveOutsideContext(Business business) {
        return super.mergeBG(business);

    }

    @Override
    public Business getBusinessByUserAccount(User user) {
        return super.searchUniqueByPropertyEqual("userAccount", user, RecordStatus.ACTIVE);

    }

    public int calculateCapacityScore() {

        return 0;
    }

    public int calculateCollateralScore() {

        return 0;
    }

    public int calculateCapitalScore() {

        return 0;
    }

    public int calculateCharacterScore() {

        return 0;
    }

    public int calculateConditionScore() {

        return 0;
    }
}
