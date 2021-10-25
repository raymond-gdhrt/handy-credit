package com.handycredit.systems.views.render;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.core.utils.ApplicationSettingsUtils;

@ManagedBean(name = "componentRenderer")
@SessionScoped
public class ComponentRenderer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean administrator = false;
	private boolean businessAdmin = false;
	private User loggedInUser;
	


	@PostConstruct
	public void init() {
	
		this.administrator = loggedInUser.hasAdministrativePrivileges();
		
		
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
	
	/**
	 * @return the businessAdmin
	 */
	public boolean isBusinessAdmin() {
		return businessAdmin;
	}

	/**
	 * @param businessAdmin the businessAdmin to set
	 */
	public void setBusinessAdmin(boolean businessAdmin) {
		this.businessAdmin = businessAdmin;
	}

	
	
}