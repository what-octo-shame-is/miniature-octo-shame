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
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;
import tn.esprit.edt.persistance.Prestation;

@Stateless
@LocalBean
public class PrestationGes implements PrestationGesLocal {

	@PersistenceContext(unitName = "EDT_EJB")
	EntityManager em;

	@Override
	public void create(Prestation prestation) {

		try {

			em.persist(prestation);

			
		} finally {

		}
	}

	@Override
	public void edit(Prestation m) throws NonexistentEntityException, Exception {

		try {

			m = em.merge(m);

		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = Integer.parseInt(m.getCodeModule());// a verifier
																	// //
				if (findPrestation(id) == null) {
					throw new NonexistentEntityException(
							"The prestation with id " + id
									+ " no longer exists.");
				}
			}
			throw ex;
		} finally {

		}
	}

	@Override
	public void destroy(Integer id) throws NonexistentEntityException {

		try {

			Prestation prestation;
			try {
				prestation = em.getReference(Prestation.class, id);
				prestation.getClass();// a verifier ********
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The prestation with id "
						+ id + " no longer exists.", enfe);
			}
			em.remove(prestation);

		} finally {
		}
	}

	@Override
	public Set<Prestation> findPrestationEntities() {
		return findPrestationEntities(true, -1, -1);
	}

	@Override
	public Set<Prestation> findPrestationEntities(int maxResults,
			int firstResult) {
		return findPrestationEntities(false, maxResults, firstResult);
	}

	@Override
	public Set<Prestation> findPrestationEntities(boolean all, int maxResults,
			int firstResult) {

		try {
			Query q = em.createQuery("select object(o) from Prestation as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			Set<Prestation> set = new HashSet<Prestation>(q.getResultList());
			return set;
		} finally {

		}
	}

	@Override
	public Prestation findPrestationMultipleId(String id1, String id2,
			String id3, Integer id4) {

		try {
			String strQ = "SELECT p FROM Prestation AS p where p.codeModule='"
					+ id1 + "' and p.codeCl='" + id2 + "' and p.anneeDeb='"
					+ id3 + "' and p.numSemestre='" + id4 + "' ";
			// + "WHERE p.codeModule = :id1 ";
			Query q = em.createQuery(strQ);

			Prestation pres = (Prestation) q.getResultList().get(0);
			return pres;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	@Override
	public int getPrestationCount() {

		try {
			return ((Long) em.createQuery(
					"select count(o) from Prestation as o").getSingleResult())
					.intValue();
		} finally {

		}
	}

	/**
	 * Récupère la liste des prestations qui se trouve (ou pourraient se
	 * trouver) sur l'Emploi Du Temps affiché (c'est-à-dire celui pour lequel
	 * des groupes ou des enseignants ont été sélectionnés, les identifiants de
	 * ces groupes et enseignants sont passés en paramètres).
	 * 
	 * @param idsGrpSel
	 *            identifiants des groupes sélectionnés pour apparaître sur
	 *            l'EDT.
	 * @param idsEnsSel
	 *            identifiants des enseignants sélectionnés pour apparaître sur
	 *            l'EDT.
	 * @param sontCasees
	 *            indique si les prestations sélectionnées doivent être déjà
	 *            placées sur l'EDT ou non.
	 * @return liste des prestations qui apparaissent ou pourraient apparaître
	 *         sur l'EDT (avec les modules, les groupes et les enseignants
	 *         préchargés).
	 */
	@Override
	public Set<Prestation> findPrestationsRelativesEDT(int[] idsGrpSel,
			int[] idsEnsSel, boolean sontCasees) {
		Set<Prestation> set = null;

		if (idsGrpSel.length == 0 && idsEnsSel.length == 0) {
			set = new HashSet(new ArrayList<Prestation>(0));
			return set;
		}
		try {
			String strQ = "SELECT DISTINCT p " + "FROM Prestation AS p "
					+ "LEFT JOIN p.groupeCollection g "
					+ "LEFT JOIN p.idEnseignant e "
					+ "INNER JOIN FETCH p.module "
					+ "INNER JOIN FETCH p.idEnseignant "
					+ "LEFT JOIN FETCH p.groupeCollection "
					+ "LEFT JOIN FETCH p.numCreneau " + "WHERE ";

			if (sontCasees)
				strQ += "p.numCreneau IS NOT NULL ";
			else
				strQ += "p.numCreneau IS NULL ";

			String critGrp = "g.id IN (?1";
			for (int i = 2; i <= idsGrpSel.length; i++)
				critGrp += (", ?" + i);
			critGrp += ")";

			String critEns = "e.id IN (?" + (idsGrpSel.length + 1);
			for (int i = idsGrpSel.length + 2; i <= (idsGrpSel.length + idsEnsSel.length); i++)
				critEns += (", ?" + i);
			critEns += ")";

			if (idsGrpSel.length > 0 && idsEnsSel.length > 0)
				strQ += ("AND (" + critGrp + " OR " + critEns + ")");
			else if (idsGrpSel.length > 0)
				strQ += ("AND " + critGrp);
			else if (idsEnsSel.length > 0)
				strQ += ("AND " + critEns);

			Query q = em.createQuery(strQ);
			for (int i = 1; i <= idsGrpSel.length; i++)
				q.setParameter(i, idsGrpSel[i - 1]);
			for (int i = (idsGrpSel.length + 1); i <= (idsGrpSel.length + idsEnsSel.length); i++)
				q.setParameter(i, idsEnsSel[i - idsGrpSel.length - 1]);

			set = new HashSet<Prestation>(q.getResultList());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return set;

	}

	/**
	 * Récupère la liste des prestations qui se trouve sur l'Emploi Du Temps
	 * affiché (c'est-à-dire celui pour lequel des groupes ou des enseignants
	 * ont été sélectionnés, les identifiants de ces groupes et enseignants sont
	 * passés en paramètres).
	 * 
	 * @param idsGrpSel
	 *            identifiants des groupes sélectionnés pour apparaître sur
	 *            l'EDT.
	 * @param idsEnsSel
	 *            identifiants des enseignants sélectionnés pour apparaître sur
	 *            l'EDT.
	 * @return liste des prestations qui doivent apparaître sur l'EDT (avec les
	 *         modules, les groupes et les enseignants préchargés).
	 */
	@Override
	public Set<Prestation> findPrestationsSurEDT(int[] idsGrpSel,
			int[] idsEnsSel) {
		return this.findPrestationsRelativesEDT(idsGrpSel, idsEnsSel, true);
	}

	/**
	 * Récupère la liste des prestations qui n'ont pas encore de créneau mais
	 * qui pourraient se trouver sur l'Emploi Du Temps affiché (c'est-à-dire
	 * celui pour lequel des groupes ou des enseignants ont été sélectionnés,
	 * les identifiants de ces groupes et enseignants sont passés en
	 * paramètres).
	 * 
	 * @param idsGrpSel
	 *            identifiants des groupes sélectionnés pour apparaître sur
	 *            l'EDT.
	 * @param idsEnsSel
	 *            identifiants des enseignants sélectionnés pour apparaître sur
	 *            l'EDT.
	 * @return liste des prestations qui ne sont pas encore sur l'EDT (avec les
	 *         modules, les groupes et les enseignants préchargés).
	 */
	@Override
	public Set<Prestation> findPrestationsNonCasees(int[] idsGrpSel,
			int[] idsEnsSel) {
		return this.findPrestationsRelativesEDT(idsGrpSel, idsEnsSel, false);
	}

	/**
	 * Récupère les prestations d'un module triée par ordre d'insertion.
	 * 
	 * @param module
	 * @return
	 */
	@Override
	public Set<Prestation> findPrestationsParModule(Module module) {

		try {
			String strQ = "SELECT p " + "FROM Prestation AS p "
					+ "WHERE p.module = :module " + "ORDER BY p.id";
			Query q = em.createQuery(strQ);
			q.setParameter("module", module);
			Set<Prestation> set = new HashSet<Prestation>(q.getResultList());
			return set;
		} finally {

		}
	}

	/**
	 * Enlève la prestation à remplacer de son créneau et met la prestation à
	 * placer dans ce créneau.
	 * 
	 * @param prestAPlacer
	 * @param prestARemplacer
	 * @throws gestionedt.controllers.exceptions.NonexistentEntityException
	 * @throws java.lang.Exception
	 */
	@Override
	public void interchanger(Prestation prestAPlacer, Prestation prestARemplacer)
			throws NonexistentEntityException, Exception {

		try {
			em.getTransaction().begin();
			Creneau c = prestARemplacer.getCreneau();
			prestARemplacer.setCreneau(null);
			edit(prestARemplacer);
			prestAPlacer.setCreneau(c);
			edit(prestAPlacer);
			em.getTransaction().commit();
		} finally {

		}
	}

	@Override
	public boolean suitPrestation(Groupe groupe, Creneau creneau) {
		String strQ = "SELECT COUNT(DISTINCT p) " + "FROM Prestation AS p "
				+ "INNER JOIN p.groupeCollection g "
				+ "WHERE g = :groupe AND p.numCreneau = :creneau ";

		Query q = em.createQuery(strQ);

		q.setParameter("groupe", groupe);
		q.setParameter("creneau", creneau);
		return ((Long) q.getSingleResult() > 0);
	}

	@Override
	public boolean assurePrestation(Enseignant enseignant, Creneau creneau) {

		String strQ = "SELECT COUNT(DISTINCT p) "
				+ "FROM Prestation AS p "
				+ "WHERE p.idEnseignant = :enseignant AND p.numCreneau = :creneau ";
		Query q = em.createQuery(strQ);
		q.setParameter("enseignant", enseignant);
		q.setParameter("creneau", creneau);

		return ((Long) q.getSingleResult() > 0);
	}

	/**
	 * Récupère une liste de prestations avec leurs relations préchargées.
	 * 
	 * @param idPrestation
	 *            paramètre facultatif, s'il vaut null toutes les prestations
	 *            seront récupérées
	 * @return
	 */

	// String strQ =
	// "SELECT p FROM Prestation AS p where p.codeModule='"+id1+"' and p.codeCl='"+id2+"' and p.anneeDeb='"+id3+"' and p.numSemestre='"+id4+"' ";
	// Query q = em.createQuery(strQ);
	//
	// Prestation pres = (Prestation) q.getResultList().get(0);
	// return pres;
	@Override
	public Set<Prestation> findAvecRelMPCS(String codeModule, String codeCl,
			String anneeDeb, String numSemestre) {

		String strQ = "SELECT DISTINCT p " + "FROM Prestation AS p "
				+ "LEFT JOIN FETCH p.module " + "LEFT JOIN FETCH p.Enseignant "
				+ "LEFT JOIN FETCH p.creneau "
				+ "LEFT JOIN FETCH p.groupeCollection ";
		if (codeModule != null)
			strQ += "WHERE p.id = :codeModule ";

		Query q = em.createQuery(strQ);
		q.setHint(QueryHints.REFRESH, HintValues.TRUE);
		if (codeModule != null)
			q.setParameter("codeModule", codeModule);

		Set<Prestation> set = new HashSet<Prestation>(q.getResultList());
		System.out.println("okiiiiiiiiii");
		return set;

	}

	/**
	 * Récupère une liste de prestations avec leurs relations préchargées.
	 * 
	 * @return
	 */
	@Override
	public Set<Prestation> findPrestationsAvecRel() {
		return this.findAvecRelMPCS(null, null, null, null);
	}

	/**
	 * Recupère une prestation avec ses relations préchargées.
	 * 
	 * @param idPrestation
	 * @return
	 */
	@Override
	public Prestation findPrestationAvecRel(String codeModule, String codeCl,
			String anneeDeb, String numSemestre) {
		Set<Prestation> setPres = findAvecRelMPCS(codeModule, codeCl, anneeDeb,
				numSemestre);
		List<Prestation> listeDePrestation = new ArrayList<Prestation>(setPres);
		System.out.println("listeDePrestation");
		for (Prestation prestation : listeDePrestation) {
			System.out.println("prestation" + prestation.getDescription());

		}
		return (listeDePrestation.size() > 0 ? listeDePrestation.get(0) : null);
	}

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Set<Prestation> getListPrestation() {

		String strQ = "SELECT m " + "FROM Prestation AS m ";
		Set<Prestation> set = new HashSet<Prestation>(em.createQuery(strQ)
				.getResultList());
		return set;

	}

	public void SetCreneauAuPrestation(Prestation p, Creneau c) {

		p.setCreneau(c);

	}

	@Override
	public Prestation findPrestation(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCrenauToPrestation(String id1, String id2, String id3,
			Integer id4, Integer numCrenau) {
		// TODO Auto-generated method stub
		Prestation p = findPrestationMultipleId(id1, id2, id3, id4);
		Creneau creneau = em.find(Creneau.class, numCrenau);
		p.setCreneau(creneau);

	}

	public Set<Prestation> getPrestationByGroupe(String codeCl) {

		try {
			String strQ = "SELECT p FROM Prestation AS p where p.codeCl='"
					+ codeCl + "'  ";

			Set<Prestation> set = new HashSet<Prestation>(em.createQuery(strQ)
					.getResultList());
			return set;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Set<Prestation> getCreneuPourEnseignant(String idEnseignant) {

		
		String strQ = "SELECT DISTINCT e " + "FROM Prestation AS e "
				+ "LEFT JOIN FETCH e.creneau ";

		if (idEnseignant != null)
			strQ += "WHERE e.Enseignant.id = :idEnseignant ";
		strQ += "ORDER BY e.codeCl";

		Query q = em.createQuery(strQ);
		q.setHint(QueryHints.REFRESH, HintValues.TRUE);
		if (idEnseignant != null)
			q.setParameter("idEnseignant", idEnseignant);

		Set<Prestation> set = new HashSet<Prestation>(q.getResultList());
		for (Prestation c : set) {
			System.out.println("nom" + c.getCodeCl());
		}
			return set;
		
	
}
	public String GetNomGroupeByID(String Code)
	{
		try {
			String strQ = "SELECT p FROM Groupe AS p where p.id='"
					+ Code + "'  ";

			Groupe set = (Groupe) em.createQuery(strQ).getSingleResult();
			return set.getLibelle();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}

//	@Override
//	public List<List<Prestation>> getListePrestationByGroupe() {
//		// à terminer et modifier
//		String strQ = "SELECT DISTINCT e " + "FROM Prestation AS e "
//				+ "LEFT JOIN FETCH e.groupe ";
//
//		Groupe grp = new Groupe();
//		Set<Prestation> setPrest = getListPrestation();
//		List<Prestation> listePrest = new ArrayList<Prestation>(setPrest);
//		
//		if (grp.getId() != null)
//			strQ += "WHERE e.codeCl = e.groupe.id ";
//		strQ += "ORDER BY e.codeCl";
//
//		Query q = em.createQuery(strQ);
//		q.setHint(QueryHints.REFRESH, HintValues.TRUE);
//		if (grp.getId() != null)
//			q.setParameter("idEnseignant", grp.getId());
//
//		Set<Prestation> set = new HashSet<Prestation>(q.getResultList());
//		for (Prestation c : set) {
//			System.out.println("nom" + c.getCodeCl());
//		}
//			return null;
//	}

	

	

}
