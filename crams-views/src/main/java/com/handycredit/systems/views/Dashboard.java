package com.handycredit.systems.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.handycredit.systems.security.HyperLinks;
import org.sers.webutils.client.controllers.WebAppExceptionHandler;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.shared.SharedAppData;


@ManagedBean(name = "dashboard")
@ViewScoped
@ViewPath(path = HyperLinks.DASHBOARD)
public class Dashboard extends WebAppExceptionHandler implements Serializable {

    private static final long serialVersionUID = 1L;
    private User loggedInUser;

    @PostConstruct
    public void init() {
    	loggedInUser = SharedAppData.getLoggedInUser();       
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


       
      
}
