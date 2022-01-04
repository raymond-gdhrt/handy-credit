package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditProfile;
import com.handycredit.systems.models.Loan;
import com.handycredit.systems.models.LoanApplication;
import com.handycredit.systems.models.LoanProvider;
import com.handycredit.systems.models.security.PermissionConstants;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.handycredit.systems.security.HyperLinks;
import java.util.List;
import org.sers.webutils.client.controllers.WebAppExceptionHandler;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "dashboard")
@ViewScoped
@ViewPath(path = HyperLinks.DASHBOARD)
public class Dashboard extends WebAppExceptionHandler implements Serializable {

    private static final long serialVersionUID = 1L;
    private User loggedInUser;
    private LoanProvider loggedInLoanProvider;
    private Business loggedInBusiness;
    private List<LoanApplication> loanApplications;
    private List<Loan> loans;

    @PostConstruct
    public void init() {
        loggedInUser = SharedAppData.getLoggedInUser();

        if (loggedInUser.hasAdministrativePrivileges( )) {
            initAdminData();
        } else {

            if (loggedInUser.hasRole(PermissionConstants.PERM_BUSINESS_OWNER)) {
                initBusinessData();
            } else {
                if (loggedInUser.hasRole(PermissionConstants.PERM_LOAN_PROVIDER)) {
                    initLoanProvidersData();
                }
            }
        }

    }

    public void initBusinessData() {
        this.loggedInBusiness = ApplicationContextProvider.getBean(BusinessService.class).getBusinessByUserAccount(loggedInUser);
        Search search = new Search().addFilterEqual("business", this.loggedInBusiness);
    }

    public void initLoanProvidersData() {
        this.loggedInLoanProvider = ApplicationContextProvider.getBean(LoanProviderService.class).getLoanProviderByUserAccount(loggedInUser);
        Search search = new Search().addFilterEqual("loanProvider", this.loggedInBusiness);
    }

    public void initAdminData() {
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public LoanProvider getLoggedInLoanProvider() {
        return loggedInLoanProvider;
    }

    public void setLoggedInLoanProvider(LoanProvider loggedInLoanProvider) {
        this.loggedInLoanProvider = loggedInLoanProvider;
    }

    public Business getLoggedInBusiness() {
        return loggedInBusiness;
    }

    public void setLoggedInBusiness(Business loggedInBusiness) {
        this.loggedInBusiness = loggedInBusiness;
    }
    public BusinessCreditProfile calculateAverageProfile(){
    
    
    
    }

}
