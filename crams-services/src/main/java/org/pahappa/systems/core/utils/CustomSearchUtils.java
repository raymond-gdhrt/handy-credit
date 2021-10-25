package org.pahappa.systems.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.pahappa.systems.constants.EndPointCategory;
import org.sers.webutils.model.Country;
import org.sers.webutils.model.Gender;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.utils.SortField;

public class CustomSearchUtils {

    private static final int MINIMUM_CHARACTERS_FOR_SEARCH_TERM = 2;

    public static boolean searchTermSatisfiesQueryCriteria(String query) {
        if (StringUtils.isBlank(query)) {
            return false;
        }
        return query.length() >= MINIMUM_CHARACTERS_FOR_SEARCH_TERM;
    }

    private static Search generateSearchTerms(String query, List<String> searchFields) {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

        if (StringUtils.isNotBlank(query) && CustomSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
            ArrayList<Filter> filters = new ArrayList<Filter>();
            CustomSearchUtils.generateSearchTerms(searchFields, query, filters);
            search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
        }
        return search;
    }

    private static boolean generateSearchTerms(List<String> searchFields, String query, List<Filter> filters) {
        if (searchFields != null && !searchFields.isEmpty()) {
            for (String token : query.replaceAll("  ", " ").split(" ")) {
                String searchTerm = "%" + StringEscapeUtils.escapeSql(token) + "%";
                Filter[] orFilters = new Filter[searchFields.size()];
                int counter = 0;
                for (String searchField : searchFields) {
                    orFilters[counter] = Filter.like(searchField, searchTerm);
                    counter++;
                }
                filters.add(Filter.or(orFilters));
            }
            return true;
        }
        return false;
    }

    public static Search genereateSearchObjectForCompanies(String searchTerm, Country country, SortField sortField) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("name", "serviceCode", "emailAddress", "apiUsername"));

        if (country != null) {
            search.addFilterEqual("country", country);
        }

        if (sortField != null) {
            search.addSort(sortField.getSort());
        }

        return search;
    }

    public static Search genereateSearchObjectForCompanyAccounts(
            String searchTerm, Float balance, Float totalDeposits, Float totalWithdraws, SortField sortField) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("accountNo"));

        if (balance != null) {
            search.addFilterGreaterOrEqual("balance", balance);
        }

        if (totalDeposits != null) {
            search.addFilterGreaterOrEqual("totalDeposits", totalDeposits);
        }

        if (totalWithdraws != null) {
            search.addFilterGreaterOrEqual("totalwithdraws", totalWithdraws);
        }

        if (sortField != null) {
            search.addSort(sortField.getSort());
        }

        return search;
    }

   
	public static Search genereateSearchObjectForUsers(String query, Gender gender, Country country, SortField sortField) {
        Search search = generateSearchTerms(query, Arrays.asList("username", "firstName", "lastName", "emailAddress"));
        
        if (gender != null) {
            search.addFilterEqual("gender", gender);
        }
        
        if(country != null) {
        	search.addFilterEqual("country", country);
        }

        if (sortField != null) {
            search.addSort(sortField.getSort());
        }

        return search;
	}


	public static Search genereateSearchObjectForTransactionRanges(String searchTerm, SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("depositCharge", "withdrawCharge"));
		
		if(sortField != null) {
			search.addSort(sortField.getSort());
		}
		
		return search;
	}

	public static Search genereateSearchObjectForRanges(String searchTerm, SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("minimum", "maximum"));
		
		if(sortField != null) {
			search.addSort(sortField.getSort());
		}
		
		return search;
	}

	public static Search genereateSearchObjectForApiRequests(String searchTerm, EndPointCategory category, 
			Date dateFrom, Date dateTo, SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("endPoint", "requesterIPAddress", "postBody", "otherDataAsJSON", "username"));

        if (category != null) {
            search.addFilterEqual("endPointCategory", category);
        }
        
        if(dateFrom != null) {
        	search.addFilterGreaterOrEqual("dateCreated", dateFrom);
        }
        
        if(dateTo != null) {
        	search.addFilterLessOrEqual("dateCreated", dateTo);
        }

		if(sortField != null) {
			search.addSort(sortField.getSort());
		}
        
		return search;
	}
	
	
	
	public static Search genereateSearchObjectForCompanyApiRequests(String searchTerm, String name, 
			Date dateFrom, Date dateTo, SortField sortField) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("endPoint", "requesterIPAddress", "postBody", "otherDataAsJSON", "username"));

        if (name != null) {
            search.addFilterEqual("name", name);
        }
        
        if(dateFrom != null) {
        	search.addFilterGreaterOrEqual("dateCreated", dateFrom);
        }
        
        if(dateTo != null) {
        	search.addFilterLessOrEqual("dateCreated", dateTo);
        }

		if(sortField != null) {
			search.addSort(sortField.getSort());
		}
        
		return search;
	}
	
	public static Search genereateSearchObjectForApiRequestsByStatus(String searchTerm, boolean authSuccessful) {
		Search search = generateSearchTerms(searchTerm, Arrays.asList("endPoint"));

            search.addFilterEqual("authSuccessful", authSuccessful);
        
		return search;
	}
	
}
