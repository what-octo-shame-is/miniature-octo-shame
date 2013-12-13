package tn.esprit.edt.persistance;

import java.io.Serializable;

/**
 * ID class for entity: ModulePanierClasseSaiso
 * 
 */

public class PrestationPK implements Serializable {

	private String codeModule;
	private String codeCl;
	private String anneeDeb;
	private Integer numSemestre;
	private static final long serialVersionUID = 1L;

	public PrestationPK() {
	}

	public String getCodeModule() {
		return this.codeModule;
	}

	public void setCodeModule(String codeModule) {
		this.codeModule = codeModule;
	}

	public String getCodeCl() {
		return this.codeCl;
	}

	public void setCodeCl(String codeCl) {
		this.codeCl = codeCl;
	}

	public String getAnneeDeb() {
		return this.anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	public Integer getNumSemestre() {
		return this.numSemestre;
	}

	public void setNumSemestre(Integer numSemestre) {
		this.numSemestre = numSemestre;
	}

	/*
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PrestationPK)) {
			return false;
		}
		PrestationPK other = (PrestationPK) o;
		return true
				&& (getCodeModule() == null ? other.getCodeModule() == null
						: getCodeModule().equals(other.getCodeModule()))
				&& (getCodeCl() == null ? other.getCodeCl() == null
						: getCodeCl().equals(other.getCodeCl()))
				&& (getAnneeDeb() == null ? other.getAnneeDeb() == null
						: getAnneeDeb().equals(other.getAnneeDeb()))
				&& (getNumSemestre() == null ? other.getNumSemestre() == null
						: getNumSemestre().equals(other.getNumSemestre()));
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (getCodeModule() == null ? 0 : getCodeModule().hashCode());
		result = prime * result
				+ (getCodeCl() == null ? 0 : getCodeCl().hashCode());
		result = prime * result
				+ (getAnneeDeb() == null ? 0 : getAnneeDeb().hashCode());
		result = prime * result
				+ (getNumSemestre() == null ? 0 : getNumSemestre().hashCode());
		return result;
	}

}
