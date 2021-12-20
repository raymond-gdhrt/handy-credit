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
import com.handycredit.systems.core.services.BusinessCreditHistoryService;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditHistory;
import com.handycredit.systems.security.HyperLinks;
import com.handycredit.systems.views.utils.ExcelUploadHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "businessCreditHistoryView")
@SessionScoped
@ViewPath(path = HyperLinks.BUSINESS_CREDIT_HISTORY_VIEW)
public class BusinessCreditHistoryView extends PaginatedTableView<BusinessCreditHistory, BusinessCreditHistoryView, BusinessCreditHistoryView> {

    private static final long serialVersionUID = 1L;
    private BusinessCreditHistoryService creditHistoryService;
    private String searchTerm;
    private BusinessCreditHistory selectedBusinessCreditHistory;
    private Search search;
    private Business loggedInBusiness;
    private int counter;
    private String counterMessage;
    private List<SearchField> searchFields;
    private List<Country> selectedCountries;
    private SortField selectedSortField;
    private List<BusinessCreditHistory> uploadedCreditHistory;

    @PostConstruct
    public void init() {

        this.searchFields = Arrays.asList(new SearchField[]{new SearchField("Name", "name"),
            new SearchField("Code", "code"), new SearchField("Email", "emailAddress"), new SearchField("Address", "physcialAddress")});

        creditHistoryService = ApplicationContextProvider.getApplicationContext().getBean(BusinessCreditHistoryService.class);
        this.loggedInBusiness = ApplicationContextProvider.getBean(BusinessService.class).getBusinessByUserAccount(SharedAppData.getLoggedInUser());

        reloadFilterReset();
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(creditHistoryService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search()
                .addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                .addFilterEqual("business", this.loggedInBusiness);
        super.setTotalRecords(creditHistoryService.countInstances(this.search));
        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBusinessCreditHistory(BusinessCreditHistory transaction) {

        try {
            creditHistoryService.deleteInstance(transaction);
            UiUtils.showMessageBox("Action successfull", "BusinessCreditHistory deleted");
        } catch (OperationFailedException ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
            Logger.getLogger(BusinessCreditHistoryView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveUploadedHistory() throws ValidationFailedException {
        System.out.println("----Inside save uploads method-------");

        if (!this.uploadedCreditHistory.isEmpty()) {
            for (BusinessCreditHistory communication : uploadedCreditHistory) {
                try {
                    this.creditHistoryService.saveInstance(communication);
                } catch (OperationFailedException ex) {
                    System.out.println("Some error occurered-------" + ex.getLocalizedMessage());
                }

            }

            UiUtils.showMessageBox("Save successfull", "Invalid communications were skipped");
            this.uploadedCreditHistory = null;
            reloadFilterReset();
        }

    }

    public void handleCSVFileUpload(FileUploadEvent event) throws IOException, Exception {
        System.out.println("----Inside upload method-------");
        ExcelUploadHelper clientUploads = new ExcelUploadHelper();
        this.uploadedCreditHistory = clientUploads.uploadCreditHistoryCSVFile(event, loggedInBusiness);

        if (uploadedCreditHistory != null) {
            this.counterMessage = "Successfully uploaded" + uploadedCreditHistory.size() + " clients";
            UiUtils.showMessageBox("Finished upload", "Success fully uploaded " + uploadedCreditHistory.size() + " communications");
        } else {
            UiUtils.ComposeFailure("Action failed", "No communication was uploaded due to invalid records in your document. Please cross-check and try again");

        }

    }

    public StreamedContent loadTemplateForDownload() {
        InputStream stream = getClass().getResourceAsStream("/custom-files/credit_history_upload_template.csv");

        return new DefaultStreamedContent(stream, "text/csv", "Crams_credit_histoy_UploadTemplate.csv");
    }

    public Boolean showSaveUploadsButton() {
        return this.uploadedCreditHistory
                != null;

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

    public BusinessCreditHistory getSelectedBusinessCreditHistory() {
        return selectedBusinessCreditHistory;
    }

    public void setSelectedBusinessCreditHistory(BusinessCreditHistory selectedBusinessCreditHistory) {
        this.selectedBusinessCreditHistory = selectedBusinessCreditHistory;
    }

    public List<Country> getSelectedCountries() {
        return selectedCountries;
    }

    public void setSelectedCountries(List<Country> selectedCountries) {
        this.selectedCountries = selectedCountries;
    }

    public SortField getSelectedSortField() {
        return selectedSortField;
    }

    public void setSelectedSortField(SortField selectedSortField) {
        this.selectedSortField = selectedSortField;
    }

    public Business getLoggedInBusiness() {
        return loggedInBusiness;
    }

    public void setLoggedInBusiness(Business loggedInBusiness) {
        this.loggedInBusiness = loggedInBusiness;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getCounterMessage() {
        return counterMessage;
    }

    public void setCounterMessage(String counterMessage) {
        this.counterMessage = counterMessage;
    }

    public List<BusinessCreditHistory> getUploadedCreditHistory() {
        return uploadedCreditHistory;
    }

    public void setUploadedCreditHistory(List<BusinessCreditHistory> uploadedCreditHistory) {
        this.uploadedCreditHistory = uploadedCreditHistory;
    }

}
