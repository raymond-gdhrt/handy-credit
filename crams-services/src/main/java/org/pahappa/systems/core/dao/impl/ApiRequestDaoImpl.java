package org.pahappa.systems.core.dao.impl;

import org.pahappa.systems.core.dao.ApiRequestDao;
import org.pahappa.systems.models.ApiRequest;
import org.sers.webutils.server.core.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ApiRequestDaoImpl extends BaseDAOImpl<ApiRequest> implements ApiRequestDao {

}
