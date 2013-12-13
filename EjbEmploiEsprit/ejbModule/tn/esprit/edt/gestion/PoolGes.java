package tn.esprit.edt.gestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import tn.esprit.edt.persistance.Pool;

/**
 * Session Bean implementation class PoolGes
 */
@Stateless
@LocalBean
public class PoolGes implements PoolGesLocal {

	@PersistenceContext(unitName = "EDT_EJB")
	EntityManager em;

	public void create(Pool pool) {

		em.persist(pool);

	}

	public void edit(Pool pool) throws NonexistentEntityException, Exception {

		try {

			pool = em.merge(pool);
			;
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = pool.getId();
				if (findPool(id) == null) {
					throw new NonexistentEntityException("The pool with id "
							+ id + " no longer exists.");
				}
			}
			throw ex;
		} finally {

		}
	}

	public void destroyPool(Integer id) throws NonexistentEntityException {

		try {

			Pool pool;
			try {
				pool = em.getReference(Pool.class, id);
				pool.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The pool with id " + id
						+ " no longer exists.", enfe);
			}
			em.remove(pool);

		} finally {
		}
	}

	public Set<Pool> findPoolEntities() {
		return findPoolEntities(true, -1, -1);
	}

	public Set<Pool> findPoolEntities(int maxResults, int firstResult) {
		return findPoolEntities(false, maxResults, firstResult);
	}

	public Set<Pool> findPoolEntities(boolean all, int maxResults,
			int firstResult) {

		try {
			Query q = em.createQuery("select object(o) from Pool as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			Set<Pool> set = new HashSet<Pool>(q.getResultList());
			return set;
		} finally {

		}
	}

	public Pool findPool(Integer id) {

		try {
			return em.find(Pool.class, id);
		} finally {

		}
	}

	public int getPoolCount() {

		try {
			return ((Long) em.createQuery("select count(o) from Pool as o")
					.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

	public Set<Pool> findPoolsByIds(int[] ids) {

		if (ids.length == 0) {
			Set<Pool> set = new HashSet<Pool>(new ArrayList<Pool>(0));
			return set;
		}

		try {
			String strQ = "SELECT p " + "FROM Pool AS p " + "WHERE p.id IN (?1";
			for (int i = 2; i <= ids.length; i++)
				strQ += (", ?" + i);
			strQ += ")";
			Query q = em.createQuery(strQ);
			for (int i = 1; i <= ids.length; i++)
				q.setParameter(i, ids[i - 1]);

			Set<Pool> set = new HashSet<Pool>(q.getResultList());
			return set;
		} finally {

		}
	}

	public Set<Pool> getListPool() {

		try {
			String strQ = "SELECT p " + "FROM Pool AS p "
					+ "ORDER BY p.libelle";
			Set<Pool> set = new HashSet<Pool>(em.createQuery(strQ)
					.getResultList());
			return set;
		} finally {

		}
	}

	/**
	 * Récupère une liste de pools avec leurs relations préchargées.
	 * 
	 * @param idPool
	 *            paramètre facultatif, s'il vaut null tous les pools seront
	 *            récupérés
	 * @return
	 */
	public Set<Pool> findAvecRelPool(Integer idPool) {

		try {
			String strQ = "SELECT DISTINCT p " + "FROM Pool AS p "
					+ "LEFT JOIN FETCH p.moduleCollection ";
			if (idPool != null)
				strQ += "WHERE p.id = :idPool ";
			strQ += "ORDER BY p.libelle";

			Query q = em.createQuery(strQ);
			q.setHint(QueryHints.REFRESH, HintValues.TRUE);
			if (idPool != null)
				q.setParameter("idPool", idPool);

			Set<Pool> set = new HashSet<Pool>(q.getResultList());
			for (Pool pool : set) {
				System.out.println("pool" + pool.getLibelle());
			}
			
			return set;
		} finally {

		}
	}

	/**
	 * Récupère une liste de pools avec leurs relations préchargées.
	 * 
	 * @return
	 */
	public Set<Pool> findPoolsAvecRel() {
		return this.findAvecRelPool(null);
	}

	/**
	 * Recupère un pool avec ses relations préchargées.
	 * 
	 * @param idEnseignant
	 * @return
	 */
	public Pool findPoolAvecRel(Integer idPool) {
		Set<Pool> liste = findAvecRelPool(idPool);
		List<Pool> listModules = new ArrayList<Pool>(liste);
		return (listModules.size() > 0 ? listModules.get(0) : null);
	}

	/**
	 * Retourne le nombre restant de prestations autorisées pour un créneau
	 * donné dans chaque pool passé en paramètre.
	 * 
	 * @param creneau
	 * @param pools
	 *            liste des pools concernés
	 * @return table de hachage qui retournera le nombre de prestations restant
	 *         en fonction du pool donné
	 */
	public Map<Pool, Integer> getNbPrestRestant(Creneau creneau, Set<Pool> pools) {

		if (pools.size() == 0)
			return new HashMap<Pool, Integer>(0);

		Map<Pool, Integer> nbRestant = new HashMap<Pool, Integer>(pools.size());

		try {
			String strQ = "SELECT NEW gestionedt.controllers.NbPrestRestant(pool, COUNT(DISTINCT prest)) "
					+ "FROM Pool AS pool "
					+ "LEFT JOIN pool.moduleCollection m "
					+ "LEFT JOIN m.prestationCollection prest "
					+ "WHERE prest.numCreneau = :creneau ";
			strQ += "AND pool.id IN (?1";
			for (int i = 2; i <= pools.size(); i++)
				strQ += (", ?" + i);
			strQ += ") ";
			strQ += "GROUP BY pool ";

			Query q = em.createQuery(strQ);
			q.setParameter("creneau", creneau);
			List<Pool> listeDePool = new ArrayList<Pool>(pools);
			for (int i = 1; i <= listeDePool.size(); i++)
				q.setParameter(i, listeDePool.get(i - 1).getId());

			List<NbPrestRestant> nbPrestRestant = q.getResultList();
			for (NbPrestRestant nb : nbPrestRestant)
				nbRestant.put(nb.getPool(), nb.getNbPrestRestant());

			return nbRestant;

		} finally {

		}
	}

}
