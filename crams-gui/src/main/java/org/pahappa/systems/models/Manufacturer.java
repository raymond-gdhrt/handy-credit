package org.pahappa.systems.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "manufacturers")
@Inheritance(strategy = InheritanceType.JOINED)
public class Manufacturer extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String companyName;
    private Sector sector;
    private String phoneNumber;
    private String emailAddress;
    private String location;
    private String postalAddress;
    private String registrationNumber;
    private Date registrationDate = new Date();
    private String contactPersonName;
    private String contactPersonPhoneNumber;
    private String contactPersonEmailAddress;
    private long averageNumberOfEmployees;
    private float averageTurnover;
    private Set<CompanySize> companySizes;
    private Set<ProductionChain> productionChains;
    private Set<HumanResource> humanResources;
    private Set<Certification> certifications;

    @Column(name = "company_name", unique = true)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

     @Column(name = "postal_address")
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
    

    @ManyToOne
    @JoinColumn(name = "sector")
    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "contact_person_name")
    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    @Column(name = "contact_person_phone_number")
    public String getContactPersonPhoneNumber() {
        return contactPersonPhoneNumber;
    }

    public void setContactPersonPhoneNumber(String contactPersonPhoneNumber) {
        this.contactPersonPhoneNumber = contactPersonPhoneNumber;
    }

    @Column(name = "contact_person_email")
    public String getContactPersonEmailAddress() {
        return contactPersonEmailAddress;
    }

    public void setContactPersonEmailAddress(String contactPersonEmailAddress) {
        this.contactPersonEmailAddress = contactPersonEmailAddress;
    }

    @Column(name = "average_number_of_employees")
    public long getAverageNumberOfEmployees() {
        return averageNumberOfEmployees;
    }

    public void setAverageNumberOfEmployees(long averageNumberOfEmployees) {
        this.averageNumberOfEmployees = averageNumberOfEmployees;
    }

    @Column(name = "average_turn_over")
    public float getAverageTurnover() {
        return averageTurnover;
    }

    public void setAverageTurnover(float averageTurnover) {
        this.averageTurnover = averageTurnover;
    }

    @Column(name = "registration_number")
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date")
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "manufacturer_company_sizes", joinColumns = @JoinColumn(name = "manufacturer_id"), inverseJoinColumns = @JoinColumn(name = "company_size_id"))
    public Set<CompanySize> getCompanySizes() {
        return companySizes;
    }

    public void setCompanySizes(Set<CompanySize> companySizes) {
        this.companySizes = companySizes;
    }
    
    public void addCompanySize(CompanySize companySize) {
        if (this.companySizes == null) {
            this.companySizes = new HashSet<CompanySize>();
        }

        this.companySizes.add(companySize);
    }

    public void removeCompanySize(CompanySize companySize) {

        this.companySizes.remove(companySize);

    }


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "manufacturer_production_chain", joinColumns = @JoinColumn(name = "manufacturer_id"), inverseJoinColumns = @JoinColumn(name = "production_chain_id"))

    public Set<ProductionChain> getProductionChains() {
        return productionChains;
    }

    public void setProductionChains(Set<ProductionChain> productionChains) {
        this.productionChains = productionChains;
    }
    
    public void addProductionChain(ProductionChain companySize) {
        if (this.companySizes == null) {
            this.productionChains = new HashSet<ProductionChain>();
        }

        this.productionChains.add(companySize);
    }

    public void removeProductionChain(ProductionChain companySize) {

        this.productionChains.remove(companySize);

    }


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "manufacturer_human_resources", joinColumns = @JoinColumn(name = "manufacturer_id"), inverseJoinColumns = @JoinColumn(name = "human_resource_id"))
    public Set<HumanResource> getHumanResources() {
        return humanResources;
    }

    public void setHumanResources(Set<HumanResource> humanResources) {
        this.humanResources = humanResources;
    }
    
    public void addHumanResource(HumanResource companySize) {
        if (this.companySizes == null) {
            this.humanResources = new HashSet<HumanResource>();
        }

        this.humanResources.add(companySize);
    }

    public void removeHumanResource(HumanResource companySize) {

        this.humanResources.remove(companySize);

    }


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "manufacturer_certifications", joinColumns = @JoinColumn(name = "manufacturer_id"), inverseJoinColumns = @JoinColumn(name = "certification_id"))
    public Set<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<Certification> certifications) {
        this.certifications = certifications;
    }
    
    public void addCertification(Certification companySize) {
        if (this.companySizes == null) {
            this.certifications = new HashSet<Certification>();
        }

        this.certifications.add(companySize);
    }

    public void removeCertification(Certification companySize) {

        this.certifications.remove(companySize);

    }


    @Override
    public boolean equals(Object object) {
        return object instanceof Manufacturer && (super.getId() != null) ? super.getId().equals(((Manufacturer) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }
}
