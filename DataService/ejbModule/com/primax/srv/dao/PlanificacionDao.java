package com.primax.srv.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.AgenciaEt;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.EvaluacionUsuarioEt;
import com.primax.jpa.param.TipoChecKListEt;
import com.primax.jpa.pla.PlanificacionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IPlanificacionDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class PlanificacionDao extends GenericDao<PlanificacionEt, Long> implements IPlanificacionDao {

	public PlanificacionDao() {
		super(PlanificacionEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarPlanificacion(PlanificacionEt planificacion, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (planificacion.getIdPlanificacion() == null) {
			planificacion.audit(usuario, ActionAuditedEnum.NEW);
			crear(planificacion);
		} else {
			planificacion.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(planificacion);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanificacionEt> getPlanificacionList(AgenciaEt estacion, Date fechaDesde, Date fechaHasta) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM PlanificacionEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		sql.append(" AND date_trunc('day',o.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		if (estacion != null) {
			sql.append(" AND o.agencia  = :estacion ");
		}
		sql.append(" ORDER BY o.fechaPlanificacion  ");
		TypedQuery<PlanificacionEt> query = em.createQuery(sql.toString(), PlanificacionEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		if (estacion != null) {
			query.setParameter("estacion", estacion);
		}
		List<PlanificacionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanificacionEt> getPlanificacionList(UsuarioEt usuario, EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, Date fechaDesde, Date fechaHasta)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT DISTINCT(o.planificacion) FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.planificacion.estado  = :estado   ");
		if (usuario != null && !usuario.getEvaluacionUsuario().isEmpty()) {
			sql.append(" AND o.evaluacion in (:evaluaciones) ");
		}
		sql.append(" AND date_trunc('day',o.planificacion.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		TypedQuery<PlanificacionEt> query = em.createQuery(sql.toString(), PlanificacionEt.class);
		List<EvaluacionEt> evaluaciones = new ArrayList<EvaluacionEt>();
		if (usuario != null && !usuario.getEvaluacionUsuario().isEmpty()) {
			for (EvaluacionUsuarioEt evaluacionUsuario : usuario.getEvaluacionUsuario()) {
				evaluaciones.add(evaluacionUsuario.getEvaluacion());
			}
			query.setParameter("evaluaciones", evaluaciones);
		}
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		List<PlanificacionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PlanificacionEt> getPlanificacionList(EvaluacionEt evaluacion, TipoChecKListEt tipoChecKList, Date fechaDesde, Date fechaHasta, UsuarioEt usuario)
			throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT DISTINCT(o.planificacion) FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.usuarioAsignado  = :usuario   ");
		sql.append(" AND date_trunc('day',o.planificacion.fechaPlanificacion) BETWEEN :fDesde AND :fHasta ");
		sql.append(" AND o.estado  = :estado   ");
		sql.append(" AND o.planificacion.estado  = :estado   ");
		if (evaluacion != null) {
			sql.append(" AND o.planificacion.evaluacion  = :evaluacion   ");
		}
		if (tipoChecKList != null) {
			sql.append(" AND o.planificacion.tipoChecKList  = :tipoChecKList   ");
		}
		TypedQuery<PlanificacionEt> query = em.createQuery(sql.toString(), PlanificacionEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("fDesde", fechaDesde, TemporalType.DATE);
		query.setParameter("fHasta", fechaHasta, TemporalType.DATE);
		if (evaluacion != null) {
			query.setParameter("evaluacion", evaluacion);
		}
		if (tipoChecKList != null) {
			query.setParameter("tipoChecKList", tipoChecKList);
		}
		List<PlanificacionEt> result = query.getResultList();
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PlanificacionEt getPlanificacionByEvaluacionList(UsuarioEt usuario, PlanificacionEt planificacion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("SELECT DISTINCT(o.planificacion) FROM CheckListEjecucionEt o ");
		sql.append(" WHERE o.planificacion.estado  = :estado   ");
		if (usuario != null) {
			sql.append(" AND o.evaluacion in (:evaluaciones) ");
		}
		sql.append(" AND o.planificacion  = :planificacion   ");
		TypedQuery<PlanificacionEt> query = em.createQuery(sql.toString(), PlanificacionEt.class);
		List<EvaluacionEt> evaluaciones = new ArrayList<EvaluacionEt>();
		if (usuario != null) {
			for (EvaluacionUsuarioEt evaluacionUsuario : usuario.getEvaluacionUsuario()) {
				evaluaciones.add(evaluacionUsuario.getEvaluacion());
			}
			query.setParameter("evaluaciones", evaluaciones);
		}
		query.setParameter("estado", EstadoEnum.ACT);
		query.setParameter("planificacion", planificacion);
		List<PlanificacionEt> result = query.getResultList();
		return getUnique(result);
	}

	@Remove
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void remove() {
		System.out.println("Finalizado Statefull Bean : " + this.getClass().getCanonicalName());
	}

	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void detached() {
		System.out.println("Terminado Statefull Bean : " + this.getClass().getCanonicalName());
	}

}
