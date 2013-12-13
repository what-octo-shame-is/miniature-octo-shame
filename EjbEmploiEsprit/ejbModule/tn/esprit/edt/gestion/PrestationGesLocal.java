package tn.esprit.edt.gestion;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.persistence.EntityManager;




import tn.esprit.edt.exceptions.NonexistentEntityException;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;
import tn.esprit.edt.persistance.Prestation;

@Local
public interface PrestationGesLocal {
	public void create(Prestation prestation);
	 public void edit(Prestation prestation) throws NonexistentEntityException, Exception;
	 public void destroy(Integer id) throws NonexistentEntityException ;
	    public Set<Prestation> findPrestationEntities();
	    public Set<Prestation> findPrestationEntities(int maxResults, int firstResult);
	    public Set<Prestation> findPrestationEntities(boolean all, int maxResults, int firstResult);
	    public Prestation findPrestation(Integer id);
	    public int getPrestationCount();
	    public Set<Prestation> findPrestationsRelativesEDT(int[] idsGrpSel,int[] idsEnsSel,boolean sontCasees);
	    public Set<Prestation> findPrestationsNonCasees(int[] idsGrpSel, int[] idsEnsSel);
	    public Set<Prestation> findPrestationsParModule(Module module);
	    public void interchanger(Prestation prestAPlacer, Prestation prestARemplacer)
	     throws NonexistentEntityException, Exception;
	    public boolean suitPrestation(Groupe groupe, Creneau creneau);
	    public boolean assurePrestation(Enseignant enseignant, Creneau creneau);
	    public Set<Prestation> findAvecRelMPCS(String codeModule,String codeCl, String anneeDeb , String numSemestre);
	    public Set<Prestation> findPrestationsAvecRel();
	    public Prestation findPrestationAvecRel(String codeModule , String codeCl, String anneeDeb, String numSemestre);
	    public Set<Prestation> findPrestationsSurEDT(int[] idsGrpSel,
				int[] idsEnsSel);
	    public void setEm(EntityManager em);
	    public EntityManager getEm();
	    public Set<Prestation> getListPrestation();
        public void addCrenauToPrestation(String id1, String id2, String id3,
    			Integer id4, Integer numCrenau);
	    public Prestation findPrestationMultipleId(String id1,String id2,String id3,Integer id4);
	    public Set<Prestation> getPrestationByGroupe( String codeCl);
	    public Set<Prestation> getCreneuPourEnseignant(String idEnseignant);
	    public String GetNomGroupeByID(String Code);
	    /*
	     * Modification
	     * pour getListPrestationByGroupe on avoir une liste de prestation pour chaque groupe
	     * */
//	    public List<List<Prestation>> getListePrestationByGroupe();
	    
	   
}
