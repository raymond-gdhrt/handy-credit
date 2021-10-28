/**
 * 
 */
package com.handycredit.systems.core.services;


import com.handycredit.systems.models.SystemSetting;
import org.sers.webutils.model.exception.ValidationFailedException;

/**
 * Responsible for CRUD operations on {@link SystemSetting}
 * 
 * @author RayGdhrt
 *
 */
public interface SystemSettingService extends GenericService<SystemSetting> {
	
	
	
	SystemSetting getSystemSettings();
   
	
}
