/**
 * 
 */
package com.handycredit.systems.core.services.impl;

import java.util.Date;
import java.util.List;

import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.server.core.dao.impl.BaseDAOImpl;
import org.sers.webutils.server.shared.SharedAppData;

import com.googlecode.genericdao.search.Search;
import com.handycredit.systems.core.services.GenericService;

/**
 * Provides for generic implementation of the {@link GenericService}.Concrete classes need to provide implementation of methods that are specific
 to that class or the associated entity.
 * 
 * 
 * @author Mzee Sr.
 * @param <T>
 *
 */
public abstract class GenericServiceImpl<T extends BaseEntity> extends BaseDAOImpl<T> implements GenericService<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handycredit.systems.core.services.GenericService#
	 * countInstances(com.googlecode.genericdao.search.Search)
	 */
	@Override
	public int countInstances(Search arg0) {
		// TODO Auto-generated method stub
		return super.count(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pahappa.systems.akinamama.utils.backend.core.services.GenericService#
	 * deleteInstance(java.lang.Object)
	 */
	@Override
	public void deleteInstance(T arg0) throws OperationFailedException {
		if (!isDeletable(arg0))
			throw new OperationFailedException("Deletion is yet supported for this instance.");
		arg0.setChangedBy(SharedAppData.getLoggedInUser());
		arg0.setDateChanged(new Date());
		arg0.setRecordStatus(RecordStatus.DELETED);
		super.save(arg0);
	}

	/**
	 * Must be implemented by all classes that extend this abstract class.
	 * 
	 * @param entity
	 * @return
	 */
	public abstract boolean isDeletable(T entity) throws OperationFailedException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pahappa.systems.akinamama.utils.backend.core.services.GenericService#
	 * getInstanceByID(java.lang.String)
	 */
	@Override
	public T getInstanceByID(String arg0) {
		// TODO Auto-generated method stub
		return super.searchUniqueByPropertyEqual("id", arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pahappa.systems.akinamama.utils.backend.core.services.GenericService#
	 * getInstances(com.googlecode.genericdao.search.Search, int, int)
	 */
	@Override
	public List<T> getInstances(Search arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return super.search(arg0.setFirstResult(arg1).setMaxResults(arg2));
	}
}
