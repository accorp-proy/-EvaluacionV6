package com.primax.jpa.param;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "CATEGORIA_FALTANTE_ET")
@Audited

@NamedStoredProcedureQuery(name = "getLimpiarReporteFaltanteInv", procedureName = "fun_limpiar_rpt_faltante_inv", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idUsuario"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class CategoriaFaltanteEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_categoria_faltante_et", sequenceName = "seq_categoria_faltante_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_categoria_faltante_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_categoria_faltante")
	private Long idCategoriaFaltante;

	@Column(name = "orden")
	private Long orden;

	@ManyToOne
	@JoinColumn(name = "id_tipo_inventario")
	private TipoInventarioEt tipoInventario;

	@Column(name = "descripcion", length = 300)
	private String descripcion;

	@Column(name = "codigo", length = 10)
	private String codigo;

	@Column(name = "top")
	private boolean top;

	public CategoriaFaltanteEt() {
		this.orden = 0L;
		this.top = false;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TipoInventarioEt getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(TipoInventarioEt tipoInventario) {
		this.tipoInventario = tipoInventario;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getIdCategoriaFaltante() {
		return idCategoriaFaltante;
	}

	public void setIdCategoriaFaltante(Long idCategoriaFaltante) {
		this.idCategoriaFaltante = idCategoriaFaltante;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CategoriaFaltanteEt) {
			CategoriaFaltanteEt other = (CategoriaFaltanteEt) obj;

			if (this.idCategoriaFaltante == null)
				return this == other;

			if (this.idCategoriaFaltante.equals(other.idCategoriaFaltante))
				return true;
		}
		return false;

	}

}
