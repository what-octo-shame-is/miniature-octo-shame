package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SaisonUniversitaire
 *
 */
@Entity
@Table(name="ESP_SAISON_UNIVERSITAIRE") 

public class SaisonUniversitaire implements Serializable {

	   
	@Id
	@Column(name="ANNEE_DEB")
	private String anneeDeb;
	@Column(name="DESCRIPTION")
	private String description;
	private static final long serialVersionUID = 1L;

	public SaisonUniversitaire() {
		super();
	}   
	public String getAnneeDeb() {
		return this.anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
