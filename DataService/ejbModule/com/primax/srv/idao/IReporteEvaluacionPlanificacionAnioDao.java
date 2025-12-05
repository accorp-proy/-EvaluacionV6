package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.enums.EstadoCheckListEnum;
import com.primax.jpa.pla.ReporteEvaluacionPlanificacionAnioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteEvaluacionPlanificacionAnioDao extends IGenericDao<ReporteEvaluacionPlanificacionAnioEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idFrecuenciaVisita, EstadoCheckListEnum estadoCheckList, Date fechaActual, Long idUsuario);

}
