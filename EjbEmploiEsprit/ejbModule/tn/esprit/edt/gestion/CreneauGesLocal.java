package tn.esprit.edt.gestion;

import java.util.Set;

import javax.ejb.Local;



import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.exceptions.PreexistingEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;

@Local
public interface CreneauGesLocal {
	public void create(Creneau creneau)
			throws PreexistingEntityException, Exception;

	public void edit(Creneau creneau) throws NonexistentEntityException,
			Exception;

	public void destroyCreneau(Integer id) throws NonexistentEntityException;

	public Set<Creneau> findCreneauEntities();

	public Set<Creneau> findCreneauEntities(int maxResults,
			int firstResult);

	public Creneau findCreneau(Integer id);

	public int getCreneauCount();

	public Set<Creneau> findCreneauxByNums(int[] numeros);

	public int countPrestationsPour(Creneau creneau, Enseignant ens,
			Set<Groupe> groupes);

	public Set<Creneau> findAvecRelCreneau(Integer numCreneau);
	public Set<Creneau> findCreneauxAvecRel();
	
	public Set<Creneau> getAllCreneaux() ;
	public Creneau findCreneauById(Integer id);
	
}
