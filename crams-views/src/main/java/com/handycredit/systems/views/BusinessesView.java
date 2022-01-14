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
import com.handycredit.systems.constants.BusinessCategory;
import com.handycredit.systems.constants.UgandanDistrict;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.utils.SearchField;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.SetupService;

@ManagedBean(name = "businessesView")
@SessionScoped
@ViewPath(path = HyperLinks.BUSINESSES_VIEW)
public class BusinessesView extends PaginatedTableView<Business, BusinessesView, BusinessesView> {

    private static final long serialVersionUID = 1L;
    private BusinessService businessService;
    private String searchTerm;
    private Business selectedBusiness;
    private Search search;
    private List<UgandanDistrict> districts;
    private UgandanDistrict selectedDistrict;
    private List<BusinessCategory> categories;
    private BusinessCategory selectedBusinessCategory;
    private SortField selectedSortField;

    @PostConstruct
    public void init() {

        businessService = ApplicationContextProvider.getApplicationContext().getBean(BusinessService.class);
        this.districts = Arrays.asList(UgandanDistrict.values());
         this.categories = Arrays.asList(BusinessCategory.values());
        reloadFilterReset();
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {

        super.setDataModels(businessService.getInstances(this.search, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        this.search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        if (this.selectedDistrict != null) {
            search.addFilterEqual("district", this.selectedDistrict);
        }
        
         if (this.selectedBusinessCategory != null) {
            search.addFilterEqual("type", this.selectedBusinessCategory);
        }

        super.setTotalRecords(businessService.countInstances(this.search));
        try {
            super.reloadFilterReset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBusiness(Business business) {

        try {
            businessService.deleteInstance(business);
            UiUtils.showMessageBox("Action successfull", "Business deleted");
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

    public Business getSelectedBusiness() {
        return selectedBusiness;
    }

    public void setSelectedBusiness(Business selectedBusiness) {
        this.selectedBusiness = selectedBusiness;
    }

   

    public SortField getSelectedSortField() {
        return selectedSortField;
    }

    public void setSelectedSortField(SortField selectedSortField) {
        this.selectedSortField = selectedSortField;
    }

    public List<UgandanDistrict> getDistricts() {
        return districts;
    }

    public void setDistricts(List<UgandanDistrict> districts) {
        this.districts = districts;
    }

    public UgandanDistrict getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(UgandanDistrict selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
    }

    public List<BusinessCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<BusinessCategory> categories) {
        this.categories = categories;
    }

    public BusinessCategory getSelectedBusinessCategory() {
        return selectedBusinessCategory;
    }

    public void setSelectedBusinessCategory(BusinessCategory selectedBusinessCategory) {
        this.selectedBusinessCategory = selectedBusinessCategory;
    }

}
