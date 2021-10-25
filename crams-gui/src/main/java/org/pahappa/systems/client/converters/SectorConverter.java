package org.pahappa.systems.client.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.pahappa.systems.core.services.ManufacturerService;
import org.pahappa.systems.core.services.SectorService;
import org.pahappa.systems.models.Manufacturer;
import org.pahappa.systems.models.Sector;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

@FacesConverter("sectorConverter")
public class SectorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2.isEmpty())
			return null;
		return (Sector) ApplicationContextProvider.getBean(SectorService.class)
				.getSectorById(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object == null || object instanceof String)
			return null;

		return ((Sector) object).getId();
	}
}
