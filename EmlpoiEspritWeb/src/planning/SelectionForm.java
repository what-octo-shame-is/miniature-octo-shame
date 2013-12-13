package planning;

public class SelectionForm {
	private String[] idsGrpSelectionnes;

	private String[] idsEnsSelectionnes;

	private String idGrpSel;

	private String idEnsSel;

	private String recherche;

	/**
     *
     */
	public SelectionForm() {
		super();
		this.idsEnsSelectionnes = new String[0];
		this.idsGrpSelectionnes = new String[0];
	}

	public String[] getIdsEnsSelectionnes() {
		return idsEnsSelectionnes;
	}

	public int[] getIdsIntEnsSelectionnes() {
		int[] ids = new int[idsEnsSelectionnes.length];
		int i = 0;
		for (String id : idsEnsSelectionnes)
			ids[i++] = Integer.parseInt(id);
		return ids;
	}

	public void setIdsEnsSelectionnes(String[] idsEnsSelectionnes) {
		this.idsEnsSelectionnes = idsEnsSelectionnes;
	}

	public String[] getIdsGrpSelectionnes() {
		return idsGrpSelectionnes;
	}

	public int[] getIdsIntGrpSelectionnes() {
		int[] ids = new int[idsGrpSelectionnes.length];
		int i = 0;
		for (String id : idsGrpSelectionnes)
			ids[i++] = Integer.parseInt(id);
		return ids;
	}

	public void setIdsGrpSelectionnes(String[] idsGrpSelectionnes) {
		this.idsGrpSelectionnes = idsGrpSelectionnes;
	}

	public String getIdEnsSel() {
		return idEnsSel;
	}

	public void setIdEnsSel(String idEnsSel) {
		this.idEnsSel = idEnsSel;
	}

	public String getIdGrpSel() {
		return idGrpSel;
	}

	public void setIdGrpSel(String idGrpSel) {
		this.idGrpSel = idGrpSel;
	}

	public void reset() {
		

		this.idsEnsSelectionnes = new String[0];
		this.idsGrpSelectionnes = new String[0];
		this.idEnsSel = "";
		this.idGrpSel = "";
	}

	public String getRecherche() {
		return recherche;
	}

	public void setRecherche(String recherche) {
		this.recherche = recherche;
	}

}
