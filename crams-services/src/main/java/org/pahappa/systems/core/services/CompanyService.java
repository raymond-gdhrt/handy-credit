/**
 * 
 */
package org.pahappa.systems.core.services;

import java.util.List;

import org.pahappa.systems.models.Company;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;

/**
 * Responsible for CRUD operations on {@link Company}
 * 
 * @author mmc
 *
 */
public interface CompanyService {
	/**
	 * Adds a business to the database.
	 * 
	 * @param business
	 * @throws ValidationFailedException if the following attributes are blank:
	 *                                   name, acronym, emailAddress, country
	 */
	Company save(Company business) throws ValidationFailedException;

	/**
	 * Gets a list of businesses that match the specified search criteria
	 * 
	 * @param searchTerm
	 * @param country
	 * @param sortField
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Company> getCompanies(String searchTerm, Country country, SortField sortField, int offset, int limit);

	/**
	 * Counts a list of business that match the specified search criteria
	 * 
	 * @param searchTerm
	 * @param country
	 * @param sortField
	 * @return
	 */
	int countCompanies(String searchTerm, Country country, SortField sortField);

	/**
	 * Gets a business that matches the specified identifier
	 * 
	 * @param businessId
	 * @return
	 */
	Company getCompanyById(String businessIid);

        
        
        /**
	 * Gets a business that matches the specified USSD service code
	 * 
	 * @param acronym
	 * @return
	 */
	Company getCompanyByServiceCode(String serviceCode);

	/**
	 * Deactivates a business along with all he data associated to it. This Company
	 * will never be accessible on the UI
	 * 
	 * @param business
	 * @throws ValidationFailedException
	 */
	void delete(Company business) throws ValidationFailedException;
	
	
	/**
	 * Sets the company status to INACTIVE. This Company
	 * can be accesses on the UI but its state will always be inactive
	 * 
	 * @param business
	 * @throws ValidationFailedException
	 */
	void activate(Company business) throws ValidationFailedException;
	
	/**
	 * Sets the company status to ACTIVE. This Company
	 * can be accesses on the UI but its state will always be ACTIVE
	 * 
	 * @param business
	 * @throws ValidationFailedException
	 */
	void deactivate(Company business) throws ValidationFailedException;

	
	/**
	 * Gets a business that matches the specified name
	 * 
	 * @param name
	 * @return
	 */
	Company getCompanyByName(String name);

	/**
	 * Gets a list of businesses
	 * @return
	 */
	List<Company> getAllCompanies();

	/**
	 * Get country by name
	 * 
	 * @param countryName
	 * @return
	 */
	Country getCountryByName(String countryName);
}
