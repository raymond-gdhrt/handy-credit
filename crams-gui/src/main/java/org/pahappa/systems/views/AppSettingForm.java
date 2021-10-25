package org.pahappa.systems.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.AppSettingService;
import org.pahappa.systems.models.AppSetting;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.context.RequestContext;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "appSettingForm")
@SessionScoped
@ViewPath(path = HyperLinks.APP_SETTING_FORM)
public class AppSettingForm extends WebFormView<AppSetting, AppSettingForm, Dashboard> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AppSettingService appSettingService;

    @Override
    public void beanInit() {
        this.appSettingService = ApplicationContextProvider.getBean(AppSettingService.class);
        if (this.appSettingService.getAppSetting() != null) {
            super.model = this.appSettingService.getAppSetting();
        } else {
            super.model = new AppSetting();
        }
    }

    @Override
    public void pageLoadInit() {
        if (this.appSettingService.getAppSetting() != null) {
            super.model = this.appSettingService.getAppSetting();
        } else {
            super.model = new AppSetting();
        }
    }

    @Override
    public void persist() throws Exception {
        this.appSettingService.save(super.model);
        UiUtils.showMessageBox("App settings updated", "Action Successful", RequestContext.getCurrentInstance());
    }

    

    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new AppSetting();
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
