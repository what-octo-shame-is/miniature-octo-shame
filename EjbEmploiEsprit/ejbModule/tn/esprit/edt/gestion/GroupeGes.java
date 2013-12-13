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
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;
import tn.esprit.edt.persistance.Prestation;

/**
 * Session Bean implementation class ClasseGes
 */
@Stateless
@LocalBean
public class GroupeGes implements GroupeGesLocal {
	@PersistenceContext(unitName = "EDT_EJB")
	EntityManager em;

	public void create(Groupe groupe) {

		em.persist(groupe);

	}

	public void edit(Groupe groupe) throws NonexistentEntityException,
			Exception {

		try {

			groupe = em.merge(groupe);

		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = Integer.parseInt(groupe.getCode());
				if (findGroupe(id) == null) {
					throw new NonexistentEntityException("The groupe with id "
							+ id + " no longer exists.");
				}
			}
			throw ex;
		} finally {

		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {

			Groupe groupe;
			try {
				groupe = em.getReference(Groupe.class, id);
				Integer.parseInt(groupe.getCode());
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The groupe with id " + id
						+ " no longer exists.", enfe);
			}
			em.remove(groupe);

		} finally {

		}
	}

	public Set<Groupe> findGroupeEntities() {
		return findGroupeEntities(true, -1, -1);
	}

	public Set<Groupe> findGroupeEntities(int maxResults, int firstResult) {
		return findGroupeEntities(false, maxResults, firstResult);
	}

	public Set<Groupe> findGroupeEntities(boolean all, int maxResults,
			int firstResult) {

		try {
			Query q = em.createQuery("select object(o) from Groupe as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			Set<Groupe> set = new HashSet<Groupe>(q.getResultList());
			return set;
		} finally {

		}
	}

	public Groupe findGroupe(Integer id) {

		try {
			return em.find(Groupe.class, id);
		} finally {

		}
	}

	public int getGroupeCount() {

		try {
			return ((Long) em.createQuery("select count(o) from Groupe as o")
					.getSingleResult()).intValue();
		} finally {

		}
	}

	public Set<Groupe> findGroupesByIds(int[] ids) {

		if (ids.length == 0) {
			Set<Groupe> set = new HashSet<Groupe>(new ArrayList<Groupe>(0));
			return set;
		}

		try {
			String strQ = "SELECT g " + "FROM Groupe AS g "
					+ "WHERE g.id IN (?1";
			for (int i = 2; i <= ids.length; i++)
				strQ += (", ?" + i);
			strQ += ")";
			Query q = em.createQuery(strQ);
			for (int i = 1; i <= ids.length; i++)
				q.setParameter(i, ids[i - 1]);

			Set<Groupe> set = new HashSet<Groupe>(q.getResultList());
			return set;
		} finally {

		}
	}

	public Set<Groupe> getList() {

		try {
			String strQ = "SELECT g " + "FROM Groupe AS g "
					+ "ORDER BY g.libelle";
			Set<Groupe> set = new HashSet<Groupe>(em.createQuery(strQ)
					.getResultList());
			return set;
		} finally {

		}
	}

	/**
	 * Trouve les groupes sans prestation pour le module (passé en paramètre)
	 * dans lequel ils sont inscrits.
	 * 
	 * @param module
	 * @return
	 */
	public Set<Groupe> findGroupesSansPrest(Module module) {

		try {
			String strQ = "SELECT DISTINCT g " + "FROM Groupe g "
					+ "INNER JOIN g.moduleCollection m " + "WHERE m = :module "
					+ "AND g.id NOT IN (SELECT grpAvecPrest.id "
					+ "FROM Groupe grpAvecPrest "
					+ "INNER JOIN grpAvecPrest.prestationCollection p "
					+ "WHERE p.module = m) " + "ORDER BY g.libelle";
			Query q = em.createQuery(strQ);
			q.setParameter("module", module);
			Set<Groupe> set = new HashSet<Groupe>(q.getResultList());
			return set;
		} finally {

		}
	}
	
	
	
	 public String GetLibelleGroupeByID(String Code){
	  		try {
	  			String strQ = "SELECT p FROM Groupe AS p where p.id='"
	  					+ Code + "'  ";

	  			Groupe set = (Groupe) em.createQuery(strQ).getSingleResult();
	  			return set.getLibelle();
	  		} catch (Exception e) {
	  		
	  			return null;
	  		}
	  		
	  	}
	 public  Set<Prestation> ModulesParGroupe( String grp){
 
		 
	
		 String strQ = "SELECT p FROM Prestation AS p where p.codeCl='"
					+ grp + "'  ";
				;
			Set<Prestation> set = new HashSet<Prestation>(em.createQuery(strQ)
					.getResultList());
			return set;
		 
		 
		 
				}
				
//			}
		 
	 /*
	  * methode qui permet normalement d'avoir la liste de prestation selon groupe càd chause groupe a ses prestation
	  */
	 public  Set<Prestation> findModulesByGroupe(String codeCl){
			
		 String strQ = "SELECT e " + "FROM Prestation AS e ";

			if (codeCl != null)
				strQ += "WHERE e.codeCl = :codeCl ";
			strQ += "ORDER BY e.codeCl";

			Query q = em.createQuery(strQ);
			q.setHint(QueryHints.REFRESH, HintValues.TRUE);
			if (codeCl != null)
				q.setParameter("codeCl", codeCl);

			Set<Prestation> set = new HashSet<Prestation>(q.getResultList());
			for (Prestation prest : set) {
				System.out.println("nom" + prest.getCodeModule());
			}
				return set;
	 }
		 
		 
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Trouve les groupes dont le libellé correspond à l'expression régulière
	 * passée en paramètre.
	 * 
	 * @param regexp
	 *            expression régulière
	 * @return
	 */
	public Set<Groupe> findGroupesLike(String regexp) {

		try {
			String strQ = "SELECT g " + "FROM Groupe g "
					+ "WHERE g.libelle LIKE :regexp";
			Query q = em.createQuery(strQ);
			q.setParameter("regexp", regexp);
			Set<Groupe> set = new HashSet<Groupe>(q.getResultList());
			return set;
		} finally {

		}
	}
	

	/**
	 * Récupère une liste de groupes avec leurs relations préchargées.
	 * 
	 * @param idGroupe
	 *            paramètre facultatif, s'il vaut null tous les groupes seront
	 *            récupérés
	 * @return
	 */
	public Set<Groupe> findAvecRel(String idGroupe) {

		try {
			String strQ = "SELECT DISTINCT g " + "FROM Groupe AS g "
				
					+ "LEFT JOIN FETCH g.creneauCollection "
					+ "LEFT JOIN FETCH g.prestationCollection ";
			if (idGroupe != null)
				strQ += "WHERE g.id = :idGroupe ";
			strQ += "ORDER BY g.libelle";

			Query q = em.createQuery(strQ);
			q.setHint(QueryHints.REFRESH, HintValues.TRUE);
			if (idGroupe != null)
				q.setParameter("idGroupe", idGroupe);
			Set<Groupe> set = new HashSet<Groupe>(q.getResultList());
			for (Groupe groupe : set) {
				System.out.println("nom" + groupe.getLibelle() );
			}
		
			return set;
		} finally {

		}
	}

	/**
	 * Récupère une liste de groupes avec leurs relations préchargées.
	 * 
	 * @return
	 */
	public Set<Groupe> findGroupesAvecRel() {
		return this.findAvecRel(null);
	}

	/**
	 * Recupère un groupe avec ses relations préchargées.
	 * 
	 * @param idGroupe
	 * @return
	 */
	public Groupe findGroupeAvecRel(String idGroupe) {
		Set<Groupe> liste = findAvecRel(idGroupe);
		List<Groupe> groupes = new ArrayList<Groupe>(liste);
		return (groupes.size() > 0 ? groupes.get(0) : null);
	}

	public Set<Creneau> getListCreneauxGroupe(Groupe g) {

		return g.getIndisponibilites();
	}

	@Override
	public Groupe findGroupeById(String id) {
		// TODO Auto-generated method stub
		String strQ = "SELECT g FROM Groupe AS g where g.id='" + id + "'";
		Query q = em.createQuery(strQ);

		Groupe gr = (Groupe) q.getResultList().get(0);
		return gr;
	
	}
	
	

	
	
	
	

	
}
