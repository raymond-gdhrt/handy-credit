package org.pahappa.systems.views;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.models.Sector;
import org.pahappa.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.pahappa.systems.core.services.SectorService;

@ManagedBean(name = "sectorForm")
@SessionScoped
@ViewPath(path = HyperLinks.SECTOR_FORM)
public class SectorForm extends WebFormView<Sector, SectorForm, SectorsView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SectorService sectorService;
	
	private String sectorName = "";
	private String sectorNumber = "";
	private boolean newView;
	
	@Override
	public void beanInit() {
		this.resetModal();
		this.sectorService = ApplicationContextProvider.getBean(SectorService.class);
		
	}

	@Override
	public void pageLoadInit() {
		this.sectorService = ApplicationContextProvider.getBean(SectorService.class);
		
	}

	@Override
	public void persist() throws Exception {
		this.sectorService.save(super.model);
	}

	public void resetModal() {
		super.resetModal();
		super.model = new Sector();
	}

	public void setFormProperties() {
		super.setFormProperties();
	}

	@Override
	public String getViewUrl() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @return the sectorNumber
	 */
	public String getManufacturerNumber() {
		return sectorNumber;
	}

	
	public void resetInput() {
		this.sectorName = "";
		this.sectorNumber = "";
		
	}
        
	
	
	/**
	 * @return the newView
	 */
	public boolean isNewView() {
		return newView;
	}

	/**
	 * @param newView the newView to set
	 */
	public void setNewView(boolean newView) {
		this.newView = newView;
	}

}
