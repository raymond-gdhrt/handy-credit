package com.handycredit.systems.views.users;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.handycredit.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

@ManagedBean(name = "profileView", eager = true)
@SessionScoped
@ViewPath(path = HyperLinks.PROFILE_VIEW)
public class ProfileView extends WebFormView<User, ProfileView, ProfileView> {

	private static final long serialVersionUID = 1L;

	public ProfileView() {
	}

	@Override
	public void persist() throws Exception {
		ApplicationContextProvider.getApplicationContext()
				.getBean(UserService.class).saveUser(super.getModel());
	}

	@Override
	public void resetModal() {
		super.model = SharedAppData.getLoggedInUser();
	}

	@Override
	@PostConstruct
	public void beanInit() {
		super.model = SharedAppData.getLoggedInUser();
	}

	@Override
	public void pageLoadInit() {

	}
}