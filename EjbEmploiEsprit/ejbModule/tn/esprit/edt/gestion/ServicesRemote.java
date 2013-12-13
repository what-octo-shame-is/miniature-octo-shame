package tn.esprit.edt.gestion;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;
import javax.persistence.EntityManager;

import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.exceptions.PreexistingEntityException;
import tn.esprit.edt.persistance.Admin;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;
import tn.esprit.edt.persistance.Pool;
import tn.esprit.edt.persistance.Prestation;

@Remote
public interface ServicesRemote {

	public void create(Enseignant enseignant);

	public void edit(Enseignant enseignant) throws NonexistentEntityException,
			Exception;

	public void destroyEns(Integer id) throws NonexistentEntityException;

	public Set<Enseignant> findEnseignantEntities();

	public String NomEnseignant(String Code);

	public Set<Enseignant> findEnseignantEntities(int maxResults,
			int firstResult);

	public Set<Enseignant> findEnseignantEntities(boolean all, int maxResults,
			int firstResult);

	public String authentifierEns(String login, String password);

	public String GetNomEnseignant(String Code);

	public Set<Enseignant> getListEnseigants();

	public int getEnseignantCount();

	public void SetInspo(Creneau creneau, Enseignant enseignant);

	public Set<Enseignant> findEnseignantsByIds(int[] ids);

	public Set<Enseignant> getListEns();

	public String GetNomEnseignantByID(String Code);

	public Set<Enseignant> findEnseignantsLike(String regexp);

	public Set<Enseignant> findAvecRelEns(String idEnseignant);

	public Set<Enseignant> findEnseignantsAvecRel();

	public String findIDbyNom(String loginEns);

	public Enseignant findEnseignantAvecRel(String idEnseignant);

	public Enseignant findEnseignantString(String nom);

	// public List<Module> getModules();
	public List<Module> getGroupesInscrits();

	public Set<Creneau> getCreneauxEns(Enseignant ens);

	public Groupe findGroupeById(String id);

	public Set<Prestation> EnsCren(Enseignant ens);

	public Set<Prestation> getPrestationByEnseignant(String codeCl);

	/***************************************/

	public void create(Groupe groupe);

	public void edit(Groupe groupe) throws NonexistentEntityException,
			Exception;

	public void destroy(Integer id) throws NonexistentEntityException;

	public Set<Groupe> findGroupeEntities();

	public Set<Groupe> findGroupeEntities(int maxResults, int firstResult);

	public Set<Groupe> findGroupeEntities(boolean all, int maxResults,
			int firstResult);

	public Groupe findGroupe(Integer id);

	public String GetLibelleGroupeByID(String Code);

	public int getGroupeCount();

	public Set<Groupe> findGroupesByIds(int[] ids);

	public Set<Groupe> getList();

	public Set<Groupe> findGroupesSansPrest(Module module);

	public Set<Groupe> findGroupesLike(String regexp);

	public Set<Groupe> findAvecRel(String idGroupe);

	public Set<Groupe> findGroupesAvecRel();

	public Groupe findGroupeAvecRel(String idGroupe);

	public Set<Creneau> getListCreneauxGroupe(Groupe g);

	/***************************************/

	public void create(Creneau creneau) throws PreexistingEntityException,
			Exception;

	public void edit(Creneau creneau) throws NonexistentEntityException,
			Exception;

	public void destroyCreneau(Integer id) throws NonexistentEntityException;

	public Set<Creneau> findCreneauEntities();

	public Set<Creneau> findCreneauEntities(int maxResults, int firstResult);

	public Creneau findCreneau(Integer id);

	public int getCreneauCount();

	public Set<Creneau> findCreneauxByNums(int[] numeros);

	public int countPrestationsPour(Creneau creneau, Enseignant ens,
			Set<Groupe> groupes);

	public Set<Creneau> findAvecRelCreneau(Integer numCreneau);

	public Set<Creneau> findCreneauxAvecRel();

	public Set<Creneau> getAllCreneaux();

	public Creneau findCreneauById(Integer id);

	// /*************/

	public void create(Module module);

	public void edit(Module module) throws NonexistentEntityException,
			Exception;

	public void destroyModule(Integer id) throws NonexistentEntityException;

	public Set<Module> findModuleEntities();

	public Set<Module> findModuleEntities(int maxResults, int firstResult);

	public Set<Module> findModuleEntities(boolean all, int maxResults,
			int firstResult);

	public Module findModule(Integer id);

	public int getModuleCount();

	public Set<Module> getListModule();

	public Set<Module> findAvecRelModule(String idModule);

	public Set<Module> findModulesAvecRel();

	public Module findModuleAvecRel(String idModule);

	public Module findModuleAvecNom(String nomModule);

	/***********/
	public void create(Prestation prestation);

	public void edit(Prestation prestation) throws NonexistentEntityException,
			Exception;

	public void destroyPrestation(Integer id) throws NonexistentEntityException;

	public Set<Prestation> findPrestationEntities();

	public Set<Prestation> findPrestationEntities(int maxResults,
			int firstResult);

	public Set<Prestation> findPrestationEntities(boolean all, int maxResults,
			int firstResult);

	public Prestation findPrestation(Integer id);

	public int getPrestationCount();

	public Set<Prestation> getListPrestation();

	public Set<Prestation> findPrestationsRelativesEDT(int[] idsGrpSel,
			int[] idsEnsSel, boolean sontCasees);

	public Set<Prestation> findPrestationsNonCasees(int[] idsGrpSel,
			int[] idsEnsSel);

	public Set<Prestation> findPrestationsParModule(Module module);

	public void interchanger(Prestation prestAPlacer, Prestation prestARemplacer)
			throws NonexistentEntityException, Exception;

	public boolean suitPrestation(Groupe groupe, Creneau creneau);

	public boolean assurePrestation(Enseignant enseignant, Creneau creneau);

	public Set<Prestation> findAvecRelPrestation(String codeModule,
			String codeCl, String anneeDeb, String numSemestre);

	public Set<Prestation> findPrestationsAvecRel();

	public Set<Prestation> getCreneuPourEnseignant(String idEnseignant);

	public Prestation findPrestationAvecRel(String codeModule, String codeCl,
			String anneeDeb, String numSemestre);

	public Set<Prestation> findPrestationsSurEDT(int[] idsGrpSel,
			int[] idsEnsSel);

	public void setEm(EntityManager em);

	public EntityManager getEm();

	public void addCrenauToPrestation(String id1, String id2, String id3,
			Integer id4, Integer numCrenau);

	public Set<Prestation> findAvecRelMPCS(String codeModule, String codeCl,
			String anneeDeb, String numSemestre);

	public Prestation findPrestationMultipleId(String id1, String id2,
			String id3, Integer id4);

	public Set<Prestation> getPrestationByGroupe(String codeCl);

	public String GetNomGroupeByID(String code);
	

	/********************************/
	public void create(Pool pool);

	public void edit(Pool pool) throws NonexistentEntityException, Exception;

	public void destroyPool(Integer id) throws NonexistentEntityException;

	public Set<Pool> findPoolEntities();

	public Set<Pool> findPoolEntities(int maxResults, int firstResult);

	public Set<Pool> findPoolEntities(boolean all, int maxResults,
			int firstResult);

	public Pool findPool(Integer id);

	public int getPoolCount();

	public Set<Pool> findPoolsByIds(int[] ids);

	public Set<Pool> getListPool();

	public Set<Pool> findAvecRelPool(Integer idPool);

	public Set<Pool> findPoolsAvecRel();

	public Pool findPoolAvecRel(Integer idPool);

	public Map<Pool, Integer> getNbPrestRestant(Creneau creneau, Set<Pool> pools);

	/*******************/
	public int getNbPrestRestant();

	public Pool getPool();

	/************************* admin ****/
	public void addAdmin(Admin m);

	public void updateAdmin(Admin m);

	public void deleteAdmin(int ID);

	public Admin getAdminByID(int ID);

	public List<Admin> getAllAdmin();

	public String authentifier(String login, String password);

	public String GetNomAdmin(String Code);
	/***
	 * Pour modif
	 */
	public  Set<Prestation> ModulesParGroupe( String grp);
	public  Set<Prestation> findModulesByGroupe(String codeCl);


	
}

