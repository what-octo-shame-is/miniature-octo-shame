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
import tn.esprit.edt.persistance.Admin;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Prestation;

/**
 * Session Bean implementation class EnseignantGes
 */
@Stateless
@LocalBean
public class EnseignantGes implements EnseignantGesLocal {
	@PersistenceContext(unitName = "EDT_EJB")
	EntityManager em;

	public void create(Enseignant enseignant) {

		Enseignant e = new Enseignant();
		e.setNom(enseignant.getNom());
		e.setLoginEns(enseignant.getLoginEns());
		e.setPasswordEns(enseignant.getPasswordEns());
		// e.setIndisponibilites(enseignant.getIndisponibilites());
		em.persist(e);

	}

	public void edit(Enseignant enseignant) throws NonexistentEntityException,
			Exception {
		try {
			enseignant = em.merge(enseignant);
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				// Integer id = Integer.parseInt(enseignant.getId());
				if (findEnseignant(enseignant.getId()) == null) {
					throw new NonexistentEntityException(
							"The enseignant with id " + enseignant.getId()
									+ " no longer exists.");
				}
			}
			throw ex;
		} finally {

		}
	}

	public void destroyEns(Integer id) throws NonexistentEntityException {

		Enseignant enseignant;
		try {
			enseignant = em.getReference(Enseignant.class, id);
			enseignant.getId();
		} catch (EntityNotFoundException enfe) {
			throw new NonexistentEntityException("The enseignant with id " + id
					+ " no longer exists.", enfe);
		}
		em.remove(enseignant);

	}

	public Set<Enseignant> findEnseignantEntities() {
		return findEnseignantEntities(true, -1, -1);
	}

	public Set<Enseignant> findEnseignantEntities(int maxResults,
			int firstResult) {
		return findEnseignantEntities(false, maxResults, firstResult);
	}

	public Set<Enseignant> findEnseignantEntities(boolean all, int maxResults,
			int firstResult) {

		try {
			Query q = em.createQuery("select object(o) from Enseignant as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}

			Set<Enseignant> set = new HashSet<Enseignant>(q.getResultList());
			return set;
		} finally {

		}
	}

	public Enseignant findEnseignant(String id) {

		try {
			return em.find(Enseignant.class, id);
		} finally {

		}
	}

	public Enseignant findEnseignantString(String id) {

		
		return em.find(Enseignant.class, id);

	}
	public String findIDbyNom(String loginEns) {

		try {
			String strQ = "SELECT p FROM Enseignant AS p where p.loginEns='"
					+ loginEns + "'  ";

			Enseignant set = (Enseignant) em.createQuery(strQ).getSingleResult();
			return set.getId();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	

	public int getEnseignantCount() {

		try {
			return ((Long) em.createQuery(
					"select count(o) from Enseignant as o").getSingleResult())
					.intValue();
		} finally {

		}
	}

	public Set<Enseignant> findEnseignantsByIds(int[] ids) {

		if (ids.length == 0) {

			Set set = new HashSet(new ArrayList<Enseignant>(0));
			return set;
		}

		try {
			String strQ = "SELECT e " + "FROM Enseignant AS e "
					+ "WHERE e.id IN (?1";
			for (int i = 2; i <= ids.length; i++)
				strQ += (", ?" + i);
			strQ += ")";
			Query q = em.createQuery(strQ);
			for (int i = 1; i <= ids.length; i++)
				q.setParameter(i, ids[i - 1]);

			Set<Enseignant> set = new HashSet<Enseignant>(q.getResultList());
			return set;
		} finally {

		}
	}

	public Set<Enseignant> getListEns() {

		try {
			String strQ = "SELECT e " + "FROM Enseignant AS e "
					+ "ORDER BY e.nom";

			Set<Enseignant> set = new HashSet<Enseignant>(em.createQuery(strQ)
					.getResultList());
			return set;
		} finally {

		}
	}

	public Set<Enseignant> findEnseignantsLike(String regexp) {

		try {
			String strQ = "SELECT e " + "FROM Enseignant e "
					+ "WHERE CONCAT(e.nom) LIKE :regexp";
			Query q = em.createQuery(strQ);
			q.setParameter("regexp", regexp);
			Set<Enseignant> set = new HashSet<Enseignant>(q.getResultList());
			return set;
		} finally {

		}
	}

	public Set<Enseignant> findAvecRelEns(String idEnseignant) {

		String strQ = "SELECT DISTINCT e " + "FROM Enseignant AS e "
				+ "LEFT JOIN FETCH e.creneauCollection "
				+ "LEFT JOIN FETCH e.prestationCollection ";
		if (idEnseignant != null)
			strQ += "WHERE e.id = :idEnseignant ";
		strQ += "ORDER BY e.nom";

		Query q = em.createQuery(strQ);
		q.setHint(QueryHints.REFRESH, HintValues.TRUE);
		if (idEnseignant != null)
			q.setParameter("idEnseignant", idEnseignant);

		Set<Enseignant> set = new HashSet<Enseignant>(q.getResultList());
		for (Enseignant enseignant : set) {
			System.out.println("nom" + enseignant.getNom());
		}

		return set;

	}

	public Set<Enseignant> findEnseignantsAvecRel() {
		return this.findAvecRelEns(null);
	}

	public Enseignant findEnseignantAvecRel(String idEnseignant) {
		Set<Enseignant> liste = findAvecRelEns(idEnseignant);
		List<Enseignant> enseignants = new ArrayList<Enseignant>(liste);
		return (enseignants.size() > 0 ? enseignants.get(0) : null);
	}

	public Set<Enseignant> getListEnseigants() {

		String strQ = "SELECT m " + "FROM Enseignant AS m ";
		Set<Enseignant> set = new HashSet<Enseignant>(em.createQuery(strQ)
				.getResultList());
		return set;

	}

	public Set<Creneau> getCreneauxEns(Enseignant ens) {
		return ens.getCreneauCollection();

	}

	public void SetInspo(Creneau creneau, Enseignant enseignant) {
		Set<Creneau> creneaux = new HashSet<Creneau>(
				enseignant.getCreneauCollection());
		creneaux.add(creneau);

		enseignant.setCreneauCollection(creneaux);
	}

	public Set<Prestation> EnsCren(Enseignant ens) {

		try {
			String strQ = "SELECT p FROM Prestation AS p where p.getEnseignant='"
					+ ens + "' ";

			Query q = em.createQuery(strQ);

			Set<Prestation> pres = (Set<Prestation>) q.getResultList();
			return pres;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	public Set<Prestation> getPrestationByEnseignant(String idEnseignant) {

		
			String strQ = "SELECT DISTINCT e " + "FROM Prestation AS e "
					+ "LEFT JOIN FETCH e.Enseignant ";

			if (idEnseignant != null)
				strQ += "WHERE e.Enseignant.id = :idEnseignant ";
			strQ += "ORDER BY e.codeCl";

			Query q = em.createQuery(strQ);
			q.setHint(QueryHints.REFRESH, HintValues.TRUE);
			if (idEnseignant != null)
				q.setParameter("idEnseignant", idEnseignant);

			Set<Prestation> set = new HashSet<Prestation>(q.getResultList());
			for (Prestation enseignant : set) {
				System.out.println("nom" + enseignant.getDescription());
			}
				return set;
			
		
	}

	public String authentifierEns(String login, String password) {
		String a = "";
		List<Enseignant> ens = em.createNativeQuery(
				"select * from ESP_ENSEIGNANT u where u.LOGINENS='" + login
						+ "' and u.PASSWORDENS='" + password + "'")
				.getResultList();
		if (ens.size() == 1) {
			a = "yesEns";
		}

		else {
			a = "noo";
		}

		return a;

	}
	public String GetNomEnseignant(String Code){
		try {
			String strQ = "SELECT p FROM Enseignant AS p where p.loginEns='"
					+ Code + "'  ";

			Enseignant set = (Enseignant) em.createQuery(strQ).getSingleResult();
			return set.getNom();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	public String GetNomEnseignantByID(String Code){
		try {
			String strQ = "SELECT p FROM Enseignant AS p where p.loginEns='"
					+ Code + "'  ";

			Enseignant set = (Enseignant) em.createQuery(strQ).getSingleResult();
			return set.getId();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	public String NomEnseignant(String Code){
		try {
			String strQ = "SELECT p FROM Enseignant AS p where p.id='"
					+ Code + "'  ";

			Enseignant set = (Enseignant) em.createQuery(strQ).getSingleResult();
			return set.getNom();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	
}
