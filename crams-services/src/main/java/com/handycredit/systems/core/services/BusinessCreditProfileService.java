package com.handycredit.systems.core.services;

import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditProfile;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.LoanApplication;
import java.util.Set;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;

/**
 * Responsible for CRUD operations on {@Link BusinessCreditProfile}
 *
 * @author RayGdhrt
 */
public interface BusinessCreditProfileService extends GenericService<BusinessCreditProfile> {

    /**
     * Create a credit profile for the business as the information in the loan application
     * @param loanApplication
     * @return
     * @throws ValidationFailedException for invalid or null required parameters
     * @throws OperationFailedException for invalid or null required parameters
     */
    public BusinessCreditProfile createProfile(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException;

    /**
     * Returns the collateral score 
     * @param loanAmount
     * @param collaterals
     * @return 
     */
    float calculateCollateralScore(float loanAmount, Set<Collateral> collaterals);

    /**
     * Returns the capital score 
     * @param loanApplication
     * @return 
     */
    float calculateCapitalScore(LoanApplication loanApplication);

    /**
     * Returns the capacity score 
     * @param loanAmount
     * @param business
     * @return 
     */
    float calculateCapacityScore(float loanAmount, Business business);

    /**
     * Returns the character score 
     * @param loanAmount
     * @param business
     * @return 
     */
    float calculateCharacterScore(float loanAmount, Business business);

    /**
     * Returns the conditions score 
     * @param business
     * @return 
     */
    float calculateConditionsScore(Business business);

    /**
     * Returns the average credit profile score 
     * @param business
     * @return 
     */
    BusinessCreditProfile calculateGeneralProfile(Business business);
}
