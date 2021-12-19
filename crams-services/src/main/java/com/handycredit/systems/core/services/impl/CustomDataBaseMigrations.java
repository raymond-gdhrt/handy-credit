package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.SystemSettingService;
import com.handycredit.systems.models.SystemSetting;
import java.util.Date;
import com.handycredit.systems.models.security.CRAMSPermissionInterpreter;
import com.handycredit.systems.models.security.PermissionConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.migrations.Migration;
import org.sers.webutils.model.security.Permission;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.server.core.dao.PermissionDao;
import org.sers.webutils.server.core.dao.RoleDao;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomDataBaseMigrations {

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    RoleDao roleDao;

    @Migration(orderNumber = 1)
    public void saveNewPermissions() {

        for (Permission permission : CRAMSPermissionInterpreter.reflectivelyGetPermissions()) {
            if (permissionDao.searchUniqueByPropertyEqual("name", permission.getName()) == null) {

                try {
                    System.out.println("saving permission");
                    permission.setRecordStatus(RecordStatus.ACTIVE);
                    permission.setDateCreated(new Date());
                    permission.setDateChanged(new Date());
                    permissionDao.mergeBG(permission);

                } catch (Exception exe) {
                    System.out.println("Permission already exists");
                }
            }
        }

    }

    @Migration(orderNumber = 2)
    public void saveDefaulSetting() {
        SystemSetting setting = new SystemSetting();
        try {
            ApplicationContextProvider.getBean(SystemSettingService.class
            ).saveInstance(setting);
        } catch (ValidationFailedException ex) {
            Logger.getLogger(CustomDataBaseMigrations.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (OperationFailedException ex) {
            Logger.getLogger(CustomDataBaseMigrations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Migration(orderNumber = 3)
    public void saveNewUpdatedRoles() {
        UserService userService=ApplicationContextProvider.getBean(UserService.class);
        try {
            System.out.println("Saving biz role...");
            Role businessRole = new Role();
            businessRole.setName(PermissionConstants.PERM_BUSINESS_OWNER);
            businessRole.setDescription(PermissionConstants.PERM_BUSINESS_OWNER);
            businessRole.setRecordStatus(RecordStatus.ACTIVE);
            //businessRole.addPermission(userService.getPermissionByName(PermissionConstants.PERM_BUSINESS_OWNER));
           // businessRole.addPermission(userService.getPermissionByName(org.sers.webutils.model.security.PermissionConstants.PERM_WEB_ACCESS));
            businessRole.setDateCreated(new Date());
            businessRole.setDateChanged(new Date());
            roleDao.mergeBG(businessRole);

            System.out.println("Saving loan provider role..");
            Role loanProviderRole = new Role();
            loanProviderRole.setName(PermissionConstants.PERM_LOAN_PROVIDER);
            loanProviderRole.setDescription(PermissionConstants.PERM_LOAN_PROVIDER);
            loanProviderRole.setRecordStatus(RecordStatus.ACTIVE);
           // loanProviderRole.addPermission(userService.getPermissionByName(PermissionConstants.PERM_LOAN_PROVIDER));
           // loanProviderRole.addPermission(userService.getPermissionByName(org.sers.webutils.model.security.PermissionConstants.PERM_WEB_ACCESS));
            loanProviderRole.setDateCreated(new Date());
            loanProviderRole.setDateChanged(new Date());
            roleDao.mergeBG(loanProviderRole);

        } catch (Exception exe) {
            System.out.println("Role already exists ");
            exe.printStackTrace();
        }
    }

}
