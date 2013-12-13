package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CLASSE")
@NamedQueries({
		@NamedQuery(name = "Classe.findAll", query = "SELECT g FROM Groupe g"),
		@NamedQuery(name = "Groupe.findById", query = "SELECT g FROM Groupe g WHERE g.id = :id"),
		@NamedQuery(name = "Groupe.findByLibelle", query = "SELECT g FROM Groupe g WHERE g.libelle = :libelle"),
		@NamedQuery(name = "Groupe.findByEffectif", query = "SELECT g FROM Groupe g WHERE g.niveauAccees = :niveauAccees"),
		@NamedQuery(name = "Groupe.ModulesParGroupe", query = "SELECT p FROM Prestation p WHERE p.codeCl = :id"),})
public class Groupe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODE_CL")
	String id;
	@Column(name = "LIBELLE_CL")
	String libelle;
	@Column(name = "DESCRIPTION_CL")
	String description;
	@Column(name = "DATE_CR")
	Date date;
	@Column(name = "DATE_DERN_MODIF")
	Date dateDernierModif;
	@Column(name = "SALLE_PRINCIPALE")
	String salePrincipale;
	@Column(name = "SELLE_SECONDAIRE_1")
	String saleSeconcaire1;
	@Column(name = "SALLE_SECONDAIRE_2")
	String saleSecondaire2;
	@Column(name = "NIVEAU_ACCEES")
	Integer niveauAccees;
	@Column(name = "FILIERE")
	String filiere;
	@Column(name = "ANNEE_SCOLAIRE")
	String anneeScolaire;
	@ManyToMany
	private Set<SaisonClasse> saisonCollection;
	// @ManyToMany
	// private Set<Module> moduleCollection;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Creneau> creneauCollection;

	@ManyToMany(mappedBy = "groupeCollection", fetch = FetchType.EAGER)
	private Set<Prestation> prestationCollection=new HashSet<Prestation>();
	
	/*
	@Column(name="GROUPE_LIST")
	private List<Prestation> prestationListConvertieDeSet = new ArrayList<Prestation> (prestationCollection);
	*/ 
/*	
	@ManyToMany(mappedBy = "groupeCollection", fetch = FetchType.EAGER)
	private ArrayList<Prestation> prestationCollection2;
*/
	public String getCode() {
		return id;
	}

	public void setCode(String code) {
		this.id = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateDernierModif() {
		return dateDernierModif;
	}

	public void setDateDernierModif(Date dateDernierModif) {
		this.dateDernierModif = dateDernierModif;
	}

	public String getSalePrincipale() {
		return salePrincipale;
	}

	public void setSalePrincipale(String salePrincipale) {
		this.salePrincipale = salePrincipale;
	}

	public String getSaleSeconcaire1() {
		return saleSeconcaire1;
	}

	public void setSaleSeconcaire1(String saleSeconcaire1) {
		this.saleSeconcaire1 = saleSeconcaire1;
	}

	public String getSaleSecondaire2() {
		return saleSecondaire2;
	}

	public void setSaleSecondaire2(String saleSecondaire2) {
		this.saleSecondaire2 = saleSecondaire2;
	}

	public Integer getNiveauAccees() {
		return niveauAccees;
	}

	public void setNiveauAccees(Integer niveauAccees) {
		this.niveauAccees = niveauAccees;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public String getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
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
		if (!(object instanceof Groupe)) {
			return false;
		}
		Groupe other = (Groupe) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getLibelle();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// public Set<Module> getModuleCollection() {
	// return moduleCollection;
	// }
	//
	// public void setModuleCollection(Set<Module> moduleCollection) {
	// this.moduleCollection = moduleCollection;
	// }

	// public Set<Creneau> getCreneauCollection() {
	// return creneauCollection;
	// }
	//
	// public void setCreneauCollection(Set<Creneau> creneauCollection) {
	// this.creneauCollection = creneauCollection;
	// }

	public Set<Prestation> getPrestationCollection() {
		return prestationCollection;
	}
	
	public void setPrestationCollection(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

	///// Ajout même méthode mais avec arraylist pour l'utillisation de tree	
	/*
	public ArrayList<Prestation> getPrestationCollection2() {
		return prestationCollection2;
	}
	
	public void setPrestationCollection2(ArrayList<Prestation> prestationCollection2) {
		this.prestationCollection2 = prestationCollection2;
	}
	*/
	
	/**
	 * Indique si le groupe suit une prestation ou non durant le créneau passé
	 * en paramètre.
	 * 
	 * @param creneau
	 * @return
	 */

	public boolean estIndispo(Creneau creneau) {
		return creneauCollection.contains(creneau);
	}

	public boolean estDispo(Creneau creneau) {
		return !estIndispo(creneau);
	}

	public void setPrestationsSuivies(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

	public List<Prestation> getPrestationsSuivies() {
		List<Prestation> prestationSuivie = new ArrayList<Prestation>(prestationCollection);
		return prestationSuivie;
	}

	public Set<Creneau> getIndisponibilites() {

		return creneauCollection;
	}

	public void setIndisponibilites(Set<Creneau> creneauCollection) {

		this.creneauCollection = creneauCollection;
	}
/*
	
	public List<Prestation> getPrestationListConvertieDeSet() {
		return prestationListConvertieDeSet;
	}

	
	 
	public void setPrestationListConvertieDeSet(
			List<Prestation> prestationListConvertieDeSet) {
		this.prestationListConvertieDeSet = prestationListConvertieDeSet;
	}
*/
}
