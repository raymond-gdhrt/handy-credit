/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.LoanService;
import com.handycredit.systems.models.Loan;
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
public class LoanServiceImpl extends GenericServiceImpl<Loan> implements LoanService {

    @Override
    public boolean isDeletable(Loan entity) throws OperationFailedException {
        return false;
    }

    @Override
    public Loan saveInstance(Loan loan) throws ValidationFailedException, OperationFailedException {
        if (loan.getMaximumAmount() < loan.getMinimumAmount()) {
            throw new ValidationFailedException("Invalid amount range");
        }

        if (loan.getTitle() == null) {

            throw new ValidationFailedException("Missing tittle");
        }

        if (loan.getInterestRateInterval() == null) {
            throw new ValidationFailedException("Missing interval");

        }
        return super.save(loan);

    }
}
