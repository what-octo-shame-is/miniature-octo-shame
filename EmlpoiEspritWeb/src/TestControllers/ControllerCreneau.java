package TestControllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.exceptions.PreexistingEntityException;
import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Creneau;



@ManagedBean
@SessionScoped
public class ControllerCreneau {

	EntityManager em;	
	@EJB
	Services services;
	private List<Creneau> listeCreneaus;
	private Creneau creneau;
	private UIData tabletud;

	public ControllerCreneau() {
		listeCreneaus = new ArrayList<Creneau>();
		creneau = new Creneau();
	}

	/**************************** Methodes 
	 * @throws Exception 
	 * @throws PreexistingEntityException **********************************/


	public String ajouterCreneauweb() throws PreexistingEntityException, Exception {
		services.create(creneau);
		listeCreneaus = new ArrayList<Creneau>(services.getAllCreneaux());
		creneau = new Creneau();
		return "ajt";

	}
	public String seDeconnecter() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.removeAttribute("islogged");
		String navigationTo = "Acceuil.xhtml?faces-redirect=true";
		return navigationTo;
	}

	
	public void editEvent(int nom) {	
		System.out.print(nom);
		creneau = services.findCreneau(nom);	
	}
	
	
	 public void edition(ActionEvent actionEvent) throws NonexistentEntityException, Exception {
		 services.edit(creneau);
	    }

 

	public String deleteCreneauweb (Creneau Creneau) throws NonexistentEntityException {
		int nom = Creneau.getNumero();
		services.destroyCreneau(nom);
		listeCreneaus =  new ArrayList<Creneau>(services.getAllCreneaux());
		return "supp";
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public List<Creneau> getListeCreneaus() {
		listeCreneaus= new ArrayList<Creneau>(services.getAllCreneaux());
		return listeCreneaus;
	}

	public void setListeCreneaus(List<Creneau> listeCreneaus) {
		
		this.listeCreneaus = listeCreneaus;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}

	public UIData getTabletud() {
		return tabletud;
	}

	public void setTabletud(UIData tabletud) {
		this.tabletud = tabletud;
	}


}
