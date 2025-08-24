package com.primax.jpa.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.enm.gen.ActionAuditedEnum;
import com.primax.jpa.base.EntityBase;
import com.primax.jpa.sec.UsuarioEt;

@Entity
@Table(name = "EVALUACION_USUARIO_ET")
@Audited
public class EvaluacionUsuarioEt extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_evaluacion_usuario_et", sequenceName = "seq_evaluacion_usuario_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_evaluacion_usuario_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_evaluacion_usuario")
	private Long idEvaluacionUsuario;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioEt usuario;

	@OneToOne
	@JoinColumn(name = "id_evaluacion")
	private EvaluacionEt evaluacion;

	public UsuarioEt getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEt usuario) {
		this.usuario = usuario;
	}

	public Long getIdEvaluacionUsuario() {
		return idEvaluacionUsuario;
	}

	public void setIdEvaluacionUsuario(Long idEvaluacionUsuario) {
		this.idEvaluacionUsuario = idEvaluacionUsuario;
	}

	public EvaluacionEt getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionEt evaluacion) {
		this.evaluacion = evaluacion;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EvaluacionUsuarioEt) {

			EvaluacionUsuarioEt other = (EvaluacionUsuarioEt) obj;

			if (this.idEvaluacionUsuario == null) {
				if (this == other) {
					return true;
				} else {
					return false;
				}
			}
			return this.idEvaluacionUsuario.equals(other.idEvaluacionUsuario) ? true : false;
		}
		return false;
	}

}
