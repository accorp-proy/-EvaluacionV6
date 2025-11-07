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

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.enums.EstadoEnum;
import com.primax.jpa.param.CategoriaFaltanteEt;
import com.primax.jpa.param.FaltanteCategoriaEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IFaltanteCategoriaDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class FaltanteCategoriaDao extends GenericDao<FaltanteCategoriaEt, Long> implements IFaltanteCategoriaDao {

	public FaltanteCategoriaDao() {
		super(FaltanteCategoriaEt.class);
	}

	private StringBuilder sql;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FaltanteCategoriaEt getFaltanteCat(long id) {
		try {
			FaltanteCategoriaEt faltanteCat = recuperar(id);
			return faltanteCat;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<FaltanteCategoriaEt> getFaltanteCatByCat(CategoriaFaltanteEt catFaltante, boolean variable) throws EntidadNoEncontradaException {
		sql = new StringBuilder("FROM FaltanteCategoriaEt o ");
		sql.append(" WHERE o.estado = :estado ");
		sql.append(" AND o.variable = :variable ");
		if (catFaltante != null) {
			sql.append(" AND o.categoriaFaltante = :catFaltante ");
		}
		TypedQuery<FaltanteCategoriaEt> query = em.createQuery(sql.toString(), FaltanteCategoriaEt.class);
		query.setParameter("variable", variable);
		query.setParameter("estado", EstadoEnum.ACT);
		if (catFaltante != null) {
			query.setParameter("catFaltante", catFaltante);
		}
		List<FaltanteCategoriaEt> result = query.getResultList();
		return result;
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
