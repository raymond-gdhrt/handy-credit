/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.constants.LoanApplicationStatus;
import com.handycredit.systems.constants.Transactiontype;
import com.handycredit.systems.core.services.BusinessCreditHistoryService;
import com.handycredit.systems.core.services.BusinessCreditProfileService;
import com.handycredit.systems.core.services.TransactionDataService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditHistory;
import com.handycredit.systems.models.BusinessCreditProfile;
import com.handycredit.systems.models.BusinessTransaction;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.LoanApplication;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.core.utils.DateUtils;
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
        businessCreditProfile.setCapitalScore(calculateCapitalScore(loanApplication));
        businessCreditProfile.setCapacityScore(calculateCapacityScore(loanApplication.getAmount(), loanApplication.getBusiness()));
        businessCreditProfile.setCharacterScore(calculateCharacterScore(loanApplication.getAmount(), loanApplication.getBusiness()));
        businessCreditProfile.setCollateralScore(calculateCollateralScore(loanApplication.getAmount(), loanApplication.getAttachedCollaterals()));

        return super.save(businessCreditProfile);

    }

    @Override
    public float calculateCollateralScore(float loanAmount, Set<Collateral> collaterals) {
        float collateralValue = 0.0f;
        for (Collateral collateral : collaterals) {
            collateralValue = collateralValue + collateral.getEstimatedValue();
        }
        if (collateralValue == 0) {
            return 0;
        }
        return collateralValue / loanAmount;
    }

    @Override
    public float calculateCapitalScore(LoanApplication loanApplication) {
        if (loanApplication.getAmount() == 0) {
            return 0;
        }

        if (loanApplication.getNeededAmount() == 0) {
            return 0;
        }

        return loanApplication.getNeededAmount() / loanApplication.getAmount() + loanApplication.getLoanRequestReason().getScore();
    }

    @Override
    public float calculateCapacityScore(float loanAmount, Business business) {
        float expenses = 0, income = 0.0f;
        List<BusinessTransaction> transactions = ApplicationContextProvider.getBean(TransactionDataService.class).getInstances(
                new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                        .addFilterEqual("business", business), 0, 0);
        for (BusinessTransaction transaction : transactions) {
            if (transaction.getTransactiontype().equals(Transactiontype.Expense)) {
                expenses = expenses + transaction.getAmount();
            } else {
                income = income + transaction.getAmount();
            }

        }

        if (income - expenses == 0) {
            return 1;
        }

        return (income - expenses) / loanAmount;
    }

    @Override
    public float calculateCharacterScore(float loanAmount, Business business) {
        float cleared = 0, numberCleared, numberDefaulted, defaiulted = 0, ongoing = 0.0f;
        List<BusinessCreditHistory> transactions = ApplicationContextProvider.getBean(BusinessCreditHistoryService.class).getInstances(
                new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                        .addFilterEqual("business", business), 0, 0);
        for (BusinessCreditHistory transaction : transactions) {
            if (transaction.getLoanApplicationStatus().equals(LoanApplicationStatus.Cleared)) {
                cleared = cleared + transaction.getAmountBorrowed();
            } else {
                if (transaction.getLoanApplicationStatus().equals(LoanApplicationStatus.Running)) {
                    ongoing = ongoing + transaction.getAmountBorrowed();
                } else {
                    defaiulted = defaiulted + transaction.getAmountBorrowed();
                }
            }

        }
        if (cleared == 0 && defaiulted == 0) {
            return 1;
        }

        return (cleared - defaiulted) / loanAmount;
    }

    @Override
    public float calculateConditionsScore(Business business) {
        float finalScore = 0;
        Date date1 = new Date();

        if (business.getFacebookLink() != null) {
            finalScore = (float) (finalScore + 0.2);
        }

        if (business.getWebsite() != null) {
            finalScore = (float) (finalScore + 0.5);
        }

        if (business.getLinkedInLink() != null) {
            finalScore = (float) (finalScore + 0.3);
        }

        if (business.getTwitterHandle() != null) {
            finalScore = (float) (finalScore + 0.2);
        }

        if (business.getRegistrationNumber() != null) {
            finalScore = (float) (finalScore + 5);
        }

        if (business.getDateOfOpening() != null) {
            long daysInBusiness = DateUtils.calculateDaysBetween(business.getDateOfOpening(), new Date());
            finalScore = finalScore + Long.toString(daysInBusiness).length();

        }

        return finalScore;
    }

    @Override
    public BusinessCreditProfile calculateGeneralProfile(Business business) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                .addFilterEqual("business", business);
        List<BusinessCreditProfile> creditProfiles = getInstances(search, 0, 0);
        float capacityScore = 1, characterScore = 1, capitalScore = 1, collateralScore = 1, conditionScore = 1;
        int counter = creditProfiles.size();
        for (BusinessCreditProfile businessCreditProfile : creditProfiles) {

            capacityScore = capacityScore + businessCreditProfile.getCapacityScore();
            characterScore = characterScore + businessCreditProfile.getCharacterScore();
            capitalScore = capitalScore + businessCreditProfile.getCapitalScore();
            collateralScore = collateralScore + businessCreditProfile.getCollateralScore();
            conditionScore = conditionScore + businessCreditProfile.getConditionsScore();

        }
        BusinessCreditProfile businessCreditProfile = new BusinessCreditProfile();

        businessCreditProfile.setCapacityScore(capacityScore / counter);
        businessCreditProfile.setCharacterScore(characterScore / counter);
        businessCreditProfile.setCollateralScore(collateralScore / counter);
        businessCreditProfile.setCapitalScore(capitalScore / counter);
        businessCreditProfile.setConditionsScore(conditionScore / counter);
        businessCreditProfile.setBusiness(business);

        return businessCreditProfile;

    }

}
