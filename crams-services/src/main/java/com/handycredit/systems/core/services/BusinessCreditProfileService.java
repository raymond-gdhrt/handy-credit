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
     *
     * @param loanApplication
     * @return
     * @throws ValidationFailedException
     * @throws OperationFailedException
     */
    public BusinessCreditProfile createProfile(LoanApplication loanApplication) throws ValidationFailedException, OperationFailedException;

    float calculateCollateralScore(float loanAmount, Set<Collateral> collaterals);

    float calculateCapitalScore(LoanApplication loanApplication);

    float calculateCapacityScore(float loanAmount, Business business);

    float calculateCharacterScore(float loanAmount, Business business);

    float calculateConditionsScore(Business business);

    BusinessCreditProfile calculateGeneralProfile(Business business);
}
