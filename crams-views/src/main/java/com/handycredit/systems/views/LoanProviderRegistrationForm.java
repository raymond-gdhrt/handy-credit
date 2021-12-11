package com.handycredit.systems.views;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.constants.LoanProviderType;
import com.handycredit.systems.constants.UgandanDistrict;
import com.handycredit.systems.core.services.LoanProviderService;
import com.handycredit.systems.core.utils.EMailClient;
import com.handycredit.systems.models.LoanProvider;
import com.handycredit.systems.security.HyperLinks;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang.WordUtils;
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
        UiUtils.showMessageBox("Thank you for regisering with CRAMS, check " + super.model.getEmailAddress() + " inbox for login credentials", "Registration successfull");

        createDefaultUser(super.model);
    }

    public void sendEmail(final User user) {
        final String mailContent = String.format(
                "Thank you for creating an account with CRAMS. "
                + "<br /><br /> Below are your login details. <br /> Username: %s <br /> Password: %s <br /> "
                + "<br /> Church code <br/> ",
                user.getUsername(), user.getClearTextPassword());

        final EMailClient mailService = new EMailClient();
        {

            new Thread(new Runnable() {
                public void run() {
                    mailService.sendHtmlEmail(user.getEmailAddress(), "Account creation on CRAMS", mailContent);

                }
            }).start();
        }

    }

    private void createDefaultUser(LoanProvider manufacturer) throws ValidationFailedException {
        User user = new User();
        user.setUsername(manufacturer.getEmailAddress());
        user.setFirstName(WordUtils.capitalize(manufacturer.getName()));
        user.setLastName("User");
        user.setClearTextPassword("loanProvider");
        List<Role> roles = userService.getRoles();
        for (Role role : roles) {
            if (!role.getName().equals(Role.DEFAULT_ADMIN_ROLE)) {
                user.addRole(role);
            }
        }

        this.userService.saveUser(user);
        sendEmail(user);

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
    
    

}
