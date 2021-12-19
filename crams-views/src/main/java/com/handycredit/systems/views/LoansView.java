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
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.core.services.LoanService;
import com.handycredit.systems.models.Loan;
import com.handycredit.systems.models.LoanProvider;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.SetupService;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "loansView")
@SessionScoped
@ViewPath(path = HyperLinks.LOANS_VIEW)
public class LoansView extends PaginatedTableView<Loan, LoansView, LoansView> {

    private static final long serialVersionUID = 1L;
    private LoanService funderService;
    private String searchTerm;
    private Loan selectedLoan;
    private Search search;
    private List<SearchField> searchFields;
    private List<Country> selectedCountries;
    private List<Country> countries;
    private SortField selectedSortField;
    private LoanProvider loanProvider;
    private User loggedInUser;

    @PostConstruct
    public void init() {

        this.searchFields = Arrays.asList(new SearchField[]{new SearchField("Name", "name"),
            new SearchField("Code", "code"), new SearchField("Email", "emailAddress"), new SearchField("Address", "physcialAddress")});

        funderService = ApplicationContextProvider.getApplicationContext().getBean(LoanService.class);
        this.countries = ApplicationContextProvider.getBean(SetupService.class).getAllCountries();
        this.loggedInUser = SharedAppData.getLoggedInUser();
        this.loanProvider = ApplicationContextProvider.getBean(LoanProviderService.class)
                .getLoanProviderByUserAccount(loggedInUser);

        reloadFilterReset();

    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(funderService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search() .addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        if (this.loanProvider != null) {
            this.search.addFilterEqual("loanProvider", this.loanProvider);
        }

        super.setTotalRecords(funderService.countInstances(this.search));

    }

    public void deleteLoan(Loan funder) {

        try {
            funderService.deleteInstance(funder);
            UiUtils.showMessageBox("Action successfull", "Loan deleted");
        } catch (OperationFailedException ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
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

}
