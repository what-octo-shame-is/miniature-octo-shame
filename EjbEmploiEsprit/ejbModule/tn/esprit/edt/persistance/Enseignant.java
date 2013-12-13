package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tn.esprit.edt.gestion.PrestationGes;

@Entity
@Table(name = "ESP_ENSEIGNANT")
@NamedQuery(name = "findAllEnseignant", query = "select p from Enseignant p")
public class Enseignant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "iD_ENS")
	private String id;
	@Column(name = "NOM_ENS")
	private String nom;
	@Column(name = "TYPE_ENS")
	private String type;
	@Column(name = "DATE_REC")
	private Date date;
	@Column(name = "NIVEAU")
	private String niveau;
	private String loginEns;
	private String passwordEns;
	@Column(name = "DATE_SAISIE")
	private Date dateSaisie;
	@Column(name = "DATE_DERN_MODIF")
	private Date dateDernierModif;
	@Column(name = "ETAT")
	private String etat;
	@Column(name = "OBSERVATION")
	private String observation;
	
	@JoinTable(name = "indisponibilite_enseignant", joinColumns = {@JoinColumn(name = "idEnseignant", referencedColumnName = "ID_ENS")}, inverseJoinColumns = {@JoinColumn(name = "numeroCreneau", referencedColumnName = "numero")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Creneau> creneauCollection;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Enseignant" , fetch = FetchType.EAGER)
	private Set<Prestation> prestationCollection;

	public Enseignant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}

	public Set<Creneau> getIndisponibilites() {
		return creneauCollection;
	}

	public void setIndisponibilites(Set<Creneau> creneauCollection) {
		this.creneauCollection = creneauCollection;
	}

	

	public boolean estIndispo(Creneau creneau) {
		return creneauCollection.contains(creneau);
	}

	public boolean estDispo(Creneau creneau) {
		return !estIndispo(creneau);
	}

	public Enseignant(String id, String nom, String type, Date date,
			String niveau, String loginEns, String passwordEns,
			Date dateSaisie, Date dateDernierModif, String etat,
			String observation) {
		super();
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.date = date;
		this.niveau = niveau;
		this.loginEns = loginEns;
		this.passwordEns = passwordEns;
		this.dateSaisie = dateSaisie;
		this.dateDernierModif = dateDernierModif;
		this.etat = etat;
		this.observation = observation;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public Date getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

	public Date getDateDernierModif() {
		return dateDernierModif;
	}

	public void setDateDernierModif(Date dateDernierModif) {
		this.dateDernierModif = dateDernierModif;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getLoginEns() {
		return loginEns;
	}

	public void setLoginEns(String loginEns) {
		this.loginEns = loginEns;
	}

	public String getPasswordEns() {
		return passwordEns;
	}

	public void setPasswordEns(String passwordEns) {
		this.passwordEns = passwordEns;
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
		if (!(object instanceof Enseignant)) {
			return false;
		}
		Enseignant other = (Enseignant) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	public String getIdentite() {
		return nom;
	}

	/**
	 * Indique si l'enseignant assure une prestation ou non durant le créneau
	 * passé en paramètre.
	 * 
	 * @param creneau
	 * @return
	 */
	public boolean assurePrestationLe(Creneau creneau) {
		PrestationGes ctPrest = new PrestationGes();
		return ctPrest.assurePrestation(this, creneau);
	}

	public Set<Creneau> getCreneauCollection() {
		return creneauCollection;
	}

	public void setCreneauCollection(Set<Creneau> creneauCollection) {
		this.creneauCollection = creneauCollection;
	}

	public Set<Prestation> getPrestationCollection() {
		return prestationCollection;
	}

	public void setPrestationCollection(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

	public Set<Prestation> getPrestationsAssurees() {
		return prestationCollection;
	}

	public void setPrestationsAssurees(Set<Prestation> prestationCollection) {
		this.prestationCollection = prestationCollection;
	}

}
