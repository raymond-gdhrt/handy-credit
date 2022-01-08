package com.handycredit.systems.views;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.core.services.BusinessCreditProfileService;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.core.services.LoanService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditProfile;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.models.Loan;
import com.handycredit.systems.models.LoanApplication;
import com.handycredit.systems.security.HyperLinks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import org.primefaces.PrimeFaces;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.SetupService;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "loansListView")
@SessionScoped
@ViewPath(path = HyperLinks.LOANS_LIST_VIEW)
public class LoansListView extends PaginatedTableView<Loan, LoansListView, Dashboard> {

    private static final long serialVersionUID = 1L;
    private LoanService loanService;
    private String searchTerm;
    private Loan selectedLoan;
    private Search search;
    private List<SearchField> searchFields;
    private List<Country> selectedCountries;
    private List<Country> countries;
    private Business business;
    private SortField selectedSortField;
    private LoanApplication selectedLoanApplication;
    private BusinessCreditProfile creditProfile;

    @PostConstruct
    public void init() {

        this.searchFields = Arrays.asList(new SearchField[]{new SearchField("Name", "name"),
            new SearchField("Code", "code"), new SearchField("Email", "emailAddress"), new SearchField("Address", "physcialAddress")});
        this.business = ApplicationContextProvider.getBean(BusinessService.class).getBusinessByUserAccount(SharedAppData.getLoggedInUser());
        loanService = ApplicationContextProvider.getApplicationContext().getBean(LoanService.class);
        this.countries = ApplicationContextProvider.getBean(SetupService.class).getAllCountries();
        reloadFilterReset();
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(loanService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        List<LoanApplication> applications = ApplicationContextProvider.getBean(LoanApplicationService.class)
                .getInstances(new Search().addFilterEqual("business", this.business), 0, 0);
        List<String> appliedLoans = new ArrayList<>();
        applications.forEach(application -> {
            appliedLoans.add(application.getLoan().getId());
        });

        search.addFilterNotIn("id",appliedLoans);
        super.setTotalRecords(loanService.countInstances(this.search));
        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLoan(Loan loan) {

        try {
            loanService.deleteInstance(loan);
            UiUtils.showMessageBox("Action successfull", "Loan deleted");
        } catch (OperationFailedException ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public void loadLoanApplication(Loan loan, Business business) {
        selectedLoanApplication = new LoanApplication();
        selectedLoanApplication.setBusiness(business);
        selectedLoanApplication.setLoan(loan);

    }

    public void makeLoanApplication(LoanApplication loanApplication) {

        try {
            ApplicationContextProvider.getBean(LoanApplicationService.class).saveInstance(loanApplication);
            PrimeFaces.current().executeScript("PF('loan_application_dialog').hide()");

        } catch (Exception ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public List<Collateral> loadCollaterals(Business business) {

        return ApplicationContextProvider.getBean(CollateralService.class)
                .getInstances(new Search()
                        .addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                        .addFilterEqual("business", business), 0, 0);
    }

    public BusinessCreditProfile loadProfile(Business business) throws ValidationFailedException, OperationFailedException {

        return ApplicationContextProvider.getBean(BusinessCreditProfileService.class).createProfile(selectedLoanApplication);
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

    @Override
    public List<ExcelReport> getExcelReportModels() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    public Loan getSelectedLoan() {
        return selectedLoan;
    }

    public void setSelectedLoan(Loan selectedLoan) {
        this.selectedLoan = selectedLoan;
    }

    public List<Country> getSelectedCountries() {
        return selectedCountries;
    }

    public void setSelectedCountries(List<Country> selectedCountries) {
        this.selectedCountries = selectedCountries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public SortField getSelectedSortField() {
        return selectedSortField;
    }

    public void setSelectedSortField(SortField selectedSortField) {
        this.selectedSortField = selectedSortField;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public LoanApplication getSelectedLoanApplication() {
        return selectedLoanApplication;
    }

    public void setSelectedLoanApplication(LoanApplication selectedLoanApplication) {
        this.selectedLoanApplication = selectedLoanApplication;
    }

    public BusinessCreditProfile getCreditProfile() {
        return creditProfile;
    }

    public void setCreditProfile(BusinessCreditProfile creditProfile) {
        this.creditProfile = creditProfile;
    }

}
