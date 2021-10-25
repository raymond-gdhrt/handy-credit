package org.pahappa.systems.views;

import com.googlecode.genericdao.search.Search;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.WordUtils;
import org.pahappa.systems.core.services.MailSettingService;

import org.pahappa.systems.core.services.ManufacturerService;
import org.pahappa.systems.core.services.SectorService;
import org.pahappa.systems.core.utils.CustomAppUtils;
import org.pahappa.systems.core.utils.HtmlEmailTemplates;
import org.pahappa.systems.core.utils.MailCustomService;
import org.pahappa.systems.models.Certification;
import org.pahappa.systems.models.CompanySize;
import org.pahappa.systems.models.HumanResource;
import org.pahappa.systems.models.MailSetting;
import org.pahappa.systems.models.Manufacturer;
import org.pahappa.systems.models.ProductionChain;
import org.pahappa.systems.models.Sector;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.context.RequestContext;
import org.sers.webutils.client.utils.UiUtils;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.Gender;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "externalManufacturerForm")
@ViewScoped
@ViewPath(path = HyperLinks.EXTERNAL_MANUFACTURER_FORM)
public class ExternalManufacturerForm extends WebFormView<Manufacturer, ExternalManufacturerForm, ExternalManufacturerForm> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ManufacturerService contactService;
    Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
    private SectorService sectorService;
    private UserService userService;
    private List<Sector> sectors;
    private List<Gender> genders;

    @Override
    public void beanInit() {
        super.model = new Manufacturer();
        this.sectorService = ApplicationContextProvider.getBean(SectorService.class);
        this.userService = ApplicationContextProvider.getBean(UserService.class);
        this.contactService = ApplicationContextProvider.getBean(ManufacturerService.class);

    }

    @Override
    public void pageLoadInit() {
        // TODO Auto-generated method stub
        super.model = new Manufacturer();

        this.sectors = this.sectorService.getSectors(search, 0, 0);
        this.genders = Arrays.asList(Gender.values());
    }

    @Override
    public void persist() throws Exception {
        this.contactService.saveOutsideContext(super.model);
        super.model = new Manufacturer();
        resetModal();
        UiUtils.showMessageBox("Thank you for regisering with UMA, check " + super.model.getEmailAddress() + " inbox for login credentials", "Registration successfull", RequestContext.getCurrentInstance());

        // createDefaultUser(super.model);
    }

    public void sendEmail(User user) {
        String mailContent = String.format(
                "Thank you for creating an account with UMA. "
                + "<br /><br /> Below are your login details. <br /> Username: %s <br /> Password: %s <br /> "
                + "<br /> Church code <br/> ",
                user.getUsername(), user.getClearTextPassword());
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        String loginUrl = CustomAppUtils.getURLWithContextPath(origRequest) + "/ServiceLogin";
        System.out.println("HttpServelet request " + origRequest);
        System.out.println("Login Url " + loginUrl);

        String mailInfo = HtmlEmailTemplates.registrationTemplate(user.getFirstName(), mailContent, "Go to Login",
                loginUrl);
        final MailCustomService mailService = new MailCustomService();
        {
            MailSetting ms = ApplicationContextProvider.getBean(MailSettingService.class).getMailSetting();
            mailService.setSenderEmail(ms.getSenderAddress());
            mailService.setSenderPassword(ms.getSenderPassword());
            mailService.setSmtpHost(ms.getSenderSmtpHost());
            mailService.setSmtpPort(ms.getSenderSmtpPort());
            mailService.setParameters("UMA Application", mailInfo, user.getUsername());
            new Thread(new Runnable() {
                public void run() {
                    mailService.sendHtmlEmail();
                }
            }).start();
        }

    }

    private void createDefaultUser(Manufacturer manufacturer) throws ValidationFailedException {
        User user = new User();
        user.setUsername(manufacturer.getEmailAddress());
        user.setFirstName(WordUtils.capitalize(manufacturer.getCompanyName()));
        user.setLastName("User");
        user.setClearTextPassword("business");
        List<Role> roles = userService.getRoles();
        for (Role role : roles) {
            if (!role.getName().equals(Role.DEFAULT_ADMIN_ROLE)) {
                user.addRole(role);
            }
        }

        this.userService.saveUser(user);
        sendEmail(user);

    }

    public void addProductionChain() {
        super.model.addProductionChain(new ProductionChain());
    }

    public void addHumanResource() {
        super.model.addHumanResource(new HumanResource());
    }

    public void addCertification() {
        super.model.addCertification(new Certification());
    }

    public void resetModal() {
        super.resetModal();
        super.model = new Manufacturer();
    }

    public void setFormProperties() {
        super.setFormProperties();
    }

    @Override
    public String getViewUrl() {
        // TODO Auto-generated method stub
        return null;
    }

//    public void handleFileUpload(FileUploadEvent event) throws IOException {
//    	try {
//			excelUploads.uploadExcelFile(event);
//			if(excelUploads.getUploadedDatasets().size() > 0) {
//				for(Manufacturer contact: excelUploads.getUploadedDatasets()) {
//					if(CustomAppUtils.validatePhoneNumber(contact.getPhoneNumber()) != null) {
//	                    contact.setPhoneNumber(CustomAppUtils.validatePhoneNumber(contact.getPhoneNumber()));
//						if(this.contactService.getManufacturerByPhoneNumber(contact.getPhoneNumber()) == null) {
//							this.contactService.save(contact);
//						}
//					}
//				}
//			}
//			super.redirectTo(HyperLinks.CONTACT_VIEW);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    public List<Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<Gender> genders) {
        this.genders = genders;
    }

}
