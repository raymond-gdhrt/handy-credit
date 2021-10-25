package org.pahappa.systems.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.pahappa.systems.core.services.SystemSettingService;

import org.pahappa.systems.models.SystemSetting;
import org.pahappa.systems.models.Themes;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.context.RequestContext;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "settingsForm")
@SessionScoped
@ViewPath(path = HyperLinks.APP_SETTINGS_FORM)
public class SystemSettingForm extends WebFormView<SystemSetting, SystemSettingForm, Dashboard> {

    private static final long serialVersionUID = 1L;
    private SystemSettingService appSettingService;
    private List<Themes> themes;

    @Override
    public void beanInit() {
        this.appSettingService = ApplicationContextProvider.getBean(SystemSettingService.class);
        if (this.appSettingService.getSystemSettings() != null) {
            super.model = this.appSettingService.getSystemSettings();
        } else {
            super.model = new SystemSetting();
        }
        this.themes = Arrays.asList(Themes.values());
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
        this.appSettingService.save(super.model);
        UiUtils.showMessageBox("App settings updated", "Action Successful", RequestContext.getCurrentInstance());
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

    public List<Themes> getThemes() {
        return themes;
    }

    public void setThemes(List<Themes> themes) {
        this.themes = themes;
    }

}
