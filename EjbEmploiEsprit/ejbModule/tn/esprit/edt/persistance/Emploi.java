package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOI")
@NamedQuery(name = "findAllEmploiDeTemps", query = "select p from Emploi p")
public class Emploi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_EMPLOI")
	private Integer idEmploi;

	@Column(name = "DATE_EMPLOI")
	private Date dateEmploi;

	public Emploi() {
		super();
		
	}

	public Integer getIdEmploi() {
		return idEmploi;
	}

	public void setIdEmploi(Integer idEmploi) {
		this.idEmploi = idEmploi;
	}

	public Date getDateEmploi() {
		return dateEmploi;
	}

	public void setDateEmploi(Date dateEmploi) {
		this.dateEmploi = dateEmploi;
	}

}
