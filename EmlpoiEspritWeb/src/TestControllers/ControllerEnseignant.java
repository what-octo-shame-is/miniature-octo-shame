package TestControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;

import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.gestion.ServicesRemote;
import tn.esprit.edt.persistance.Enseignant;

@ManagedBean(name = "controllerEnseignant",eager=true)
@SessionScoped
public class ControllerEnseignant implements Serializable {

	/*********************** Déclaration *****************************/

	@EJB
	private ServicesRemote serviceEns;
	private List<Enseignant> listeEns = new ArrayList<Enseignant>();
	private Enseignant nouvEns = new Enseignant();
	private Enseignant suppEns = new Enseignant();
	private Enseignant modifEns = new Enseignant();

	private Enseignant suppEns2 = new Enseignant();
	private String login;
	private String password;


	public ControllerEnseignant() {
		System.out.println("Creating Enseignant Controller MBean");
		listeEns = new ArrayList<Enseignant>();
	}

//	public String NomEnseignant() {
//		return serviceEns.GetNomEnseignant(SelectedEns);
//	}
//
//	public String NomEnseignantByID() {
//		return serviceEns.GetNomEnseignantByID(SelectedEns);
//	}

//	public String authentif() {
//
//		System.out.println(serviceEns.authentifier(SelectedEns, password));
//		return serviceEns.authentifierEns(SelectedEns, password);
//
//	}

//	public void selectEnsListener(AjaxBehaviorEvent event) {
//		System.out.println("Selected : " + SelectedEns);
//	}

	// public String deleteEnseignant() throws NumberFormatException,
	// NonexistentEntityException {
	//
	// suppEns = (Enseignant) tableEns.getRowData();
	// String num = suppEns.getId();
	// serviceEns.destroyEns(Integer.parseInt(num));
	// listeEns = new ArrayList<Enseignant>(serviceEns.getListEns());
	//
	// return "oki";
	//
	// }
//	public String FindIDbyNom() {
//		return serviceEns.findIDbyNom(SelectedEns);
//	}

	public String FindIDbyNom(String nom) {
		return serviceEns.findIDbyNom(nom);
	}

	public String ajouterEnseignant() {
		// nouvAbs.setType("ens");
		serviceEns.create(nouvEns);
		listeEns = new ArrayList<Enseignant>(serviceEns.getListEns());
		nouvEns = new Enseignant();
		// String navigationTo = "Authentifier.xhtml?faces-redirect=true";

		return "ajt";

	}

	public String actualiser() {
		nouvEns = new Enseignant();
		return "add";
	}

	public String modifierEnseignant() throws NonexistentEntityException,
			Exception {
		System.out.println("modif");
		serviceEns.edit(modifEns);
		System.out.println("tttttttt");

		return "modif";
	}

	public void onEdit(RowEditEvent event) throws NonexistentEntityException,
			Exception {
		FacesMessage msg = new FacesMessage("Enseignant Modifié",
				((Enseignant) event.getObject()).getId());
		serviceEns.edit((Enseignant) event.getObject());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void editEvent(String id) {
		System.out.print(id);
		nouvEns = serviceEns.findEnseignantString(id);
	}

	/*********************** Getter & Setter *****************************/

	public List<Enseignant> getListeEns() {
		listeEns = new ArrayList<Enseignant>(serviceEns.getListEns());
		return listeEns;
	}

	public ServicesRemote getServiceEns() {
		return serviceEns;
	}

	public void setServiceEns(Services serviceEns) {
		this.serviceEns = serviceEns;
	}

	public void setListeEns(List<Enseignant> listeEns) {
		this.listeEns = listeEns;
	}

	public Enseignant getNouvEns() {
		return nouvEns;
	}

	public void setNouvEns(Enseignant nouvEns) {
		this.nouvEns = nouvEns;
	}

	public Enseignant getSuppEns() {
		return suppEns;
	}

	public void setSuppEns(Enseignant suppEns) {
		this.suppEns = suppEns;
	}

	public Enseignant getModifEns() {
		return modifEns;
	}

	public void setModifEns(Enseignant modifEns) {
		this.modifEns = modifEns;
	}

	public Enseignant getSuppEns2() {
		return suppEns2;
	}

	public void setSuppEns2(Enseignant suppEns2) {
		this.suppEns2 = suppEns2;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

//	public String getSelectedEns() {
//		return SelectedEns;
//	}
//
//	public void setSelectedEns(String selectedEns) {
//		SelectedEns = selectedEns;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
