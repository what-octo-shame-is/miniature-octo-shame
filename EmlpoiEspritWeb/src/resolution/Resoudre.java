package resolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.gestion.ServicesRemote;
import tn.esprit.edt.solveur.SolveurEDT;
import tn.esprit.edt.solveur.SolveurEDTCHOCO;

@ManagedBean("resoudre")
@SessionScoped
public class Resoudre {
private	String s;
private String message;
private	List<String> l =new ArrayList<String>();
private List<String> list =new ArrayList<String>();
     public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	String m;
	public Resoudre() {

	}

	/**
	 * Instancie un solveur d'emploi du temps.
	 * 
	 * @return
	 */
	@EJB
	Services servicesRemote;
	private ResolutionForm rForm = new ResolutionForm();

	private SolveurEDT instancierSolveur() {

		return new SolveurEDTCHOCO(servicesRemote.findEnseignantsAvecRel(),
				servicesRemote.findModulesAvecRel(),
				servicesRemote.findPoolsAvecRel(),
				servicesRemote.findPrestationsAvecRel(),
				servicesRemote.findGroupesAvecRel(),
				servicesRemote.findCreneauEntities());
	}

	/**
	 * s V�rifie la coh�rence de l'emploi du temps tel qu'il est actuellement et
	 * retourne les messages g�n�r�s par cette v�rification.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public String verifierCoherenceEDT() throws Exception {
		SolveurEDT solveur = instancierSolveur();
		
l.clear();
s= "";
m="";
		String[] conflits = solveur.verifier();
		Set<String> msg;
		if (conflits.length > 0) {
			rForm.setTypeMessage(ResolutionForm.TypeMessage.ERREUR);
			rForm.setTitreMessages("Conflits trouv�s");
			List<String> list = new ArrayList<String>(Arrays.asList(conflits));

			msg = new HashSet<String>(list);
			rForm.setMessages(msg);
			 s = rForm.getTitreMessages();
				l = new ArrayList<String>(rForm.getMessages());
				m="Essayer de changer le creneau";
		} else {
			rForm.setTypeMessage(ResolutionForm.TypeMessage.INFO);
			rForm.setTitreMessages("Pas de conflit trouv�");
			List<String> list = new ArrayList<String>(1);
			msg = new HashSet<String>(list);
			msg.add("Aucune incoh�rence n'a �t� d�tect�e dans "
					+ "l'emploi du temps tel qu'il est actuellement.");
			rForm.setMessages(msg);
			System.out.println(msg);
			 s = rForm.getTitreMessages();
				l = new ArrayList<String>(rForm.getMessages());
				
		}
		return "envoyermsg";
	}

	/**
	 * Essaye de r�soudre le probl�me de l'emploi du temps, en effectuant une
	 * v�rification au pr�alable.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public String resoudreEDT() throws Exception {
		list.clear();
		message= "";

		SolveurEDT solveur = instancierSolveur();

		String[] conflits = solveur.verifier();
		Set<String> msg;
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(conflits));

		Set<String> setS = new HashSet<String>(list);
		if (conflits.length > 0) {
			rForm.setTypeMessage(ResolutionForm.TypeMessage.ERREUR);
			rForm.setTitreMessages("Conflits trouv�s");

			msg = setS;
			rForm.setMessages(msg);
			rForm.setResolu(false);
			message = rForm.getTitreMessages();
		 list = new ArrayList<String>(rForm.getMessages());
			
		} else {
			boolean estResolu = solveur.resoudre();
			rForm.setResolu(estResolu);
			if (estResolu) {
				rForm.setTypeMessage(ResolutionForm.TypeMessage.SUCCES);
				rForm.setTitreMessages("Une solution a �t� trouv�e. Toutes les prestations "
						+ "ont �t� plac�es sur l'emploi du temps ");
				msg = new HashSet<String>();
				msg.add("Une solution a �t� trouv�e. Toutes les prestations "
						+ "ont �t� plac�es sur l'emploi du temps et tous les "
						+ "groupes suivent une prestation de chaque module "
						+ "auquel ils sont inscrits.");
				rForm.setMessages(msg);
				System.out.println(msg);
				message = rForm.getTitreMessages();
				list = new ArrayList<String>(rForm.getMessages());
			} else {
				rForm.setTypeMessage(ResolutionForm.TypeMessage.ERREUR);
				rForm.setTitreMessages("Aucune solution trouv�e");
				msg = new HashSet<String>();
				msg.add("Le solveur n'a trouv� aucune solution, le "
						+ "probl�me semble sur-contraint.");
				rForm.setMessages(msg);
				System.out.println(msg);
				message = rForm.getTitreMessages();
				list = new ArrayList<String>(rForm.getMessages());
			}
		}
		return "envoyermsg";
	}
//	public void addError(ActionEvent actionEvent) {
//		FacesContext.getCurrentInstance()
//				.addMessage(
//						null,
//						new FacesMessage(FacesMessage.SEVERITY_ERROR,
//								rForm.getTitreMessages(),
//								l.toString()));
//	}

	public ServicesRemote getServicesRemote() {
		return servicesRemote;
	}

	public void setServicesRemote(Services servicesRemote) {
		this.servicesRemote = servicesRemote;
	}

	public ResolutionForm getrForm() {
		rForm = new ResolutionForm(servicesRemote);
		return rForm;
	}

	public void setrForm(ResolutionForm rForm) {
		this.rForm = rForm;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<String> getL() {
		return l;
	}

	public void setL(List<String> l) {
		this.l = l;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
