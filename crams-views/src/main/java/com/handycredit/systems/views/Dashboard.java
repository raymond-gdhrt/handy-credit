package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.core.services.BusinessCreditProfileService;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.core.services.LoanService;
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
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "dashboard")
@ViewScoped
@ViewPath(path = HyperLinks.DASHBOARD)
public class Dashboard extends WebAppExceptionHandler implements Serializable {

    private static final long serialVersionUID = 1L;
    private int totalLoans, totalLoanApplications, totalBusinesses, totalLoanProviders;
    private User loggedInUser;
    private LoanProvider loggedInLoanProvider;
    private Business loggedInBusiness;
    private List<LoanApplication> loanApplications;
    private List<Loan> loans;
    private BusinessCreditProfile businessCreditProfile;
    private ChartData chartData;

    @PostConstruct
    public void init() {
        loggedInUser = SharedAppData.getLoggedInUser();

        if (loggedInUser.hasAdministrativePrivileges()) {
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

    public void loadChartData() {
        List<BusinessCreditProfile> creditProfiles = ApplicationContextProvider.getBean(BusinessCreditProfileService.class)
                .getInstances(new Search().addFilterEqual("business", this.loggedInBusiness), 0, 10);

        this.chartData = new ChartData();
        String[] labels = new String[creditProfiles.size()];
        float[] dataCapacity = new float[creditProfiles.size()];
        float[] dataCharacter = new float[creditProfiles.size()];
        float[] dataCapital = new float[creditProfiles.size()];
        float[] dataConditions = new float[creditProfiles.size()];
        float[] dataCollateral = new float[creditProfiles.size()];

        int counter = 0;
        for (BusinessCreditProfile creditProfile : creditProfiles) {
            labels[counter] = creditProfile.getDateCreated().toString();
            dataCapacity[counter] = creditProfile.getCapacityScore();
            dataCapital[counter] = creditProfile.getCapitalScore();
            dataCharacter[counter] = creditProfile.getCharacterScore();
            dataConditions[counter] = creditProfile.getConditionsScore();
            dataCollateral[counter] = creditProfile.getCollateralScore();
            counter=counter+1;

        }

        this.chartData.setLabels(labels);
        this.chartData.setFloatData1(dataCapacity);
        this.chartData.setFloatData2(dataCharacter);
        this.chartData.setFloatData3(dataCapital);
        this.chartData.setFloatData4(dataConditions);
        this.chartData.setFloatData5(dataCollateral);

    }

    public void initBusinessData() {
        System.out.println("Initialising business....");
        loadChartData();
        this.loggedInBusiness = ApplicationContextProvider.getBean(BusinessService.class).getBusinessByUserAccount(loggedInUser);
        this.businessCreditProfile = ApplicationContextProvider.getBean(BusinessCreditProfileService.class).calculateGeneralProfile(loggedInBusiness);

    }

    public void initLoanProvidersData() {
        System.out.println("Initialising loan provider....");
        this.loggedInLoanProvider = ApplicationContextProvider.getBean(LoanProviderService.class).getLoanProviderByUserAccount(loggedInUser);
        totalLoanApplications = ApplicationContextProvider.getBean(LoanApplicationService.class).countInstances(new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE).
                addFilterEqual("loan.loanProvider", loggedInLoanProvider));
        totalLoans = ApplicationContextProvider.getBean(LoanService.class).countInstances(new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                .addFilterEqual("loanProvider", loggedInLoanProvider));

    }

    public void initAdminData() {
        System.out.println("Initialising admin....");
        totalBusinesses = ApplicationContextProvider.getBean(BusinessService.class).countInstances(new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE));
        totalLoanApplications = ApplicationContextProvider.getBean(LoanApplicationService.class).countInstances(new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE));
        totalLoanProviders = ApplicationContextProvider.getBean(LoanProviderService.class).countInstances(new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE));
        totalLoans = ApplicationContextProvider.getBean(LoanService.class).countInstances(new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE));

    }

    public List<LoanApplication> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(List<LoanApplication> loanApplications) {
        this.loanApplications = loanApplications;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public ChartData getChartData() {
        return chartData;
    }

    public void setChartData(ChartData chartData) {
        this.chartData = chartData;
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

    public BusinessCreditProfile getBusinessCreditProfile() {
        return businessCreditProfile;
    }

    public void setBusinessCreditProfile(BusinessCreditProfile businessCreditProfile) {
        this.businessCreditProfile = businessCreditProfile;
    }

    public int getTotalLoans() {
        return totalLoans;
    }

    public void setTotalLoans(int totalLoans) {
        this.totalLoans = totalLoans;
    }

    public int getTotalLoanApplications() {
        return totalLoanApplications;
    }

    public void setTotalLoanApplications(int totalLoanApplications) {
        this.totalLoanApplications = totalLoanApplications;
    }

    public int getTotalBusinesses() {
        return totalBusinesses;
    }

    public void setTotalBusinesses(int totalBusinesses) {
        this.totalBusinesses = totalBusinesses;
    }

    public int getTotalLoanProviders() {
        return totalLoanProviders;
    }

    public void setTotalLoanProviders(int totalLoanProviders) {
        this.totalLoanProviders = totalLoanProviders;
    }

}
