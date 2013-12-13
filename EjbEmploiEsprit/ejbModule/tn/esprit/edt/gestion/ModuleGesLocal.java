package tn.esprit.edt.gestion;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;



import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Module;

@Local
public interface ModuleGesLocal {
	public void create(Module module);

	public void edit(Module module) throws NonexistentEntityException,
			Exception;

	public void destroyModule(Integer id) throws NonexistentEntityException;

	public Set<Module> findModuleEntities();

	public Set<Module> findModuleEntities(int maxResults, int firstResult);

	public Set<Module> findModuleEntities(boolean all, int maxResults,
			int firstResult);

	public Module findModule(Integer id);
	public List<Module> getGroupesInscrits();

	public int getModuleCount();

	public Set<Module> getListModule();

	public Set<Module> findAvecRelModule(String idModule);

	public Set<Module> findModulesAvecRel();

	public Module findModuleAvecRel(String idModule);
	public Module findModuleAvecNom(String nomModule);

}
