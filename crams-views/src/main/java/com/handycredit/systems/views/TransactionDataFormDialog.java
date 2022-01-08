/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views;

import com.handycredit.systems.constants.Transactiontype;
import com.handycredit.systems.constants.UgandanDistrict;
import com.handycredit.systems.core.services.TransactionDataService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.BusinessTransaction;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

/**
 *
 * @author RayGdhrt
 */
@ManagedBean(name = "transactionFormDialog", eager = true)
@SessionScoped
public class TransactionDataFormDialog extends DialogForm<BusinessTransaction> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(TransactionDataFormDialog.class.getSimpleName());
  
    private TransactionDataService businessService;
    private Business business;
    
    private List<Transactiontype> types;
   
    @PostConstruct
    public void init() {

        this.businessService = ApplicationContextProvider.getApplicationContext().getBean(TransactionDataService.class);
        this.types= Arrays.asList(Transactiontype.values());
     
    }

    public TransactionDataFormDialog() {
        super(HyperLinks.TRANSACTION_DATA_DIALOG_FORM, 800, 400);
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
        super.model = new BusinessTransaction();
    }

    @Override
    public void setFormProperties() {
        super.setFormProperties();
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public List<Transactiontype> getTypes() {
        return types;
    }

    public void setTypes(List<Transactiontype> types) {
        this.types = types;
    }

    

   

  
}
