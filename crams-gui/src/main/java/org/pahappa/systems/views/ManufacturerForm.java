package org.pahappa.systems.views;

import com.googlecode.genericdao.search.Search;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.core.services.ManufacturerService;
import org.pahappa.systems.core.services.SectorService;
import org.pahappa.systems.models.Certification;
import org.pahappa.systems.models.CompanySize;
import org.pahappa.systems.models.HumanResource;
import org.pahappa.systems.models.Manufacturer;
import org.pahappa.systems.models.ProductionChain;
import org.pahappa.systems.models.Sector;
import org.pahappa.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.Gender;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "manufacturerForm")
@SessionScoped
@ViewPath(path = HyperLinks.MANUFACTURER_FORM)
public class ManufacturerForm extends WebFormView<Manufacturer, ManufacturerForm, ManufacturersView> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ManufacturerService contactService;
    Search search= new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
    private SectorService sectorService;
    private List<Sector> sectors;
    private List<Gender> genders;
    
    @Override
    public void beanInit() {
        this.sectorService=ApplicationContextProvider.getBean(SectorService.class);
        this.contactService = ApplicationContextProvider.getBean(ManufacturerService.class);
    }
    
    @Override
    public void pageLoadInit() {
        // TODO Auto-generated method stub
        this.sectors=this.sectorService.getSectors(search, 0, 0);
        this.genders=Arrays.asList(Gender.values());
    }
    
    @Override
    public void persist() throws Exception {
        this.contactService.save(super.model);
    }
    
    public void addCompanySize() {
        super.model.addCompanySize(new CompanySize());
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
