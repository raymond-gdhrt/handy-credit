package org.pahappa.systems.core.services.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pahappa.systems.core.services.ManufacturerService;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Manufacturer;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.core.utils.CustomAppUtils;
import org.pahappa.systems.core.dao.ManufacturerDao;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService{
    
    @Autowired
    private ManufacturerDao manufacturerDao;

	@Override
	public Manufacturer save(Manufacturer manufacturer) throws ValidationFailedException {
				
		if (StringUtils.isBlank(manufacturer.getPhoneNumber()))
			throw new ValidationFailedException("Phonenumber should not be empty");

        String validatedPhoneNumber = CustomAppUtils.validatePhoneNumber(manufacturer.getPhoneNumber());

		if(validatedPhoneNumber == null)
			throw new ValidationFailedException("Invalid Phone number");
        manufacturer.setPhoneNumber(validatedPhoneNumber);
		
		Manufacturer existingWithPhone = getManufacturerByPhoneNumber(manufacturer.getPhoneNumber());
		
		if(existingWithPhone != null && !existingWithPhone.getId().equals(manufacturer.getId()))
			throw new ValidationFailedException("A manufacturer with the same phone number already exists!");
				
		return manufacturerDao.merge(manufacturer);
	}
        
        
        
        @Override
	public Manufacturer saveOutsideContext(Manufacturer manufacturer) throws ValidationFailedException {
				
		if (StringUtils.isBlank(manufacturer.getPhoneNumber()))
			throw new ValidationFailedException("Phonenumber should not be empty");

        String validatedPhoneNumber = CustomAppUtils.validatePhoneNumber(manufacturer.getPhoneNumber());

		if(validatedPhoneNumber == null)
			throw new ValidationFailedException("Invalid Phone number");
        manufacturer.setPhoneNumber(validatedPhoneNumber);
		
		Manufacturer existingWithPhone = getManufacturerByPhoneNumber(manufacturer.getPhoneNumber());
		
		if(existingWithPhone != null && !existingWithPhone.getId().equals(manufacturer.getId()))
			throw new ValidationFailedException("A manufacturer with the same phone number already exists!");
				
		return manufacturerDao.mergeBG(manufacturer);
	}
	
	@Override
	public Manufacturer getManufacturerByPhoneNumber(String phoneNumber) {
		Search search = new Search();
        search.addFilterEqual("phoneNumber", phoneNumber);
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        List<Manufacturer> manufacturers = manufacturerDao.search(search);
		if(manufacturers.isEmpty()) {
			return null;
		}
		return manufacturers.get(0);
	}

	@Override
	public List<Manufacturer> getManufacturers(Search search, int offset, int limit) {
		search.setFirstResult(offset);
		search.setMaxResults(limit);
		return manufacturerDao.search(search);
	}

	@Override
	public int countManufacturers(Search search) {
		return manufacturerDao.count(search);
	}

	@Override
	public Manufacturer getManufacturerById(String manufacturerId) {
		return manufacturerDao.searchUniqueByPropertyEqual("id", manufacturerId, RecordStatus.ACTIVE);
	}

	@Override
	public void delete(Manufacturer manufacturer) throws ValidationFailedException {
		manufacturer.setRecordStatus(RecordStatus.DELETED);
		manufacturerDao.save(manufacturer);
	}    
}
