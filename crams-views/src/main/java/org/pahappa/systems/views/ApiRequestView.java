package org.pahappa.systems.views;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.pahappa.systems.core.services.ApiRequestService;
import org.pahappa.systems.models.ApiRequest;
import org.pahappa.systems.constants.EndPointCategory;
import org.pahappa.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "apiRequestView")
@ViewScoped
@ViewPath(path = HyperLinks.APIREQUESTVIEW)
public class ApiRequestView extends PaginatedTableView<ApiRequest, ApiRequestView, Dashboard> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ApiRequestService apiRequestService;
    private String searchTerm;
    private Country country;
    private ApiRequest selectedApiRequest;
    private List<SortField> sortFields;
    private SortField selectedSortField= new SortField("Date Created Desc", "dateCreated", true);
    private SortField sortField;
    private List<EndPointCategory> endPointCategories;
    private EndPointCategory selectedCategory;
    private Date dateFrom;
    private Date dateTo;
    private List<ApiRequest> apiRequests;

    @PostConstruct
    public void init() {
        this.apiRequestService = ApplicationContextProvider.getBean(ApiRequestService.class);
        this.endPointCategories = Arrays.asList(EndPointCategory.values());
        this.sortFields = Arrays.asList(new SortField[]{new SortField("Date Created Asc", "dateCreated", false),
            new SortField("Date Created Desc", "dateCreated", true), new SortField("Endpoint Asc", "endPoint", false),
            new SortField("Endpoint Desc", "endPoint", true), new SortField("Request IP Asc", "requesterIPAddress", false),
            new SortField("Request IP Desc", "requesterIPAddress", true),});
        super.setMaximumresultsPerpage(10);
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) throws Exception {
       super.setDataModels(this.apiRequestService.getApiRequests(searchTerm, this.selectedCategory, this.dateFrom, this.dateTo, selectedSortField, offset, limit));
    }

    @Override
    public void reloadFilterReset() {
        super.setTotalRecords(this.apiRequestService.countApiRequest(searchTerm, this.selectedCategory, this.dateFrom, this.dateTo, selectedSortField));
        this.resetInput();
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

    /**
     * @return the sortFields
     */
    public List<SortField> getSortFields() {
        return sortFields;
    }

    /**
     * @param sortFields the sortFields to set
     */
    public void setSortFields(List<SortField> sortFields) {
        this.sortFields = sortFields;
    }

    /**
     * @return the selectedSortField
     */
    public SortField getSelectedSortField() {
        return selectedSortField;
    }

    /**
     * @param selectedSortField the selectedSortField to set
     */
    public void setSelectedSortField(SortField selectedSortField) {
        this.selectedSortField = selectedSortField;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @return the sortField
     */
    public SortField getSortField() {
        return sortField;
    }

    /**
     * @param sortField the sortField to set
     */
    public void setSortField(SortField sortField) {
        this.sortField = sortField;
    }

    /**
     * @return the selectedCategory
     */
    public EndPointCategory getSelectedCategory() {
        return selectedCategory;
    }

    /**
     * @param selectedCategory the selectedCategory to set
     */
    public void setSelectedCategory(EndPointCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    /**
     * @return the endPointCategories
     */
    public List<EndPointCategory> getEndPointCategories() {
        return endPointCategories;
    }

    /**
     * @param endPointCategories the endPointCategories to set
     */
    public void setEndPointCategories(List<EndPointCategory> endPointCategories) {
        this.endPointCategories = endPointCategories;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the apiRequests
     */
    public List<ApiRequest> getApiRequests() {
        return apiRequests;
    }

    public ApiRequestService getApiRequestService() {
        return apiRequestService;
    }

    public void setApiRequestService(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public ApiRequest getSelectedApiRequest() {
        return selectedApiRequest;
    }

    public void setSelectedApiRequest(ApiRequest selectedApiRequest) {
        this.selectedApiRequest = selectedApiRequest;
    }

    /**
     * @param apiRequests the apiRequests to set
     */
    public void setApiRequests(List<ApiRequest> apiRequests) {
        this.apiRequests = apiRequests;
    }

    public void resetInput() {
        this.searchTerm = "";
        this.dateFrom = null;
        this.dateTo = null;
    }
}
