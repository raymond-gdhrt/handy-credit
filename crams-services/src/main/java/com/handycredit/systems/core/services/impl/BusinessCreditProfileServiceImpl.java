/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.BusinessCreditProfileService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditProfile;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.Loan;
import com.handycredit.systems.models.LoanApplication;
import java.util.Set;
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

    @Override
    public BusinessCreditProfile createProfile(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException {
        BusinessCreditProfile businessCreditProfile = new BusinessCreditProfile();
        businessCreditProfile.setLoan(loanApplication.getLoan());
        businessCreditProfile.setBusiness(loanApplication.getBusiness());
        businessCreditProfile.setCollateralScore(calculateCollateralScore(loanApplication.getAmount(),loanApplication.getAttachedCollaterals()));
        return super.save(businessCreditProfile);

    }

    public float calculateCollateralScore(float loanAmount, Set<Collateral> collaterals) {
        float collateralValue = 0.0f;
        for (Collateral collateral : collaterals) {
            collateralValue = collateralValue + collateral.getEstimatedValue();
        }

        return loanAmount / collateralValue;
    }
}
