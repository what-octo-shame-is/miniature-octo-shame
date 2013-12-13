package TestControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;

import tn.esprit.edt.gestion.GroupeGesLocal;
import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Prestation;

@ManagedBean(name = "controllerGroupe")
@SessionScoped
public class ControllerGroupe {
	public List<Prestation> getPrestation(Groupe groupe){
		List<Prestation> list=new ArrayList<Prestation>();
		Set<Prestation> prestations=gesLocal.ModulesParGroupe(groupe.getId());
		list.addAll(prestations);
		return list; 
	}
	@EJB
	Services serviceGroupe;
	@EJB
	GroupeGesLocal gesLocal;
	private String grp, codeCl;

	private List<Groupe> listeGroupe = new ArrayList<Groupe>();
	private Groupe nouvGroupe = new Groupe();
	private Groupe suppGroupe = new Groupe();
	private Groupe modifGroupe = new Groupe();
	private UIData tableGroupe;
	private Groupe suppAbs2 = new Groupe();
	/*
	 * /
	 */
	// private List<Prestation> listeModule = new ArrayList<Prestation>();
	private List<String> listeModule = new ArrayList<String>();
	private List<Groupe> groupes = new ArrayList<Groupe>();
	private List<Prestation> prestations = new ArrayList<Prestation>();
	private List<Prestation> listeModuleParGroupe = new ArrayList<Prestation>();

	public ControllerGroupe() {
		super();
		listeGroupe = new ArrayList<Groupe>();
		prestations = new ArrayList<Prestation>();
	}

	public String actualiser() {
		nouvGroupe = new Groupe();
		return "add";
	}

	/*********************** Getter & Setter *****************************/

	public List<Groupe> getListeGroupe() {
		listeGroupe = new ArrayList<Groupe>(serviceGroupe.getList());
		return listeGroupe;
	}

	public Services getServiceGroupe() {
		return serviceGroupe;
	}

	public void setServiceGroupe(Services serviceGroupe) {
		this.serviceGroupe = serviceGroupe;
	}

	public void setListeGroupe(List<Groupe> listeGroupe) {
		this.listeGroupe = listeGroupe;
	}

	public Groupe getNouvGroupe() {
		return nouvGroupe;
	}

	public void setNouvGroupe(Groupe nouvGroupe) {
		this.nouvGroupe = nouvGroupe;
	}

	public Groupe getSuppGroupe() {
		return suppGroupe;
	}

	public void setSuppGroupe(Groupe suppGroupe) {
		this.suppGroupe = suppGroupe;
	}

	public Groupe getModifGroupe() {
		return modifGroupe;
	}

	public void setModifGroupe(Groupe modifGroupe) {
		this.modifGroupe = modifGroupe;
	}

	public UIData getTableGroupe() {
		return tableGroupe;
	}

	public void setTableGroupe(UIData tableGroupe) {
		this.tableGroupe = tableGroupe;
	}

	public Groupe getSuppAbs2() {
		return suppAbs2;
	}

	public void setSuppAbs2(Groupe suppAbs2) {
		this.suppAbs2 = suppAbs2;
	}

	public List<String> getListeModule() {

		for (Groupe groupe : listeGroupe) {
			for (Prestation prestation : prestations) {
				if (groupe.getId().equals(prestation.getCodeCl())) {
					listeModule.add(prestation.getCodeModule());
				}
			}
		}

		return listeModule;
	}

	public void setListeModule(List<String> listeModule) {
		this.listeModule = listeModule;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public List<Prestation> getPrestations() {
		return prestations;
	}

	public void setPrestations(List<Prestation> prestations) {
		this.prestations = prestations;
	}

	public String getGrp() {
		return grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}

	public String getCodeCl() {
		return grp;
	}

	public void setCodeCl(String codeCl) {
		this.codeCl = codeCl;
	}

	/**
	 * @return the listeModuleParGroupe
	 */
	public List<Prestation> getListeModuleParGroupe() {
		groupes = new ArrayList<Groupe>(serviceGroupe.getList());
		for (Groupe grp : groupes) {
			listeModuleParGroupe = new ArrayList<Prestation>(
					serviceGroupe.findModulesByGroupe(grp.getId()));
		}

		return listeModuleParGroupe;
	}

	/**
	 * @param listeModuleParGroupe
	 *            the listeModuleParGroupe to set
	 */
	public void setListeModuleParGroupe(List<Prestation> listeModuleParGroupe) {
		this.listeModuleParGroupe = listeModuleParGroupe;
	}
}
