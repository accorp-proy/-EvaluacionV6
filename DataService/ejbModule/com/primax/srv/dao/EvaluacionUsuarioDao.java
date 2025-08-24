package com.primax.srv.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.EvaluacionUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IEvaluacionUsuarioDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class EvaluacionUsuarioDao extends GenericDao<EvaluacionUsuarioEt, Long> implements IEvaluacionUsuarioDao {

	public EvaluacionUsuarioDao() {
		super(EvaluacionUsuarioEt.class);
	}

	private StringBuilder sql;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarEvaluacionUsuario(EvaluacionUsuarioEt evaluacionUsuario, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (evaluacionUsuario.getIdEvaluacionUsuario() == null) {
			evaluacionUsuario.audit(usuario, ActionAuditedEnum.NEW);
			crear(evaluacionUsuario);
		} else {
			evaluacionUsuario.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(evaluacionUsuario);
		}
		em.flush();
		em.clear();
	}

	public EvaluacionUsuarioEt getEvaluacionUsuarioById(long id) {
		try {
			return this.recuperar(id);
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EvaluacionUsuarioEt getEvaluacionExiste(EvaluacionEt evaluacion, UsuarioEt usuario) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM EvaluacionUsuarioEt o ");
		sql.append(" WHERE o.usuario = :usuario   ");
		sql.append(" AND   o.evaluacion = :evaluacion ");
		TypedQuery<EvaluacionUsuarioEt> query = em.createQuery(sql.toString(), EvaluacionUsuarioEt.class);
		query.setParameter("usuario", usuario);
		query.setParameter("evaluacion", evaluacion);
		List<EvaluacionUsuarioEt> result = query.getResultList();
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
