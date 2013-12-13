package tn.esprit.edt.gestion;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;
import tn.esprit.edt.persistance.Prestation;

@Local
public interface GroupeGesLocal {
	public void create(Groupe groupe);

	public void edit(Groupe groupe) throws NonexistentEntityException,
			Exception;

	public void destroy(Integer id) throws NonexistentEntityException;

	public Set<Groupe> findGroupeEntities();

	public Set<Groupe> findGroupeEntities(int maxResults, int firstResult);

	public Set<Groupe> findGroupeEntities(boolean all, int maxResults,
			int firstResult);

	public Groupe findGroupe(Integer id);

	public int getGroupeCount();

	public Set<Groupe> findGroupesByIds(int[] ids);

	public Set<Groupe> getList();

	public Set<Groupe> findGroupesSansPrest(Module module);

	public Set<Groupe> findGroupesLike(String regexp);

	public Set<Groupe> findAvecRel(String idGroupe);

	public Set<Groupe> findGroupesAvecRel();



	public Groupe findGroupeAvecRel(String idGroupe);
	public Set<Creneau> getListCreneauxGroupe(Groupe g);
	
	public Groupe findGroupeById(String id);
	 public String GetLibelleGroupeByID(String Code);
	 /* Modif
	  * */
	 public  Set<Prestation> ModulesParGroupe( String grp);
	
	 public  Set<Prestation> findModulesByGroupe(String codeCl);

	void updatePres();
}
