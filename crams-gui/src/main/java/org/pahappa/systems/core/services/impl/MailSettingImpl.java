package org.pahappa.systems.core.services.impl;

import org.apache.commons.lang.StringUtils;
import org.pahappa.systems.core.dao.MailSettingDao;
import org.pahappa.systems.core.services.MailSettingService;
import org.pahappa.systems.models.MailSetting;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.server.core.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailSettingImpl implements MailSettingService{
    
    @Autowired
    private MailSettingDao mailSettingDao;

	@Override
	public MailSetting save(MailSetting mailSetting) throws ValidationFailedException {
		if(StringUtils.isBlank(mailSetting.getSenderAddress()))
			throw new ValidationFailedException("Missing sender address");
		
		if(!MailUtils.Util.getInstance().isValidEmail(mailSetting.getSenderAddress()))
			throw new ValidationFailedException("Invalid sender address");
		
		if(StringUtils.isBlank(mailSetting.getSenderPassword()))
			throw new ValidationFailedException("Missing sender password");

		if(StringUtils.isBlank(mailSetting.getSenderSmtpHost()))
			throw new ValidationFailedException("Missing smtp host");
		
		if(StringUtils.isBlank(mailSetting.getSenderSmtpPort()))
			throw new ValidationFailedException("Missing smtp port");
		
		return mailSettingDao.save(mailSetting);
	}

	@Override
	public MailSetting getMailSetting() {
		if(mailSettingDao.findAll().size() > 0) {
			return mailSettingDao.findAll().get(0);
		}
		return null;
	}
	
}
