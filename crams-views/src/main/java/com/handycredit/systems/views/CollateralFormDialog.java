/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views;

import com.handycredit.systems.constants.CollateralStatus;
import com.handycredit.systems.constants.UgandanDistrict;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
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
@ManagedBean(name = "collateralFormDialog", eager = true)
@SessionScoped
public class CollateralFormDialog extends DialogForm<Collateral> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CollateralFormDialog.class.getSimpleName());
  
    private CollateralService businessService;
    
    private List<CollateralStatus> statuses;
    private Business business;
   
    @PostConstruct
    public void init() {

        this.businessService = ApplicationContextProvider.getApplicationContext().getBean(CollateralService.class);
        this.statuses= Arrays.asList(CollateralStatus.values());
     
    }

    public CollateralFormDialog() {
        super(HyperLinks.COLLATERAL_DIALOG_FORM, 700, 500);
    }

    @Override
    public void persist() throws ValidationFailedException, OperationFailedException {
      if(super.model.getBusiness()==null){
      super.model.setBusiness(business);
      }
            this.businessService.saveInstance(super.model);
            
    }

    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new Collateral();
    }

    @Override
    public void setFormProperties() {
        super.setFormProperties();
    }

    public List<CollateralStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CollateralStatus> statuses) {
        this.statuses = statuses;
    }

    
   

  
}
