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
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.models.LoanApplication;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.SetupService;

@ManagedBean(name = "loanApplicationsView")
@SessionScoped
@ViewPath(path = HyperLinks.LOANS_VIEW)
public class LoanApplicationsView extends PaginatedTableView<LoanApplication, LoanApplicationsView, LoanApplicationsView> {

    private static final long serialVersionUID = 1L;
    private LoanApplicationService funderService;
    private String searchTerm;
    private LoanApplication selectedLoanApplication;
    private Search search;
    private List<SearchField> searchFields;
    private List<Country> selectedCountries;
    private List<Country> countries;
    private SortField selectedSortField;

    @PostConstruct
    public void init() {

        this.searchFields = Arrays.asList(new SearchField[]{new SearchField("Name", "name"),
            new SearchField("Code", "code"), new SearchField("Email", "emailAddress"), new SearchField("Address", "physcialAddress")});

        funderService = ApplicationContextProvider.getApplicationContext().getBean(LoanApplicationService.class);
        this.countries = ApplicationContextProvider.getBean(SetupService.class).getAllCountries();
        reloadFilterReset();
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(funderService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        super.setTotalRecords(funderService.countInstances(this.search));
        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLoanApplication(LoanApplication funder) {

        try {
            funderService.deleteInstance(funder);
            UiUtils.showMessageBox("Action successfull", "LoanApplication deleted");
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

    public LoanApplication getSelectedLoanApplication() {
        return selectedLoanApplication;
    }

    public void setSelectedLoanApplication(LoanApplication selectedLoanApplication) {
        this.selectedLoanApplication = selectedLoanApplication;
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
