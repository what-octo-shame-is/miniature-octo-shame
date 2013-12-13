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
import javax.persistence.Table;

/**
 *
 *
 */
@Entity
@Table(name = "POOL")
@NamedQueries({
		@NamedQuery(name = "Pool.findAll", query = "SELECT p FROM Pool p"),
		@NamedQuery(name = "Pool.findById", query = "SELECT p FROM Pool p WHERE p.id = :id"),
		@NamedQuery(name = "Pool.findByLibelle", query = "SELECT p FROM Pool p WHERE p.libelle = :libelle"),
		@NamedQuery(name = "Pool.findByNbPrestMaxParPlage", query = "SELECT p FROM Pool p WHERE p.nbPrestMaxParPlage = :nbPrestMaxParPlage") })
public class Pool implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "LIBELLE")
	private String libelle;
	@Basic(optional = false)
	@Column(name = "nbPrestMaxParPlage")
	private int nbPrestMaxParPlage;
	@Column(name = "description")
	private String description;
	@ManyToMany(mappedBy = "poolCollection", fetch = FetchType.EAGER)
	private Set<Module> moduleCollection;

	public Pool() {
	}

	public Pool(Integer id) {
		this.id = id;
	}

	public Pool(Integer id, int nbPrestMaxParPlage) {
		this.id = id;
		this.nbPrestMaxParPlage = nbPrestMaxParPlage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getNbPrestMaxParPlage() {
		return nbPrestMaxParPlage;
	}

	public void setNbPrestMaxParPlage(int nbPrestMaxParPlage) {
		this.nbPrestMaxParPlage = nbPrestMaxParPlage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Module> getModules() {
		return moduleCollection;
	}

	public void setModules(Set<Module> moduleCollection) {
		this.moduleCollection = moduleCollection;
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
		if (!(object instanceof Pool)) {
			return false;
		}
		Pool other = (Pool) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String str = getLibelle();
		if (!getDescription().trim().equals(""))
			str += (" (" + getDescription().trim() + ")");
		return str;
	}

	public Set<Module> getModuleCollection() {
		return moduleCollection;
	}

	public void setModuleCollection(Set<Module> moduleCollection) {
		this.moduleCollection = moduleCollection;
	}

}