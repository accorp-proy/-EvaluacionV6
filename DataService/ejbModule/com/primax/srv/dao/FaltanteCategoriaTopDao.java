package com.primax.srv.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.param.FaltanteCategoriaTopEt;
import com.primax.srv.dao.base.GenericDao;
import com.primax.srv.idao.IFaltanteCategoriaTopDao;

@Stateful
@StatefulTimeout(unit = TimeUnit.HOURS, value = 8)
public class FaltanteCategoriaTopDao extends GenericDao<FaltanteCategoriaTopEt, Long> implements IFaltanteCategoriaTopDao {

	public FaltanteCategoriaTopDao() {
		super(FaltanteCategoriaTopEt.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public FaltanteCategoriaTopEt getFaltanteCatTopById(long id) {
		try {
			FaltanteCategoriaTopEt faltanteCatTop = recuperar(id);
			return faltanteCatTop;
		} catch (EntidadNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
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
