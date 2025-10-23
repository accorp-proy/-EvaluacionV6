package com.primax.srv.idao;

import java.util.List;

import com.primax.exc.gen.EntidadNoEncontradaException;
import com.primax.exc.gen.EntidadNoGrabadaException;
import com.primax.jpa.param.FaltanteInventarioEt;
import com.primax.jpa.param.TipoInventarioEt;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.dao.base.IGenericDao;

public interface IFaltanteInventarioDao extends IGenericDao<FaltanteInventarioEt, Long> {

	public void remove();

	public String limpiarReporte(Long idUsuario);

	public String generar(Long idFaltanteInv, Long idUsuario);

	public FaltanteInventarioEt getFaltanteInventario(long id);

	public Long getOrdenFaltanteInv() throws EntidadNoEncontradaException;

	public String getCodigoFaltanteInv() throws EntidadNoEncontradaException;

	public FaltanteInventarioEt getFaltanteInvEjecucion(UsuarioEt usuario) throws EntidadNoEncontradaException;

	public List<FaltanteInventarioEt> getFaltanteInveEjecutando(UsuarioEt usuario) throws EntidadNoEncontradaException;

	public List<FaltanteInventarioEt> getFaltanteInventarioList(String condicion) throws EntidadNoEncontradaException;

	public FaltanteInventarioEt getFaltanteInvByCheck(CheckListEjecucionEt checkListEje) throws EntidadNoEncontradaException;

	public void guardarFaltanteInv(FaltanteInventarioEt FaltanteInventario, UsuarioEt usuario) throws EntidadNoGrabadaException;

	public List<FaltanteInventarioEt> getFaltanteInvByTipoInv(TipoInventarioEt tipoInventario) throws EntidadNoEncontradaException;

}
