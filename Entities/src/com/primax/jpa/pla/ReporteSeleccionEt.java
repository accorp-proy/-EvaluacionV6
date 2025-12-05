package com.primax.jpa.pla;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "REPORTE_SELECCION_ET")
@Audited

public class ReporteSeleccionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "sec_reporte_seleccion_et", sequenceName = "seq_reporte_seleccion_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_reporte_seleccion_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_reporte_seleccion")
	private Long idReporteSeleccion;

	@Column(name = "id_generico")
	private Long idGenerico;

	public ReporteSeleccionEt() {
		this.idGenerico = 0L;
	}

	public Long getIdReporteSeleccion() {
		return idReporteSeleccion;
	}

	public void setIdReporteSeleccion(Long idReporteSeleccion) {
		this.idReporteSeleccion = idReporteSeleccion;
	}

	public Long getIdGenerico() {
		return idGenerico;
	}

	public void setIdGenerico(Long idGenerico) {
		this.idGenerico = idGenerico;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ReporteSeleccionEt) {

			ReporteSeleccionEt other = (ReporteSeleccionEt) obj;
			if (this.idReporteSeleccion == null)
				return this == other;

			return this.idReporteSeleccion.equals(other.idReporteSeleccion);
		}
		return false;
	}

}
