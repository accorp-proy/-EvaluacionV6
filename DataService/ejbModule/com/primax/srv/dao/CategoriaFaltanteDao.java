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
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CategoriaFaltanteEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.ICategoriaFaltanteDao;
import com.primax.srv.util.QUL;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class CategoriaFaltanteDao extends GenericDao<CategoriaFaltanteEt, Long> implements ICategoriaFaltanteDao {

	public CategoriaFaltanteDao() {
		super(CategoriaFaltanteEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarCatFaltante(CategoriaFaltanteEt catFaltante, UsuarioEt usuario) throws EntidadNoGrabadaException {
		if (catFaltante.getIdCategoriaFaltante() == null) {
			catFaltante.audit(usuario, ActionAuditedEnum.NEW);
			crear(catFaltante);
		} else {
			catFaltante.audit(usuario, ActionAuditedEnum.UPD);
			actualizar(catFaltante);
		}
		em.flush();
		em.clear();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CategoriaFaltanteEt> getCategoriaFaltanteList(String condicion) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CategoriaFaltanteEt o ");
		sql.append(" WHERE o.estado  = :estado   ");
		if (condicion != null && !condicion.isEmpty()) {
			sql.append(" AND o.descripcion like :condicion ");
		}
		sql.append(" ORDER BY o.idCategoriaFaltante ");
		TypedQuery<CategoriaFaltanteEt> query = em.createQuery(sql.toString(), CategoriaFaltanteEt.class);
		query.setParameter("estado", EstadoEnum.ACT);
		if (condicion != null && !condicion.isEmpty()) {
			query.setParameter("condicion", "%" + QUL.getString(condicion) + "%");
		}
		List<CategoriaFaltanteEt> result = query.getResultList();
		return result;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CategoriaFaltanteEt getCatFaltante(long id) {
		try {
			CategoriaFaltanteEt catFaltante = recuperar(id);
			return catFaltante;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CategoriaFaltanteEt getCatFaltanteExiste(String desc) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM CategoriaFaltanteEt o ");
		sql.append(" WHERE o.estado  = :estado ");
		sql.append(" AND o.codigo = :codigo ");
		TypedQuery<CategoriaFaltanteEt> query = em.createQuery(sql.toString(), CategoriaFaltanteEt.class);
		query.setParameter("codigo", desc.toUpperCase());
		query.setParameter("estado", EstadoEnum.ACT);
		List<CategoriaFaltanteEt> result = query.getResultList();
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
