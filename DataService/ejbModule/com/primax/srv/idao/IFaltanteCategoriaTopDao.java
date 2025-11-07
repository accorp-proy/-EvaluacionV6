package com.primax.srv.idao;

import com.primax.jpa.param.FaltanteCategoriaTopEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IFaltanteCategoriaTopDao extends IGenericDao<FaltanteCategoriaTopEt, Long> {

	public void remove();

	public FaltanteCategoriaTopEt getFaltanteCatTopById(long id);
}
