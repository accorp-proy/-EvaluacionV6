package com.primax.srv.idao;

import java.util.Date;

import com.primax.jpa.pla.ReporteFaltanteInventarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IReporteFaltanteInventarioDao extends IGenericDao<ReporteFaltanteInventarioEt, Long> {

	public void remove();

	public String generar(Date fechaDesde, Date fechaHasta, Long idZona, Long idEvaluacion, Long idUsuario);

}
