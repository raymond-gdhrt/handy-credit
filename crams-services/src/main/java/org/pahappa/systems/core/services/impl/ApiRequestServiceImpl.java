package org.pahappa.systems.core.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pahappa.systems.core.dao.ApiRequestDao;
import org.pahappa.systems.core.services.ApiRequestService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.ApiRequest;
import org.pahappa.systems.constants.EndPointCategory;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

@Service
@Transactional
public class ApiRequestServiceImpl implements ApiRequestService {

	@Autowired
	ApiRequestDao apiRequestDao;
	
	@Override
	public ApiRequest save(ApiRequest apiRequest) {		
		return apiRequestDao.save(apiRequest);
	}

	@Override
	public List<ApiRequest> getApiRequests(String searchTerm, EndPointCategory category, Date dateFrom, 
			Date dateTo, SortField sortField, int offset, int limit) {
		Search search = CustomSearchUtils.genereateSearchObjectForApiRequests(searchTerm, category, dateFrom, dateTo, sortField);
		search.setFirstResult(offset);
		search.setMaxResults(limit);
		return apiRequestDao.search(search);
	}
	

	@Override
	public int countApiRequest(String searchTerm, EndPointCategory category,Date dateFrom, Date dateTo,  SortField sortField) {
		return apiRequestDao.count(CustomSearchUtils.genereateSearchObjectForApiRequests(searchTerm, category, dateFrom, dateTo, sortField));
	}
	
	
	@Override
	public int countApiRequestByStatus(String searchTerm, boolean status) {
		return apiRequestDao.count(CustomSearchUtils.genereateSearchObjectForApiRequestsByStatus(searchTerm, status));
	}
	
	@Override
	public int countCompanyApiRequest(String searchTerm, String companyName, Date dateFrom, Date dateTo, SortField sortField) {
	
		return apiRequestDao.count(CustomSearchUtils.genereateSearchObjectForCompanyApiRequests(searchTerm, companyName, dateFrom, dateTo, sortField));
	}

	@Override
	public ApiRequest getApiRequestById(String businessAccountId) {
		return apiRequestDao.searchUniqueByPropertyEqual("id", businessAccountId);
	}

	@Override
	public void delete(ApiRequest businessAccount) throws ValidationFailedException {
		businessAccount.setRecordStatus(RecordStatus.DELETED);
		apiRequestDao.save(businessAccount);
	}       
        
}
