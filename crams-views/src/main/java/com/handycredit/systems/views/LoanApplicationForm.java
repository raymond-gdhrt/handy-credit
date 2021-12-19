package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.constants.LoanRequestReason;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.Loan;
import com.handycredit.systems.models.LoanApplication;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "loanApplicationForm")
@ViewScoped
@ViewPath(path = HyperLinks.LOAN_APPLICATION_FORM)
public class LoanApplicationForm extends WebFormView<LoanApplication, LoanApplicationForm, LoansView> {

    private static final long serialVersionUID = 1L;
    private LoanApplicationService loanApplicationService;
    private Loan loan;
    private Business business;
    private List<LoanRequestReason> reasons;
    private List<Collateral> collaterals;

    @Override
    public void beanInit() {
        this.loanApplicationService = ApplicationContextProvider.getBean(LoanApplicationService.class);
    }

    @Override
    public void pageLoadInit() {
        this.reasons = Arrays.asList(LoanRequestReason.values());
        this.business = ApplicationContextProvider.getBean(BusinessService.class).getBusinessByUserAccount(SharedAppData.getLoggedInUser());
        this.collaterals = ApplicationContextProvider.getBean(CollateralService.class).getInstances(new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE).addFilterEqual("business", this.business), 0, 0);

    }

    @Override
    public void persist() throws Exception {
        System.out.println("Saving application...");
        super.model.setDateSubmitted(new Date());

        super.model.setLoan(loan);
        super.model.setBusiness(business);
        this.loanApplicationService.saveInstance(super.model);
        UiUtils.showMessageBox("Loan application submitted", "Action Successful");
    }

    public void addCollateral() {
        this.model.getAttachedCollaterals().add(new Collateral());

    }

    public void removeCollateral(Collateral collateral) {
        this.model.getAttachedCollaterals().remove(collateral);

    }

    @Override
    public String getViewUrl() {
        return HyperLinks.LOAN_APPLICATION_FORM;
    }

    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new LoanApplication();
    }

    @Override
    public void setFormProperties() {
        super.setFormProperties();
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public List<LoanRequestReason> getReasons() {
        return reasons;
    }

    public void setReasons(List<LoanRequestReason> reasons) {
        this.reasons = reasons;
    }

    public List<Collateral> getCollaterals() {
        return collaterals;
    }

    public void setCollaterals(List<Collateral> collaterals) {
        this.collaterals = collaterals;
    }

}
