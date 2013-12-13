package tn.esprit.edt.gestion;

import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Pool;

@Local
public interface PoolGesLocal {
	public void create(Pool pool);
	 public void edit(Pool pool) throws NonexistentEntityException, Exception;
	 public void destroyPool(Integer id) throws NonexistentEntityException;
	 public Set<Pool> findPoolEntities();
	 public Set<Pool> findPoolEntities(int maxResults, int firstResult);
	 public Set<Pool> findPoolEntities(boolean all, int maxResults, int firstResult);
	 public Pool findPool(Integer id);
	 public int getPoolCount();
	 public Set<Pool> findPoolsByIds(int[] ids);
	 public Set<Pool> getListPool();
	 public Set<Pool> findAvecRelPool(Integer idPool);
	 public Set<Pool> findPoolsAvecRel();
	 public Pool findPoolAvecRel(Integer idPool);
	 public Map<Pool, Integer> getNbPrestRestant(Creneau creneau, Set<Pool> pools);
}
