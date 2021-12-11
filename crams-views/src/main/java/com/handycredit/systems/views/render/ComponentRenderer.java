package com.handycredit.systems.views.render;

import com.handycredit.systems.models.security.PermissionConstants;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.sers.webutils.model.security.User;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "componentRenderer")
@SessionScoped
public class ComponentRenderer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean administrator = false;
    private boolean businessOwner = false;
    private boolean loanProvider = false;
    private User loggedInUser;

    @PostConstruct
    public void init() {

        this.loggedInUser = SharedAppData.getLoggedInUser();
        this.administrator = loggedInUser.hasAdministrativePrivileges();
        this.loanProvider = loggedInUser.hasPermission(PermissionConstants.PERM_LOAN_PROVIDER);
        this.businessOwner = loggedInUser.hasPermission(PermissionConstants.PERM_BUSINESS_OWNER);

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

    public boolean isBusinessOwner() {
        return businessOwner;
    }

    public void setBusinessOwner(boolean businessOwner) {
        this.businessOwner = businessOwner;
    }

    public boolean isLoanProvider() {
        return loanProvider;
    }

    public void setLoanProvider(boolean loanProvider) {
        this.loanProvider = loanProvider;
    }

  

}
