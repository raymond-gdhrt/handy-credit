/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.services.impl;

import com.handycredit.systems.core.services.SystemSettingService;
import com.handycredit.systems.models.SystemSetting;
import java.util.List;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RayGdhrt
 */
@Service
@Transactional
public class SystemSettingServiceImpl extends GenericServiceImpl<SystemSetting> implements SystemSettingService {

    @Override
    public SystemSetting getSystemSettings() {

        List<SystemSetting> systemSettings = super.findAll();
        if (systemSettings.isEmpty()) {
            return super.save(new SystemSetting());
        }
        return super.findAll().get(0);
    }

    @Override
    public boolean isDeletable(SystemSetting entity) throws OperationFailedException {
        return false;
    }

    @Override
    public SystemSetting saveInstance(SystemSetting instance) throws ValidationFailedException, OperationFailedException {
        return super.save(instance);

    }
}
