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
import com.handycredit.systems.constants.LoanProviderType;
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.models.LoanProvider;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;

@ManagedBean(name = "loanProvidersView")
@SessionScoped
@ViewPath(path = HyperLinks.LOAN_PROVIDERS_VIEW)
public class LoanProvidersView extends PaginatedTableView<LoanProvider, LoanProvidersView, LoanProvidersView> {

    private static final long serialVersionUID = 1L;
    private LoanProviderService loanProviderService;
    private String searchTerm;
    private LoanProvider selectedLoanProvider;
    private Search search;
    private List<LoanProviderType> loanProviderTypes;
    private LoanProviderType selectedType;
    private SortField selectedSortField;

    @PostConstruct
    public void init() {

        loanProviderService = ApplicationContextProvider.getApplicationContext().getBean(LoanProviderService.class);
        this.loanProviderTypes = Arrays.asList(LoanProviderType.values());
        reloadFilterReset();
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(loanProviderService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        if (this.selectedType != null) {
            search.addFilterEqual("type", this.selectedType);
        }
        super.setTotalRecords(loanProviderService.countInstances(this.search));
        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLoanProvider(LoanProvider loanProvider) {

        try {
            loanProviderService.deleteInstance(loanProvider);
            UiUtils.showMessageBox("Action successfull", "LoanProvider deleted");
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

    public LoanProvider getSelectedLoanProvider() {
        return selectedLoanProvider;
    }

    public void setSelectedLoanProvider(LoanProvider selectedLoanProvider) {
        this.selectedLoanProvider = selectedLoanProvider;
    }

    public List<LoanProviderType> getLoanProviderTypes() {
        return loanProviderTypes;
    }

    public void setLoanProviderTypes(List<LoanProviderType> loanProviderTypes) {
        this.loanProviderTypes = loanProviderTypes;
    }

    public SortField getSelectedSortField() {
        return selectedSortField;
    }

    public void setSelectedSortField(SortField selectedSortField) {
        this.selectedSortField = selectedSortField;
    }

    public LoanProviderType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(LoanProviderType selectedType) {
        this.selectedType = selectedType;
    }

}
