/*
 * To change this license header, choose License Headers in Loan Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views;

import com.handycredit.systems.constants.BusinessCategory;
import com.handycredit.systems.constants.InterestRateInterval;
import com.handycredit.systems.constants.LoanRequestReason;
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.core.services.LoanService;
import com.handycredit.systems.models.DynamicField;
import com.handycredit.systems.models.Loan;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

/**
 *
 * @author RayGdhrt
 */
@ManagedBean(name = "loanCreationForm", eager = true)
@SessionScoped
@ViewPath(path = HyperLinks.LOAN_CREATION_FORM)
public class LoanCreationForm extends WebFormView<Loan, LoanCreationForm, LoansView> {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoanCreationForm.class.getSimpleName());
    private LoanService loanService;
    private List<BusinessCategory> businessCategories;
    private List<LoanRequestReason> loanRequestReasons;
    private List<InterestRateInterval> rateIntervals;
    
    @Override
    public void beanInit() {
        this.loanService = ApplicationContextProvider.getApplicationContext().getBean(LoanService.class);
        this.businessCategories = Arrays.asList(BusinessCategory.values());
        this.loanRequestReasons = Arrays.asList(LoanRequestReason.values());
        this.rateIntervals = Arrays.asList(InterestRateInterval.values());
        
    }
    
    @Override
    public void pageLoadInit() {
        
    }
    
    @Override
    public void persist() throws ValidationFailedException, OperationFailedException {
        if (super.model.getLoanProvider() == null) {
            super.model.setLoanProvider(ApplicationContextProvider.getBean(LoanProviderService.class).getLoanProviderByUserAccount(SharedAppData.getLoggedInUser()));
        }
        
        this.loanService.saveInstance(super.model);
    }
    
    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new Loan();
    }
    
    @Override
    public void setFormProperties() {
        super.setFormProperties();
    }
    
    public void addDynamicField() {
        
        this.model.getDynamicFields().add(new DynamicField());
    }
    
    public void removeDynamicField(DynamicField dynamicField) {
        
        this.model.getDynamicFields().remove(dynamicField);
    }
    
    public List<BusinessCategory> getBusinessCategories() {
        return businessCategories;
    }
    
    public void setBusinessCategories(List<BusinessCategory> businessCategories) {
        this.businessCategories = businessCategories;
    }
    
    public List<LoanRequestReason> getLoanRequestReasons() {
        return loanRequestReasons;
    }
    
    public void setLoanRequestReasons(List<LoanRequestReason> loanRequestReasons) {
        this.loanRequestReasons = loanRequestReasons;
    }
    
    public List<InterestRateInterval> getRateIntervals() {
        return rateIntervals;
    }
    
    public void setRateIntervals(List<InterestRateInterval> rateIntervals) {
        this.rateIntervals = rateIntervals;
    }
    
}
