package org.pahappa.systems.views;

import com.googlecode.genericdao.search.Search;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.pahappa.systems.core.services.AppSettingService;
import org.pahappa.systems.core.services.ManufacturerService;
import org.pahappa.systems.models.AppSetting;
import org.pahappa.systems.models.Sector;
import org.pahappa.systems.security.HyperLinks;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.sers.webutils.client.controllers.WebAppExceptionHandler;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.LegendPlacement;
import org.pahappa.systems.core.services.SectorService;
import org.pahappa.systems.models.Manufacturer;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.sers.webutils.model.RecordStatus;

@ManagedBean
@ViewScoped
@ViewPath(path = HyperLinks.DASHBOARD)
public class Dashboard extends WebAppExceptionHandler implements Serializable {

    private static final long serialVersionUID = 1L;
    private String loggedinUser;
    private String logoutUrl = "ServiceLogout";
    @SuppressWarnings("unused")
    private String viewPath;
    private int manufacturers;
    private int packages;
    private int totalInboxMessages;
    private int totalOutBoxMessages;
    private int totalOutBoxBatches;
    private int totalSubscribers;
    private List<Sector> sectors;
    private int sectorsCount;
    private AppSetting appSetting;
    private LineChartModel dateModel;

    private PieChartModel pieModel1;

    private float yoPaymentbalance, egosmsBalance;

    private BarChartModel sectorsBarModel;

    private BarChartModel churchManufacturerBarModel;
    Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);

    @PostConstruct
    public void init() {

        this.appSetting = ApplicationContextProvider.getBean(AppSettingService.class).getAppSetting();

        this.manufacturers = ApplicationContextProvider.getBean(ManufacturerService.class).countManufacturers(search);
        this.sectorsCount = ApplicationContextProvider.getBean(SectorService.class).countSectors(search);
        this.sectors = ApplicationContextProvider.getBean(SectorService.class).getSectors(search, 0, 0);

        createSectorsBarModel();
        createDateModel();
        createPieModel1();

    }

    public void showReminder(String widgetId, String header, String message) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(widgetId, new FacesMessage(FacesMessage.SEVERITY_WARN, header, message));
    }

    private void createDateModel() {
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Daily Devotionals");

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Single/Pdf Books");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        //search.addFilterGreaterOrEqual("dateCreated", DateUtils.getFirstDateForMonth(DateUtils.currentMonth()));
        // search.addFilterLessOrEqual("dateCreated", DateUtils.getLastDateForMonth(DateUtils.currentMonth()));

        series1.set("2020-01-01", 52000);
        series1.set("2020-02-01", 46700);
        series1.set("2020-03-01", 783000);
        series1.set("2020-04-01", 567000);
        series1.set("2020-05-01", 713000);
        series1.set("2020-06-01", 893000);
        series1.set("2020-07-01", 670000);

        series2.set("2020-01-01", 356000);
        series2.set("2020-02-01", 289000);
        series2.set("2020-03-01", 367000);
        series2.set("2020-04-01", 463000);
        series2.set("2020-05-01", 213000);
        series2.set("2020-06-01", 287000);
        series2.set("2020-07-01", 316000);

        dateModel.addSeries(series1);
        dateModel.addSeries(series2);

      
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Revenue");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        //axis.setMax("2014-02-01");
        axis.setTickFormat("%b %#d, %y");

        dateModel.getAxes().put(AxisType.X, axis);
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        Search manufacturerSearch = new Search();
        //search.addFilterGreaterOrEqual("dateCreated", DateUtils.getFirstDateForMonth(DateUtils.currentMonth()));
        // search.addFilterLessOrEqual("dateCreated", DateUtils.getLastDateForMonth(DateUtils.currentMonth()));

        for (Sector sector : ApplicationContextProvider.getBean(SectorService.class).getSectors(search, 0, 0)) {
            manufacturerSearch = new Search();
            manufacturerSearch.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

            manufacturerSearch.addFilterEqual("sector", sector);

            int count = ApplicationContextProvider.getBean(ManufacturerService.class).countManufacturers(manufacturerSearch);

            pieModel1.set(sector.getName(), count);

        }

        
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }

    private BarChartModel initSectorsBarModel() {
        Search manufacturerSearch = new Search();

        BarChartModel model = new BarChartModel();

        for (Sector sector : ApplicationContextProvider.getBean(SectorService.class).getSectors(search, 0, 0)) {
            manufacturerSearch = new Search();
            manufacturerSearch.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

            manufacturerSearch.addFilterEqual("sector", sector);
            int count = ApplicationContextProvider.getBean(ManufacturerService.class).countManufacturers(manufacturerSearch);

            ChartSeries groups = new ChartSeries();
            groups.setLabel(sector.getName());
            groups.set(sector.getName(), count);
            model.addSeries(groups);
        }

        return model;
    }

    private void createSectorsBarModel() {
        sectorsBarModel = initSectorsBarModel();

      
        sectorsBarModel.setLegendPosition("e");
        sectorsBarModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);

        Axis xAxis = sectorsBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Sector");

        Axis yAxis = sectorsBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Manufacturers");
        yAxis.setMin(0);
        yAxis.setMax(manufacturers);

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

    /**
     * @return the manufacturers
     */
    public int getManufacturers() {
        return manufacturers;
    }

    /**
     * @param manufacturers the manufacturers to set
     */
    public void setManufacturers(int manufacturers) {
        this.manufacturers = manufacturers;
    }

    /**
     * @return the packages
     */
    public int getPackages() {
        return packages;
    }

    /**
     * @param packages the packages to set
     */
    public void setPackages(int packages) {
        this.packages = packages;
    }

    /**
     * @return the sectors
     */
    public List<Sector> getSectors() {
        return sectors;
    }

    /**
     * @param sectors the sectors to set
     */
    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    /**
     * @return the sectorsCount
     */
    public int getSectorsCount() {
        return sectorsCount;
    }

    /**
     * @param sectorsCount the sectorsCount to set
     */
    public void setSectorsCount(int sectorsCount) {
        this.sectorsCount = sectorsCount;
    }

    public int getTotalInboxMessages() {
        return totalInboxMessages;
    }

    public void setTotalInboxMessages(int totalInboxMessages) {
        this.totalInboxMessages = totalInboxMessages;
    }

    public int getTotalOutBoxMessages() {
        return totalOutBoxMessages;
    }

    public void setTotalOutBoxMessages(int totalOutBoxMessages) {
        this.totalOutBoxMessages = totalOutBoxMessages;
    }

    public int getTotalOutBoxBatches() {
        return totalOutBoxBatches;
    }

    public void setTotalOutBoxBatches(int totalOutBoxBatches) {
        this.totalOutBoxBatches = totalOutBoxBatches;
    }

    /**
     * @return the churchManufacturerBarModel
     */
    public BarChartModel getChurchManufacturerBarModel() {
        return churchManufacturerBarModel;
    }

    /**
     * @param churchManufacturerBarModel the churchManufacturerBarModel to set
     */
    public void setChurchManufacturerBarModel(BarChartModel churchManufacturerBarModel) {
        this.churchManufacturerBarModel = churchManufacturerBarModel;
    }

    /**
     * @return the packageSubscriptionBarModel
     */
    public int getTotalSubscribers() {
        return totalSubscribers;
    }

    public void setTotalSubscribers(int totalSubscribers) {
        this.totalSubscribers = totalSubscribers;
    }

    /**
     * @return the egosmsBalance
     */
    public float getEgosmsBalance() {
        return egosmsBalance;
    }

    /**
     * @param egosmsBalance the egosmsBalance to set
     */
    public void setEgosmsBalance(float egosmsBalance) {
        this.egosmsBalance = egosmsBalance;
    }

    public AppSetting getAppSetting() {
        return appSetting;
    }

    public void setAppSetting(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    public float getYoPaymentbalance() {
        return yoPaymentbalance;
    }

    public void setYoPaymentbalance(float yoPaymentbalance) {
        this.yoPaymentbalance = yoPaymentbalance;
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public void setDateModel(LineChartModel dateModel) {
        this.dateModel = dateModel;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public BarChartModel getSectorsBarModel() {
        return sectorsBarModel;
    }

    public void setSectorsBarModel(BarChartModel sectorsBarModel) {
        this.sectorsBarModel = sectorsBarModel;
    }

}
