package planning;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.persistence.EntityManager;

import planning.PlanningForm.EDT.CaseCreneau.SousCasePrestation;
import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.gestion.ServicesRemote;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Pool;
import tn.esprit.edt.persistance.Prestation;



public class GererPlanning {
	@EJB
	ServicesRemote servicesRemote;
	private PlanningForm planningForm = new PlanningForm();
	/**
	 * Affiche la page du planning compl�te (s�lection entit�s, emploi du temps
	 * correspondant et liste des prestations non cas�es).
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param form
	 *            The optional ActionForm bean for this request.
	 * @param request
	 *            The HTTP Request we are processing.
	 * @param response
	 *            The HTTP Response we are processing.
	 * @throws java.lang.Exception
	 * @return
	 */
	public String afficher()
			throws Exception {


		return"planning";
	}

	/**
	 * R�pond � une requ�te AJAX qui va utiliser la r�ponse produite pour mettre
	 * � jour uniquement la partie emploi du temps de la page principale
	 * (fournit aussi quelques �ventuels messages d'information).
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param form
	 *            The optional ActionForm bean for this request.
	 * @param request
	 *            The HTTP Request we are processing.
	 * @param response
	 *            The HTTP Response we are processing.
	 * @throws java.lang.Exception
	 * @return
	 */
	public String mettreAJourEDT()
			throws Exception {

//		PlanningForm planForm = (PlanningForm) form;
//		SelectionForm selForm = (SelectionForm) request.getSession()
//				.getAttribute("SelectionForm");
//
//		this.remplirPlanningFormCommun(planForm, selForm);

		return "majedt";
	}

	/**
	 * Remplissage du PlanningForm (interm�diaire entre le contr�leur et la vue)
	 * commun entre les deux actions "afficher" et "mettreAJourEDT".
	 * 
	 * @param planForm
	 * @param selForm
	 */
	private void remplirPlanningFormCommun(PlanningForm planForm,
			SelectionForm selForm) {

	
//		

		planForm.setGroupes(servicesRemote.getList());


		planForm.setEnseignants(servicesRemote.getListEns());

		if (selForm != null) {
			int nbEntitesSel = selForm.getIdsGrpSelectionnes().length
					+ selForm.getIdsEnsSelectionnes().length;
			if (nbEntitesSel > 0) {
				// si au moins une entit� (grp ou ens) est s�lectionn�e :

				// r�cup�ration des groupes s�lectionn�s pour appara�tre sur
				// l'EDT
				int[] idsGrpSel = selForm.getIdsIntGrpSelectionnes();
				planForm.setGroupesSel(servicesRemote.findGroupesByIds(idsGrpSel));
				// r�cup�ration des enseignants s�lectionn�s pour appara�tre sur
				// l'EDT
				int[] idsEnsSel = selForm.getIdsIntEnsSelectionnes();
				planForm.setEnseignantsSel(servicesRemote
						.findEnseignantsByIds(idsEnsSel));

				// r�cup�ration des prestations qui doivent appara�tre sur l'EDT
				// PrestationJpaController ctPrest = new
				// PrestationJpaController();
				Set<Prestation> prestations = servicesRemote
						.findPrestationsSurEDT(idsGrpSel, idsEnsSel);
				planForm.setPrestationCasees(prestations);
				// remplissage de l'objet EDT
				PlanningForm.EDT edt = planForm.new EDT();
				int i = 0;
				
				for (Groupe g : planForm.getGroupesSel()) {
					for (Prestation p : prestations) {
						// si le groupe courant suit la prestation courante :
						if (p.getGroupes().contains(g)) {
							int numCreneau = p.getCreneau().getNumero();
							PlanningForm.EDT.CaseCreneau c = edt
									.getCase(numCreneau);
							List<SousCasePrestation> sousC = 
								new ArrayList<PlanningForm.EDT.CaseCreneau.SousCasePrestation>(c.getSousCases());
							sousC.get(i).setPrestation(p);
						}
					}
					i++;
				}
				for (Enseignant e : planForm.getEnseignantsSel()) {
					for (Prestation p : prestations) {
						// si l'enseignant courant assure la prestation courante
						// :
						if (p.getEnseignant().equals(e)) {
							int numCreneau = p.getCreneau().getNumero();
							PlanningForm.EDT.CaseCreneau c = edt
									.getCase(numCreneau);
							List<SousCasePrestation> sousC = 
								new ArrayList<PlanningForm.EDT.CaseCreneau.SousCasePrestation>(c.getSousCases());
							sousC.get(i).setPrestation(p);
						}
					}
					i++;
				}
				// une fois que l'objet EDT est rempli, il est pass� �
				// l'ActionForm idoine
				planForm.setPlanning(edt);
				// et l'on fussionne les sous-cases qui le permettent
				edt.fusionnerSousCases();
			}
		}
	}

	/**
	 * Remplissage du PlanningForm (interm�diaire entre le contr�leur et la vue)
	 * sp�cifique � l'action "afficher".
	 * 
	 * @param planForm
	 * @param selForm
	 */
	private void remplirPlanningFormSpe(PlanningForm planForm,
			SelectionForm selForm) {
		
		if (selForm != null) {
			int nbEntitesSel = selForm.getIdsGrpSelectionnes().length
					+ selForm.getIdsEnsSelectionnes().length;
			if (nbEntitesSel > 0) {
				// si au moins une entit� (grp ou ens) est s�lectionn�e :

				// r�cup�ration des identifiants des entit�s (groupes
				// ou enseignants qui ont �t� s�lectionn�es
				int[] idsGrpSel = selForm.getIdsIntGrpSelectionnes();
				int[] idsEnsSel = selForm.getIdsIntEnsSelectionnes();

				// r�cup�ration des prestations qui pourraient appara�tre
				// sur l'EDT, mais ne sont pas encore plac�es
				// PrestationJpaController ctPrest = new
				// PrestationJpaController();
				Set<Prestation> prestations = servicesRemote
						.findPrestationsNonCasees(idsGrpSel, idsEnsSel);
				planForm.setPrestationsNonCasees(prestations);
			}
		}
	}

	/**
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param form
	 *            The optional ActionForm bean for this request.
	 * @param request
	 *            The HTTP Request we are processing.
	 * @param response
	 *            The HTTP Response we are processing.
	 * @throws java.lang.Exception
	 * @return
	 */
	public String placerPrestation() throws Exception {
		

		

		// r�cup�ration de la prestation
		// PrestationJpaController ctPrest = new PrestationJpaController();
		Prestation prest = servicesRemote.findPrestation(planningForm
				.getIdPrestation());
		// r�cup�ration du cr�neau
		// CreneauJpaController ctCreneau = new CreneauJpaController();
		Creneau creneau = servicesRemote.findCreneau(planningForm.getNumCreneau());

		List<String> msg = verifierAucunConflit(prest, creneau);
		Set<String> seMSG = new HashSet<String>(msg);
		if (msg.size() > 0) { // s'il y a des conflits, la prestation ne pourra
								// pas �tre plac�e
			planningForm.setTypeMessage(PlanningForm.TypeMessage.ERREUR);
			planningForm.setTitreMessages("Placement impossible");
			
			planningForm.setMessages(seMSG);
		} else { // pas de conflit => la prestation est plac�e dans le cr�neau
			prest.setCreneau(creneau);
			servicesRemote.edit(prest);

			planningForm.setTypeMessage(PlanningForm.TypeMessage.SUCCES);
			planningForm.setTitreMessages("Placement effectu�");
			msg.add("La prestation dont l'id est " + prest.getCodeCl()
					+ " et dont l'enseignant est " + prest.getEnseignant()
					+ " a bien �t� plac�e le " + creneau + ".");
			planningForm.setMessages(seMSG);
		}
	////prestation.getcodecl **********************************************
		return "mettreAJourEDT";
	}

	/**
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param form
	 *            The optional ActionForm bean for this request.
	 * @param request
	 *            The HTTP Request we are processing.
	 * @param response
	 *            The HTTP Response we are processing.
	 * @throws java.lang.Exception
	 * @return
	 * @throws NonexistentEntityException 
	 */
	public String remplacerPrestation() throws NonexistentEntityException, Exception{

	

		// r�cup�ration des prestations
		
		Prestation prestAPlacer = servicesRemote.findPrestation(planningForm
				.getIdPrestation());
		Prestation prestARemplacer = servicesRemote.findPrestation(planningForm
				.getIdPrestARemplacer());
		// r�cup�ration du cr�neau
		Creneau creneau = prestARemplacer.getCreneau();

		EntityManager em = servicesRemote.getEm();
		em.getTransaction().begin(); // d�marrage d'une transaction pour le
										// remplacement de prestations

		// Avant de v�rifier l'absence de conflit,
		// il faut enlever de l'EDT la prestation � remplacer :
		prestARemplacer.setCreneau(null);
          servicesRemote.edit(prestARemplacer);

		List<String> msg = verifierAucunConflit(prestAPlacer, creneau);
		Set<String> seMSG = new HashSet<String>(msg);
		if (msg.size() > 0) { // s'il y a des conflits, la prestation ne pourra
								// pas �tre remplac�e
			// transaction annul�e (la prestation � remplacer � remise sur
			// l'EDT)
			em.getTransaction().rollback();
			planningForm.setTypeMessage(PlanningForm.TypeMessage.ERREUR);
			planningForm.setTitreMessages("Placement impossible");
			planningForm.setMessages(seMSG);
		} else { // pas de conflit => la prestation est remplac�e
			prestAPlacer.setCreneau(creneau);
			servicesRemote.edit(prestAPlacer);
			em.getTransaction().commit(); // transaction valid�e

			planningForm.setTypeMessage(PlanningForm.TypeMessage.SUCCES);
			planningForm.setTitreMessages("Remplacement effectu�");
			msg.add("La prestation dont l'id est " + prestARemplacer.getCodeCl()
					+ " et dont l'enseignant est "
					+ prestARemplacer.getEnseignant()
					+ " a bien �t� retir�e de l'emploi du temps.");
			msg.add("La prestation dont l'id est " + prestAPlacer.getCodeCl()
					+ " et dont l'enseignant est "
					+ prestAPlacer.getEnseignant() + " a bien �t� plac�e le "
					+ prestAPlacer.getCreneau() + ".");
			planningForm.setMessages(seMSG);
		}
		return "mettreAJourEDT";
	}

	/**
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param form
	 *            The optional ActionForm bean for this request.
	 * @param request
	 *            The HTTP Request we are processing.
	 * @param response
	 *            The HTTP Response we are processing.
	 * @throws java.lang.Exception
	 * @return
	 */
	public String enleverPrestation() throws Exception {
		

	

		// PrestationJpaController ctPrest = new PrestationJpaController();
		Prestation prest = servicesRemote.findPrestation(planningForm
				.getIdPrestation());
		prest.setCreneau(null);
		servicesRemote.edit(prest);

		planningForm.setTypeMessage(PlanningForm.TypeMessage.SUCCES);
		planningForm.setTitreMessages("Prestation retir�e");
		List<String> msg = new ArrayList<String>(1);
		
		msg.add("La prestation dont l'id est " + prest.getCodeCl()
				+ " et dont l'enseignant est " + prest.getEnseignant()
				+ " a bien �t� retir�e de l'emploi du temps.");
		Set<String> seMSG = new HashSet<String>(msg);
		planningForm.setMessages(seMSG);

		return "mettreAJourEDT";
	}

	/**
	 * Effectue les v�rifications n�cessaires pour s'assurer qu'il n'y a pas de
	 * conflit lorsque la prestation est plac�e dans le cr�neau.
	 * 
	 * @param prest
	 *            prestation � placer
	 * @param creneau
	 *            cr�neau dans lequel on veut placer la prestation
	 * @return liste des conflits
	 */
	private List<String> verifierAucunConflit(Prestation prest, Creneau creneau) {
		
		// v�rification des disponibilit�s de l'enseignant et des groupes :
		List<String> msg = new ArrayList<String>();
		if (prest.getEnseignant().estIndispo(creneau))
			msg.add("L'enseignant " + prest.getEnseignant() + " n'est pas "
					+ "disponible le " + creneau + ".");
		for (Groupe groupe : prest.getGroupes())
			if (groupe.estIndispo(creneau))
				msg.add("Le groupe '" + groupe + "' n'est pas "
						+ "disponible le " + creneau + ".");

		// puis v�rification qu'il n'y a pas de conflit avec d'autres
		// prestations

		// CreneauJpaController ctCreneau = new CreneauJpaController();
		int nbPrest = servicesRemote.countPrestationsPour(creneau,
				prest.getEnseignant(), prest.getGroupes());
		if (nbPrest > 0) { // conflit, la prestation ne peut pas �tre plac�e
							// dans ce cr�neau
			// recherche des conflits
			if (prest.getEnseignant().assurePrestationLe(creneau))
				msg.add("L'enseignant " + prest.getEnseignant()
						+ " assure d�j� " + "une prestation le " + creneau
						+ ".");
			for (Groupe groupe : prest.getGroupes())
				if (servicesRemote.suitPrestation(groupe,creneau))
					msg.add("Le groupe '" + groupe + "' suit d�j� "
							+ "une prestation le " + creneau + ".");
		}

		// derni�re v�rification, les �ventuels pools associ�s au module de la
		// prestation
		// ont-ils encore de la place pour une prestation dans ce cr�neau
		// PoolJpaController ctPool = new PoolJpaController();
		List<Pool> pools = new ArrayList<Pool>(prest.getModule().getPools());
		Map<Pool, Integer> nbPrestRestant = servicesRemote.getNbPrestRestant(creneau,
				prest.getModule().getPools());
		for (Pool pool : pools) {
			Integer nbRestant = nbPrestRestant.get(pool);
			if (nbRestant != null && nbRestant < 1)
				msg.add("Le pool '" + pool.getLibelle()
						+ "' est satur� pour ce cr�neau.");
		}
		return msg; // retourne la liste des conflits
	}

}
