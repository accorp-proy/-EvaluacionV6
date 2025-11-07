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
@Table(name = "FALTANTE_CATEGORIA_TOP_ET")
@Audited

public class FaltanteCategoriaTopEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_faltante_categoria_top_et", sequenceName = "seq_faltante_categoria_top_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_faltante_categoria_top_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_faltante_categoria_top")
	private Long idFaltanteCategoriaTop;

	@ManyToOne
	@JoinColumn(name = "id_faltante_inventario")
	private FaltanteInventarioEt faltanteInventario;

	@ManyToOne
	@JoinColumn(name = "id_categoria_faltante")
	private CategoriaFaltanteEt categoriaFaltante;

	public FaltanteCategoriaTopEt() {

	}

	public FaltanteInventarioEt getFaltanteInventario() {
		return faltanteInventario;
	}

	public void setFaltanteInventario(FaltanteInventarioEt faltanteInventario) {
		this.faltanteInventario = faltanteInventario;
	}

	public CategoriaFaltanteEt getCategoriaFaltante() {
		return categoriaFaltante;
	}

	public void setCategoriaFaltante(CategoriaFaltanteEt categoriaFaltante) {
		this.categoriaFaltante = categoriaFaltante;
	}

	public Long getIdFaltanteCategoriaTop() {
		return idFaltanteCategoriaTop;
	}

	public void setIdFaltanteCategoriaTop(Long idFaltanteCategoriaTop) {
		this.idFaltanteCategoriaTop = idFaltanteCategoriaTop;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FaltanteCategoriaTopEt) {
			FaltanteCategoriaTopEt other = (FaltanteCategoriaTopEt) obj;
			if (this.idFaltanteCategoriaTop == null)
				return this == other;

			if (this.idFaltanteCategoriaTop.equals(other.idFaltanteCategoriaTop))
				return true;
		}
		return false;

	}

}
