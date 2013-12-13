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
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;



import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.exceptions.PreexistingEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;

/**
 * Session Bean implementation class CreneauHoraireGes
 */
@Stateless
@LocalBean
public class CreneauGes implements CreneauGesLocal {
	@PersistenceContext(unitName = "EDT_EJB")
	EntityManager em;
   @Override
	 public void create(Creneau creneau) throws PreexistingEntityException, Exception {
    
	    	   
	            em.persist(creneau);
	           
	         
	        
	    }
   @Override
	    public void edit(Creneau creneau) throws NonexistentEntityException, Exception {
	       
	        try {
	          
	            creneau = em.merge(creneau);
	         
	        } catch (Exception ex) {
	            String msg = ex.getLocalizedMessage();
	            if (msg == null || msg.length() == 0) {
	                Integer id =  creneau.getNumero();
	                if (findCreneau(id) == null) {
	                    throw new NonexistentEntityException("The creneau with id " + id + " no longer exists.");
	                }
	            }
	            throw ex;
	        } 
	    }

   @Override
	    public void destroyCreneau(Integer id) throws NonexistentEntityException {
	       
	        try {
	           
	        	Creneau creneau;
	            try {
	                creneau = em.getReference(Creneau.class, id);
	                creneau.getNumero();
	            } catch (EntityNotFoundException enfe) {
	                throw new NonexistentEntityException("The creneau with id " + id + " no longer exists.", enfe);
	            }
	            em.remove(creneau);
	           
	        } finally {
	            
	        }
	    }
   @Override
	    public Set<Creneau> findCreneauEntities() {
	        return findCreneauEntities(true, -1, -1);
	    }
   @Override
	    public Set<Creneau> findCreneauEntities(int maxResults, int firstResult) {
	        return findCreneauEntities(false, maxResults, firstResult);
	    }
   
	    private Set<Creneau> findCreneauEntities(boolean all, int maxResults, int firstResult) {
	        
	        try {
	            Query q = em.createQuery("select object(o) from Creneau as o");
	            if (!all) {
	                q.setMaxResults(maxResults);
	                q.setFirstResult(firstResult);
	            }

	            Set<Creneau> set = new HashSet<Creneau>(q.getResultList());
	            for (Creneau creneau : set) {
					System.out.println(creneau.getNumero());
				}
	            return set;
	        }finally {
	            
	        }
	    }
   @Override
	    public Creneau findCreneau(Integer id) {
	       
	        
	            return em.find(Creneau.class, id);
	      
	    }
   @Override
	    public int getCreneauCount() {
	        
	        try {
	            return ((Long) em.createQuery("select count(o) from Creneau as o").getSingleResult()).intValue();
	        } finally {
	           
	        }
	    }
   @Override
	    public Set<Creneau> findCreneauxByNums(int[] numeros) {

	        if(numeros.length == 0){
	          
	        Set set = new HashSet(new ArrayList<Creneau>(0));
	        return set;
	        }
	                 
	        try {
	            String strQ = "SELECT c " +
	                          "FROM Creneau AS c " +
	                          "WHERE c.numero IN (?1";
	            for(int i = 2; i <= numeros.length; i++)
	                strQ += (", ?"+i);
	            strQ += ")";
	            Query q = em.createQuery(strQ);
	            for(int i = 1; i <= numeros.length; i++)
	                q.setParameter(i, numeros[i-1]);


	            Set<Creneau> set = new HashSet<Creneau>(q.getResultList());
	            return set;
	        } finally {
	            
	        }
	    }
   @Override
	    public int countPrestationsPour(Creneau creneau, Enseignant ens, Set<Groupe> groupes) {
	        
	        try {
	            String strQ = "SELECT COUNT(DISTINCT p) " +
	                          "FROM Prestation AS p " +
	                            "LEFT JOIN p.groupeCollection g " +
	                          "WHERE p.numCreneau = :creneau " +
	                               "AND (p.idEnseignant = :ens ";
	            if(groupes.size() > 0) {
	               strQ += "OR g.id IN (?1";
	               for(int i = 2; i <= groupes.size(); i++)
	                    strQ += (", ?"+i);
	                strQ += ")";
	            }
	            strQ += ")";
	            
	            Query q = em.createQuery(strQ);
	            q.setParameter("creneau", creneau);
	            q.setParameter("ens", ens);
	            List<Groupe> listeDeGroupe = new ArrayList<Groupe>(groupes);
	            for(int i = 1; i <= listeDeGroupe.size(); i++){
	            	
	                q.setParameter(i, listeDeGroupe.get(i-1).getCode());
	                }

	            Long nbP = (Long) q.getSingleResult();
	            return nbP.intValue();
	        } finally {
	           
	        }
	    }
	    
   @Override
	    public Set<Creneau> findAvecRelCreneau(Integer numCreneau) {
	       
	       
	            String strQ = "SELECT DISTINCT c " +
	                          "FROM Creneau AS c " +
	                            "LEFT JOIN FETCH c.prestationCollection " +
	                            "LEFT JOIN FETCH c.enseignantCollection " +
	                            "LEFT JOIN FETCH c.groupeCollection ";
	            if(numCreneau != null)
	                strQ += "WHERE c.numero = :numCreneau ";

	            Query q = em.createQuery(strQ);
	            q.setHint(QueryHints.REFRESH, HintValues.TRUE);
	            if(numCreneau != null)
	                q.setParameter("numCreneau", numCreneau);


	            Set<Creneau> set = new HashSet<Creneau>(q.getResultList());
	            return set;
	       
	    }
   @Override
	    public Set<Creneau> findCreneauxAvecRel() {
	        return this.findAvecRelCreneau(null);
	    }
   
   public Set<Creneau> getAllCreneaux() {
	   
	     
       String strQ = "SELECT m " +
                     "FROM Creneau AS m " ;
       Set<Creneau> set = new HashSet<Creneau>(em.createQuery(strQ).getResultList());
       return set;
   
}
   
   public Creneau findCreneauById(Integer id) {
		
		return em.find(Creneau.class, id);
//		insert into gestion.indisponibilite_enseignant  values (4,4);

	}
}
