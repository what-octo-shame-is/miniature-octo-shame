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

	

		// si le zoom �tait activ�, il est d�sactiv�, et inversement
		sjForm.setZoomJour(!sjForm.isZoomJour());

		// puis on s'assure que si le num�ro de jour s�lectionn� n'est pas
		// valide
		if (!(sjForm.getNumJourSel() > 0 && sjForm.getNumJourSel() <= 7))
			sjForm.setZoomJour(false); // le zoom doit �tre n�cessairement
										// d�sactiv�

		return "SUCCESS";
	}

}
