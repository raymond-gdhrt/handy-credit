/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.views;

import com.handycredit.systems.constants.CollateralStatus;
import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.core.utils.AppUtils;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.Collateral;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
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
    private boolean editDetails=true;

    @PostConstruct
    public void init() {

        this.businessService = ApplicationContextProvider.getApplicationContext().getBean(CollateralService.class);
        this.statuses = Arrays.asList(CollateralStatus.values());

    }

    public CollateralFormDialog() {
        super(HyperLinks.COLLATERAL_DIALOG_FORM, 700, 600);
    }

    @Override
    public void persist() throws ValidationFailedException, OperationFailedException {
        if (super.model.getBusiness() == null) {
            super.model.setBusiness(business);
        }
        this.businessService.saveInstance(super.model);

    }

    /*
    Upload images to cloudinary
     */
    public void imageUploadEvent(FileUploadEvent event) {
        try {
            if (super.model.isNew()) {
                super.model = this.businessService.saveInstance(super.model);
            }

            byte[] contents = IOUtils.toByteArray(event.getFile().getInputstream());
            String imageUrl = new AppUtils().uploadCloudinaryImage(contents, "crams_collaterals/" + super.model.getId());
            System.out.println("Image url = " + imageUrl);

            super.model.setAttachementLink(imageUrl);
            super.model = businessService.saveInstance(super.model);

        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage("Failed", "Image upload failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(CollateralFormDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public CollateralService getBusinessService() {
        return businessService;
    }

    public void setBusinessService(CollateralService businessService) {
        this.businessService = businessService;
    }

    public boolean isEditDetails() {
        return editDetails;
    }

    public void setEditDetails(boolean editDetails) {
        this.editDetails = editDetails;
    }

}
