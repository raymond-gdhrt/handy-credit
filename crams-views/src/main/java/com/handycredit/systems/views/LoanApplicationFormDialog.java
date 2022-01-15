/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.constants.CollateralStatus;
import com.handycredit.systems.constants.LoanRequestReason;
import com.handycredit.systems.core.services.BusinessCreditProfileService;
import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditProfile;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.Loan;
import com.handycredit.systems.models.LoanApplication;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

/**
 *
 * @author RayGdhrt
 */
@ManagedBean(name = "loanApplicationFormDialog", eager = true)
@SessionScoped
public class LoanApplicationFormDialog extends DialogForm<LoanApplication> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CollateralFormDialog.class.getSimpleName());
    private LoanApplicationService loanApplicationService;
    private List<LoanRequestReason> requestReasons;
    private List<CollateralStatus> statuses;
    private Business business;
    private Loan loan;
    private BusinessCreditProfile creditProfile;
    private boolean editDetails = true;

    @PostConstruct
    public void init() {
        System.out.println("Initialising laon application dialog...");
        this.loanApplicationService = ApplicationContextProvider.getBean(LoanApplicationService.class);
        this.statuses = Arrays.asList(CollateralStatus.values());
        requestReasons = Arrays.asList(LoanRequestReason.values());

    }

    public LoanApplicationFormDialog() {
        super(HyperLinks.LOAN_APPLICATION_FORM_DIALOG, 700, 600);
    }

    @Override
    public void persist() throws ValidationFailedException, OperationFailedException {
        if (super.model.getBusiness() == null) {
            super.model.setBusiness(business);
        }
        
         if (super.model.getCreditRiskProfile()== null) {
            super.model.setCreditRiskProfile(creditProfile);
        }
        this.loanApplicationService.saveInstance(super.model);

    }

    public List<Collateral> loadCollaterals(Business business) {

        return ApplicationContextProvider.getBean(CollateralService.class)
                .getInstances(new Search()
                        .addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                        .addFilterEqual("status", CollateralStatus.free)
                        .addFilterEqual("business", business), 0, 0);
    }

    public void calculateCreditProfile() {
        try {
            this.creditProfile = ApplicationContextProvider.getBean(BusinessCreditProfileService.class).createProfile(super.model);
             super.model.setCreditRiskProfile(creditProfile);
        } catch (ValidationFailedException | OperationFailedException ex) {
            UiUtils.ComposeFailure("Warning", ex.getLocalizedMessage());
        }

    }

    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new LoanApplication();
        this.creditProfile = null;
    }

    @Override
    public void setFormProperties() {
        super.setFormProperties();
    }

    public List<CollateralStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CollateralStatus> statuses) {
        this.statuses = statuses;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
        super.model.setBusiness(this.business);
    }

    public List<LoanRequestReason> getRequestReasons() {
        return requestReasons;
    }

    public void setRequestReasons(List<LoanRequestReason> requestReasons) {
        this.requestReasons = requestReasons;
    }

    public boolean isEditDetails() {
        return editDetails;
    }

    public void setEditDetails(boolean editDetails) {
        this.editDetails = editDetails;
    }

    public BusinessCreditProfile getCreditProfile() {
        return creditProfile;
    }

    public void setCreditProfile(BusinessCreditProfile creditProfile) {
        this.creditProfile = creditProfile;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
        super.model.setLoan(this.loan);
    }

}
