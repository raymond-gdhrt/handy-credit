package org.pahappa.systems.views.users;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.pahappa.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.PaginatedTable;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.RoleService;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "rolesView")
@SessionScoped
@ViewPath(path = HyperLinks.ROLE_VIEW)
public class RolesView extends PaginatedTable<Role> {

	private static final long serialVersionUID = 1L;
	private RoleService roleService;

	@PostConstruct
	public void init() {
		roleService = ApplicationContextProvider.getApplicationContext()
				.getBean(RoleService.class);
		super.setMaximumresultsPerpage(10);
	}

	@Override
	public void reloadFromDB(int offset, int limit, Map<String, Object> filters)
			throws Exception {
		super.setDataModels(roleService.getRoles());
		super.setTotalRecords(roleService.getRoles().size());
	}

	@Override
	public List<ExcelReport> getExcelReportModels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}
}