package com.handycredit.systems.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditHistory;
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
    private String searchTerm;
    private Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
    private Business selectedBusiness;
    private List<BusinessCreditHistory> histories;

    @Override
    public void beanInit() {

        businessService = ApplicationContextProvider.getApplicationContext().getBean(BusinessService.class);
        if (super.model == null) {
            super.model = this.businessService.getBusinessByUserAccount(SharedAppData.getLoggedInUser());

        }
    }

    @Override
    public void pageLoadInit() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE).addFilterEqual("business", super.model
        );
        businessService = ApplicationContextProvider.getApplicationContext().getBean(BusinessService.class);

    }

    @Override
    public void persist() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteBusiness(Business business) {

        try {
            businessService.deleteInstance(business);
            UiUtils.showMessageBox("Action successfull", "Business deleted");
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

    public List<BusinessCreditHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<BusinessCreditHistory> histories) {
        this.histories = histories;
    }

}
