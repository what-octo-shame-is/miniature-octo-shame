package planning;

public class SelectionJourForm {
	private boolean zoomJour;

	private int numJourSel;

	/**
     *
     */
	public SelectionJourForm() {
		super();
		this.zoomJour = false;
		this.numJourSel = 0;
	}

	public int getNumJourSel() {
		return numJourSel;
	}

	public void setNumJourSel(int numJourSel) {
		this.numJourSel = numJourSel;
	}

	public boolean isZoomJour() {
		return zoomJour;
	}

	public void setZoomJour(boolean zoomJour) {
		this.zoomJour = zoomJour;
	}


}
