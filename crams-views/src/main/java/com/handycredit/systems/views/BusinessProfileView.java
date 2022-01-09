package com.handycredit.systems.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.LoanApplication;
import com.handycredit.systems.security.HyperLinks;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "businessProfileView")
@SessionScoped
@ViewPath(path = HyperLinks.BUSINESS_PROFILE_VIEW)
public class BusinessProfileView extends WebFormView<Business, BusinessProfileView, BusinessesView> {

    private static final long serialVersionUID = 1L;
    private BusinessService businessService;
    private CollateralService collateralService;
    private String searchTerm;
    private Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
    private Business selectedBusiness;
    private Collateral selectedCollateral;
    private List<LoanApplication> loanApplications;
    private LoanApplication selectedLoanApplication;
    private List<Collateral> collaterals;

    @Override
    public void beanInit() {

        businessService = ApplicationContextProvider.getApplicationContext().getBean(BusinessService.class);
        collateralService = ApplicationContextProvider.getApplicationContext().getBean(CollateralService.class);
        if (super.model == null) {
            super.model = this.businessService.getBusinessByUserAccount(SharedAppData.getLoggedInUser());

        }
    }

    @Override
    public void pageLoadInit() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE).addFilterEqual("business", super.model
        );
        businessService = ApplicationContextProvider.getApplicationContext().getBean(BusinessService.class);
        this.loanApplications = ApplicationContextProvider.getBean(LoanApplicationService.class).getInstances(new Search()
                .addFilterEqual("business", super.model)
                .addFilterEqual("recordStatus", RecordStatus.ACTIVE), 0, 0);
        this.collaterals = this.collateralService.getInstances(new Search()
                .addFilterEqual("business", super.model)
                .addFilterEqual("recordStatus", RecordStatus.ACTIVE), 0, 0);

    }

    @Override
    public void persist() throws Exception {
        doNothing();

    }

    public void loadCollateral(Collateral collateral) {
        if (collateral == null) {
            collateral = new Collateral();
        }

        this.selectedCollateral = collateral;

    }

    public void loadLoanApplication(LoanApplication loanApplication
    ) {

        this.selectedLoanApplication = loanApplication;

    }

  

    public void deleteCollateral(Collateral collateral) {

        try {
            collateralService.deleteInstance(collateral);
            UiUtils.showMessageBox("Action successfull", "Collateral");
        } catch (OperationFailedException ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
            Logger.getLogger(BusinessProfileView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the searchTerm
     */
    public String getSearchTerm() {
        return searchTerm;
    }

    /**
     * @param searchTerm the searchTerm to set
     */
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Business getSelectedBusiness() {
        return selectedBusiness;
    }

    public void setSelectedBusiness(Business selectedBusiness) {
        this.selectedBusiness = selectedBusiness;
    }

    public List<Collateral> getCollaterals() {
        return collaterals;
    }

    public void setCollaterals(List<Collateral> collaterals) {
        this.collaterals = collaterals;
    }

    public List<LoanApplication> getLoanApplications() {
        return loanApplications;
    }

    public Collateral getSelectedCollateral() {
        return selectedCollateral;
    }

    public void setSelectedCollateral(Collateral selectedCollateral) {
        this.selectedCollateral = selectedCollateral;
    }

    public LoanApplication getSelectedLoanApplication() {
        return selectedLoanApplication;
    }

    public void setSelectedLoanApplication(LoanApplication selectedLoanApplication) {
        this.selectedLoanApplication = selectedLoanApplication;
    }

}
