package com.primax.bean.vs;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.primax.bean.ss.AppMain;
import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.enums.EstadoPlanAccionEnum;
import com.primax.jpa.param.CategoriaFaltanteEt;
import com.primax.jpa.param.FaltanteCategoriaEt;
import com.primax.jpa.param.FaltanteCategoriaTopEt;
import com.primax.jpa.param.FaltanteDetalleEt;
import com.primax.jpa.param.FaltanteInventarioEt;
import com.primax.jpa.param.FaltanteResumenEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.IFaltanteCategoriaDao;
import com.primax.srv.idao.IFaltanteDetalleDao;
import com.primax.srv.idao.IFaltanteInventarioDao;

@Named("EjecucionPlanAccionFBn")
@ViewScoped
public class EjecucionPlanAccionFBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IFaltanteCategoriaDao iFaltanteCatDao;
	@EJB
	private IFaltanteInventarioDao iFaltanteInvDao;
	@EJB
	private IFaltanteDetalleDao iFaltanteDetalleDao;

	private Double totVarN = 0D;
	private Double totVarP = 0D;
	private Double totCantN = 0D;
	private Double totCantP = 0D;
	private String totVarNS = "0";
	private String totVarPS = "0";
	private String totCantPS = "0";
	private String totCantNS = "0";
	private Double totCantidad = 0D;
	private List<String> condiciones;
	private Double totVariacion = 0D;
	private String totCantidadS = "0";
	private String totVariacionS = "0";
	private String condicionSeleccionada;
	private List<FaltanteDetalleEt> faltanteDet;
	private FaltanteInventarioEt faltanteInvSelecc;
	private FaltanteCategoriaTopEt faltanteCatTopSelecc;
	private List<FaltanteCategoriaEt> faltanteCategorias;
	

	@Inject
	private AppMain appMain;

	@Override
	protected void init() {
		inicializarObj();
		buscar();
	}

	public void buscar() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			faltanteInvSelecc = iFaltanteInvDao.getFaltanteInvPlanAccion(usuario);
			if (faltanteInvSelecc != null) {
				mostrarTotal(faltanteInvSelecc);
				for (FaltanteCategoriaTopEt catTop : faltanteInvSelecc.getFaltanteCategoriaTop()) {
					faltanteCatTopSelecc = catTop;
					eventSeleccionCatTop();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método buscar " + " " + e.getMessage());
		}

	}

	public void inicializarObj() {
		faltanteDet = new ArrayList<>();
		faltanteCategorias = new ArrayList<>();
		condiciones = new ArrayList<String>();
		condiciones.add("Top-Positivo");
		condiciones.add("Top-Negativo");
		condicionSeleccionada = "Top-Negativo";
	}

	public void verDetalle(FaltanteResumenEt faltanteR) {
		try {
			faltanteDet = new ArrayList<>();
			faltanteDet = iFaltanteDetalleDao.getFaltanteDetByCat(faltanteR.getFaltanteInventario(),
					faltanteR.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Metodo verDetalle " + " " + e.getMessage());
		}
	}

	public void guardar() {
		String pagina = "";
		String mensaje = "";
		try {
			mensaje = validarguardar();
			if (!mensaje.equals("")) {
				showInfo(mensaje, FacesMessage.SEVERITY_WARN);
				return;
			}
			UsuarioEt usuario = appMain.getUsuario();
			FacesContext contex = FacesContext.getCurrentInstance();
			faltanteInvSelecc.setEstadoPlanAccion(EstadoPlanAccionEnum.INGRESADO);
			iFaltanteInvDao.guardarFaltanteInv(faltanteInvSelecc, usuario);
			pagina = "/PrimaxEvaluacion/pages/gerencia/ger_005.xhtml";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardar " + " " + e.getMessage());
		}
	}

	public String validarguardar() {
		String mensaje = "";
		try {
//			for (CheckListProcesoEjecucionEt checkListProceso : checkListEjecucion.getCheckListProcesoEjecucion()) {
//				for (CheckListKpiEjecucionEt checkListKpi : checkListProceso.getCheckListKpiEjecucion()) {
//					if (checkListKpi.getComentarioPlanAccion() == null) {
//						mensaje = "Por favor ingresar plan de acción del KPI " + " " + checkListKpi.getDescripcion();
//						break;
//					}
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mensaje " + " " + e.getMessage());
		}
		return mensaje;
	}

	public void guardarDet() {
		try {
			UsuarioEt usuario = appMain.getUsuario();
			iFaltanteInvDao.guardarFaltanteInv(faltanteInvSelecc, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método guardarDet " + " " + e.getMessage());
		}

	}

	public void retroceder() {
		String pagina = "";
		try {
			FacesContext contex = FacesContext.getCurrentInstance();
			pagina = "/PrimaxEvaluacion/pages/gerencia/ger_005.xhtml";
			contex.getExternalContext().redirect(pagina);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método retroceder " + " " + e.getMessage());
		}
	}

	public void mostrarTotal(FaltanteInventarioEt faltanteInv) {
		DecimalFormat format = new DecimalFormat("###,###.##");
		try {

			totVarN = faltanteInv.getFaltanteTopNegativo().stream().mapToDouble(p -> p.getVariacion()).sum();
			totVarP = faltanteInv.getFaltanteTopPositivo().stream().mapToDouble(p -> p.getVariacion()).sum();

			totCantN = faltanteInv.getFaltanteTopNegativo().stream().mapToDouble(p -> p.getCantidad()).sum();
			totCantP = faltanteInv.getFaltanteTopPositivo().stream().mapToDouble(p -> p.getCantidad()).sum();
			totCantidad = faltanteInv.getFaltanteResumen().stream().mapToDouble(p -> p.getCantidad()).sum();
			totVariacion = faltanteInv.getFaltanteResumen().stream().mapToDouble(p -> p.getVariacion()).sum();

			totCantNS = format.format(totCantN);
			totCantPS = format.format(totCantP);

			totVarNS = format.format(totVarN);
			totVarPS = format.format(totVarP);

			totCantidadS = format.format(totCantidad);
			totVariacionS = format.format(totVariacion);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método mostrarTotal " + " " + e.getMessage());
		}
	}

	public void eventSeleccionCatTop() {
		boolean variable = false;
		CategoriaFaltanteEt catFaltante = null;
		try {
			if (condicionSeleccionada.equals("Top-Positivo")) {
				variable = true;
			}
			catFaltante = faltanteCatTopSelecc.getCategoriaFaltante();
			System.out.println("Categoria Seleccionada" + " " + catFaltante.getDescripcion());
			faltanteCategorias = iFaltanteCatDao.getFaltanteCatByCat(catFaltante, variable);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método eventSeleccionCatTop " + " " + e.getMessage());
		}
	}
	
	public FaltanteInventarioEt getFaltanteInvSelecc() {
		return faltanteInvSelecc;
	}

	public void setFaltanteInvSelecc(FaltanteInventarioEt faltanteInvSelecc) {
		this.faltanteInvSelecc = faltanteInvSelecc;
	}

	public Double getTotCantidad() {
		return totCantidad;
	}

	public void setTotCantidad(Double totCantidad) {
		this.totCantidad = totCantidad;
	}

	public Double getTotVariacion() {
		return totVariacion;
	}

	public void setTotVariacion(Double totVariacion) {
		this.totVariacion = totVariacion;
	}

	public List<FaltanteDetalleEt> getFaltanteDet() {
		return faltanteDet;
	}

	public void setFaltanteDet(List<FaltanteDetalleEt> faltanteDet) {
		this.faltanteDet = faltanteDet;
	}

	public String getTotCantidadS() {
		return totCantidadS;
	}

	public void setTotCantidadS(String totCantidadS) {
		this.totCantidadS = totCantidadS;
	}

	public String getTotVariacionS() {
		return totVariacionS;
	}

	public void setTotVariacionS(String totVariacionS) {
		this.totVariacionS = totVariacionS;
	}

	public Double getTotVarN() {
		return totVarN;
	}

	public void setTotVarN(Double totVarN) {
		this.totVarN = totVarN;
	}

	public Double getTotVarP() {
		return totVarP;
	}

	public void setTotVarP(Double totVarP) {
		this.totVarP = totVarP;
	}

	public Double getTotCantN() {
		return totCantN;
	}

	public void setTotCantN(Double totCantN) {
		this.totCantN = totCantN;
	}

	public Double getTotCantP() {
		return totCantP;
	}

	public void setTotCantP(Double totCantP) {
		this.totCantP = totCantP;
	}

	public String getTotCantNS() {
		return totCantNS;
	}

	public void setTotCantNS(String totCantNS) {
		this.totCantNS = totCantNS;
	}

	public String getTotCantPS() {
		return totCantPS;
	}

	public void setTotCantPS(String totCantPS) {
		this.totCantPS = totCantPS;
	}

	public String getTotVarNS() {
		return totVarNS;
	}

	public void setTotVarNS(String totVarNS) {
		this.totVarNS = totVarNS;
	}

	public String getTotVarPS() {
		return totVarPS;
	}

	public void setTotVarPS(String totVarPS) {
		this.totVarPS = totVarPS;
	}

	public List<String> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(List<String> condiciones) {
		this.condiciones = condiciones;
	}

	public String getCondicionSeleccionada() {
		return condicionSeleccionada;
	}

	public void setCondicionSeleccionada(String condicionSeleccionada) {
		this.condicionSeleccionada = condicionSeleccionada;
	}

	public FaltanteCategoriaTopEt getFaltanteCatTopSelecc() {
		return faltanteCatTopSelecc;
	}

	public void setFaltanteCatTopSelecc(FaltanteCategoriaTopEt faltanteCatTopSelecc) {
		this.faltanteCatTopSelecc = faltanteCatTopSelecc;
	}

	public List<FaltanteCategoriaEt> getFaltanteCategorias() {
		return faltanteCategorias;
	}

	public void setFaltanteCategorias(List<FaltanteCategoriaEt> faltanteCategorias) {
		this.faltanteCategorias = faltanteCategorias;
	}

	@Override
	protected void onDestroy() {
		iFaltanteCatDao.remove();
		iFaltanteInvDao.remove();
		iFaltanteDetalleDao.remove();
	}
}
