package org.pahappa.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.pahappa.systems.constants.CompanyStatus;
import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.Country;

@Entity
@Table(name = "company")
@Inheritance(strategy = InheritanceType.JOINED)
public class Company extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;

   private CompanyStatus companyStatus = CompanyStatus.ACTIVE;

    private Country country;

    

    private String serviceCode;
    
    private String endPointUrl;

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


  
    @OneToOne
    @JoinColumn(name = "country")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

   

    @Column(name = "service_code", nullable = false,  length = 10)
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Column(name = "end_point_url", nullable = false)
    public String getEndPointUrl() {
        return endPointUrl;
    }

    public void setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
    }
    
    
    
    @Enumerated(EnumType.STRING)
	@Column(name = "company_status")
    public CompanyStatus getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(CompanyStatus companyStatus) {
		this.companyStatus = companyStatus;
	}

	@Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Company && (super.getId() != null) ? super.getId().equals(((Company) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }

}
