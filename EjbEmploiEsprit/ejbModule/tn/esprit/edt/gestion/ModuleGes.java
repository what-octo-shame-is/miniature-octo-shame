package tn.esprit.edt.gestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;





/**
 * Session Bean implementation class ModuleGes
 */
@Stateless
@LocalBean
public class ModuleGes implements ModuleGesLocal {

	@PersistenceContext(unitName = "EDT_EJB")
	EntityManager em;
	
      @Override
	 public void create(Module module) {
	        
     
         em.persist(module);
      
 }
      @Override
 public void edit(Module module) throws NonexistentEntityException, Exception {
    
         module = em.merge(module);
         
     
 }
      @Override
 public void destroyModule(Integer id) throws NonexistentEntityException {
    
    
       
         Module module;
         try {
             module = em.getReference(Module.class, id);
            Integer.parseInt( module.getId());
         } catch (EntityNotFoundException enfe) {
             throw new NonexistentEntityException("The module with id " + id + " no longer exists.", enfe);
         }
         em.remove(module);
        
     
 }
      @Override
 public Set<Module> findModuleEntities() {
     return findModuleEntities(true, -1, -1);
 }
      @Override
 public Set<Module> findModuleEntities(int maxResults, int firstResult) {
     return findModuleEntities(false, maxResults, firstResult);
 }
      @Override
 public Set<Module> findModuleEntities(boolean all, int maxResults, int firstResult) {
    
     
         Query q = em.createQuery("select object(o) from Module as o");
         if (!all) {
             q.setMaxResults(maxResults);
             q.setFirstResult(firstResult);
         }
         Set<Module> set = new HashSet<Module>(q.getResultList());
         return set;
     
 }
      @Override
 public Module findModule(Integer id) {
   
         return em.find(Module.class, id);
     
 }
      @Override
 public int getModuleCount() {
    
     try {
         return ((Long) em.createQuery("select count(o) from Module as o").getSingleResult()).intValue();
     } finally {
        
     }
 }
      @Override
 public Set<Module> getListModule() {
   
     
         String strQ = "SELECT m " +
                       "FROM Module AS m " ;
         Set<Module> set = new HashSet<Module>(em.createQuery(strQ).getResultList());
         return set;
     
 }

 /**
  * Récupère une liste de modules avec leurs relations préchargées.
  * @param idModule paramètre facultatif, s'il vaut null tous les modules seront récupérés
  * @return
  */
      @Override
 public Set<Module> findAvecRelModule(String idModule) {
    
     try {
         String strQ = "SELECT DISTINCT m " +
                       "FROM Module AS m " +
                         "LEFT JOIN FETCH m.poolCollection " +
//                      "LEFT JOIN FETCH m.groupeCollection " +
                         "LEFT JOIN FETCH m.prestationCollection ";
         if(idModule != null)
             strQ += "WHERE m.id = :idModule ";
         strQ += "ORDER BY m.designation";

         Query q = em.createQuery(strQ);
         q.setHint(QueryHints.REFRESH, HintValues.TRUE);
         if(idModule != null)
             q.setParameter("idModule", idModule);
         Set<Module> set = new HashSet<Module>(q.getResultList());
         for (Module module : set) {
        	 System.out.println("designation"+module.getDesignation());
 		}
        
       
         return set;
       
     } finally {
         
     }
 }

 /**
  * Récupère une liste de modules avec leurs relations préchargées.
  * @return
  */
      @Override
 public Set<Module> findModulesAvecRel() {
     return this.findAvecRelModule(null);
 }

      
      public List<Module> getGroupesInscrits() {
  	    
	    	return em.createNativeQuery("select * from CLASSE").getResultList();
	    }
 /**
  * Recupère un module avec ses relations préchargées.
  * @param idModule
  * @return
  */
 
      @Override
 public Module findModuleAvecRel(String idModule) {
 	Set<Module> liste = findAvecRelModule(idModule);
 	List<Module> listModules = new ArrayList<Module>(liste);
     return (listModules.size() > 0 ? listModules.get(0): null);
 }
      
      public Module findModuleAvecNom(String nomModule){
    	  
    	  String strQ = "select m from Module AS m where m.designation = "+  nomModule;

    Module module =  (Module)em.createQuery(strQ).getSingleResult();
    return module;
      }

      

     
     
}
