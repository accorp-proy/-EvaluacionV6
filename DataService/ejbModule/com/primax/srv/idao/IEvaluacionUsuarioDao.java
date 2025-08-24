package com.primax.srv.idao;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.EvaluacionEt;
import com.primax.jpa.param.EvaluacionUsuarioEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IEvaluacionUsuarioDao extends IGenericDao<EvaluacionUsuarioEt, Long> {

	public void remove();

	public EvaluacionUsuarioEt getEvaluacionUsuarioById(long id);

	public EvaluacionUsuarioEt getEvaluacionExiste(EvaluacionEt evaluacion, UsuarioEt usuario) throws EntidadNoEncontradaException;

	public void guardarEvaluacionUsuario(EvaluacionUsuarioEt evaluacionUsuario, UsuarioEt usuario) throws EntidadNoGrabadaException;

}
