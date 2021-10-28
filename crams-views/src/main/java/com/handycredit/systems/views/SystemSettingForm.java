package com.handycredit.systems.views;

import com.handycredit.systems.core.services.SystemSettingService;
import com.handycredit.systems.models.SystemSetting;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.handycredit.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "settingsForm")
@SessionScoped
@ViewPath(path = HyperLinks.APP_SETTINGS_FORM)
public class SystemSettingForm extends WebFormView<SystemSetting, SystemSettingForm, Dashboard> {

    private static final long serialVersionUID = 1L;
    private SystemSettingService appSettingService;
   

    @Override
    public void beanInit() {
        this.appSettingService = ApplicationContextProvider.getBean(SystemSettingService.class);
        if (this.appSettingService.getSystemSettings() != null) {
            super.model = this.appSettingService.getSystemSettings();
        } else {
            super.model = new SystemSetting();
        }
       
    }

    @Override
    public void pageLoadInit() {
        if (this.appSettingService.getSystemSettings() != null) {
            super.model = this.appSettingService.getSystemSettings();
        } else {
            super.model = new SystemSetting();
        }
    }

    @Override
    public void persist() throws Exception {
        this.appSettingService.saveInstance(super.model);
        UiUtils.showMessageBox("App settings updated", "Action Successful");
    }

    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new SystemSetting();
    }

    @Override
    public void setFormProperties() {
        super.setFormProperties();
    }

    @Override
    public String getViewUrl() {
        return this.getViewPath();
    }

    public SystemSettingService getAppSettingService() {
        return appSettingService;
    }

    public void setAppSettingService(SystemSettingService appSettingService) {
        this.appSettingService = appSettingService;
    }

  
}
