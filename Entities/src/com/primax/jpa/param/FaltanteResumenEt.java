package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "FALTANTE_RESUMEN_ET")
@Audited
public class FaltanteResumenEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_faltante_resumen_et", sequenceName = "seq_faltante_resumen_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_faltante_resumen_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_faltante_resumen")
	private Long idFaltanteResumen;

	@ManyToOne
	@JoinColumn(name = "id_faltante_inventario")
	private FaltanteInventarioEt faltanteInventario;

	@Column(name = "descripcion", length = 300)
	private String descripcion;

	@Column(name = "cantidad")
	private Double cantidad;

	@Column(name = "variacion")
	private Double variacion;

	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private AgenciaEt agencia;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria_faltante")
	private CategoriaFaltanteEt categoriaFaltante;

	@Column(name = "comentario_control", length = 500)
	private String comentarioControl;

	@Column(name = "comentario_plan_accion", length = 500)
	private String comentarioPlanAccion;

	@Column(name = "reincidente")
	private boolean reincidente;

	public FaltanteResumenEt() {
		this.descripcion = "";
		this.variacion = 0D;
		this.cantidad = 0D;
		this.reincidente = false;
		this.comentarioControl = "";
		this.comentarioPlanAccion = "";
	}

	public Long getIdFaltanteResumen() {
		return idFaltanteResumen;
	}

	public void setIdFaltanteResumen(Long idFaltanteResumen) {
		this.idFaltanteResumen = idFaltanteResumen;
	}

	public FaltanteInventarioEt getFaltanteInventario() {
		return faltanteInventario;
	}

	public void setFaltanteInventario(FaltanteInventarioEt faltanteInventario) {
		this.faltanteInventario = faltanteInventario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getVariacion() {
		return variacion;
	}

	public void setVariacion(Double variacion) {
		this.variacion = variacion;
	}

	public String getComentarioControl() {
		return comentarioControl;
	}

	public void setComentarioControl(String comentarioControl) {
		this.comentarioControl = comentarioControl;
	}

	public String getComentarioPlanAccion() {
		return comentarioPlanAccion;
	}

	public void setComentarioPlanAccion(String comentarioPlanAccion) {
		this.comentarioPlanAccion = comentarioPlanAccion;
	}

	public AgenciaEt getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaEt agencia) {
		this.agencia = agencia;
	}

	public boolean isReincidente() {
		return reincidente;
	}

	public void setReincidente(boolean reincidente) {
		this.reincidente = reincidente;
	}

	public CategoriaFaltanteEt getCategoriaFaltante() {
		return categoriaFaltante;
	}

	public void setCategoriaFaltante(CategoriaFaltanteEt categoriaFaltante) {
		this.categoriaFaltante = categoriaFaltante;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FaltanteResumenEt) {
			FaltanteResumenEt other = (FaltanteResumenEt) obj;
			if (this.idFaltanteResumen == null)
				return this == other;

			if (this.idFaltanteResumen.equals(other.idFaltanteResumen))
				return true;
		}
		return false;

	}

}
