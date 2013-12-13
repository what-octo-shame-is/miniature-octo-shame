package tn.esprit.edt.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "ESP_ADMIN")  
@NamedQuery(name="findAllAdmin",query="select p from Admin p")

public class Admin implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	 @Column(name = "ID_ADMIN")
	private Integer id;
	 @Column(name = "LOGIN")
	 private String login;
	 @Column(name = "PASSWORD")
	 private String password;
	 @Column(name = "NOM_ADMIN")
	 private String nomAdmin ;
	 @Column(name = "PRENOM_ADMIN")
	 private String prenomAdmin;
	 @Column(name = "DATE_REC")
	 private Date dateRec;
	 @Column(name = "DATE_SAISIE")
	 private Date dateSaisie;
	 @Column(name = "DATE_DERN_MODIF")
	 private Date dateDernierModif;
	 @Column(name = "ETAT")
	 private String etat;
	 @Column(name = "OBSERVATION")
	 private String observation;
	 
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(Integer id, String login, String password, String nomAdmin,
			String prenomAdmin, Date dateRec, Date dateSaisie,
			Date dateDernierModif, String etat, String observation) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.nomAdmin = nomAdmin;
		this.prenomAdmin = prenomAdmin;
		this.dateRec = dateRec;
		this.dateSaisie = dateSaisie;
		this.dateDernierModif = dateDernierModif;
		this.etat = etat;
		this.observation = observation;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNomAdmin() {
		return nomAdmin;
	}
	public void setNomAdmin(String nomAdmin) {
		this.nomAdmin = nomAdmin;
	}
	public String getPrenomAdmin() {
		return prenomAdmin;
	}
	public void setPrenomAdmin(String prenomAdmin) {
		this.prenomAdmin = prenomAdmin;
	}
	public Date getDateRec() {
		return dateRec;
	}
	public void setDateRec(Date dateRec) {
		this.dateRec = dateRec;
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

	
}
