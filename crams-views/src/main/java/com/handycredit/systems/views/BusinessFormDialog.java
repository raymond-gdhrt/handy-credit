/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views;

import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.security.HyperLinks;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.service.SetupService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

/**
 *
 * @author RayGdhrt
 */
@ManagedBean(name = "businessFormDialog", eager = true)
@SessionScoped
public class BusinessFormDialog extends DialogForm<Business> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(BusinessFormDialog.class.getSimpleName());
  
    private BusinessService businessService;
    
    private List<Country> countryList;
   
    @PostConstruct
    public void init() {

        this.businessService = ApplicationContextProvider.getApplicationContext().getBean(BusinessService.class);
        this.countryList=ApplicationContextProvider.getBean(SetupService.class).getAllCountries();
     
    }

    public BusinessFormDialog() {
        super(HyperLinks.BUSINESS_DIALOG_FORM, 700, 500);
    }

    @Override
    public void persist() throws ValidationFailedException, OperationFailedException {
      
            this.businessService.saveInstance(super.model);
            
    }

    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new Business();
    }

    @Override
    public void setFormProperties() {
        super.setFormProperties();
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

  
}
