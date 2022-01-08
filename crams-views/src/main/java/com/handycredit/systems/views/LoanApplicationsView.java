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
import com.handycredit.systems.constants.LoanApplicationStatus;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.LoanApplicationService;
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.models.LoanApplication;
import com.handycredit.systems.models.security.PermissionConstants;
import com.handycredit.systems.security.HyperLinks;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "loanApplicationsView")
@SessionScoped
@ViewPath(path = HyperLinks.LOANS_VIEW)
public class LoanApplicationsView extends PaginatedTableView<LoanApplication, LoanApplicationsView, LoanApplicationsView> {

    private static final long serialVersionUID = 1L;
    private LoanApplicationService loanService;
    private String searchTerm;
    private User loggeUser;
    private LoanApplication selectedLoanApplication;
    private Search search;
    private String notes;
    private int total, approved, running, cleared;
    private SortField selectedSortField;

    @PostConstruct
    public void init() {

        loanService = ApplicationContextProvider.getApplicationContext().getBean(LoanApplicationService.class);
        this.loggeUser = SharedAppData.getLoggedInUser();
        reloadFilterReset();
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(loanService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);

        if (!loggeUser.hasAdministrativePrivileges() && loggeUser.hasRole(PermissionConstants.PERM_BUSINESS_OWNER)) {
            this.search.addFilterEqual("business", ApplicationContextProvider.getBean(BusinessService.class).getBusinessByUserAccount(loggeUser));
        }

        if (!loggeUser.hasAdministrativePrivileges() && loggeUser.hasRole(PermissionConstants.PERM_LOAN_PROVIDER)) {
            this.search.addFilterEqual("loan.loanProvider", ApplicationContextProvider.getBean(LoanProviderService.class).getLoanProviderByUserAccount(loggeUser));

        }

        super.setTotalRecords(loanService.countInstances(this.search));
        this.total = super.getTotalRecords();
        this.approved = loanService.countInstances(this.search.copy().addFilterEqual("status", LoanApplicationStatus.Approved));
        this.cleared = loanService.countInstances(this.search.copy().addFilterEqual("status", LoanApplicationStatus.Cleared));
        this.running = loanService.countInstances(this.search.copy().addFilterEqual("status", LoanApplicationStatus.Running));

        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLoanApplication(LoanApplication loan) {

        try {
            loanService.deleteInstance(loan);
            UiUtils.showMessageBox("Action successfull", "LoanApplication deleted");
        } catch (OperationFailedException ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public void approveLoanApplication(LoanApplication loanApplication) {

        try {
            loanService.approveLoan(loanApplication);
            UiUtils.showMessageBox("Action successfull", "Loan Application approved");
        } catch (Exception ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public void rejectLoanApplication(LoanApplication loanApplication) {

        try {
            loanService.rejectLoan(loanApplication, notes);
            UiUtils.showMessageBox("Action successfull", "Loan Application rejected");
        } catch (Exception ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public void disburseLoanApplication(LoanApplication loanApplication) {

        try {
            loanService.disburseLoan(loanApplication);
            UiUtils.showMessageBox("Action successfull", "Loan Application rejected");
        } catch (Exception ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public void clearLoanApplication(LoanApplication loanApplication) {

        try {
            loanService.clearLoan(loanApplication);
            UiUtils.showMessageBox("Action successfull", "Loan Application rejected");
        } catch (Exception ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public void defaultLoanApplication(LoanApplication loanApplication) {

        try {
            loanService.defaultLoan(loanApplication);
            UiUtils.showMessageBox("Action successfull", "Loan Application rejected");
        } catch (Exception ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
        }

    }

    public void loadSelectedLoanApplication(LoanApplication loanApplication) {
       
        this.selectedLoanApplication = loanApplication;

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

    public SortField getSelectedSortField() {
        return selectedSortField;
    }

    public void setSelectedSortField(SortField selectedSortField) {
        this.selectedSortField = selectedSortField;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getRunning() {
        return running;
    }

    public void setRunning(int running) {
        this.running = running;
    }

    public int getCleared() {
        return cleared;
    }

    public void setCleared(int cleared) {
        this.cleared = cleared;
    }

}
