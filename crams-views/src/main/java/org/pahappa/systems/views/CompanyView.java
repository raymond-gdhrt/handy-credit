package org.pahappa.systems.views;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.constants.CompanyStatus;
import org.pahappa.systems.core.services.CompanyService;
import org.pahappa.systems.models.Company;
import org.pahappa.systems.models.security.PermissionConstants;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.context.RequestContext;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "companyView")
@SessionScoped
@ViewPath(path = HyperLinks.COMPANYVIEW)
public class CompanyView extends PaginatedTableView<Company, CompanyView, CompanyView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private String searchTerm;
	private Country country;
	private List<SortField> sortFields;
	private SortField selectedSortField;
	private SortField sortField;
	

	@PostConstruct
	public void init() {
		this.companyService = ApplicationContextProvider.getBean(CompanyService.class);
		this.sortFields = Arrays.asList(new SortField[] { new SortField("Date Created Asc", "dateCreated", false),
				new SortField("Date Created Desc", "dateCreated", true), new SortField("Name Asc", "name", false),
				new SortField("Name Desc", "name", true), new SortField("Service Code Asc", "serviceCode", false),
				new SortField("Service Code Desc", "serviceCode", true), });
		super.setMaximumresultsPerpage(10);
	}
	

	@Override
	public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) throws Exception {
			super.setDataModels(this.companyService.getCompanies(searchTerm, country, sortField, offset, limit));
		
	}

	@Override
	public void reloadFilterReset() {
			super.setTotalRecords(
					this.companyService.countCompanies(searchTerm, country, selectedSortField));
		
	}
	
	
	
	
	public void deactivate(Company company) {
		try {
			this.companyService.deactivate(company);
			super.reloadFilterReset();
			 UiUtils.showMessageBox("Company Deactivated", company.getName()+ "has been deactivated", RequestContext.getCurrentInstance());
			

		} catch (Exception e) {
			UiUtils.showMessageBox("Action Failed", e.getMessage(), RequestContext.getCurrentInstance());
			
		}

	}
	
	
	public void activate(Company company) {
		try {
			this.companyService.activate(company);
			super.reloadFilterReset();
			 UiUtils.showMessageBox("Company Activated Successfully", company.getName()+ "has been Activated", RequestContext.getCurrentInstance());
			

		} catch (Exception e) {
			UiUtils.showMessageBox("Action Failed", e.getMessage(), RequestContext.getCurrentInstance());
			
		}

	}
	
	 public Boolean checkStatus(Company company) {
	        
	        return company.getCompanyStatus() == CompanyStatus.ACTIVE;
	  }
	 
	 public Boolean isDeactivated(Company company) {
	        
	        return company.getCompanyStatus() == CompanyStatus.INACTIVE;
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
	
	public void resetInput() {
		this.searchTerm = "";
	}
	
}