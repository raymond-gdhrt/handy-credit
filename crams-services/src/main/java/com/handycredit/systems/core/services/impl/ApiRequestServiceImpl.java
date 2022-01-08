package com.handycredit.systems.core.services.impl;

import com.googlecode.genericdao.search.Filter;
import java.util.List;

import com.handycredit.systems.core.services.ApiRequestService;
import com.handycredit.systems.models.ApiRequest;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.core.utils.GeneralSearchUtils;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.utils.SearchField;

@Service
@Transactional
public class ApiRequestServiceImpl extends GenericServiceImpl<ApiRequest> implements ApiRequestService  {

	


    @Override
    public boolean isDeletable(ApiRequest entity) throws OperationFailedException {
       return true;
    }

    @Override
    public ApiRequest saveInstance(ApiRequest instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);
    
    }
    
    /**
	 * 
	 * @param searchFields
	 * @param query
	 * @param sortField
	 * @return
	 */
	public static final Search composeDBSearch(List<SearchField> searchFields, String query, SortField sortField) {
		if (query == null)
			query = "";

		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		if (sortField != null)
			search.addSort(sortField.getSort());

		if (StringUtils.isNotBlank(query) && GeneralSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
			ArrayList<Filter> filters = new ArrayList<Filter>();
			                 GeneralSearchUtils.generateSearchTerms(searchFields, query, filters);
			search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
		}
		return search;
	}
        
}
