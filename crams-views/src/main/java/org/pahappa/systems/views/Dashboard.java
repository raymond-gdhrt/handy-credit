package org.pahappa.systems.views;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.pahappa.systems.core.services.ApiRequestService;
import org.pahappa.systems.core.services.CompanyService;
import org.pahappa.systems.core.services.SystemSettingService;
import org.pahappa.systems.models.ApiRequest;
import org.pahappa.systems.models.Company;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.sers.webutils.client.controllers.WebAppExceptionHandler;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.core.utils.DateUtils;
import org.sers.webutils.server.shared.SharedAppData;

import antlr.collections.List;

@ManagedBean(name = "dashboard")
@ViewScoped
@ViewPath(path = HyperLinks.DASHBOARD)
public class Dashboard extends WebAppExceptionHandler implements Serializable {

    private static final long serialVersionUID = 1L;
    private String loggedinUser;
    private String logoutUrl = "ServiceLogout";
    @SuppressWarnings("unused")
    private String viewPath;
    private int totalCompanies;
    
    private int totalrequests;
    
    private int dailyRequests;
    
    private java.util.List<Company> companies = null;
   
    private LineChartModel lineModel2;
   

    @PostConstruct
    public void init() {
    	
       
        loggedinUser = SharedAppData.getLoggedInUser().getFirstName();
        this.totalCompanies = ApplicationContextProvider.getBean(CompanyService.class).countCompanies(null, null, null);
        
        this.totalrequests = ApplicationContextProvider.getBean(ApiRequestService.class).countApiRequest(null, null, DateUtils.getFirstDateOfThisMonth(), DateUtils.getLastDateOfThisMonth(), null);
        
        this.dailyRequests = ApplicationContextProvider.getBean(ApiRequestService.class).countApiRequest(null, null, DateUtils.getCurrentDateWithTime(00, 00, 00), DateUtils.getCurrentDateWithTime(23, 59, 59), null);
        
        //---displays chart data
        createLineModels();
        
        this.companies = ApplicationContextProvider.getBean(CompanyService.class).getCompanies(null, null, null, 0, 0);

    }


       
       private void createLineModels() {
    
           lineModel2 = initCategoryModel();
           lineModel2.setTitle("Company API Requests");
           lineModel2.setLegendPosition("e");
           lineModel2.setShowPointLabels(true);
           lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Companies"));
           
           Axis yAxis = lineModel2.getAxis(AxisType.Y);
           yAxis.setLabel("API Requests");
           yAxis.setMin(0);
           yAxis.setMax(200);
    
           
       }
       
       private LineChartModel initCategoryModel() {
           LineChartModel model = new LineChartModel();
    
           ChartSeries successfulRequests = new ChartSeries();
           successfulRequests.setLabel("API Requests");
         
           
           for(Company company:ApplicationContextProvider.getBean(CompanyService.class).getCompanies(null, null, null, 0, 0)) {
        	   successfulRequests.set(company.getServiceCode(), ApplicationContextProvider.getBean(ApiRequestService.class).countApiRequest(company.getName(), null, null, null, null));
        	  
           }
           model.addSeries(successfulRequests);
    
           return model;
       }
    

    
    
    /**
     * @return the loggedinUser
     */
    public String getLoggedinUser() {
        return loggedinUser;
    }

    /**
     * @param loggedinUser the loggedinUser to set
     */
    public void setLoggedinUser(String loggedinUser) {
        this.loggedinUser = loggedinUser;
    }

    /**
     * @return the logoutUrl
     */
    public String getLogoutUrl() {
        // super.invalidateSession();
        return logoutUrl;
    }

    /**
     * @param logoutUrl the logoutUrl to set
     */
    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    /**
     * @return the viewPath
     */
    public String getViewPath() {
        return Dashboard.class.getAnnotation(ViewPath.class).path();
    }

    /**
     * @param viewPath the viewPath to set
     */
    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public int getTotalCompanies() {
        return totalCompanies;
    }

    public void setTotalCompanies(int totalCompanies) {
        this.totalCompanies = totalCompanies;
    }
	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel2 = lineModel2;
	}

	public int getTotalrequests() {
		return totalrequests;
	}

	public void setTotalrequests(int totalrequests) {
		this.totalrequests = totalrequests;
	}

	public java.util.List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(java.util.List<Company> companies) {
		this.companies = companies;
	}

	public int getDailyRequests() {
		return dailyRequests;
	}
	public void setDailyRequests(int dailyRequests) {
		this.dailyRequests = dailyRequests;
	}
   
    
}
