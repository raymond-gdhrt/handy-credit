package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.constants.AccountStatus;
import com.handycredit.systems.constants.BusinessCategory;
import com.handycredit.systems.constants.UgandanDistrict;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang.WordUtils;
import org.primefaces.PrimeFaces;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "businessRegistrationForm")
@ViewScoped
@ViewPath(path = HyperLinks.DASHBOARD)
public class BusinessRegistrationForm extends WebFormView<Business, BusinessRegistrationForm, BusinessRegistrationForm> {

    private static final long serialVersionUID = 1L;
    private BusinessService businessService;
    private List<UgandanDistrict> districts;
    private List<BusinessCategory> businessTypes;
    Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
    private UserService userService;
    private String customUiMessage;
    private String verificationCode;
    private boolean successResponse = true;
    private boolean showForm = true;
    private boolean showCodeSection, showSuccessMessageSection, showFinalSection;

    @Override
    public void beanInit() {
        super.model = new Business();
        this.userService = ApplicationContextProvider.getBean(UserService.class);
        this.businessService = ApplicationContextProvider.getBean(BusinessService.class);
        this.districts = Arrays.asList(UgandanDistrict.values());
        this.businessTypes = Arrays.asList(BusinessCategory.values());

    }

    @Override
    public void pageLoadInit() {
        // TODO Auto-generated method stub
        super.model = new Business();

    }

    @Override
    public void persist() throws Exception {
        this.businessService.saveOutsideContext(super.model);
        super.model = new Business();
        resetModal();

        createDefaultUser(super.model);
    }

    public void createMember() {
        try {
            String code = String.valueOf(new Random(6).nextInt());
            super.model.setLastVerificationCode(code);
            super.model.setAccountStatus(AccountStatus.created);
            super.model = this.businessService.saveOutsideContext(super.model);

            // new EMailClient().sendHtmlEmail(super.model.getEmailAddress(), "AAPU registartion", "Confirm your email address with this code\n ");
            showCodeForm();
        } catch (Exception ex) {
            customUiMessage = "Ops, some error occured\n " + ex.getLocalizedMessage();
            this.successResponse = false;
            Logger.getLogger(BusinessRegistrationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verifyCode() {
        try {
            if (verificationCode.equalsIgnoreCase(super.model.getLastVerificationCode())) {

                super.model.setAccountStatus(AccountStatus.verified);
                super.model = this.businessService.saveOutsideContext(super.model);
                showFinalSection();
            } else {
                customUiMessage = "Invalid code supplied, please try again";
                this.successResponse = false;
            }
        } catch (Exception ex) {
            customUiMessage = "Ops, some error occured\n " + ex.getLocalizedMessage();
            this.successResponse = false;
            Logger.getLogger(BusinessRegistrationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createDefaultUser(Business manufacturer) throws ValidationFailedException {
        User user = new User();
        user.setUsername(manufacturer.getEmailAddress());
        user.setFirstName(WordUtils.capitalize(manufacturer.getName()));
        user.setLastName("User");
        user.setClearTextPassword("business");
        List<Role> roles = userService.getRoles();
        for (Role role : roles) {
            if (!role.getName().equals(Role.DEFAULT_ADMIN_ROLE)) {
                user.addRole(role);
            }
        }

        this.userService.saveUser(user);

    }

    public void showRegForm() {
        this.showForm = true;
        this.showCodeSection = false;
        this.showFinalSection = false;
        PrimeFaces.current().ajax().update("businessRegistrationForm");

    }

    public void showCodeForm() {
        this.showForm = false;
        this.showCodeSection = true;
        this.showFinalSection = false;
        PrimeFaces.current().ajax().update("businessRegistrationForm");

    }

    public void showFinalSection() {
        this.showForm = false;
        this.showCodeSection = false;
        this.showFinalSection = true;
        PrimeFaces.current().ajax().update("businessRegistrationForm");

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

    @Override
    public String getViewUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<UgandanDistrict> getDistricts() {
        return districts;
    }

    public void setDistricts(List<UgandanDistrict> districts) {
        this.districts = districts;
    }

    public List<BusinessCategory> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<BusinessCategory> businessTypes) {
        this.businessTypes = businessTypes;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public String getCustomUiMessage() {
        return customUiMessage;
    }

    public void setCustomUiMessage(String customUiMessage) {
        this.customUiMessage = customUiMessage;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(boolean successResponse) {
        this.successResponse = successResponse;
    }

    public boolean isShowForm() {
        return showForm;
    }

    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
    }

    public boolean isShowCodeSection() {
        return showCodeSection;
    }

    public void setShowCodeSection(boolean showCodeSection) {
        this.showCodeSection = showCodeSection;
    }

    public boolean isShowSuccessMessageSection() {
        return showSuccessMessageSection;
    }

    public void setShowSuccessMessageSection(boolean showSuccessMessageSection) {
        this.showSuccessMessageSection = showSuccessMessageSection;
    }

}
