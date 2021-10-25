package com.handycredit.systems.views.users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.handycredit.systems.security.HyperLinks;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.client.views.presenters.WebFormView;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.Permission;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.PermissionService;
import org.sers.webutils.server.core.service.RoleService;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@ManagedBean(name = "rolesForm")
@SessionScoped
@ViewPath(path = HyperLinks.ROLE_FORM)
public class RolesForm extends WebFormView<Role, RolesForm, RolesView> {

	private static final long serialVersionUID = 1L;

	private RoleService roleService;
	private List<Permission> allPermissions;
	private List<String> selectedPermissions;
	private List<User> roleUsers;
	private List<User> allUsers;
	private List<User> selectedUsers;

	@Override
	public void beanInit() {
		this.roleService = ApplicationContextProvider.getApplicationContext().getBean(RoleService.class);
	}

	@Override
	public void pageLoadInit(){
		this.allPermissions = ApplicationContextProvider.getBean(PermissionService.class).getPermissions();
		try {
			this.allUsers = ApplicationContextProvider.getBean(UserService.class).getUsers();
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
		if(this.isEditing) {
			this.roleUsers = ApplicationContextProvider.getBean(UserService.class)
					.getUsersByRoleName(super.model.getName());
		}
	}

	@Override
	public void persist() throws Exception {
		Set<Permission> permissions = new HashSet<Permission>();
		for (String permission : this.getSelectedPermissions()) {
			Permission permissionObj = ApplicationContextProvider.getBean(PermissionService.class)
					.getPermissionByName(permission);
			permissions.add(permissionObj);
		}
		if (permissions.size() > 0) {
			super.model.setPermissions(permissions);
		}
		this.roleService.saveRole(super.model);
		this.getSelectedPermissions().clear();
	}

	@Override
	public void resetModal() {
		super.resetModal();
		super.model = new Role();
	}

	public void setFormProperties() {
		super.setFormProperties();
	}
	
	public void addUsers() throws Exception {
		List<User> users = null;
		if(super.isEditing()) {
			users = this.roleUsers;
			for (User user : this.getSelectedUsers()) {
				User userObj = ApplicationContextProvider.getBean(UserService.class)
						.getUserById(user.getId());
				userObj.addRole(super.model);
				ApplicationContextProvider.getBean(UserService.class).saveUser(userObj);
			}
			super.model.setUsers(new HashSet<User>(users));
			this.roleService.saveRole(super.model);
			this.getSelectedUsers().clear();
		}
	}
	
	public void addPermissions() throws Exception {
		Set<Permission> permissions = null;
		if(super.isEditing()) {
			permissions = super.model.getPermissions();			
			for (String permission : this.getSelectedPermissions()) {
				Permission permissionObj = ApplicationContextProvider.getBean(PermissionService.class)
						.getPermissionByName(permission);
				permissions.add(permissionObj);
			}
			super.model.setPermissions(permissions);
			this.roleService.saveRole(super.model);
			this.getSelectedPermissions().clear();
		}
	}

	public void removeUserFromRole(User user) throws Exception {
		super.model.removeUser(user);
		this.roleService.saveRole(super.model);
	}
	
	public void removePermissionFromRole(Permission permission) throws Exception {
		super.model.removePermission(permission);
		this.roleService.saveRole(super.model);
	}

	public List<Permission> getAllPermissions() {
		return allPermissions;
	}

	public void setAllPermissions(List<Permission> allPermissions) {
		this.allPermissions = allPermissions;
	}

	/**
	 * @return the selectedPermissions
	 */
	public List<String> getSelectedPermissions() {
		return selectedPermissions;
	}

	/**
	 * @param selectedPermissions the selectedPermissions to set
	 */
	public void setSelectedPermissions(List<String> selectedPermissions) {
		this.selectedPermissions = selectedPermissions;
	}

	/**
	 * @return the users
	 */
	public List<User> getAllUsers() {
		return allUsers;
	}

	/**
	 * @param users the users to set
	 */
	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	/**
	 * @return the selectedUsers
	 */
	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	/**
	 * @param selectedUsers the selectedUsers to set
	 */
	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	/**
	 * @return the roleUsers
	 */
	public List<User> getRoleUsers() {
		return roleUsers;
	}

	/**
	 * @param roleUsers the roleUsers to set
	 */
	public void setRoleUsers(List<User> roleUsers) {
		this.roleUsers = roleUsers;
	}
}