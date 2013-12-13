package TestControllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Groupe;

@ManagedBean(name = "controllerGroupe2")
@SessionScoped
public class ControllerGroupe2 {
	@EJB
	Services serviceGroupe;

	private List<Groupe> listeGroupe = new ArrayList<Groupe>();
	private Groupe nouvGroupe = new Groupe();
	private Groupe suppGroupe = new Groupe();
	private Groupe modifGroupe = new Groupe();
	private UIData tableGroupe;
	private Groupe suppAbs2 = new Groupe();

	



	public ControllerGroupe2() {
		super();
		listeGroupe = new ArrayList<Groupe>();
	}

	

	public String actualiser() {
		nouvGroupe = new Groupe();
		return "add";
	}
	

	


	/*********************** Getter & Setter *****************************/

	public List<Groupe> getListeGroupe() {
		listeGroupe = new ArrayList<Groupe>(serviceGroupe.getList());
//		Collections.sort(list);
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

}
