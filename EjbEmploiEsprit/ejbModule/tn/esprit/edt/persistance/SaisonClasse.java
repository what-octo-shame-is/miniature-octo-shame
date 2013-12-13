package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SaisonClasse
 *
 */
@Entity

@IdClass(SaisonClassePK.class)
public class SaisonClasse implements Serializable {

	   
	@Id
	@Column(name="ANNEE_DEB")
	private String anneeDeb;   
	@Id
	@Column(name="CODE_CL")
	private String codeCl;
	@Column(name="DATE_DEMARRAGE")
	private Date dateDemarrage;
	@Column(name="DESCRIPTION")
	private String Description;
	@Column(name="nbEtudiant")
	private Integer nbEtudiant;
	@Column(name="SALLE_PRINCIPALE")
	private String sallePrincipale;
	@Column(name="SALLE_SECONDAIRE_1")
	private String salleSecondaire1;
	@Column(name="SALLE_SECONDAIRE_2")
	private String salleSecondaire2;
	@Column(name="NATURE")
	private String nature;
	@Column(name="TYPE_CLASSE")
	private String typeClasse;
	@Column(name="NB_SEANCE")
	private Integer nbSeance;
	@Column(name="CLASSE_ENTREPRISE")
	private String classeEntreprise;   
	@Id
	@Column(name="SEMESTRE")
	private Integer semestre;
	@Column(name="CODE_SALLE")
	private String codeSalle;
	@Column(name="CL_ECLATE")
	private String clEclate;
	@Column(name="DATE_ECLATEMENT")
	private Date dateEclatement;
	@Column(name="NUMPROMOTIONCS")
	private Integer numPromotioncs;
	@Column(name="CLASSE_S2")
	private String classeS2;
	@Column(name="CODE_CLS1")
	private String codeClS2;
	private static final long serialVersionUID = 1L;

	public SaisonClasse() {
		super();
	}   
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
	public Date getDateDemarrage() {
		return this.dateDemarrage;
	}

	public void setDateDemarrage(Date dateDemarrage) {
		this.dateDemarrage = dateDemarrage;
	}   
	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}   
	public Integer getNbEtudiant() {
		return this.nbEtudiant;
	}

	public void setNbEtudiant(Integer nbEtudiant) {
		this.nbEtudiant = nbEtudiant;
	}
	public String getSallePrincipale() {
		return sallePrincipale;
	}
	public void setSallePrincipale(String sallePrincipale) {
		this.sallePrincipale = sallePrincipale;
	}
	public String getSalleSecondaire1() {
		return salleSecondaire1;
	}
	public void setSalleSecondaire1(String salleSecondaire1) {
		this.salleSecondaire1 = salleSecondaire1;
	}
	public String getSalleSecondaire2() {
		return salleSecondaire2;
	}
	public void setSalleSecondaire2(String salleSecondaire2) {
		this.salleSecondaire2 = salleSecondaire2;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getTypeClasse() {
		return typeClasse;
	}
	public void setTypeClasse(String typeClasse) {
		this.typeClasse = typeClasse;
	}
	public Integer getNbSeance() {
		return nbSeance;
	}
	public void setNbSeance(Integer nbSeance) {
		this.nbSeance = nbSeance;
	}
	public String getClasseEntreprise() {
		return classeEntreprise;
	}
	public void setClasseEntreprise(String classeEntreprise) {
		this.classeEntreprise = classeEntreprise;
	}
	public Integer getSemestre() {
		return semestre;
	}
	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}
	public String getCodeSalle() {
		return codeSalle;
	}
	public void setCodeSalle(String codeSalle) {
		this.codeSalle = codeSalle;
	}
	public String getClEclate() {
		return clEclate;
	}
	public void setClEclate(String clEclate) {
		this.clEclate = clEclate;
	}
	public Date getDateEclatement() {
		return dateEclatement;
	}
	public void setDateEclatement(Date dateEclatement) {
		this.dateEclatement = dateEclatement;
	}
	public Integer getNumPromotioncs() {
		return numPromotioncs;
	}
	public void setNumPromotioncs(Integer numPromotioncs) {
		this.numPromotioncs = numPromotioncs;
	}
	public String getClasseS2() {
		return classeS2;
	}
	public void setClasseS2(String classeS2) {
		this.classeS2 = classeS2;
	}
	public String getCodeClS2() {
		return codeClS2;
	}
	public void setCodeClS2(String codeClS2) {
		this.codeClS2 = codeClS2;
	}   
	
}
