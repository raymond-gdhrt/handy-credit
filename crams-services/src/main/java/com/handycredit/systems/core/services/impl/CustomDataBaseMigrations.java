package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.SystemSettingService;
import com.handycredit.systems.models.SystemSetting;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.SessionFactory;
import com.handycredit.systems.models.security.CRAMSPermissionInterpreter;
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
    public void savePermissions() {

        for (Permission permission : CRAMSPermissionInterpreter.reflectivelyGetPermissions()) {
            if (permissionDao.searchUniqueByPropertyEqual("name", permission.getName()) == null) {

                try {
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
            ApplicationContextProvider.getBean(SystemSettingService.class).saveInstance(setting);
        } catch (ValidationFailedException ex) {
            Logger.getLogger(CustomDataBaseMigrations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationFailedException ex) {
            Logger.getLogger(CustomDataBaseMigrations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
