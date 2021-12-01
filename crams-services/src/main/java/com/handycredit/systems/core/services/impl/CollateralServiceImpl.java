/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.models.Collateral;
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
public class CollateralServiceImpl extends GenericServiceImpl<Collateral> implements CollateralService {

   

    @Override
    public boolean isDeletable(Collateral entity) throws OperationFailedException {
        return false;
    }

    @Override
    public Collateral saveInstance(Collateral instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }
}
