package com.primax.jpa.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.pla.CheckListEjecucionEt;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "FALTANTE_INVENTARIO_ET")
@Audited

@NamedStoredProcedureQuery(name = "getGenerarFaltInvResumen", procedureName = "fun_generar_faltante_inv_resumen", resultClasses = FaltanteInventarioEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idFaltanteInv"),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class FaltanteInventarioEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_faltante_inventario_et", sequenceName = "seq_faltante_inventario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_faltante_inventario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_faltante_inventario")
	private Long idFaltanteInventario;

	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private AgenciaEt agencia;

	@ManyToOne
	@JoinColumn(name = "id_check_list_ejecucion")
	private CheckListEjecucionEt checkListEjecucion;

	@Column(name = "ejecutando")
	private boolean ejecutando;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_carga_archivo")
	private Date fechaCargaArchivo;

	@Column(name = "fecha_ejecucion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEjecucion;

	@Column(name = "fecha_finalizacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFinalizacion;

	@Column(name = "nombre_archivo")
	private String nombreArchivo;

	@Column(name = "cantidad_registro")
	private Long cantidadRegistro;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "faltanteInventario", fetch = FetchType.LAZY)
	@OrderBy("idFaltanteResumen ")
	@Where(clause = "estado = 'ACT'")
	private List<FaltanteResumenEt> faltanteResumen;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "faltanteInventario", fetch = FetchType.LAZY)
	@OrderBy("idFaltanteDetalle ")
	@Where(clause = "estado = 'ACT'")
	private List<FaltanteDetalleEt> faltanteDetalle;

	public FaltanteInventarioEt() {
		this.ejecutando = false;
		this.cantidadRegistro = 0L;
	}

	public Long getIdFaltanteInventario() {
		return idFaltanteInventario;
	}

	public void setIdFaltanteInventario(Long idFaltanteInventario) {
		this.idFaltanteInventario = idFaltanteInventario;
	}

	public List<FaltanteDetalleEt> getFaltanteDetalle() {
		return faltanteDetalle;
	}

	public void setFaltanteDetalle(List<FaltanteDetalleEt> faltanteDetalle) {
		this.faltanteDetalle = faltanteDetalle;
	}

	public Date getFechaCargaArchivo() {
		return fechaCargaArchivo;
	}

	public void setFechaCargaArchivo(Date fechaCargaArchivo) {
		this.fechaCargaArchivo = fechaCargaArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Long getCantidadRegistro() {
		return cantidadRegistro;
	}

	public void setCantidadRegistro(Long cantidadRegistro) {
		this.cantidadRegistro = cantidadRegistro;
	}

	public AgenciaEt getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEt agencia) {
		this.agencia = agencia;
	}

	public CheckListEjecucionEt getCheckListEjecucion() {
		return checkListEjecucion;
	}

	public void setCheckListEjecucion(CheckListEjecucionEt checkListEjecucion) {
		this.checkListEjecucion = checkListEjecucion;
	}

	public boolean isEjecutando() {
		return ejecutando;
	}

	public void setEjecutando(boolean ejecutando) {
		this.ejecutando = ejecutando;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public List<FaltanteResumenEt> getFaltanteResumen() {
		return faltanteResumen;
	}

	public void setFaltanteResumen(List<FaltanteResumenEt> faltanteResumen) {
		this.faltanteResumen = faltanteResumen;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FaltanteInventarioEt) {
			FaltanteInventarioEt other = (FaltanteInventarioEt) obj;

			if (this.idFaltanteInventario == null)
				return this == other;

			if (this.idFaltanteInventario.equals(other.idFaltanteInventario))
				return true;
		}
		return false;

	}

}
