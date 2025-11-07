package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.jpa.param.CategoriaFaltanteEt;
import com.primax.jpa.param.FaltanteCategoriaEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IFaltanteCategoriaDao extends IGenericDao<FaltanteCategoriaEt, Long> {

	public void remove();

	public FaltanteCategoriaEt getFaltanteCat(long id);

	public List<FaltanteCategoriaEt> getFaltanteCatByCat(CategoriaFaltanteEt catFaltante, boolean tipo) throws EntidadNoEncontradaException;

}
