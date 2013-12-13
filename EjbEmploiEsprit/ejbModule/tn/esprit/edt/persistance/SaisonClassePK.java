package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

/**
 * ID class for entity: SaisonClasse
 *
 */ 
public class SaisonClassePK  implements Serializable {   
   
	         
	private String anneeDeb;         
	private String codeCl;         
	private Integer semestre;
	private static final long serialVersionUID = 1L;

	public SaisonClassePK() {}

	

	public String getAnneeDeb() {
		return this.anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}
	

	public String getCodeCl() {
		return this.codeCl;
	}

	public void setCodeCl(String codeCl) {
		this.codeCl = codeCl;
	}
	

	public Integer getSEMESTRE() {
		return this.semestre;
	}

	public void setSEMESTRE(Integer SEMESTRE) {
		this.semestre = SEMESTRE;
	}
	
   
	/*
	 * @see java.lang.Object#equals(Object)
	 */	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SaisonClassePK)) {
			return false;
		}
		SaisonClassePK other = (SaisonClassePK) o;
		return true
			&& (getAnneeDeb() == null ? other.getAnneeDeb() == null : getAnneeDeb().equals(other.getAnneeDeb()))
			&& (getCodeCl() == null ? other.getCodeCl() == null : getCodeCl().equals(other.getCodeCl()))
			&& (getSEMESTRE() == null ? other.getSEMESTRE() == null : getSEMESTRE().equals(other.getSEMESTRE()));
	}
	
	/*	 
	 * @see java.lang.Object#hashCode()
	 */	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getAnneeDeb() == null ? 0 : getAnneeDeb().hashCode());
		result = prime * result + (getCodeCl() == null ? 0 : getCodeCl().hashCode());
		result = prime * result + (getSEMESTRE() == null ? 0 : getSEMESTRE().hashCode());
		return result;
	}
   
   
}
