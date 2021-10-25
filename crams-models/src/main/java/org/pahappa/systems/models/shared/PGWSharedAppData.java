package org.pahappa.systems.models.shared;

import org.pahappa.systems.models.Company;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.shared.SharedAppData;

public class PGWSharedAppData {

	private static Company business;
	
	/**
	 * @return the business
	 */
	public static Company getCompany() {
		return business;
	}

	/**
	 * @param business the business to set
	 */
	public static void setCompany(Company business) {
		PGWSharedAppData.business = business;
	}

	public static User getLoggedInUser() {
		return SharedAppData.getLoggedInUser();
	}	
}
