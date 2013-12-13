package tn.esprit.edt.gestion;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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

/**
 * Session Bean implementation class Services
 */
@Stateless
@LocalBean
public class Services implements ServicesRemote {

	public Services() {
		
	}
	@EJB
	EnseignantGesLocal enseignantGes;

	@Override
	public void create(Enseignant enseignant) {
		enseignantGes.create(enseignant);

	}

	@Override
	public void edit(Enseignant enseignant) throws NonexistentEntityException,
			Exception {

		enseignantGes.edit(enseignant);

	}

	@Override
	public void destroyEns(Integer id) throws NonexistentEntityException {

		enseignantGes.destroyEns(id);
	}

	@Override
	public Set<Enseignant> findEnseignantEntities() {
		return enseignantGes.findEnseignantEntities();
	}

	@Override
	public Set<Enseignant> findEnseignantEntities(int maxResults,
			int firstResult) {
		return enseignantGes.findEnseignantEntities(false, maxResults,
				firstResult);
	}

	@Override
	public Set<Enseignant> findEnseignantEntities(boolean all, int maxResults,
			int firstResult) {

		return enseignantGes.findEnseignantEntities(all, maxResults,
				firstResult);
	}
	public void  SetInspo(Creneau creneau ,Enseignant enseignant){
		enseignantGes.SetInspo(creneau, enseignant);
	}

	
	public Enseignant findEnseignantString(String id){
		return enseignantGes.findEnseignantString(id);
		}
	
	@Override
	public int getEnseignantCount() {

		return enseignantGes.getEnseignantCount();
	}
	public Set<Prestation> getPrestationByEnseignant(String codeCl){
		return enseignantGes.getPrestationByEnseignant(codeCl);
	}
	public String authentifierEns(String login,String password)
	{
	return	enseignantGes.authentifierEns(login, password);
	}

	
	@Override
	public Set<Enseignant> findEnseignantsByIds(int[] ids) {

		return enseignantGes.findEnseignantsByIds(ids);
	}

	@Override
	public Set<Enseignant> getListEns() {

		return enseignantGes.getListEns();
	}

	@Override
	public Set<Enseignant> findEnseignantsLike(String regexp) {

		return enseignantGes.findEnseignantsLike(regexp);
	}

	@Override
	public Set<Enseignant> findAvecRelEns(String idEnseignant) {

		return enseignantGes.findAvecRelEns(idEnseignant);
	}
	public String GetNomEnseignantByID(String Code){
		return enseignantGes.GetNomEnseignantByID(Code);
	}
	@Override
	public String NomEnseignant(String Code){
		return enseignantGes.NomEnseignant(Code);
	}

	@Override
	public Set<Enseignant> findEnseignantsAvecRel() {
		return enseignantGes.findEnseignantsAvecRel();
	}

	@Override
	public Enseignant findEnseignantAvecRel(String idEnseignant) {

		return enseignantGes.findEnseignantAvecRel(idEnseignant);
	}
	 public Set<Enseignant> getListEnseigants(){
		 return enseignantGes.getListEnseigants();
	 }
	 public Set<Prestation> EnsCren (Enseignant ens){
		 return enseignantGes.EnsCren(ens);
	 }

	 public Set<Creneau> getCreneauxEns(Enseignant ens){
		return enseignantGes.getCreneauxEns(ens);
	 }
	/************************* Classe *****************/
	@EJB
	GroupeGesLocal groupeGes;

	@Override
	public void create(Groupe groupe) {
		groupeGes.create(groupe);
	}

	@Override
	public void edit(Groupe groupe) throws NonexistentEntityException,
			Exception {

		groupeGes.edit(groupe);
	}

	@Override
	public void destroyPrestation(Integer id) throws NonexistentEntityException {
		groupeGes.destroy(id);
	}

	@Override
	public Set<Groupe> findGroupeEntities() {
		return groupeGes.findGroupeEntities();
	}

	@Override
	public Set<Groupe> findGroupeEntities(int maxResults, int firstResult) {
		return findGroupeEntities(maxResults, firstResult);
	}

	@Override
	public Set<Groupe> findGroupeEntities(boolean all, int maxResults,
			int firstResult) {

		return groupeGes.findGroupeEntities(all, maxResults, firstResult);
	}

	@Override
	public Groupe findGroupe(Integer id) {

		return groupeGes.findGroupe(id);
	}

	 public String GetLibelleGroupeByID(String Code){
		 return groupeGes.GetLibelleGroupeByID(Code);
	 }
	@Override
	public int getGroupeCount() {

		return groupeGes.getGroupeCount();
	}

	@Override
	public Set<Groupe> findGroupesByIds(int[] ids) {
		return groupeGes.findGroupesByIds(ids);
		// if(ids.length == 0){
		// Set<Classe> set = new HashSet<Classe>(new ArrayList<Classe>(0));
		// return set;
		// }
		//
		//
		//
		// try {
		// String strQ = "SELECT g " +
		// "FROM Groupe AS g " +
		// "WHERE g.id IN (?1";
		// for(int i = 2; i <= ids.length; i++)
		// strQ += (", ?"+i);
		// strQ += ")";
		// Query q = em.createQuery(strQ);
		// for(int i = 1; i <= ids.length; i++)
		// q.setParameter(i, ids[i-1]);
		//
		// Set<Classe> set = new HashSet<Classe>(q.getResultList());
		// return set;
		// } finally {
		//
		// }
	}

	@Override
	public Set<Groupe> getList() {

		return groupeGes.getList();
	}

	/**
	 * Trouve les groupes sans prestation pour le module (passé en paramètre)
	 * dans lequel ils sont inscrits.
	 * 
	 * @param module
	 * @return
	 */
	@Override
	public Set<Groupe> findGroupesSansPrest(Module module) {
		return groupeGes.findGroupesSansPrest(module);
	}
	
	
	
	public Set<Creneau> getListCreneauxGroupe(Groupe g){
		return groupeGes.getListCreneauxGroupe(g);
	}

	/**
	 * Trouve les groupes dont le libellé correspond à l'expression régulière
	 * passée en paramètre.
	 * 
	 * @param regexp
	 *            expression régulière
	 * @return
	 */
	@Override
	public Set<Groupe> findGroupesLike(String regexp) {

		return groupeGes.findGroupesLike(regexp);
	}

	/**
	 * Récupère une liste de groupes avec leurs relations préchargées.
	 * 
	 * @param idGroupe
	 *            paramètre facultatif, s'il vaut null tous les groupes seront
	 *            récupérés
	 * @return
	 */
	@Override
	public Set<Groupe> findAvecRel(String idGroupe) {

		return groupeGes.findAvecRel(idGroupe);
	}

	/**
	 * Récupère une liste de groupes avec leurs relations préchargées.
	 * 
	 * @return
	 */
	@Override
	public Set<Groupe> findGroupesAvecRel() {
		return groupeGes.findGroupesAvecRel();
	}

	/**
	 * Recupère un groupe avec ses relations préchargées.
	 * 
	 * @param idGroupe
	 * @return
	 */
	@Override
	public Groupe findGroupeAvecRel(String idGroupe) {
		return groupeGes.findGroupeAvecRel(idGroupe);
	}
//	@Override
//	public List<Module> getModules() {
//		
//		return groupeGes.getModules();
//	}

	/********************/

	@EJB
	CreneauGesLocal creneauGes;

	@Override
	public void create(Creneau creneau) throws PreexistingEntityException,
			Exception {
		creneauGes.create(creneau);
	}

	@Override
	public void edit(Creneau creneau) throws NonexistentEntityException,
			Exception {
		creneauGes.edit(creneau);
	}

	@Override
	public void destroyCreneau(Integer id) throws NonexistentEntityException {
		creneauGes.destroyCreneau(id);
	}

	@Override
	public Set<Creneau> findCreneauEntities() {
		return creneauGes.findCreneauEntities();
	}

	@Override
	public Set<Creneau> findCreneauEntities(int maxResults, int firstResult) {
		return creneauGes.findCreneauEntities(maxResults, firstResult);
	}

	@Override
	public Creneau findCreneau(Integer id) {
		return creneauGes.findCreneau(id);
	}

	@Override
	public int getCreneauCount() {
		return creneauGes.getCreneauCount();
	}

	@Override
	public Set<Creneau> findCreneauxByNums(int[] numeros) {
		return creneauGes.findCreneauxByNums(numeros);
	}
	public Creneau findCreneauById(Integer id){
		return creneauGes.findCreneauById(id);
	}

	@Override
	public int countPrestationsPour(Creneau creneau, Enseignant ens,
			Set<Groupe> groupes) {
		return creneauGes.countPrestationsPour(creneau, ens, groupes);
	}

	@Override
	public Set<Creneau> findAvecRelCreneau(Integer numCreneau) {
		return creneauGes.findAvecRelCreneau(numCreneau);
	}

	@Override
	public Set<Creneau> findCreneauxAvecRel() {
		return creneauGes.findCreneauxAvecRel();
	}
	public Set<Creneau> getAllCreneaux(){
		return creneauGes.getAllCreneaux();
	}

	/************************/
	@EJB
	ModuleGesLocal moduleGes;

	@Override
	public void create(Module module) {
		moduleGes.create(module);
	}

	@Override
	public void edit(Module module) throws NonexistentEntityException,
			Exception {
		moduleGes.edit(module);
	}

	@Override
	public void destroyModule(Integer id) throws NonexistentEntityException {
		moduleGes.destroyModule(id);
	}

	@Override
	public Set<Module> findModuleEntities() {
		return moduleGes.findModuleEntities();
	}

	@Override
	public Set<Module> findModuleEntities(int maxResults, int firstResult) {
		return moduleGes.findModuleEntities(maxResults, firstResult);
	}

	@Override
	public Set<Module> findModuleEntities(boolean all, int maxResults,
			int firstResult) {
		return moduleGes.findModuleEntities(all, maxResults, firstResult);
	}
	 

	@Override
	public Module findModule(Integer id) {
		return moduleGes.findModule(id);
	}

	@Override
	public int getModuleCount() {
		return moduleGes.getModuleCount();
	}

	@Override
	public Set<Module> getListModule() {

		return moduleGes.getListModule();
	}

	@Override
	public Set<Module> findAvecRelModule(String idModule) {

		return moduleGes.findAvecRelModule(idModule);
	}

	@Override
	public Set<Module> findModulesAvecRel() {
		return moduleGes.findModulesAvecRel();
	}

	@Override
	public Module findModuleAvecRel(String idModule) {
		return moduleGes.findModuleAvecRel(idModule);
	}
	public Module findModuleAvecNom(String nomModule)
	{
		return moduleGes.findModuleAvecNom(nomModule);
	}
	
	/*****************************/
	@EJB
	PrestationGesLocal prestationGes;

	@Override
	public void create(Prestation prestation) {
		prestationGes.create(prestation);
	}

	@Override
	public void edit(Prestation prestation) throws NonexistentEntityException,
			Exception {
		prestationGes.edit(prestation);

	}

	@Override
	public void destroy(Integer id) throws NonexistentEntityException {
		prestationGes.destroy(id);

	}

	@Override
	public Set<Prestation> findPrestationEntities() {
	
		return prestationGes.findPrestationEntities();
	}

	@Override
	public Set<Prestation> findPrestationEntities(int maxResults,
			int firstResult) {
	
		return prestationGes.findPrestationEntities(maxResults, firstResult);
	}

	@Override
	public Set<Prestation> findPrestationEntities(boolean all, int maxResults,
			int firstResult) {
		return prestationGes.findPrestationEntities(all,maxResults,firstResult);
	}

	@Override
	public Prestation findPrestation(Integer id) {
		return prestationGes.findPrestation(id);
	}

	@Override
	public int getPrestationCount() {
		return prestationGes.getPrestationCount();
	}

	@Override
	public Set<Prestation> findPrestationsRelativesEDT(int[] idsGrpSel,
			int[] idsEnsSel, boolean sontCasees) {
		
		return prestationGes.findPrestationsRelativesEDT(idsGrpSel, idsEnsSel, sontCasees);
	}

	@Override
	public Set<Prestation> findPrestationsNonCasees(int[] idsGrpSel,
			int[] idsEnsSel) {
		return prestationGes.findPrestationsNonCasees(idsGrpSel, idsEnsSel);
	}

	@Override
	public Set<Prestation> findPrestationsParModule(Module module) {
		return prestationGes.findPrestationsParModule(module);
	}

	@Override
	public void interchanger(Prestation prestAPlacer, Prestation prestARemplacer)
			throws NonexistentEntityException, Exception {
		prestationGes.interchanger(prestAPlacer, prestARemplacer);

	}

	@Override
	public boolean suitPrestation(Groupe groupe, Creneau creneau) {
	
		return prestationGes.suitPrestation(groupe, creneau);
	}

	@Override
	public boolean assurePrestation(Enseignant enseignant, Creneau creneau) {
		return prestationGes.assurePrestation(enseignant, creneau);
	}
	@Override
	public Set<Prestation> findAvecRelMPCS(String codeModule,String codeCl, String anneeDeb , String numSemestre)
	{
		return prestationGes.findAvecRelMPCS(codeModule, codeCl, anneeDeb, numSemestre);
	}

	@Override
	public Set<Prestation> findAvecRelPrestation(String codeModule , String codeCl, String anneeDeb, String numSemestre) {
		return prestationGes.findAvecRelMPCS(codeModule, codeCl, anneeDeb, numSemestre)
				
		;
	}
	public Set<Prestation> getCreneuPourEnseignant(String idEnseignant){
		return prestationGes.getCreneuPourEnseignant(idEnseignant);
	}

	@Override
	public Set<Prestation> findPrestationsAvecRel() {
		return prestationGes.findPrestationsAvecRel();
	}

	@Override
	public Prestation findPrestationAvecRel(String codeModule , String codeCl, String anneeDeb, String numSemestre) {
		return prestationGes.findPrestationAvecRel( codeModule ,  codeCl,  anneeDeb,  numSemestre);
	}

	@Override
	public Set<Prestation> findPrestationsSurEDT(int[] idsGrpSel,
			int[] idsEnsSel) {

		return prestationGes.findPrestationsSurEDT(idsGrpSel, idsEnsSel);
	}
	public String GetNomGroupeByID(String code){
		return prestationGes.GetNomGroupeByID(code);
	}
	
	
	
	@Override
	public void setEm(EntityManager em) {
		prestationGes.setEm(em);
	}

	@Override
	public EntityManager getEm() {
		return prestationGes.getEm();
	}

	/**********************/
	@EJB
	PoolGesLocal poolGes;

	@Override
	public void create(Pool pool) {
		poolGes.create(pool);
		
	}

	@Override
	public void edit(Pool pool) throws NonexistentEntityException, Exception {
		poolGes.edit(pool);
		
	}

	@Override
	public void destroyPool(Integer id) throws NonexistentEntityException {
		poolGes.destroyPool(id);
		
	}

	@Override
	public Set<Pool> findPoolEntities() {
	
		return poolGes.findPoolEntities();
	}

	@Override
	public Set<Pool> findPoolEntities(int maxResults, int firstResult) {
		
		return poolGes.findPoolEntities();
	}

	@Override
	public Set<Pool> findPoolEntities(boolean all, int maxResults,
			int firstResult) {
		
		return poolGes.findPoolEntities(all, maxResults, firstResult);
	}

	@Override
	public Pool findPool(Integer id) {
		return poolGes.findPool(id);
	}

	@Override
	public int getPoolCount() {
		return poolGes.getPoolCount();
	}

	@Override
	public Set<Pool> findPoolsByIds(int[] ids) {
		return poolGes.findPoolsByIds(ids);
	}

	@Override
	public Set<Pool> getListPool() {
		return poolGes.getListPool();
	}

	@Override
	public Set<Pool> findAvecRelPool(Integer idPool) {
	
		return poolGes.findAvecRelPool(idPool);	}

	@Override
	public Set<Pool> findPoolsAvecRel() {
		return poolGes.findPoolsAvecRel();
	}

	@Override
	public Pool findPoolAvecRel(Integer idPool) {
		return poolGes.findPoolAvecRel(idPool);
	}

	@Override
	public Map<Pool, Integer> getNbPrestRestant(Creneau creneau, Set<Pool> pools) {
	
		return poolGes.getNbPrestRestant(creneau, pools);
	}
	/****************/
	@EJB
	NbPrestRestantLocal nbPrestRestant;
	
	

	@Override
	public int getNbPrestRestant() {
		
		return nbPrestRestant.getNbPrestRestant();
	}

	@Override
	public Pool getPool() {
		return nbPrestRestant.getPool();
	}

	

	@Override
	public List<Module> getGroupesInscrits() {
		
		return moduleGes.getGroupesInscrits();
	}

	@Override
	public Set<Prestation> getListPrestation() {
		
		return prestationGes.getListPrestation();
	}
	public Set<Prestation> getPrestationByGroupe( String codeCl){
		return prestationGes.getPrestationByGroupe(codeCl);
	}

	
	@EJB
	AdminGesLocal adminGes;
	
	@Override
	public void addAdmin(Admin m) {
		adminGes.addAdmin(m);
		
	}

	@Override
	public void updateAdmin(Admin m) {
		adminGes.updateAdmin(m);
		
	}

	@Override
	public void deleteAdmin(int ID) {
		adminGes.deleteAdmin(ID);		
	}

	@Override
	public Admin getAdminByID(int ID) {
		
		return adminGes.getAdminByID(ID);
	}

	@Override
	public List<Admin> getAllAdmin() {
		return adminGes.getAllAdmin();
	}

	@Override
	public String authentifier(String login, String password) {
		return adminGes.authentifier(login, password);
	}

	@Override
	public Prestation findPrestationMultipleId(String id1,String id2,String id3,Integer id4) {
		
		return prestationGes.findPrestationMultipleId(id1,id2,id3,id4);
	}

	@Override
	public void addCrenauToPrestation(String id1, String id2, String id3,
			Integer id4, Integer numCrenau) {
		prestationGes.addCrenauToPrestation(id1, id2, id3, id4, numCrenau);
		
	}

	@Override
	public Groupe findGroupeById(String id) {
	
		return groupeGes.findGroupeById(id);
	}

	@Override
	public String findIDbyNom(String loginEns){
	
		return enseignantGes.findIDbyNom(loginEns);
	}

	@Override
	public String GetNomAdmin(String Code) {
		return adminGes.GetNomAdmin(Code);
	}

	@Override
	public String GetNomEnseignant(String Code) {
		
		return enseignantGes.GetNomEnseignant(Code);
	}

	/*
	 *  pour Modif, 
	 * */
	
	@Override
	public Set<Prestation> ModulesParGroupe(String grp) {
		return	groupeGes.ModulesParGroupe(grp);
		
	}
	

	@Override
	public Set<Prestation> findModulesByGroupe(String codeCl) {
		return groupeGes.findModulesByGroupe(codeCl);
	}
	
	

}
