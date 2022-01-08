package com.handycredit.systems.client.converters;

import com.handycredit.systems.core.services.CollateralService;
import com.handycredit.systems.models.Collateral;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.sers.webutils.model.security.Role;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@FacesConverter("collateralConverter")
public class CollateralConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2.isEmpty())
			return null;
		return (Collateral) ApplicationContextProvider.getBean(CollateralService.class)
				.getInstanceByID(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object == null || object instanceof String)
			return null;

		return ((Role) object).getId();
	}
}
