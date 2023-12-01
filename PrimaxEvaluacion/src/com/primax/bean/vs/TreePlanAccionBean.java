package com.primax.bean.vs;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.primax.bean.vs.base.BaseBean;
import com.primax.jpa.gen.PlanAccionOrganizacion;
import com.primax.jpa.pla.CheckListEjecucionPlnAdjuntoEt;
import com.primax.jpa.pla.PlanAccionAnioEt;
import com.primax.jpa.pla.PlanAccionChekListEt;
import com.primax.jpa.pla.PlanAccionEstacionEt;
import com.primax.jpa.pla.PlanAccionMesEt;
import com.primax.jpa.pla.PlanAccionZonaEt;
import com.primax.jpa.sec.UsuarioEt;
import com.primax.srv.idao.ICheckListEjecucionPlnAdjuntoDao;
import com.primax.srv.idao.IPlanAccionAnioDao;
import com.primax.srv.idao.IPlanAccionChekListDao;
import com.primax.srv.idao.IPlanAccionEstacionDao;
import com.primax.srv.idao.IPlanAccionMesDao;
import com.primax.srv.idao.IPlanAccionZonaDao;
import com.primax.srv.idao.IUsuarioDao;

@Named("TreePlanAccionBn")
@ViewScoped
public class TreePlanAccionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IUsuarioDao iUsuarioDao;
	@EJB
	private IPlanAccionMesDao iPlanAccionMesDao;
	@EJB
	private IPlanAccionAnioDao iPlanAccionAnioDao;
	@EJB
	private IPlanAccionZonaDao iPlanAccionZonaDao;
	@EJB
	private IPlanAccionEstacionDao iPlanAccionEstacionDao;
	@EJB
	private IPlanAccionChekListDao iPlanAccionChekListDao;
	@EJB
	private ICheckListEjecucionPlnAdjuntoDao iCheckListEjecucionPlnAdjuntoDao;

	private TreeNode root;
	private TreeNode selectedNode;
	private PlanAccionOrganizacion planAccionOrganizacionSelect;

	@Override
	protected void init() {
		generar();
		disenarTree();

	}

	public void generar() {
		try {
			UsuarioEt usuario = iUsuarioDao.getUsuarioId(1L);
			iPlanAccionAnioDao.generar(usuario.getIdUsuario());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método generar " + " " + e.getMessage());
		}

	}

	public void disenarTree() {
		Long idPlanAccion = 0L;
		try {
			root = new DefaultTreeNode("Files", null);
			List<PlanAccionAnioEt> anios = iPlanAccionAnioDao.getPlanAccionAnioList();
			for (PlanAccionAnioEt anio : anios) {
				idPlanAccion = anio.getIdPlanAccionAnio();
				TreeNode node0 = new DefaultTreeNode(
						new PlanAccionOrganizacion(0L, 0L, anio.getAnio().toString(), "Carpeta"), root);

				List<PlanAccionMesEt> meses = iPlanAccionMesDao.getPlanAccionMesList(idPlanAccion, anio.getAnio());
				for (PlanAccionMesEt mes : meses) {
					TreeNode node00 = new DefaultTreeNode(
							new PlanAccionOrganizacion(0L, 0L, mes.getMesLetra().toString(), "Carpeta"), node0);
					List<PlanAccionZonaEt> zonas = iPlanAccionZonaDao.getPlanAccionZonaList(idPlanAccion,
							anio.getAnio(), mes.getMesNumero());
					for (PlanAccionZonaEt zona : zonas) {
						TreeNode node000 = new DefaultTreeNode(
								new PlanAccionOrganizacion(0L, 0L, zona.getZona(), "Carpeta"), node00);

						List<PlanAccionEstacionEt> estaciones = iPlanAccionEstacionDao.getPlanAccionMesList(
								idPlanAccion, anio.getAnio(), mes.getMesNumero(), zona.getIdZona());
						for (PlanAccionEstacionEt estacion : estaciones) {
							TreeNode node0000 = new DefaultTreeNode(
									new PlanAccionOrganizacion(0L, 0L, estacion.getAgencia(), "Carpeta"), node000);

							List<PlanAccionChekListEt> checks = iPlanAccionChekListDao.getPlanAccionChekListList(
									idPlanAccion, anio.getAnio(), mes.getMesNumero(), zona.getIdZona(),
									estacion.getIdAgencia());
							for (PlanAccionChekListEt check : checks) {
								List<CheckListEjecucionPlnAdjuntoEt> adjuntos = iCheckListEjecucionPlnAdjuntoDao
										.getAdjuntoList(check.getIdCheckListEjecucion());

								if (adjuntos.isEmpty()) {
									TreeNode node00000 = new DefaultTreeNode(
											new PlanAccionOrganizacion(check.getIdCheckListEjecucion(), 0L,
													check.getDescripcion(), "Carpeta"),
											node0000);
									CheckListEjecucionPlnAdjuntoEt adj = new CheckListEjecucionPlnAdjuntoEt();
									adj.setIdCheckListEjecucion(check.getIdCheckListEjecucion());
									adj.setNombreAdjunto("PLAN DE ACCIÓN Y SEGUIMIENTO");
									node00000.getChildren()
											.add(new DefaultTreeNode(
													new PlanAccionOrganizacion(adj.getIdCheckListEjecucion(),
															check.getIdAgencia(), adj.getNombreAdjunto(), "Plan")));

								} else {
									TreeNode node00000 = new DefaultTreeNode(
											new PlanAccionOrganizacion(check.getIdCheckListEjecucion(), 0L,
													check.getDescripcion(), "Carpeta"),
											node0000);
									CheckListEjecucionPlnAdjuntoEt adj = new CheckListEjecucionPlnAdjuntoEt();
									adj.setIdCheckListEjecucion(check.getIdCheckListEjecucion());
									adj.setNombreAdjunto("PLAN DE ACCIÓN Y SEGUIMIENTO");
									node00000.getChildren()
											.add(new DefaultTreeNode(
													new PlanAccionOrganizacion(adj.getIdCheckListEjecucion(),
															check.getIdAgencia(), adj.getNombreAdjunto(), "Plan")));
									for (CheckListEjecucionPlnAdjuntoEt adjunto : adjuntos) {
										node00000.getChildren()
												.add(new DefaultTreeNode(new PlanAccionOrganizacion(
														adjunto.getCheckListEjecucion().getIdCheckListEjecucion(), 0L,
														adjunto.getNombreAdjunto(), "Adjunto")));
									}
								}
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :Método disenarTree " + " " + e.getMessage());
		}
	}

	public void nodeSelect(PlanAccionOrganizacion planAccionOrganizacion) {
		System.out.println("Información" + " " + planAccionOrganizacion.getNombre());
	}

	public boolean customFilter(TreeNode treeNode, Object filter, Locale locale) {
		if (treeNode.getData() == null || filter == null) {
			return true;
		}
		String filterText = filter.toString().trim().toLowerCase(locale);
		if (filterText.isEmpty()) {
			return true;
		}
		return ((String) treeNode.getData()).toLowerCase(locale).contains(filterText);
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText.isEmpty()) {
			return true;
		}
		PlanAccionOrganizacion customer = (PlanAccionOrganizacion) value;
		return customer.getNombre().toLowerCase().contains(filterText)
				|| customer.getTipo().toLowerCase().contains(filterText);
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public PlanAccionOrganizacion getPlanAccionOrganizacionSelect() {
		return planAccionOrganizacionSelect;
	}

	public void setPlanAccionOrganizacionSelect(PlanAccionOrganizacion planAccionOrganizacionSelect) {
		this.planAccionOrganizacionSelect = planAccionOrganizacionSelect;
	}

	@Override
	protected void onDestroy() {
		iPlanAccionMesDao.remove();
		iPlanAccionAnioDao.remove();
		iPlanAccionZonaDao.remove();
		iPlanAccionChekListDao.remove();
		iPlanAccionEstacionDao.remove();
		iCheckListEjecucionPlnAdjuntoDao.remove();
	}

}
