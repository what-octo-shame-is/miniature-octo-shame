package planning;

import javax.ejb.EJB;

import tn.esprit.edt.gestion.ServicesRemote;

public class SelectJour {
	/* forward name="success" path="" */
	private final static String SUCCESS = "success";
	@EJB
	ServicesRemote servicesRemote;
	private SelectionJourForm sjForm = new SelectionJourForm();
	/**
	 * 
	 */
	public String execute()
			throws Exception {

	

		// si le zoom était activé, il est désactivé, et inversement
		sjForm.setZoomJour(!sjForm.isZoomJour());

		// puis on s'assure que si le numéro de jour sélectionné n'est pas
		// valide
		if (!(sjForm.getNumJourSel() > 0 && sjForm.getNumJourSel() <= 7))
			sjForm.setZoomJour(false); // le zoom doit être nécessairement
										// désactivé

		return "SUCCESS";
	}

}
