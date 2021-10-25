package org.pahappa.systems.core.services.impl;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pahappa.systems.core.utils.CustomSearchUtils;
import org.pahappa.systems.models.Sector;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import org.pahappa.systems.core.dao.SectorDao;
import org.pahappa.systems.core.services.SectorService;


@Service
@Transactional
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorDao sectorDao;

    @Override
    public void save(Sector sector) throws ValidationFailedException {
        if (StringUtils.isBlank(sector.getName()))
            throw new ValidationFailedException("Missing contact group name");
        
		if (!sector.getName().matches("^[a-zA-Z0-9_' ]*$") || sector.getName().length() < 3)
			throw new ValidationFailedException("Group name must contain only letters minimum(3 characters)");
        
        Sector existingWithName = getSectorByName(sector.getName());
        
		if(existingWithName != null && !existingWithName.getId().equals(sector.getId()))
			throw new ValidationFailedException("A group with the same name already exists!");

        sectorDao.save(sector);
    }

    @Override
    public List<Sector> getSectors(Search search, int offset, int limit) {
  
        search.setFirstResult(offset);
        search.setMaxResults(limit);
        return sectorDao.search(search);
    }

    @Override
    public int countSectors(Search search) {
        return sectorDao.count(search);
    }

    @Override
    public Sector getSectorById(String sectorId) {
        return sectorDao.searchUniqueByPropertyEqual("id", sectorId, RecordStatus.ACTIVE);
    }

    @Override
    public void delete(Sector sector) throws ValidationFailedException {
        sector.setRecordStatus(RecordStatus.DELETED);
        sectorDao.save(sector);
    }
    
	@Override
	public Sector getSectorByName(String name) {
		Search search = new Search();
        search.addFilterEqual("name", name);
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        List<Sector> groups = sectorDao.search(search);
		if(groups.isEmpty()) {
			return null;
		}
		return groups.get(0);
	}
    
}
