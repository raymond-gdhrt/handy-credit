package com.handycredit.systems.core.services;

import com.handycredit.systems.models.Business;
import org.sers.webutils.model.security.User;

/**
 * Responsible for CRUD operations on {@Link Business}
 * @author RayGdhrt
 */
public interface BusinessService extends GenericService<Business>  {
    
    /**
     * 
     * @param business
     * @return 
     */
     Business saveOutsideContext(Business business);
     
     /**
      * 
      * @param user
      * @return 
      */
     Business getBusinessByUserAccount(User user);

}
