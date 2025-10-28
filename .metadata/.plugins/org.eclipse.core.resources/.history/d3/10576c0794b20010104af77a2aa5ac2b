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
@Table(name = "FALTANTE_DETALLE_ET")
@Audited

@NamedStoredProcedureQuery(name = "getEliminarFaltInvResumen", procedureName = "fun_limpiar_faltante_inv_resumen", resultClasses = ProcesoDetalleEt.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "idFaltanteInv"),
		@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "respuesta"), })

public class FaltanteDetalleEt extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3318332355036766787L;

	@Id
	@SequenceGenerator(name = "sec_faltante_detalle_et", sequenceName = "seq_faltante_detalle_et", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_faltante_detalle_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_faltante_detalle")
	private Long idFaltanteDetalle;

	@ManyToOne
	@JoinColumn(name = "id_faltante_inventario")
	private FaltanteInventarioEt faltanteInventario;

	@Column(name = "nro_documento", length = 50)
	private String nroDocumento;

	@Column(name = "posicion")
	private Long posicion;

	@Column(name = "centro", length = 50)
	private String centro;

	@Column(name = "almacen", length = 50)
	private String almacen;

	@Column(name = "contabilizacion", length = 50)
	private String contabilizacion;

	@Column(name = "material", length = 50)
	private String material;

	@Column(name = "referencia", length = 50)
	private String referencia;

	@Column(name = "nombre_producto", length = 300)
	private String nombreProducto;

	@Column(name = "cantidad_teorica")
	private Double cantidadTeorica;

	@Column(name = "cantidad_contada")
	private Double cantidadContada;

	@Column(name = "cantidad_diferencia")
	private Double cantidadDiferencia;

	@Column(name = "valor_dif_ven_positivo")
	private Double valorDifVenPositivo;

	@Column(name = "valor_dif_ven_negativo")
	private Double valorDifVenNegativo;

	@Column(name = "estatus_inv", length = 50)
	private String estatusInv;

	@Column(name = "contado_por", length = 50)
	private String contadoPor;

	@Column(name = "costo_unitario")
	private Double costoUnitario;

	@Column(name = "costo_variacion")
	private Double costoVariacion;

	@Column(name = "categoria", length = 100)
	private String categoria;

	public FaltanteDetalleEt() {
		this.nroDocumento = "";
		this.costoUnitario = 0D;
		this.costoVariacion = 0D;
		this.cantidadTeorica = 0D;
		this.cantidadContada = 0D;
		this.valorDifVenPositivo = 0D;
		this.valorDifVenNegativo = 0D;
	}

	public Long getIdFaltanteDetalle() {
		return idFaltanteDetalle;
	}

	public void setIdFaltanteDetalle(Long idFaltanteDetalle) {
		this.idFaltanteDetalle = idFaltanteDetalle;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public FaltanteInventarioEt getFaltanteInventario() {
		return faltanteInventario;
	}

	public void setFaltanteInventario(FaltanteInventarioEt faltanteInventario) {
		this.faltanteInventario = faltanteInventario;
	}

	public Long getPosicion() {
		return posicion;
	}

	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public String getAlmacen() {
		return almacen;
	}

	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}

	public String getContabilizacion() {
		return contabilizacion;
	}

	public void setContabilizacion(String contabilizacion) {
		this.contabilizacion = contabilizacion;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Double getCantidadTeorica() {
		return cantidadTeorica;
	}

	public void setCantidadTeorica(Double cantidadTeorica) {
		this.cantidadTeorica = cantidadTeorica;
	}

	public Double getCantidadContada() {
		return cantidadContada;
	}

	public void setCantidadContada(Double cantidadContada) {
		this.cantidadContada = cantidadContada;
	}

	public Double getCantidadDiferencia() {
		return cantidadDiferencia;
	}

	public void setCantidadDiferencia(Double cantidadDiferencia) {
		this.cantidadDiferencia = cantidadDiferencia;
	}

	public Double getValorDifVenPositivo() {
		return valorDifVenPositivo;
	}

	public void setValorDifVenPositivo(Double valorDifVenPositivo) {
		this.valorDifVenPositivo = valorDifVenPositivo;
	}

	public Double getValorDifVenNegativo() {
		return valorDifVenNegativo;
	}

	public void setValorDifVenNegativo(Double valorDifVenNegativo) {
		this.valorDifVenNegativo = valorDifVenNegativo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEstatusInv() {
		return estatusInv;
	}

	public void setEstatusInv(String estatusInv) {
		this.estatusInv = estatusInv;
	}

	public String getContadoPor() {
		return contadoPor;
	}

	public void setContadoPor(String contadoPor) {
		this.contadoPor = contadoPor;
	}

	public Double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(Double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public Double getCostoVariacion() {
		return costoVariacion;
	}

	public void setCostoVariacion(Double costoVariacion) {
		this.costoVariacion = costoVariacion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public <T> void audit(UsuarioEt user, ActionAuditedEnum act) {
		super.audit(user, act);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FaltanteDetalleEt) {
			FaltanteDetalleEt other = (FaltanteDetalleEt) obj;
			if (this.idFaltanteDetalle == null)
				return this == other;

			if (this.idFaltanteDetalle.equals(other.idFaltanteDetalle))
				return true;
		}
		return false;

	}

}
