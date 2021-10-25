package org.pahappa.systems.core.services;

import com.googlecode.genericdao.search.Search;
import java.util.List;

import org.pahappa.systems.models.Manufacturer;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.utils.SortField;

/**
 * Responsible for CRUD operations on {@link Manufacturer}
 *
 * @author mmc
 *
 */
public interface ManufacturerService {

    /**
     * Adds a manufacturer to the database.
     *
     * @param manufacturer
     * @return
     * @throws ValidationFailedException if the following attributes are blank:
     * name, phoneNumber
     */
    Manufacturer save(Manufacturer manufacturer) throws ValidationFailedException;

    /**
     * Adds a manufacturer to the database outside the spring security context.
     *
     * @param manufacturer
     * @return
     * @throws ValidationFailedException
     */
    Manufacturer saveOutsideContext(Manufacturer manufacturer) throws ValidationFailedException;

    /**
     * Gets a list of manufacturers that match the specified search criteria
     *
     * @param searchTerm
     * @param sortField
     * @param offset
     * @param limit
     * @return
     */
    List<Manufacturer> getManufacturers(Search search, int offset, int limit);

    /**
     * Counts a list of manufacturers that match the specified search criteria
     *
     * @param searchTerm
     * @param sortField
     * @return
     */
    int countManufacturers(Search search);

    /**
     * Gets a manufacturer that matches the specified identifier
     *
     * @param manufacturerId
     * @return
     */
    Manufacturer getManufacturerById(String manufacturerId);

    /**
     * Deactivates a manufacturer along with all he data associated to it. This
     * manufacturer will never be accessible on the UI
     *
     * @param manufacturer
     * @throws ValidationFailedException
     */
    void delete(Manufacturer manufacturer) throws ValidationFailedException;

    /**
     * Gets a manufacturer that matches the specified identifier
     *
     * @param phoneNumber
     * @return
     */
    Manufacturer getManufacturerByPhoneNumber(String phoneNumber);
}
