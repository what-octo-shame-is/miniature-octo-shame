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
	 * Affiche la page du planning complète (sélection entités, emploi du temps
	 * correspondant et liste des prestations non casées).
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
	 * Répond à une requête AJAX qui va utiliser la réponse produite pour mettre
	 * à jour uniquement la partie emploi du temps de la page principale
	 * (fournit aussi quelques éventuels messages d'information).
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
	 * Remplissage du PlanningForm (intermédiaire entre le contrôleur et la vue)
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
				// si au moins une entité (grp ou ens) est sélectionnée :

				// récupération des groupes sélectionnés pour apparaître sur
				// l'EDT
				int[] idsGrpSel = selForm.getIdsIntGrpSelectionnes();
				planForm.setGroupesSel(servicesRemote.findGroupesByIds(idsGrpSel));
				// récupération des enseignants sélectionnés pour apparaître sur
				// l'EDT
				int[] idsEnsSel = selForm.getIdsIntEnsSelectionnes();
				planForm.setEnseignantsSel(servicesRemote
						.findEnseignantsByIds(idsEnsSel));

				// récupération des prestations qui doivent apparaître sur l'EDT
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
				// une fois que l'objet EDT est rempli, il est passé à
				// l'ActionForm idoine
				planForm.setPlanning(edt);
				// et l'on fussionne les sous-cases qui le permettent
				edt.fusionnerSousCases();
			}
		}
	}

	/**
	 * Remplissage du PlanningForm (intermédiaire entre le contrôleur et la vue)
	 * spécifique à l'action "afficher".
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
				// si au moins une entité (grp ou ens) est sélectionnée :

				// récupération des identifiants des entités (groupes
				// ou enseignants qui ont été sélectionnées
				int[] idsGrpSel = selForm.getIdsIntGrpSelectionnes();
				int[] idsEnsSel = selForm.getIdsIntEnsSelectionnes();

				// récupération des prestations qui pourraient apparaître
				// sur l'EDT, mais ne sont pas encore placées
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
		

		

		// récupération de la prestation
		// PrestationJpaController ctPrest = new PrestationJpaController();
		Prestation prest = servicesRemote.findPrestation(planningForm
				.getIdPrestation());
		// récupération du créneau
		// CreneauJpaController ctCreneau = new CreneauJpaController();
		Creneau creneau = servicesRemote.findCreneau(planningForm.getNumCreneau());

		List<String> msg = verifierAucunConflit(prest, creneau);
		Set<String> seMSG = new HashSet<String>(msg);
		if (msg.size() > 0) { // s'il y a des conflits, la prestation ne pourra
								// pas être placée
			planningForm.setTypeMessage(PlanningForm.TypeMessage.ERREUR);
			planningForm.setTitreMessages("Placement impossible");
			
			planningForm.setMessages(seMSG);
		} else { // pas de conflit => la prestation est placée dans le créneau
			prest.setCreneau(creneau);
			servicesRemote.edit(prest);

			planningForm.setTypeMessage(PlanningForm.TypeMessage.SUCCES);
			planningForm.setTitreMessages("Placement effectué");
			msg.add("La prestation dont l'id est " + prest.getCodeCl()
					+ " et dont l'enseignant est " + prest.getEnseignant()
					+ " a bien été placée le " + creneau + ".");
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

	

		// récupération des prestations
		
		Prestation prestAPlacer = servicesRemote.findPrestation(planningForm
				.getIdPrestation());
		Prestation prestARemplacer = servicesRemote.findPrestation(planningForm
				.getIdPrestARemplacer());
		// récupération du créneau
		Creneau creneau = prestARemplacer.getCreneau();

		EntityManager em = servicesRemote.getEm();
		em.getTransaction().begin(); // démarrage d'une transaction pour le
										// remplacement de prestations

		// Avant de vérifier l'absence de conflit,
		// il faut enlever de l'EDT la prestation à remplacer :
		prestARemplacer.setCreneau(null);
          servicesRemote.edit(prestARemplacer);

		List<String> msg = verifierAucunConflit(prestAPlacer, creneau);
		Set<String> seMSG = new HashSet<String>(msg);
		if (msg.size() > 0) { // s'il y a des conflits, la prestation ne pourra
								// pas être remplacée
			// transaction annulée (la prestation à remplacer à remise sur
			// l'EDT)
			em.getTransaction().rollback();
			planningForm.setTypeMessage(PlanningForm.TypeMessage.ERREUR);
			planningForm.setTitreMessages("Placement impossible");
			planningForm.setMessages(seMSG);
		} else { // pas de conflit => la prestation est remplacée
			prestAPlacer.setCreneau(creneau);
			servicesRemote.edit(prestAPlacer);
			em.getTransaction().commit(); // transaction validée

			planningForm.setTypeMessage(PlanningForm.TypeMessage.SUCCES);
			planningForm.setTitreMessages("Remplacement effectué");
			msg.add("La prestation dont l'id est " + prestARemplacer.getCodeCl()
					+ " et dont l'enseignant est "
					+ prestARemplacer.getEnseignant()
					+ " a bien été retirée de l'emploi du temps.");
			msg.add("La prestation dont l'id est " + prestAPlacer.getCodeCl()
					+ " et dont l'enseignant est "
					+ prestAPlacer.getEnseignant() + " a bien été placée le "
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
		planningForm.setTitreMessages("Prestation retirée");
		List<String> msg = new ArrayList<String>(1);
		
		msg.add("La prestation dont l'id est " + prest.getCodeCl()
				+ " et dont l'enseignant est " + prest.getEnseignant()
				+ " a bien été retirée de l'emploi du temps.");
		Set<String> seMSG = new HashSet<String>(msg);
		planningForm.setMessages(seMSG);

		return "mettreAJourEDT";
	}

	/**
	 * Effectue les vérifications nécessaires pour s'assurer qu'il n'y a pas de
	 * conflit lorsque la prestation est placée dans le créneau.
	 * 
	 * @param prest
	 *            prestation à placer
	 * @param creneau
	 *            créneau dans lequel on veut placer la prestation
	 * @return liste des conflits
	 */
	private List<String> verifierAucunConflit(Prestation prest, Creneau creneau) {
		
		// vérification des disponibilités de l'enseignant et des groupes :
		List<String> msg = new ArrayList<String>();
		if (prest.getEnseignant().estIndispo(creneau))
			msg.add("L'enseignant " + prest.getEnseignant() + " n'est pas "
					+ "disponible le " + creneau + ".");
		for (Groupe groupe : prest.getGroupes())
			if (groupe.estIndispo(creneau))
				msg.add("Le groupe '" + groupe + "' n'est pas "
						+ "disponible le " + creneau + ".");

		// puis vérification qu'il n'y a pas de conflit avec d'autres
		// prestations

		// CreneauJpaController ctCreneau = new CreneauJpaController();
		int nbPrest = servicesRemote.countPrestationsPour(creneau,
				prest.getEnseignant(), prest.getGroupes());
		if (nbPrest > 0) { // conflit, la prestation ne peut pas être placée
							// dans ce créneau
			// recherche des conflits
			if (prest.getEnseignant().assurePrestationLe(creneau))
				msg.add("L'enseignant " + prest.getEnseignant()
						+ " assure déjà " + "une prestation le " + creneau
						+ ".");
			for (Groupe groupe : prest.getGroupes())
				if (servicesRemote.suitPrestation(groupe,creneau))
					msg.add("Le groupe '" + groupe + "' suit déjà "
							+ "une prestation le " + creneau + ".");
		}

		// dernière vérification, les éventuels pools associés au module de la
		// prestation
		// ont-ils encore de la place pour une prestation dans ce créneau
		// PoolJpaController ctPool = new PoolJpaController();
		List<Pool> pools = new ArrayList<Pool>(prest.getModule().getPools());
		Map<Pool, Integer> nbPrestRestant = servicesRemote.getNbPrestRestant(creneau,
				prest.getModule().getPools());
		for (Pool pool : pools) {
			Integer nbRestant = nbPrestRestant.get(pool);
			if (nbRestant != null && nbRestant < 1)
				msg.add("Le pool '" + pool.getLibelle()
						+ "' est saturé pour ce créneau.");
		}
		return msg; // retourne la liste des conflits
	}

}
