package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.pla.PlanAccionAnioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IPlanAccionAnioDao extends IGenericDao<PlanAccionAnioEt, Long> {

	public void remove();

	public String generar(Long idUsuario);

	public List<PlanAccionAnioEt> getPlanAccionAnioList();

	public void guardarPlanAccionAnio(PlanAccionAnioEt planAccionAnio, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
