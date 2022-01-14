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
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.TransactionDataService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessCreditHistory;
import com.handycredit.systems.models.BusinessTransaction;
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

@ManagedBean(name = "businessPerformanceView")
@SessionScoped
@ViewPath(path = HyperLinks.BUSINESS_PEFORMANCE_VIEW)
public class BusinessPerformanceView extends PaginatedTableView<BusinessTransaction, BusinessPerformanceView, BusinessPerformanceView> {

    private static final long serialVersionUID = 1L;
    private TransactionDataService transactionService;
    private String searchTerm;
    private BusinessTransaction selectedTransaction;
    private Search search;
    private List<SearchField> searchFields;
    private List<Country> selectedCountries;
    private SortField selectedSortField;
    private Business business;
    private int counter;
    private String counterMessage;

    private List<BusinessTransaction> uploadedTransactions;

    @PostConstruct
    public void init() {

      
        transactionService = ApplicationContextProvider.getApplicationContext().getBean(TransactionDataService.class);
        this.business = ApplicationContextProvider.getBean(BusinessService.class).getBusinessByUserAccount(SharedAppData.getLoggedInUser());
        reloadFilterReset();
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(transactionService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE)
                .addFilterEqual("business", this.business);

        super.setTotalRecords(transactionService.countInstances(this.search));
        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(BusinessTransaction transaction) {

        try {
            transactionService.deleteInstance(transaction);
            UiUtils.showMessageBox("Action successfull", "BusinessCreditHistory deleted");
        } catch (OperationFailedException ex) {
            UiUtils.ComposeFailure("Action failed", ex.getLocalizedMessage());
            Logger.getLogger(BusinessPerformanceView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveUploadedTransactions() throws ValidationFailedException {
        System.out.println("----Inside save uploads method-------");

        if (!this.uploadedTransactions.isEmpty()) {
            for (BusinessTransaction communication : uploadedTransactions) {
                try {
                    this.transactionService.saveInstance(communication);
                } catch (OperationFailedException ex) {
                    System.out.println("Some error occurered-------" + ex.getLocalizedMessage());
                }

            }

            UiUtils.showMessageBox("Save successfull", "Invalid communications were skipped");
            this.uploadedTransactions= null;
            reloadFilterReset();
        }

    }

    public void handleCSVFileUpload(FileUploadEvent event) throws IOException, Exception {
        System.out.println("----Inside upload method-------");
        ExcelUploadHelper clientUploads = new ExcelUploadHelper();
        this.uploadedTransactions = clientUploads.uploadBusinessTransactionsCSVFile(event, business);

        if (uploadedTransactions != null) {
            this.counterMessage = "Successfully uploaded" + uploadedTransactions.size() + " clients";
            UiUtils.showMessageBox("Finished upload", "Success fully uploaded " + uploadedTransactions.size() + " communications");
        } else {
            UiUtils.ComposeFailure("Action failed", "No communication was uploaded due to invalid records in your document. Please cross-check and try again");

        }

    }

    public StreamedContent loadTemplateForDownload() {
        InputStream stream = getClass().getResourceAsStream("/custom-files/transactions_upload_template.csv");

        return new DefaultStreamedContent(stream, "text/csv", "Crams_transactions_UploadTemplate.csv");
    }
    
    public Boolean showSaveUploadsButton() {
        return this.uploadedTransactions != null;

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

    public BusinessTransaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(BusinessTransaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
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

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
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

    public List<BusinessTransaction> getUploadedTransactions() {
        return uploadedTransactions;
    }

    public void setUploadedTransactions(List<BusinessTransaction> uploadedTransactions) {
        this.uploadedTransactions = uploadedTransactions;
    }

  
    

}
