package org.pahappa.systems.core.services;

import com.googlecode.genericdao.search.Search;
import java.util.List;

import org.pahappa.systems.models.Sector;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;

/**
 * Responsible for CRUD operations on {@link Sector}
 * 
 * @author mmc
 *
 */
public interface SectorService {
	/**
	 * Adds a sector to the database.
	 * 
	 * @param sector
	 * @throws ValidationFailedException if the following attributes are blank:
	 *                                   name, phoneNumber
	 */
	void save(Sector sector) throws ValidationFailedException;

	/**
	 * Gets a list of sectors that match the specified search criteria
	 * 
	 * @param searchTerm
	 * @param church
	 * @param sortField
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Sector> getSectors(Search search, int offset, int limit);

	/**
	 * Counts a list of sectors that match the specified search criteria
	 * 
	 * @param searchTerm
	 * @param church
	 * @param sortField
	 * @return
	 */
	int countSectors(Search search);

	/**
	 * Gets a sector that matches the specified identifier
	 * 
	 * @param sectorId
	 * @return
	 */
	Sector getSectorById(String sectorId);

	/**
	 * Deactivates a sector along with all he data associated to it. This sector
	 * will never be accessible on the UI
	 * 
	 * @param sector
	 * @throws ValidationFailedException
	 */
	void delete(Sector sector) throws ValidationFailedException;

	Sector getSectorByName(String name);     
        
}
