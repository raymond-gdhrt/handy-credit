/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.constants.CollateralStatus;
import com.handycredit.systems.constants.LoanApplicationStatus;
import com.handycredit.systems.core.services.BusinessCreditProfileService;
import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.LoanApplication;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
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
    public LoanApplication saveInstance(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException {
        if (loanApplication == null) {
            throw new ValidationFailedException("No details provided");
        }

        if (loanApplication.getBusiness() == null) {
            throw new ValidationFailedException("Missing business details");
        }

        if (loanApplication.getLoan() == null) {
            throw new ValidationFailedException("Missing loan details");
        }

        if (loanApplication.getLoan().getMaximumAmount() < loanApplication.getAmount()) {
            throw new ValidationFailedException("Amount higher than maximum loan limit");
        }

        if (loanApplication.getLoan().getMinimumAmount() > loanApplication.getAmount()) {
            throw new ValidationFailedException("Amount lower than minimum loan limit");
        }

        if (loanApplication.isNew()) {
            loanApplication.setStatus(LoanApplicationStatus.Submitted);
            loanApplication.setDateSubmitted(new Date());

        }

        if (loanApplication.getCreditRiskProfile() == null) {
            loanApplication.setCreditRiskProfile(ApplicationContextProvider.getBean(BusinessCreditProfileService.class).createProfile(loanApplication));

        }

        for (Collateral collateral : loanApplication.getAttachedCollaterals()) {

            collateral.setStatus(CollateralStatus.inUse);

        }

        return super.merge(loanApplication);

    }

    @Override
    public LoanApplication approveLoan(LoanApplication loanApplication) throws ValidationFailedException {
        if (loanApplication.isNew()) {
            throw new ValidationFailedException("Entity not yet persisted");
        }
        loanApplication.setStatus(LoanApplicationStatus.Approved);
        loanApplication.setDateApproved(new Date());
        return super.save(loanApplication);
    }

    @Override
    public LoanApplication rejectLoan(LoanApplication loanApplication, String reason) throws ValidationFailedException {
        if (loanApplication.isNew()) {
            throw new ValidationFailedException("Entity not yet persisted");
        }
        loanApplication.setRejectionNotes(reason);
        loanApplication.setStatus(LoanApplicationStatus.Rejected);
        loanApplication.setDateRejected(new Date());

        for (Collateral collateral : loanApplication.getAttachedCollaterals()) {

            collateral.setStatus(CollateralStatus.free);

        }
        return super.merge(loanApplication);
    }

    @Override
    public LoanApplication disburseLoan(LoanApplication loanApplication) throws ValidationFailedException {
        if (loanApplication.isNew()) {
            throw new ValidationFailedException("Entity not yet persisted");
        }
        loanApplication.setStatus(LoanApplicationStatus.Running);
        loanApplication.setDateGivenOut(new Date());
        return super.save(loanApplication);
    }

    @Override
    public LoanApplication clearLoan(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException {
        if (loanApplication.isNew()) {
            throw new ValidationFailedException("Entity not yet persisted");
        }
        loanApplication.setStatus(LoanApplicationStatus.Cleared);
        loanApplication.setDateCleared(new Date());

        for (Collateral collateral : loanApplication.getAttachedCollaterals()) {

            collateral.setStatus(CollateralStatus.free);

        }
        return super.merge(loanApplication);
    }

    @Override
    public LoanApplication defaultLoan(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException {
        if (loanApplication.isNew()) {
            throw new ValidationFailedException("Entity not yet persisted");
        }
        loanApplication.setStatus(LoanApplicationStatus.Defaulted);
        loanApplication.setDateDefaulted(new Date());

        return super.save(loanApplication);
    }

    @Override
    public LoanApplication withdrawLoan(LoanApplication loanApplication) throws ValidationFailedException {
        if (loanApplication.isNew()) {
            throw new ValidationFailedException("Entity not yet persisted");
        }

        if (loanApplication.getStatus().equals(LoanApplicationStatus.Running)) {
            throw new ValidationFailedException("A running loan cant be closed");
        }

        if (loanApplication.getStatus().equals(LoanApplicationStatus.Cleared)) {
            throw new ValidationFailedException("A cleared loan cant be closed");
        }

        loanApplication.setStatus(LoanApplicationStatus.Closed);
        loanApplication.setDateDefaulted(new Date());
        return super.save(loanApplication);
    }

}
