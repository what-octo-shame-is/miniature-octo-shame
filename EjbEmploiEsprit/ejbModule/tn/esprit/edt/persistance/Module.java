package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
 @NamedQueries({
 @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m"),
 @NamedQuery(name = "Module.findById", query =
 "SELECT m FROM Module m WHERE m.id = :id"),
 @NamedQuery(name = "Module.findByDescription", query =
 "SELECT m FROM Module m WHERE m.description = :description"),
 @NamedQuery(name = "Module.findByDesignation", query =
 "SELECT m FROM Module m WHERE m.designation = :designation"),
 @NamedQuery(name = "Module.findByNbHeures", query =
 "SELECT m FROM Module m WHERE m.nbHeures = :nbHeures"),
 @NamedQuery(name = "Module.findByEtat", query =
 "SELECT m FROM Module m WHERE m.etat = :etat") })
@NamedQuery(name = "findAllModule", query = "select p from Module p")
@Table(name = "ESP_MODULE")
public class Module implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODE_MODULE")
	String id;
	@Column(name = "DESIGNATION")
	String designation;
	@Column(name = "DESCRIPTION")
	String description;
	@Column(name = "NB_HEURES")
	Integer nbHeures;
	@Column(name = "ETAT")
	String etat;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Pool> poolCollection;
//	@ManyToMany(fetch = FetchType.EAGER)
//	private Set<Groupe> groupeCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "module", fetch = FetchType.EAGER)
	private Set<Prestation> prestationCollection;

	public Module() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Module(String code, String designation, String description,
			Integer nbHeures, String etat) {
		super();
		this.id = code;
		this.designation = designation;
		this.description = description;
		this.nbHeures = nbHeures;
		this.etat = etat;

	}

	public String getId() {
		return id;
	}

	public void setId(String code) {
		this.id = code;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNbHeures() {
		return nbHeures;
	}

	public void setNbHeures(Integer nbHeures) {
		this.nbHeures = nbHeures;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Module)) {
			return false;
		}
		Module other = (Module) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	/**
	 * Retourne la couleur (libellé CSS) associée au module qui pourra être
	 * utilisée comme couleur de fond. Cette couleur est déterminée en fonction
	 * du libellé et de l'id (elle restera donc statique si le libellé et l'id
	 * ne sont pas modifié).
	 * 
	 * @return
	 */
	/*
	 * public String getBgColor() { int oo = 0; for(char c :
	 * getDesignation().toCharArray()) oo += Character.getNumericValue(c); oo *=
	 * (getId().intValue()+1) * 17 - 6; int nbCouleurs = CSSColor.countColors();
	 * int i = oo % nbCouleurs; return CSSColor.getColor(i); }
	 */

	/**
	 * Retourne la couleur proposée pour le texte en fonction de la couleur de
	 * fond associé au module.
	 * 
	 * @return blanc ou noir en fonction de la couleur de fond
	 */
	// public String getFontColor() {
	// if(CSSColor.isDark(getBgColor()))
	// return CSSColor.getLightest();
	// else
	// return CSSColor.getDarkest();
	// }

	public Set<Pool> getPools() {
		return poolCollection;
	}

	public void setPools(Set<Pool> poolCollection) {
		this.poolCollection = poolCollection;
	}

//	public Set<Groupe> getGroupesInscrits() {
//		return groupeCollection;
//	}
//
//	public void setGroupesInscrits(Set<Groupe> groupeCollection) {
//		this.groupeCollection = groupeCollection;
//	}

	public Set<Pool> getPoolCollection() {
		return poolCollection;
	}

	public Set<Prestation> getPrestations() {
		return prestationCollection;
	}

	public void setPrestations(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

	public void setPoolCollection(Set<Pool> poolCollection) {
		this.poolCollection = poolCollection;
	}

//	public Set<Groupe> getGroupeCollection() {
//		return groupeCollection;
//	}
//
//	public void setGroupeCollection(Set<Groupe> groupeCollection) {
//		this.groupeCollection = groupeCollection;
//	}

	public Set<Prestation> getPrestationCollection() {
		return prestationCollection;
	}

	public void setPrestationCollection(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

}
