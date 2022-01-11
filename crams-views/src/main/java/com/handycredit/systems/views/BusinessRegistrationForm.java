package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.constants.AccountStatus;
import com.handycredit.systems.constants.BusinessCategory;
import com.handycredit.systems.constants.CompanySize;
import com.handycredit.systems.constants.UgandanDistrict;
import com.handycredit.systems.core.services.BusinessService;
import com.handycredit.systems.core.utils.AppUtils;
import com.handycredit.systems.core.utils.EmailService;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.Business;
import com.handycredit.systems.models.security.PermissionConstants;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.text.WordUtils;
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
@ViewPath(path = HyperLinks.BUSINESS_REGISTRATION_FORM)
public class BusinessRegistrationForm extends WebFormView<Business, BusinessRegistrationForm, BusinessRegistrationForm> {

    private static final long serialVersionUID = 1L;
    private BusinessService businessService;
    private List<UgandanDistrict> districts;
    private List<BusinessCategory> businessTypes;
     private List<CompanySize> companySizes;
    Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
    private UserService userService;
    private String customUiMessage;
    private String verificationCode;
    private String password;
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
         this.companySizes = Arrays.asList(CompanySize.values());

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
            String code = AppUtils.generateOTP(6);
            System.out.println("Generated code: " + code);
            super.model.setLastVerificationCode(code);
            super.model.setAccountStatus(AccountStatus.created);
            super.model = this.businessService.saveOutsideContext(super.model);

            new EmailService().sendMail(super.model.getEmailAddress(), "CRAMS registration", "Confirm your email address with this code\n <h2><b>" + code + "</b></h2>");
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
                password = "business" + AppUtils.generateOTP(4);

                new EmailService().sendMail(super.model.getEmailAddress(), "CRAMS Login details", "Your CRAMs account wwas successfully created, Your login credentials are <h5>Username: <b>" + super.model.getEmailAddress() + "</b> </h5> <h5>Password: <b>" + password + "</b> </h5>");
                super.model.setAccountStatus(AccountStatus.verified);
                super.model.setUserAccount(createDefaultUser(super.model));
                this.businessService.saveOutsideContext(super.model);
                super.model = new Business();
                customUiMessage = "Account created successfully. Check your email for login details";
                this.successResponse = true;

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

    private User createDefaultUser(Business business) throws ValidationFailedException {
        System.out.println("Creating user account...");

        User user = new User();
        user.setUsername(business.getEmailAddress());
        user.setFirstName(WordUtils.capitalize(business.getName()));
        user.setLastName("User");
        user.setClearTextPassword(password);

        user.addRole(userService.getRoleByRoleName(PermissionConstants.PERM_BUSINESS_OWNER));

        return this.userService.saveUser(user);

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

    public boolean isShowFinalSection() {
        return showFinalSection;
    }

    public void setShowFinalSection(boolean showFinalSection) {
        this.showFinalSection = showFinalSection;
    }

    public List<CompanySize> getCompanySizes() {
        return companySizes;
    }

    public void setCompanySizes(List<CompanySize> companySizes) {
        this.companySizes = companySizes;
    }
    
    

}
