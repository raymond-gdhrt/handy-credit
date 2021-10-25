package org.pahappa.systems.california.settings;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.pahappa.systems.core.services.SystemSettingService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "guestPreferences")
@SessionScoped
public class CaliforniaSettings implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String layout = "moody";

    private String theme = "green";
    
    private boolean darkMenu = true;

    private boolean gradientMenu = false;

    private boolean darkMegaMenu = true;

    private boolean gradientMegaMenu = false;

    private String menuLayout = "static";

    private String profileMode = "inline";

    public String getTheme() {
//        if (ApplicationContextProvider.getBean(SystemSettingService.class).getSystemSettings() != null) {
//            return ApplicationContextProvider.getBean(SystemSettingService.class).getSystemSettings().getThemeColor().name();
//
//        }
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public boolean isDarkMenu() {
        return this.darkMenu;
    }

    public boolean isGradientMenu() {
        return this.gradientMenu;
    }

    public boolean isDarkMegaMenu() {
        return this.darkMegaMenu;
    }

    public boolean isGradientMegaMenu() {
        return this.gradientMegaMenu;
    }

    public void setMenuMode(boolean dark, boolean gradient, String theme) {
        this.darkMenu = dark;
        this.gradientMenu = gradient;
        this.theme = theme;
    }

    public void setMegaMenuMode(boolean dark, boolean gradient) {
        this.darkMegaMenu = dark;
        this.gradientMegaMenu = gradient;
    }

    public String getMenuLayout() {
        return menuLayout;
    }

    public void setMenuLayout(String menuLayout) {
        this.menuLayout = menuLayout;
    }

    public String getProfileMode() {
        return profileMode;
    }

    public void setProfileMode(String profileMode) {
        this.profileMode = profileMode;
    }
}
