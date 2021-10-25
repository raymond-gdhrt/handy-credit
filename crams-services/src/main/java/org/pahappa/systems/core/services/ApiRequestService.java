/**
 *
 */
package org.pahappa.systems.core.services;

import java.util.Date;
import java.util.List;

import org.pahappa.systems.models.ApiRequest;
import org.pahappa.systems.constants.EndPointCategory;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;

/**
 * Responsible for CRUD operations on {@link ApiRequest}
 *
 * @author mmc
 *
 */
public interface ApiRequestService {

    /**
     * Gets a apiRequest log that matches the specified identifier
     *
     * @param apiRequestId
     * @return
     */
    ApiRequest getApiRequestById(String apiRequestId);

    /**
     * Deactivates an apiRequest log along with all he data associated to it.
     * This apiRequest log will never be accessible on the UI
     *
     * @param apiRequest
     * @throws ValidationFailedException
     */
    void delete(ApiRequest apiRequest) throws ValidationFailedException;

    /**
     * Adds a apiRequest log to the database.
     *
     * @param apiRequest
     * @throws ValidationFailedException if the following attributes are blank:
     * accountNo
     */
    ApiRequest save(ApiRequest apiRequest);

    /**
     * Gets a list of apiRequests that match the specified search criteria
     *
     * @param searchTerm
     * @param category
     * @param dateFrom
     * @param dateTo
     * @param sortField
     * @param offset
     * @param limit
     * @return
     */
    List<ApiRequest> getApiRequests(String searchTerm, EndPointCategory category, Date dateFrom, 
			Date dateTo, SortField sortField, int offset, int limit);

    /**
     * Counts a list of apiRequest that match the specified search criteria
     *
     * @param searchTerm
     * @param category
     * @param dateFrom
     * @param dateTo
     * @param sortField
     * @return
     */
    int countApiRequest(String searchTerm, EndPointCategory category, Date dateFrom, 
			Date dateTo, SortField sortField);
    
    
    int countApiRequestByStatus(String searchTerm, boolean status);
     
    
    int countCompanyApiRequest(String searchTerm, String companyName, Date dateFrom, 
			Date dateTo, SortField sortField);
}
