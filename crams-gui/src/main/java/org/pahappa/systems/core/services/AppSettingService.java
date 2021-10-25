package org.pahappa.systems.core.services;

import org.pahappa.systems.models.AppSetting;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.exception.ValidationFailedException;

/**
 * Responsible for CRUD operations on {@link AppSetting}
 * 
 * @author mmc
 *
 */
public interface AppSettingService {
	/**
	 * Adds a {@link AppSetting} to the database.
	 * 
	 * @param appSetting
	 * @throws ValidationFailedException if the following attributes are blank:
	 *               appName, serviceCode
	 */
	AppSetting save(AppSetting appSetting) throws ValidationFailedException;

	/**
	 * Gets mail settings
	 * 
	 * @return
	 */
	AppSetting getAppSetting();

	/**
	 * Gets country by name
	 * 
	 * @return
	 */
	Country getCountryByName(String countryName);
}
