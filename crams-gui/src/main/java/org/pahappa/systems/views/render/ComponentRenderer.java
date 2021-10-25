package org.pahappa.systems.views.render;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.AppSettingService;
import org.pahappa.systems.core.services.MailSettingService;
import org.pahappa.systems.models.AppSetting;
import org.pahappa.systems.models.MailSetting;
import org.pahappa.systems.models.security.PermissionConstants;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "umaRenderer")
@SessionScoped
public class ComponentRenderer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean administrator = false;
    private boolean companyPerm = false;
    private boolean dashboardsPerm = false;
    private User loggedInUser;
    private MailSetting mailSetting = new MailSetting();
    private MailSettingService mailSettingService;
    private AppSetting appSetting = new AppSetting();
    private AppSettingService appSettingService;

    @PostConstruct
    public void init() {
        this.administrator = SharedAppData.getLoggedInUser().hasAdministrativePrivileges();
        this.companyPerm = SharedAppData.getLoggedInUser()
                .hasPermission(PermissionConstants.PERM_MANAGE_COMPANIES);

        this.dashboardsPerm = SharedAppData.getLoggedInUser()
                .hasPermission(PermissionConstants.PERM_VIEW_DASHBOARD);

        this.loggedInUser = SharedAppData.getLoggedInUser();

        this.mailSettingService = ApplicationContextProvider.getBean(MailSettingService.class);
        this.appSettingService = ApplicationContextProvider.getBean(AppSettingService.class);

        if (this.mailSettingService.getMailSetting() != null) {
            this.mailSetting = this.mailSettingService.getMailSetting();
        }

        if (this.appSettingService.getAppSetting() != null) {
            this.appSetting = this.appSettingService.getAppSetting();
        }
    }

    /**
     * @return the loggedInUser
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * @param loggedInUser the loggedInUser to set
     */
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * @return the administrator
     */
    public boolean isAdministrator() {
        return administrator;
    }

    /**
     * @param administrator the administrator to set
     */
    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isCompanyPerm() {
        return companyPerm;
    }

    public void setCompanyPerm(boolean companyPerm) {
        this.companyPerm = companyPerm;
    }

    /**
     * @return the dashboardsPerm
     */
    public boolean isDashboardsPerm() {
        return dashboardsPerm;
    }

    /**
     * @param dashboardsPerm the dashboardsPerm to set
     */
    public void setDashboardsPerm(boolean dashboardsPerm) {
        this.dashboardsPerm = dashboardsPerm;
    }

    /**
     *
     *
     *
     * /
     *
     **
     * @return the mailSetting
     */
    public MailSetting getMailSetting() {
        return mailSetting;
    }

    /**
     * @param mailSetting the mailSetting to set
     */
    public void setMailSetting(MailSetting mailSetting) {
        this.mailSetting = mailSetting;
    }

    /**
     * @return the appSetting
     */
    public AppSetting getAppSetting() {
        return appSetting;
    }

    /**
     * @param appSetting the appSetting to set
     */
    public void setAppSetting(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

}
