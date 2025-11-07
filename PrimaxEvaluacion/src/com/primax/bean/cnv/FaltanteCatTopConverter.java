package com.primax.bean.cnv;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.primax.ejb.lkp.BaseNaming;
import com.primax.ejb.lkp.EnumNaming;
import com.primax.jpa.param.CategoriaInventarioEt;
import com.primax.jpa.param.FaltanteCategoriaTopEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICategoriaInventarioDao;
import com.primax.srv.idao.IFaltanteCategoriaTopDao;
import com.primax.srv.idao.IUsuarioDao;

@Named
@ApplicationScoped
public class FaltanteCatTopConverter extends BaseNaming implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		IFaltanteCategoriaTopDao iFaltanteCatTopDao = EJB(EnumNaming.IFaltanteCategoriaTopDao);
		if (value != null) {
			Long id = Long.parseLong(value);
			FaltanteCategoriaTopEt faltanteCatTop = iFaltanteCatTopDao.getFaltanteCatTopById(id);
			iFaltanteCatTopDao.remove();
			return faltanteCatTop;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && ((FaltanteCategoriaTopEt) value).getIdFaltanteCategoriaTop() != null) {
			return ((FaltanteCategoriaTopEt) value).getIdFaltanteCategoriaTop().toString();
		} else {
			return "";
		}
	}

}
