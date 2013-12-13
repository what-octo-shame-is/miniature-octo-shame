package tn.esprit.edt.persistance;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: ModulePanierClasseSaiso
 * 
 */
@Entity
@IdClass(PrestationPK.class)
@Table(name = "PRESTATION")
public class Prestation implements Serializable {

	@Id
	@Column(name = "CODE_MODULE")
	private String codeModule;

	@Id
	@Column(name = "CODE_CL")
	private String codeCl;

	@Id
	@Column(name = "ANNEE_DEB")
	private String anneeDeb;

	@Id
	@Column(name = "NUM_SEMESTRE")
	private Integer numSemestre;

	@Column(name = "NUM_PANIER")
	private String numPanier;

	@Column(name = "ANNEE_FIN")
	private String anneeFin;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "NB_HEURES")
	private Integer nbHeures;

	@Column(name = "COEF")
	private Integer coef;

	@Column(name = "NUM_PERIODFE")
	private Integer numPeriodfe;

	@Column(name = "DATE_FIN")
	private Date dateFin;

	@Column(name = "DATE_EXAMEN")
	private Date dateExamen;

	@Column(name = "DATE_RATTRAPAGE")
	private Date dateRattrapage;

	@Column(name = "DATE_DEBUT")
	private Date dateDebut;

	@Column(name = "NB_HORAIRE_REALISES")
	private Integer nbHoraireRealises;

	@Column(name = "ACOMPTABILISER")
	private String acomptabiliser;

	@Column(name = "ID_ENS")
	private String idEns;

	@Column(name = "ID_ENS2")
	private String idEns2;
	
	@Column(name = "NB_HEURES_ENS")
	private Integer nbHeuresEns;
	
	@Column(name = "NB_HEURES_ENS2")
	private Integer nbHeuresEns2;
	
	@Column(name = "SURVEILLANT")
	private String surveillant;
	
	@Column(name = "HEURE_EXAM")
	private String heureExamn;
	
	@Column(name = "SALLE_EXAM")
	private String salleExam;
	
	@Column(name = "SURVEILLANT2")
	private String surveillant2;
	
	@Column(name = "SALLE_EXAM2")
	private String salleExam2;
	
	@Column(name = "PERIODE")
	private Integer periode;
	
	@Column(name = "NUMPROMOTIONCS")
	private Integer numPromotioncs;
	
	@Column(name = "AP_CS")
	private Integer apCs;
	
	 @JoinTable(name = "groupes_par_prestation", joinColumns = {
		 @JoinColumn(name = "idPrestation1", referencedColumnName = "CODE_MODULE"),
		 @JoinColumn(name = "idPrestation2", referencedColumnName = "CODE_CL"),
		 @JoinColumn(name = "idPrestation3", referencedColumnName = "NUM_SEMESTRE"),
		 @JoinColumn(name = "idPrestation4", referencedColumnName = "ANNEE_DEB")
	 }, inverseJoinColumns = {@JoinColumn(name = "idGroupe", referencedColumnName = "CODE_CL")})
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Groupe> groupeCollection;

	@ManyToOne(optional = false)
	private Enseignant Enseignant;

	@ManyToOne(optional = false)
	private Module module;

	@ManyToOne
	private Creneau creneau;
	 
	private PrestationPK prestationPK;

	public String getNumPanier() {
		return numPanier;
	}

	public void setNumPanier(String numPanier) {
		this.numPanier = numPanier;
	}

	public String getAnneeFin() {
		return anneeFin;
	}

	public void setAnneeFin(String anneeFin) {
		this.anneeFin = anneeFin;
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

	public Integer getCoef() {
		return coef;
	}

	public void setCoef(Integer coef) {
		this.coef = coef;
	}

	public Integer getNumPeriodfe() {
		return numPeriodfe;
	}

	public void setNumPeriodfe(Integer numPeriodfe) {
		this.numPeriodfe = numPeriodfe;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Date getDateExamen() {
		return dateExamen;
	}

	public void setDateExamen(Date dateExamen) {
		this.dateExamen = dateExamen;
	}

	public Date getDateRattrapage() {
		return dateRattrapage;
	}

	public void setDateRattrapage(Date dateRattrapage) {
		this.dateRattrapage = dateRattrapage;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Integer getNbHoraireRealises() {
		return nbHoraireRealises;
	}

	public void setNbHoraireRealises(Integer nbHoraireRealises) {
		this.nbHoraireRealises = nbHoraireRealises;
	}

	public String getAcomptabiliser() {
		return acomptabiliser;
	}

	public void setAcomptabiliser(String acomptabiliser) {
		this.acomptabiliser = acomptabiliser;
	}

	public String getIdEns() {
		return idEns;
	}

	public void setIdEns(String idEns) {
		this.idEns = idEns;
	}

	public String getIdEns2() {
		return idEns2;
	}

	public void setIdEns2(String idEns2) {
		this.idEns2 = idEns2;
	}

	public Integer getNbHeuresEns() {
		return nbHeuresEns;
	}

	public void setNbHeuresEns(Integer nbHeuresEns) {
		this.nbHeuresEns = nbHeuresEns;
	}

	public Integer getNbHeuresEns2() {
		return nbHeuresEns2;
	}

	public void setNbHeuresEns2(Integer nbHeuresEns2) {
		this.nbHeuresEns2 = nbHeuresEns2;
	}

	public String getSurveillant() {
		return surveillant;
	}

	public void setSurveillant(String surveillant) {
		this.surveillant = surveillant;
	}

	public String getHeureExamn() {
		return heureExamn;
	}

	public void setHeureExamn(String heureExamn) {
		this.heureExamn = heureExamn;
	}

	public String getSalleExam() {
		return salleExam;
	}

	public void setSalleExam(String salleExam) {
		this.salleExam = salleExam;
	}

	public String getSurveillant2() {
		return surveillant2;
	}

	public void setSurveillant2(String surveillant2) {
		this.surveillant2 = surveillant2;
	}

	public String getSalleExam2() {
		return salleExam2;
	}

	public void setSalleExam2(String salleExam2) {
		this.salleExam2 = salleExam2;
	}

	public Integer getPeriode() {
		return periode;
	}

	public void setPeriode(Integer periode) {
		this.periode = periode;
	}

	public Integer getNumPromotioncs() {
		return numPromotioncs;
	}

	public void setNumPromotioncs(Integer numPromotioncs) {
		this.numPromotioncs = numPromotioncs;
	}

	public Integer getApCs() {
		return apCs;
	}

	public void setApCs(Integer apCs) {
		this.apCs = apCs;
	}

	private static final long serialVersionUID = 1L;

	public Prestation() {
		super();
	}

	public String getCodeModule() {
		return this.codeModule;
	}

	public void setCodeModule(String codeModule) {
		this.codeModule = codeModule;
	}

	public String getCodeCl() {
		return this.codeCl;
	}

	public void setCodeCl(String codeCl) {
		this.codeCl = codeCl;
	}

	public String getAnneeDeb() {
		return this.anneeDeb;
	}

	public void setAnneeDeb(String anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	public Integer getNumSemestre() {
		return this.numSemestre;
	}

	public void setNumSemestre(Integer numSemestre) {
		this.numSemestre = numSemestre;
	}

	public Set<Groupe> getGroupeCollection() {
		return groupeCollection;
	}

	public void setGroupeCollection(Set<Groupe> groupeCollection) {
		this.groupeCollection = groupeCollection;
	}

	public Enseignant getEnseignant() {
		return Enseignant;
	}

	public void setEnseignant(Enseignant idEnseignant) {
		this.Enseignant = idEnseignant;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}

	public Prestation(String codeModule, String codeCl, String anneeDeb,
			Integer numSemestre, String numPanier, String anneeFin,
			String description, Integer nbHeures, Integer coef,
			Integer numPeriodfe, Date dateFin, Date dateExamen,
			Date dateRattrapage, Date dateDebut, Integer nbHoraireRealises,
			String acomptabiliser, String idEns, String idEns2,
			Integer nbHeuresEns, Integer nbHeuresEns2, String surveillant,
			String heureExamn, String salleExam, String surveillant2,
			String salleExam2, Integer periode, Integer numPromotioncs,
			Integer apCs, Set<Groupe> groupeCollection,
			Enseignant idEnseignant, Module module, Creneau creneau) {
		super();
		this.codeModule = codeModule;
		this.codeCl = codeCl;
		this.anneeDeb = anneeDeb;
		this.numSemestre = numSemestre;
		this.numPanier = numPanier;
		this.anneeFin = anneeFin;
		this.description = description;
		this.nbHeures = nbHeures;
		this.coef = coef;
		this.numPeriodfe = numPeriodfe;
		this.dateFin = dateFin;
		this.dateExamen = dateExamen;
		this.dateRattrapage = dateRattrapage;
		this.dateDebut = dateDebut;
		this.nbHoraireRealises = nbHoraireRealises;
		this.acomptabiliser = acomptabiliser;
		this.idEns = idEns;
		this.idEns2 = idEns2;
		this.nbHeuresEns = nbHeuresEns;
		this.nbHeuresEns2 = nbHeuresEns2;
		this.surveillant = surveillant;
		this.heureExamn = heureExamn;
		this.salleExam = salleExam;
		this.surveillant2 = surveillant2;
		this.salleExam2 = salleExam2;
		this.periode = periode;
		this.numPromotioncs = numPromotioncs;
		this.apCs = apCs;
		this.groupeCollection = groupeCollection;
		this.Enseignant = idEnseignant;
		this.module = module;
		this.creneau = creneau;
	}

	public Set<Groupe> getGroupes() {
		return groupeCollection;
	}

	public void setGroupes(Set<Groupe> groupeCollection) {
		this.groupeCollection = groupeCollection;
	} 
	
 
	
	public PrestationPK getPrestationPK() {
		return prestationPK;
	}

	public void setPrestationPK(PrestationPK prestationPK) {
		this.prestationPK = prestationPK;
	}

}
