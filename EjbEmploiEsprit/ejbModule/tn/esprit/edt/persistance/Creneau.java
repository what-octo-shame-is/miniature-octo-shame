package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
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
@Table(name = "CRENEAU")
@NamedQueries({
		@NamedQuery(name = "Creneau.findAll", query = "SELECT c FROM Creneau c"),
		@NamedQuery(name = "Creneau.findByNumero", query = "SELECT c FROM Creneau c WHERE c.numero = :numero") })
public class Creneau implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Nombre de jours géré par l'emploi du temps.
	 */
	public final static int NB_JOURS = 6;

	public static int getNbJours() {
		return NB_JOURS;
	}

	public static String getJour(int numJour) {
		switch (numJour) {
		case 1:
			return "lundi";
		case 2:
			return "mardi";
		case 3:
			return "merdredi";
		case 4:
			return "jeudi";
		case 5:
			return "vendredi";
		case 6:
			return "samedi";
		case 7:
		default:
			return "dimanche";
		}
	}

	/**
	 * Nombre de créneaux par jour géré par l'emploi du temps.
	 */
	public final static int NB_CRENEAUX_PAR_JOUR = 4;

	public static int getNbCreneauxParJour() {
		return NB_CRENEAUX_PAR_JOUR;
	}

	public static String getPlageHoraire(int numHeure) {
		switch (numHeure) {
		case 1:
			return "9h-10:30h";
		case 2:
			return "10:30h-12h";
		case 3:
			return "14h-15:30h";
		case 4:
			return "15:30h-17h";
		case 5:
		default:
			return "17h-20h";
		}
	}

	@Id
	@Basic(optional = false)
	@Column(name = "numero")
	private Integer numero;
	@ManyToMany
	private Set<Groupe> groupeCollection;

	@ManyToMany(mappedBy = "creneauCollection", fetch = FetchType.LAZY)
	private Set<Enseignant> enseignantCollection;

	@OneToMany(mappedBy = "creneau", fetch = FetchType.EAGER)
	private Set<Prestation> prestationCollection;

	public Creneau() {
	}

	public Creneau(Integer numero) {
		this.numero = numero;
	}

	public Integer getNumero() {
		return numero;
	}

	public int getNumJour() {
		int numC = getNumero().intValue();
		return ((numC - 1) / 4) + 1;
	}

	public String getJour() {
		return Creneau.getJour(getNumJour());
	}

	public int getNumHeure() {
		int numC = getNumero().intValue();
		return ((numC - 1) % 4) + 1;
	}

	public String getPlageHoraire() {
		return Creneau.getPlageHoraire(getNumHeure());
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Set<Groupe> getGroupesIndispo() {
		return groupeCollection;
	}

	public void setGroupesIndispo(Set<Groupe> groupeCollection) {
		this.groupeCollection = groupeCollection;
	}

	public Set<Enseignant> getEnseignantsIndispo() {
		return enseignantCollection;
	}

	public void setEnseignantsIndispo(Set<Enseignant> enseignantCollection) {
		this.enseignantCollection = enseignantCollection;
	}

	public Set<Prestation> getPrestations() {
		return prestationCollection;
	}

	public void setPrestations(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (numero != null ? numero.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Creneau)) {
			return false;
		}
		Creneau other = (Creneau) object;
		if ((this.numero == null && other.numero != null)
				|| (this.numero != null && !this.numero.equals(other.numero))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		// on suppose ici qu'une semaine est découpée en 20 créneaux
		int numC = getNumero().intValue();
		if (numC > 20) // au cas où le numéro de créneau dépasserait 20 :
			return "créneau n°" + numC;
		String creneau = "";
		switch (getNumJour()) {
		case 1:
			creneau += "lundi";
			break;
		case 2:
			creneau += "mardi";
			break;
		case 3:
			creneau += "mercredi";
			break;
		case 4:
			creneau += "jeudi";
			break;
		case 5:
			creneau += "vendredi";
			break;
		case 6:
			creneau += "samedi";
			break;
		}
		switch (getNumHeure()) {
		case 1:
			creneau += " de 9h à 10:30h";
			break;
		case 2:
			creneau += " de 10:45h à 12:15h";
			break;
		case 3:
			creneau += " de 14h à 15:30h";
			break;
		case 4:
			creneau += " de 15:45h à 17h";
			break;
		}
		return creneau;
	}

	public Set<Groupe> getGroupeCollection() {
		return groupeCollection;
	}

	public void setGroupeCollection(Set<Groupe> groupeCollection) {
		this.groupeCollection = groupeCollection;
	}

	public Set<Enseignant> getEnseignantCollection() {
		return enseignantCollection;
	}

	public void setEnseignantCollection(Set<Enseignant> enseignantCollection) {
		this.enseignantCollection = enseignantCollection;
	}

	public Set<Prestation> getPrestationCollection() {
		return prestationCollection;
	}

	public void setPrestationCollection(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

}
