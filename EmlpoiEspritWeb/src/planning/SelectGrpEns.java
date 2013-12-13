package planning;

import java.util.Set;

import javax.ejb.EJB;

import tn.esprit.edt.gestion.ServicesRemote;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;

public class SelectGrpEns {
	@EJB
	ServicesRemote servicesRemote;
	private SelectionForm selForm = new SelectionForm();
	private SelectionForm sForm = new SelectionForm();
	/* forward name="success" path="" */
	private final static String SUCCESS = "success";

	/**
	 * This is the action called from the Struts framework.
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
	public String selectionnerPlusieurs() throws Exception {
		
	
		String recherche = sForm.getRecherche().trim();
		if (!recherche.equals("")) { // si le critère de recherce est renseigné
										// :
			// on recherche d'abord les groupes qui répondent à ce critère
			// GroupeJpaController ctGrp = new GroupeJpaController();
			Set<Groupe> groupes = servicesRemote.findGroupesLike("%" + recherche
					+ "%");
			String[] idsGroupes = new String[groupes.size()];
			int i = 0;
			for (Groupe groupe : groupes)
				idsGroupes[i++] = groupe.getId().toString();
			sForm.setIdsGrpSelectionnes(idsGroupes);
			// puis on recherche les enseignants qui répondent à ce critère
			// EnseignantJpaController ctEns = new EnseignantJpaController();
			Set<Enseignant> enseignants = servicesRemote
					.findEnseignantsLike("%" + recherche + "%");
			String[] idsEns = new String[enseignants.size()];
			i = 0;
			for (Enseignant enseignant : enseignants)
				idsEns[i++] = enseignant.getId().toString();
			sForm.setIdsEnsSelectionnes(idsEns);
		}

		return "SUCCESS";
	}

	/**
	 * This is the action called from the Struts framework.
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
	public String selectionnerUnGrp() throws Exception {

		
		String idGroupe = selForm.getIdGrpSel();
		selForm.setIdsGrpSelectionnes(new String[] { idGroupe });
		selForm.setIdsEnsSelectionnes(new String[] {});
		selForm.setRecherche("");

		return "SUCCESS";
	}

	/**
	 * This is the action called from the Struts framework.
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
	public String selectionnerUnEns() throws Exception {

		
		String idEnseignant = selForm.getIdEnsSel();
		selForm.setIdsEnsSelectionnes(new String[] { idEnseignant });
		selForm.setIdsGrpSelectionnes(new String[] {});
		selForm.setRecherche("");

		return "SUCCESS";
	}

}
