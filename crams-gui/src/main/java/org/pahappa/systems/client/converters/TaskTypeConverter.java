package org.pahappa.systems.client.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.sers.webutils.model.bgtasks.constants.TaskType;

@FacesConverter("taskTypeConverter")
public class TaskTypeConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null)
			return null;
		return TaskType.valueOf(arg2);
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object == null || object instanceof String)
			return null;
		return ((TaskType) object).toString();
	}
}
