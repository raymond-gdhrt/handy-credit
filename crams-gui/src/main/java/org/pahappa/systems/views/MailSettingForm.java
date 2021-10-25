package org.pahappa.systems.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.MailSettingService;
import org.pahappa.systems.models.MailSetting;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.context.RequestContext;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "mailSettingForm")
@SessionScoped
@ViewPath(path = HyperLinks.MAIL_SETTING_FORM)
public class MailSettingForm extends WebFormView<MailSetting, MailSettingForm, Dashboard> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MailSettingService mailSettingService;

	@Override
	public void beanInit() {
		this.mailSettingService = ApplicationContextProvider.getBean(MailSettingService.class);
        if (this.mailSettingService.getMailSetting() != null) {
            super.model = this.mailSettingService.getMailSetting();
        } else {
            super.model = new MailSetting();
        }
	}

	@Override
	public void pageLoadInit() {
        if (this.mailSettingService.getMailSetting() != null) {
            super.model = this.mailSettingService.getMailSetting();
        } else {
            super.model = new MailSetting();
        }
	}

	@Override
	public void persist() throws Exception {
		this.mailSettingService.save(super.model);
		UiUtils.showMessageBox("Mail settings updated", "Action Successful", RequestContext.getCurrentInstance());
	}

    @Override
	public void resetModal() {
		super.resetModal();
		super.model = new MailSetting();
	}

    @Override
	public void setFormProperties() {
		super.setFormProperties();
	}

	@Override
	public String getViewUrl() {
		return this.getViewPath();
	}
}
