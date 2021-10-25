package org.pahappa.systems.core.services.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.pahappa.systems.core.services.CompanyService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Company;
import org.pahappa.systems.models.security.PermissionConstants;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.model.security.User;
import org.sers.webutils.model.utils.SortField;
import org.sers.webutils.server.core.dao.CountryDao;
import org.sers.webutils.server.core.security.util.CustomSecurityUtil;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import org.pahappa.systems.constants.CompanyStatus;
import org.pahappa.systems.core.dao.CompanyDao;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDao companyDao;

    @Autowired
    UserService userService;

    @Autowired
    CustomApplicationSettings applicationSettings;
    @Autowired
    CountryDao countryDao;

    @Override
    public Company save(Company company) throws ValidationFailedException {
        if (StringUtils.isBlank(company.getName())) {
            throw new ValidationFailedException("Company missing name");
        }
        if (StringUtils.isBlank(company.getServiceCode())) {
            throw new ValidationFailedException("Company missing Service Code");
        }
        if (StringUtils.isBlank(company.getEndPointUrl())) {
            throw new ValidationFailedException("Company missing url");
        }
//        if (MailUtils.Util.getInstance().isValidEmail(company.getEmailAddress()) == false) {
//            throw new ValidationFailedException("Company email address is not valid");
//        }
        if (company.getCountry() == null) {
            throw new ValidationFailedException("Company missing country");
        }

        Company existingWithName = getCompanyByName(company.getName());
        if (existingWithName != null && !existingWithName.getId().equals(company.getId())) {
            throw new ValidationFailedException("A company with the same name already exists!");
        }

//        Company existingWithEmail = getCompanyByEmailAddress(company.getEmailAddress());
//        if (existingWithEmail != null && !existingWithEmail.getId().equals(company.getId())) {
//            throw new ValidationFailedException("A company with the same email already exists!");
//        }

  
        Company existingWithServiceCode = getCompanyByServiceCode(company.getServiceCode());
        if (existingWithServiceCode != null && !existingWithServiceCode.getId().equals(company.getId())) {
            throw new ValidationFailedException("Company with the same service code already exists!");
        }
        
       //Check whether the endpoint url is valid
        validateLinks(company.getEndPointUrl());
        
        
        Company savedCompany = companyDao.save(company);
//        if (savedCompany != null && existingWithServiceCode == null) {
//            createDefaultUser(savedCompany);
//           
//        }

        return savedCompany;
    }

//    private void createDefaultUser(Company company) throws ValidationFailedException {
//        User user = new User();
//        user.setUsername("admin@" + company.getServiceCode());
//        user.setFirstName(WordUtils.capitalize(company.getServiceCode()));
//        user.setLastName("User");
//        user.setClearTextPassword("company");
//        user.addRole(userService.getRoleByRoleName(Role.DEFAULT_WEB_ACCESS_ROLE));
//        user.addRole(userService.getRoleByRoleName(PermissionConstants.PERM_BUSINESS_ADMIN));
//        user.setCustomPropOne(company.getServiceCode());
//        this.userService.saveUser(user);
//    }

    private static String generateAccountNo(Company company) {
        long refNumber = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
        return Long.toString(refNumber);
    }

    @Override
    public List<Company> getCompanies(String searchTerm, Country country, SortField sortField, int offset, int limit) {
        Search search = CustomSearchUtils.genereateSearchObjectForCompanies(searchTerm, country, sortField);
        search.setFirstResult(offset);
        search.setMaxResults(limit);
        return companyDao.search(search);
    }

    @Override
    public int countCompanies(String searchTerm, Country country, SortField sortField) {
        return companyDao.count(CustomSearchUtils.genereateSearchObjectForCompanies(searchTerm, country, sortField));
    }

    @Override
    public Company getCompanyById(String companyId) {
        return companyDao.searchUniqueByPropertyEqual("id", companyId);
    }

   
    @Override
    public Company getCompanyByName(String name) {
        return companyDao.searchUniqueByPropertyEqual("name", name, RecordStatus.ACTIVE);
    }

    @Override
    public void delete(Company company) throws ValidationFailedException {
        company.setRecordStatus(RecordStatus.DELETED);
        companyDao.save(company);
    }
    
    @Override
    public void deactivate(Company company) throws ValidationFailedException {
        company.setCompanyStatus(CompanyStatus.INACTIVE);
        companyDao.save(company);
    }
    
    @Override
    public void activate(Company company) throws ValidationFailedException {
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        companyDao.save(company);
    }

   
    @Override
    public List<Company> getAllCompanies() {
        return companyDao.searchByRecordStatus(RecordStatus.ACTIVE);
    }

    @Override
    public Country getCountryByName(String countryName) {
        return countryDao.searchUniqueByPropertyEqual("name", countryName);
    }

    @Override
    public Company getCompanyByServiceCode(String serviceCode) {
        return companyDao.searchUniqueByPropertyEqual("serviceCode", serviceCode, RecordStatus.ACTIVE);
    }
    
    private void validateLinks(String url) throws ValidationFailedException {
        String[] schemes = {"http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
            if (StringUtils.isBlank(url)) {
                throw new ValidationFailedException("Missing url");
            }
            if (!urlValidator.isValid(url)) {
                throw new ValidationFailedException("Url doesnt exist for " + url);
            }
    }
}
