package org.pahappa.systems.views;

import com.googlecode.genericdao.search.Search;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.ManufacturerService;
import org.pahappa.systems.models.Manufacturer;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.context.RequestContext;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "manufacturersView")
@SessionScoped
@ViewPath(path = HyperLinks.MANUFACTURERS_VIEW)
public class ManufacturersView extends PaginatedTableView<Manufacturer, ManufacturersView, ManufacturersView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManufacturerService manufacturerService;
	private String searchTerm;
        Search search= new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);

	@PostConstruct
	public void init() {
		this.manufacturerService = ApplicationContextProvider.getBean(ManufacturerService.class);
		super.setMaximumresultsPerpage(10);
	}

	@Override
	public void reloadFromDB(int offset, int limit, Map<String, Object> arg2) throws Exception {
		super.setDataModels(this.manufacturerService.getManufacturers(search, offset, limit));
	}

	@Override
	public void reloadFilterReset() {
		super.setTotalRecords(this.manufacturerService.countManufacturers(search));
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
	
	public void deleteManufacturer(Manufacturer manufacturer) {
		try {
			this.manufacturerService.delete(manufacturer);
			super.reloadFilterReset();
			UiUtils.showMessageBox(manufacturer.getPhoneNumber() + " has been deleted", "Action Successful", RequestContext.getCurrentInstance());
		} catch (Exception e) {
			UiUtils.showMessageBox(e.getMessage(), "Action failed", RequestContext.getCurrentInstance());
		}
	}
	
	private void resetInput() {
		this.searchTerm = "";
	}
	
	
}
