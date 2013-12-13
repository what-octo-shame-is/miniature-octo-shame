package tn.esprit.edt.gestion;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;



import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Prestation;


@Local
public interface EnseignantGesLocal {
	public void create(Enseignant enseignant);

	public void edit(Enseignant enseignant) throws NonexistentEntityException,
			Exception;

	public void destroyEns(Integer id) throws NonexistentEntityException;

	public Set<Enseignant> findEnseignantEntities();

	public Set<Enseignant> findEnseignantEntities(int maxResults,
			int firstResult);

	public Set<Enseignant> findEnseignantEntities(boolean all, int maxResults,
			int firstResult);
	public String authentifierEns(String login,String password);


	public Enseignant findEnseignantString(String id);

	public int getEnseignantCount();
	public Set<Prestation> EnsCren (Enseignant ens);

	public Set<Enseignant> findEnseignantsByIds(int[] ids);

	public Set<Enseignant> getListEns();

	public Set<Enseignant> findEnseignantsLike(String regexp);

	public Set<Enseignant> findAvecRelEns(String idEnseignant);

	public Set<Enseignant> findEnseignantsAvecRel();

	public Enseignant findEnseignantAvecRel(String idEnseignant);
	 public Set<Enseignant> getListEnseigants();
	 public Set<Creneau> getCreneauxEns(Enseignant ens);
	 public void  SetInspo(Creneau creneau ,Enseignant enseignant);
	 public Set<Prestation> getPrestationByEnseignant(String codeCl);
	 public String findIDbyNom(String loginEns);
	 public String GetNomEnseignant(String Code);
	 public String GetNomEnseignantByID(String Code);
	 public String NomEnseignant(String Code);
}   
