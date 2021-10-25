package com.handycredit.systems.core.services.impl;

import org.sers.webutils.model.exception.ValidationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handycredit.systems.core.dao.SystemSettingDao;
import com.handycredit.systems.core.services.SystemSettingService;
import com.handycredit.systems.models.SystemSetting;

@Service
@Transactional
public class SystemSettingServiceImpl implements SystemSettingService {

    @Autowired
    SystemSettingDao settingsDao;

    @Override
    public SystemSetting save(SystemSetting settings) throws ValidationFailedException {
//    	if(settings.getPercentageCharge() > 0) {
//    		settings.setPercentageCharge(settings.getPercentageCharge() * 0.01f);
//    	}
        return  settingsDao.save(settings);
    }

    @Override
    public SystemSetting getSystemSettings() {
       
        if(settingsDao.findAll()==null||settingsDao.findAll().isEmpty()){
        	return null;
        }
        return settingsDao.findAll().get(0);
    }

   
}
