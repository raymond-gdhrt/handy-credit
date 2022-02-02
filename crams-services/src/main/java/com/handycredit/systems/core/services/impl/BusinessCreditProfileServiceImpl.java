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
        if (loanApplication == null) {
            throw new ValidationFailedException("No details provided");
        }

        if (loanApplication.getBusiness() == null) {
            throw new ValidationFailedException("Missing business details");
        }

        if (loanApplication.getLoan() == null) {
            throw new ValidationFailedException("Missing loan details");
        }

        if (loanApplication.getNeededAmount() < loanApplication.getAmount()) {
            throw new ValidationFailedException("Amount higher than the needed amount");
        }

        if (loanApplication.getLoan().getMaximumAmount() < loanApplication.getAmount()) {
            throw new ValidationFailedException("Amount higher than maximum loan limit");
        }

        if (loanApplication.getLoan().getMinimumAmount() > loanApplication.getAmount()) {
            throw new ValidationFailedException("Amount lower than minimum loan limit");
        }

        System.out.println("Started creating profile...");
        BusinessCreditProfile businessCreditProfile = new BusinessCreditProfile();
        businessCreditProfile.setLoan(loanApplication.getLoan());
        businessCreditProfile.setBusiness(loanApplication.getBusiness());
        businessCreditProfile.setCapitalScore(calculateCapitalScore(loanApplication));
        businessCreditProfile.setCapacityScore(calculateCapacityScore(loanApplication.getAmount(), loanApplication.getBusiness()));
        businessCreditProfile.setCharacterScore(calculateCharacterScore(loanApplication.getAmount(), loanApplication.getBusiness()));
        businessCreditProfile.setCollateralScore(calculateCollateralScore(loanApplication.getAmount(), loanApplication.getAttachedCollaterals()));
        businessCreditProfile.setConditionsScore(calculateConditionsScore(loanApplication.getBusiness()));

        return super.save(businessCreditProfile);

    }

    @Override
    public float calculateCollateralScore(float loanAmount, Set<Collateral> collaterals) {
        System.out.println("Calculating collateral score...");
        float collateralValue = 0.0f;
        for (Collateral collateral : collaterals) {
            collateralValue = collateralValue + collateral.getEstimatedValue();
        }
        if (collateralValue == 0) {
            return 1;
        }

        float ratio = collateralValue / loanAmount;
        if (ratio > 0.9) {
            return 90;
        }
        return ratio * 100;
    }

    @Override
    public float calculateCapitalScore(LoanApplication loanApplication) {
        System.out.println("Calculating capital score...");
        if (loanApplication.getAmount() == 0) {
            return 0;
        }

        if (loanApplication.getNeededAmount() == 0) {
            return 0;
        }
        float needToCapitalRatio = (loanApplication.getNeededAmount() - loanApplication.getAmount()) / loanApplication.getNeededAmount();

        if (needToCapitalRatio > 0.9) {

            needToCapitalRatio = 0.9f;
        }
        return needToCapitalRatio * 100 + loanApplication.getLoanRequestReason().getScore();
    }

    @Override
    public float calculateCapacityScore(float loanAmount, Business business) {
        System.out.println("Calculating capacity score...");
        float expenses = 0, income = 0.0f;
        List<BusinessTransaction> transactions = ApplicationContextProvider.getBean(TransactionDataService.class).getInstances(
                new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                        .addFilterEqual("business", business), 0, 0);

        // loop through transactions
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
        float profitToLoanRatio = (income - expenses) / loanAmount;
        if (profitToLoanRatio >= 1) {
            return 90;
        }
        return profitToLoanRatio * 100;
    }

    @Override
    public List<BusinessCreditProfile> getInstances(Search search, int offeset, int limit) {

        if (search == null) {
            search = new Search();

        }
        search.setMaxResults(limit);
        search.setFirstResult(offeset);

        return super.search(search);

    }

    @Override
    public float calculateCharacterScore(float loanAmount, Business business) {
        System.out.println("Calculating character score...");
        float cleared = 0, numberCleared, numberDefaulted, defaiulted = 0, ongoing = 0.0f;

        //Fetch credit histories
        List<BusinessCreditHistory> transactions = ApplicationContextProvider.getBean(BusinessCreditHistoryService.class).getInstances(
                new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                        .addFilterEqual("business", business), 0, 0);

//Loop through credit histories
        for (BusinessCreditHistory transaction : transactions) {
            if (LoanApplicationStatus.Cleared.equals(transaction.getLoanApplicationStatus())) {
                cleared = cleared + transaction.getAmountBorrowed();
            } else {
                if (LoanApplicationStatus.Running.equals(transaction.getLoanApplicationStatus())) {
                    ongoing = ongoing + transaction.getAmountBorrowed();
                } else {
                    defaiulted = defaiulted + transaction.getAmountBorrowed();
                }
            }

        }
        if (cleared == 0 && defaiulted == 0) {
            return 1;
        }

        if (cleared < defaiulted) {
            return 1;
        }

        float loanRatio = (cleared - defaiulted) / loanAmount;
        if (loanRatio >= 1) {
            return 90;
        }

        return loanRatio * 100;
    }

    @Override
    public float calculateConditionsScore(Business business) {
        System.out.println("Calculating condition score...");
        float finalScore = 0;
        Date date1 = new Date();

        if (business.getFacebookLink() != null) {
            finalScore = (float) (finalScore + 2);
        }

        if (business.getWebsite() != null) {
            finalScore = (float) (finalScore + 5);
        }

        if (business.getLinkedInLink() != null) {
            finalScore = (float) (finalScore + 3);
        }

        if (business.getTwitterHandle() != null) {
            finalScore = (float) (finalScore + 2);
        }

        if (business.getRegistrationNumber() != null) {
            finalScore = (float) (finalScore + 10);
        }

        if (business.getDateOfOpening() != null) {
            long daysInBusiness = DateUtils.calculateDaysBetween(business.getDateOfOpening(), new Date());

            int daysScore = Long.toString(daysInBusiness).length();
            if (daysScore > 30) {
                daysScore = 60;
            } else {

                daysScore = daysScore * 3;
            }

            finalScore = finalScore + daysScore;

        }

        return finalScore;
    }

    @Override
    public BusinessCreditProfile calculateGeneralProfile(Business business) {
        System.out.println("Calculating general score...");
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
