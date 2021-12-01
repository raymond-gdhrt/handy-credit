/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import com.handycredit.systems.constants.AccountStatus;
import com.handycredit.systems.constants.BusinessCategory;
import com.handycredit.systems.constants.CompanySize;
import com.handycredit.systems.constants.LoanProviderType;
import com.handycredit.systems.constants.UgandanDistrict;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

/**
 *
 * @author RayGdhrt
 */
@Entity
@Table(name = "businesses")
public class Business extends BaseEntity {

    private BusinessCategory type;
    private String name;
    private String description;
    private String registrationNumber;
    private byte[] registrationDocument;
    private UgandanDistrict district;
    private String emailAddress;
    private String phoneNumber;
    private Date dateOfOpening;
    private CompanySize size;
    private String website;
    private String twitterHandle;
    private String facebookLink;
    private String linkedInLink;
    private String physicalAddress;
    private AccountStatus accountStatus;
    private User userAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public BusinessCategory getType() {
        return type;
    }

    public void setType(BusinessCategory type) {
        this.type = type;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "registration_number", length = 100)
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Lob
    @Column(name = "registration_document", columnDefinition = "BLOB")
    public byte[] getRegistrationDocument() {
        return registrationDocument;
    }

    public void setRegistrationDocument(byte[] registrationDocument) {
        this.registrationDocument = registrationDocument;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "district")
    public UgandanDistrict getDistrict() {
        return district;
    }

    public void setDistrict(UgandanDistrict district) {
        this.district = district;
    }

    @Column(name = "email_address", length = 50)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name = "phone_number", length = 20)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "opening_date")
    public Date getDateOfOpening() {
        return dateOfOpening;
    }

    public void setDateOfOpening(Date dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "company_size")
    public CompanySize getSize() {
        return size;
    }

    public void setSize(CompanySize size) {
        this.size = size;
    }

    @Column(name = "website", length = 100)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "twitter_handle", length = 50)
    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    @Column(name = "facebook_link", length = 100)
    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    @Column(name = "linkedin_link", length = 100)
    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    @Column(name = "physical_address", length = 500)
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @OneToOne(optional = true)
    @JoinColumn(name = "user_account")
    public User getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

}
