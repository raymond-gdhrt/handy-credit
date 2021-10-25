/**
 * 
 */
package com.handycredit.systems.core.services;

import java.util.List;

import com.handycredit.systems.models.SystemSetting;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;

/**
 * Responsible for CRUD operations on {@link SystemSetting}
 * 
 * @author mmc
 *
 */
public interface SystemSettingService {
	
	SystemSetting save(SystemSetting settings) throws ValidationFailedException;
	
	
	SystemSetting getSystemSettings();
   
	
}
