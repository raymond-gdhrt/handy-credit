package org.pahappa.systems.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.ApiRequestService;
import org.pahappa.systems.models.ApiRequest;
import org.pahappa.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "apiRequestForm")
@SessionScoped
@ViewPath(path = HyperLinks.APIREQUESTFORM)
public class ApiRequestForm extends WebFormView<ApiRequest, ApiRequestForm, ApiRequestView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApiRequestService apiRequestService;

	@Override
	public void beanInit() {
		this.apiRequestService = ApplicationContextProvider.getBean(ApiRequestService.class);
	}

	@Override
	public void pageLoadInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void persist() throws Exception {
		this.apiRequestService.save(super.model);
	}

	public void resetModal() {
		super.resetModal();
		super.model = new ApiRequest();
	}

	public void setFormProperties() {
		super.setFormProperties();
	}

	@Override
	public String getViewUrl() {
		// TODO Auto-generated method stub
		return null;
	}
}
