package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.constants.AccountStatus;
import com.handycredit.systems.constants.LoanProviderType;
import com.handycredit.systems.constants.UgandanDistrict;
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.core.utils.AppUtils;
import com.handycredit.systems.core.utils.EmailService;
import com.handycredit.systems.models.LoanProvider;
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

@ManagedBean(name = "loanProviderRegistrationForm")
@ViewScoped
@ViewPath(path = HyperLinks.LOAN_PROVIDER_REGISTRATION_FORM)
public class LoanProviderRegistrationForm extends WebFormView<LoanProvider, LoanProviderRegistrationForm, LoanProviderRegistrationForm> {

    private static final long serialVersionUID = 1L;
    private LoanProviderService loanProviderService;
    private List<UgandanDistrict> districts;
    private List<LoanProviderType> loanProviderTypes;
    Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
    private UserService userService;
    private String customUiMessage;
    private String verificationCode, password;
    private boolean successResponse = true;
    private boolean showForm = true;

    private boolean showCodeSection, showSuccessMessageSection, showFinalSection;

    @Override
    public void beanInit() {
        super.model = new LoanProvider();
        this.userService = ApplicationContextProvider.getBean(UserService.class);
        this.loanProviderService = ApplicationContextProvider.getBean(LoanProviderService.class);
        this.districts = Arrays.asList(UgandanDistrict.values());
        this.loanProviderTypes = Arrays.asList(LoanProviderType.values());

    }

    @Override
    public void pageLoadInit() {
        // TODO Auto-generated method stub
        super.model = new LoanProvider();

    }

    @Override
    public void persist() throws Exception {
        this.loanProviderService.saveOutsideContext(super.model);
        super.model = new LoanProvider();
        resetModal();

        createDefaultUser(super.model);
    }

    public void createMember() {
        try {
            String code = AppUtils.generateOTP(6);
            System.out.println("Generated code: " + code);
            super.model.setLastVerificationCode(code);
            super.model.setAccountStatus(AccountStatus.created);
            super.model = this.loanProviderService.saveOutsideContext(super.model);

            new EmailService().sendMail(super.model.getEmailAddress(), "CRAMS registration", "Confirm your email address with this code\n <h2><b>" + code + "</b></h2>");
            showCodeForm();
        } catch (Exception ex) {
            customUiMessage = "Ops, some error occured\n " + ex.getLocalizedMessage();
            this.successResponse = false;
            Logger.getLogger(LoanProviderRegistrationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verifyCode() {
        try {
            if (verificationCode.equalsIgnoreCase(super.model.getLastVerificationCode())) {

                password = "loanProvider" + AppUtils.generateOTP(4);
                new EmailService().sendMail(super.model.getEmailAddress(), "CRAMS Login details", "Your CRAMs account was successfully created, Your login credentials are <h2>Username: <b>" + super.model.getEmailAddress() + "</b> </h2> <h2>Password: <b>" + password + "</b> </h2>");
                super.model.setAccountStatus(AccountStatus.verified);
                super.model.setUserAccount(createDefaultUser(super.model));
                this.loanProviderService.saveOutsideContext(super.model);
                super.model = new LoanProvider();
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
            Logger.getLogger(LoanProviderRegistrationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private User createDefaultUser(LoanProvider loanProvider) throws ValidationFailedException {
        System.out.println("Creating user account...");

        User user = new User();
        user.setUsername(loanProvider.getEmailAddress());
        user.setFirstName(WordUtils.capitalize(loanProvider.getName()));
        user.setLastName("User");
        user.setClearTextPassword(password);

        user.addRole(userService.getRoleByRoleName(PermissionConstants.PERM_LOAN_PROVIDER));

        return this.userService.saveUser(user);

    }

    public void showRegForm() {
        this.showForm = true;
        this.showCodeSection = false;
        this.showFinalSection = false;
        PrimeFaces.current().ajax().update("loanProviderRegistrationForm");

    }

    public void showCodeForm() {
        this.showForm = false;
        this.showCodeSection = true;
        this.showFinalSection = false;
        PrimeFaces.current().ajax().update("loanProviderRegistrationForm");

    }

    public void showFinalSection() {
        this.showForm = false;
        this.showCodeSection = false;
        this.showFinalSection = true;
        PrimeFaces.current().ajax().update("loanProviderRegistrationForm");

    }

    @Override
    public void resetModal() {
        super.resetModal();
        super.model = new LoanProvider();
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

    public List<LoanProviderType> getLoanProviderTypes() {
        return loanProviderTypes;
    }

    public void setLoanProviderTypes(List<LoanProviderType> loanProviderTypes) {
        this.loanProviderTypes = loanProviderTypes;
    }

    public boolean isShowFinalSection() {
        return showFinalSection;
    }

    public void setShowFinalSection(boolean showFinalSection) {
        this.showFinalSection = showFinalSection;
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
