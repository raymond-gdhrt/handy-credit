package org.pahappa.systems.views;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.pahappa.systems.core.services.CompanyService;
import org.pahappa.systems.models.Company;
import org.pahappa.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.Country;
import org.sers.webutils.server.core.service.SetupService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "companyForm")
@SessionScoped
@ViewPath(path = HyperLinks.COMPANYFORM)
public class CompanyForm extends WebFormView<Company, CompanyForm, CompanyView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CompanyService businessService;
	private Country selectedCountry;
	private List<Country> countries;

	@Override
	public void beanInit() {
		this.businessService = ApplicationContextProvider.getBean(CompanyService.class);
		this.countries = ApplicationContextProvider.getBean(SetupService.class)
				.getAllCountries();
	}

	@Override
	public void pageLoadInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void persist() throws Exception {
		this.businessService.save(super.model);
	}

	public void resetModal() {
		super.resetModal();
		super.model = new Company();
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
	 * @return the selectedCountry
	 */
	public Country getSelectedCountry() {
		return selectedCountry;
	}

	/**
	 * @param selectedCountry the selectedCountry to set
	 */
	public void setSelectedCountry(Country selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	/**
	 * @return the countries
	 */
	public List<Country> getCountries() {
		return countries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
}
