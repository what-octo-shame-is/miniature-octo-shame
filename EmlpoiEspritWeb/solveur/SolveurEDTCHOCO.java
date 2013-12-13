package gestionEdt.solveur;

import static choco.Choco.eq;
import static choco.Choco.leq;
import static choco.Choco.makeIntVar;
import static choco.Choco.scalar;
import static choco.Choco.sum;
import static choco.Choco.times;
import gestionedt.controllers.gestion.PrestationGes;
import gestionedt.models.Creneau;
import gestionedt.models.Enseignant;
import gestionedt.models.Groupe;
import gestionedt.models.Module;
import gestionedt.models.Pool;
import gestionedt.models.Prestation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

public class SolveurEDTCHOCO extends SolveurEDT {

	/* MODELE */
	private Model m;

	/* SOLVEUR */
	private Solver s;

	/** VARIABLES DE TRAVAIL **/
	/**
	 * Tableau � deux dimensions de variables bool�ennes tel que Q[i][k] indique
	 * si la prestation i a lieu au cr�neau k ou non.
	 */
	private IntegerVariable[][] Q;
	/**
	 * Tableau � deux dimensions de variables bool�ennes tel que U[i][j] indique
	 * si la prestation i est suivie par le groupe j ou non.
	 */
	private IntegerVariable[][] U;
	/**
	 * Tableau � trois dimensions de variables bool�ennes tel que T[i][j][k] =
	 * U[i][j] &cap; Q[i][k] (T[i][j][k] indique donc si la prestation i a lieu
	 * au cr�neau k et est suivie par le groupe j ou non).
	 */
	private IntegerVariable[][][] T;

	/**
	 * Constructeur.
	 * 
	 * @param enseignants
	 * @param modules
	 * @param pools
	 * @param prestations
	 * @param groupes
	 * @param creneaux
	 */
	public SolveurEDTCHOCO(Set<Enseignant> enseignants, Set<Module> modules,
			Set<Pool> pools, Set<Prestation> prestations,
			Set<Groupe> groupes, Set<Creneau> creneaux) {

		super(enseignants, modules, pools, prestations, groupes, creneaux);
	}

	/**
	 * Initialise le mod�le, c'est-�-dire instanciation du mod�le puis cr�ation
	 * des variables.
	 */
	private void initialiserModele() {
		/**
		 * instanciation du mod�le
		 */
		this.m = new CPModel();
		/**
		 * initialisation des variables
		 */
		Q = new IntegerVariable[prestations.size()][creneaux.size()];
		for (int i = 0; i < prestations.size(); i++)
			for (int k = 0; k < creneaux.size(); k++)
				Q[i][k] = makeIntVar("Q" + i + "_" + k, 0, 1);

		U = new IntegerVariable[prestations.size()][groupes.size()];
		for (int i = 0; i < prestations.size(); i++)
			for (int j = 0; j < groupes.size(); j++)
				U[i][j] = makeIntVar("U" + i + "_" + j, 0, 1);

		T = new IntegerVariable[prestations.size()][groupes.size()][creneaux
				.size()];
		for (int i = 0; i < prestations.size(); i++)
			for (int j = 0; j < groupes.size(); j++)
				for (int k = 0; k < creneaux.size(); k++)
					T[i][j][k] = makeIntVar("T" + i + "_" + j + "_" + k, 0, 1);
	}

	/**
	 * Mise en place des contraintes en fonction des donn�es fournies en entr�e.
	 */
	private void definirContraintes() {

		Constraint c; // variable interm�diaire pour ajouter une contrainte au
						// mod�le
		List<Groupe> listG = new ArrayList <Groupe> (groupes);
		List<Prestation> listPres = new ArrayList <Prestation> (prestations);
		List<Creneau> listCren = new ArrayList <Creneau> (creneaux);
		List<Enseignant> listEns = new ArrayList <Enseignant> (enseignants);
		// Lien entre les variables T et les variables Q et U :
		for (int i = 0; i < prestations.size(); i++)
			for (int j = 0; j < groupes.size(); j++)//n5aliwhom ?
				for (int k = 0; k < creneaux.size(); k++)
					m.addConstraint(times(U[i][j], Q[i][k], T[i][j][k]));

		// Contrainte 1 : � toute prestation est associ� un cr�neau
		for (int i = 0; i < prestations.size(); i++) {
			c = eq(sum(Q[i]), 1);
			m.addConstraint(c);
		}
		// Contrainte 2 : tout groupe doit suivre exactement une prestation
		// de chacun des modules auquel il est inscrit
		for (int j = 0; j < listG.size(); j++) {
			Groupe groupe = listG.get(j);
				for (Module module : groupe.getModules()) {
				Collection<Prestation> prest = module.getPrestations();
				if (prest.size() > 0) {
					IntegerVariable[] E = new IntegerVariable[prest.size()];
					int i = 0;
					for (Prestation p : prest)
						E[i++] = U[listPres.indexOf(p)][j];
					c = eq(sum(E), 1);
					m.addConstraint(c);
				}
			}
		}

		// Contrainte 3 : la somme des effectifs des groupes qui suivent une
		// m�me
		// prestation ne doit pas d�passer l'effectif maximal du module concern�
		for (int i = 0; i < prestations.size(); i++) {
			int[] effectifs = new int[groupes.size()];
			for (int j = 0; j < groupes.size(); j++){
				effectifs[j] = listG.get(j).getEffectif();
			System.out.println(listG.get(j)+"*****************************************************");
			}
			int effectifMax = listPres.get(i).getModule().getEffectifMax();
			c = leq(scalar(effectifs, U[i]), effectifMax);
			m.addConstraint(c);
		}

		// Contrainte 4 : deux prestations suivies par un m�me groupe
		// d'�tudiants ne peuvent avoir lieu en m�me temps
		for (int j = 0; j < groupes.size(); j++) {
			for (int k = 0; k < creneaux.size(); k++) {
				c = leq(sum3(T, j, k), 1);
				m.addConstraint(c);
			}
		}

		// Contrainte 5 : deux prestations dispens�es par un m�me
		// enseignant ne peuvent avoir lieu simultan�ment
		for (int k = 0; k < creneaux.size(); k++) {
			for (int e = 0; e < enseignants.size(); e++) {
				Enseignant ens = listEns.get(e);
				Collection<Prestation> prest = ens.getPrestationsAssurees();
				if (prest.size() > 0) {
					IntegerVariable[] E = new IntegerVariable[prest.size()];
					int i = 0;
					for (Prestation p : prest)
						E[i++] = Q[listPres.indexOf(p)][k];
					c = leq(sum(E), 1);
					m.addConstraint(c);
				}
			}
		}

		// Contrainte 6 : une prestation ne peut avoir lieu � un horaire
		// que si l'enseignant auquel elle est assign�e est disponible
		for (int i = 0; i < prestations.size(); i++) {
			Enseignant ens = listPres.get(i).getEnseignant();
			Collection<Creneau> indisponibilites = ens.getIndisponibilites();
			if (indisponibilites.size() > 0) {
				IntegerVariable[] I = new IntegerVariable[indisponibilites
						.size()];
				int k = 0;
				for (Creneau indisponibilite : indisponibilites)
					I[k++] = Q[i][listCren.indexOf(indisponibilite)];
				c = eq(sum(I), 0);
				m.addConstraint(c);
			}
		}

		// Contrainte 7 : une prestation ne peut avoir lieu � un horaire
		// que si les groupes qui la suivent sont disponibles
		for (int j = 0; j < groupes.size(); j++) {
			Collection<Creneau> indisponibilites = listG.get(j)
					.getIndisponibilites();
			for (Creneau indisponibilite : indisponibilites) {
				int k = listCren.indexOf(indisponibilite);
				c = eq(sum3(T, j, k), 0);
				m.addConstraint(c);
			}
		}

		// Contrainte 8 : Le nombre de prestations, ayant lieu simultan�ment,
		// de modules appartenant � un m�me pool ne doit pas d�passer
		// le nombre autoris� pour ce pool
		for (int k = 0; k < creneaux.size(); k++) {
			for (Pool pool : pools) {
				int nbPrestMax = pool.getNbPrestMaxParPlage();
				List<IntegerVariable> P = new ArrayList<IntegerVariable>();
				for (Module mod : pool.getModules()) {
					for (Prestation prest : mod.getPrestations()) {
						int i = listPres.indexOf(prest);
						P.add(Q[i][k]);
					}
				}
				// la contrainte est ajout�e seulement si le nombre de
				// prestations,
				// ayant lieu durant le cr�neau k et dont le module est
				// associ� au pool courant, est sup�rieur � 0
				if (P.size() > 0) {
					c = leq(sum(P.toArray(new IntegerVariable[0])), nbPrestMax);
					m.addConstraint(c);
				}
			}
		}

		// affectations de groupes et de cr�neaux aux prestations d�j� fix�es,
		// autrement dit le champ de recherche est par cons�quent restreint
		for (int i = 0; i < prestations.size(); i++) {
			Prestation prestation = listPres.get(i);
			// affections de groupes
			for (Groupe groupe : prestation.getGroupes()) {
				int j = listG.indexOf(groupe);
				c = eq(U[i][j], 1);
				m.addConstraint(c);
			}
			if (prestation.getCreneau() != null) {
				// affection d'un cr�neau
				int k = listCren.indexOf(prestation.getCreneau());
				c = eq(Q[i][k], 1);
				m.addConstraint(c);
			}
		}
		groupes = new HashSet<Groupe>(listG);
		prestations = new HashSet<Prestation>(listPres);
		creneaux = new HashSet<Creneau>(listCren);
		enseignants = new HashSet<Enseignant>(listEns);

	}

	@Override
	public boolean resoudre() {
		// initialisation du mod�le (cr�ation des variables...)
		initialiserModele();
	
		// d�finition des contraintes pour le mod�le
		definirContraintes();
		
		//boolean trouvee = false;
		// instanciation du solveur
		s = new CPSolver();
		
		s.read(m); // le mod�le est fourni au solveur
		
		boolean trouvee = s.solve(); // lancement de la r�solution
		
		if (trouvee) { // si une solution a �t� trouv�e
			creerLiensAvecRes(); // les objets sont mis � jour en fonction de la
									// solution
			enregistrerResultat(); // puis la persistance du r�sultat est
									// r�alis�e
		}
		return trouvee;
	}

	/**
	 * Cr�ation des liens entre les prestations et les groupes, ainsi qu'entre
	 * les prestations et les cr�neaux en fonction du r�sultat trouv� par le
	 * solveur.
	 */
	@SuppressWarnings("empty-statement")
	private void creerLiensAvecRes() {
		List<Groupe> listG = new ArrayList <Groupe> (groupes);
		List<Prestation> listPres = new ArrayList <Prestation> (prestations);
		List<Creneau> listCren = new ArrayList <Creneau> (creneaux);
		for (Prestation prestation : prestations) {
			int i = listPres.indexOf(prestation);

			// cr�ation des liens entre les prestations et les groupes
			Collection<Groupe> groupesInscrits = prestation.getGroupes();
			for (int j = 0; j < groupes.size(); j++) {
				// r�cup�ration de la valeur boole�nne qui indique
				// si le groupe j doit suivre la prestation i
				boolean doitSuivre = s.getVar(U[i][j]).getVal() == 1;
				if (doitSuivre) {
					// ajout du lien (seulement s'il n'existe pas encore)
					Groupe groupe = listG.get(j);
					if (!groupesInscrits.contains(groupe))
						groupesInscrits.add(groupe);
				}
			}

			// affection d'un cr�neau pour chaque prestation
			int k = 0; // parcours du tableau Q[i] jusqu'� tomber sur 1
			while (s.getVar(Q[i][k++]).getVal() == 0)
				;
			prestation.setCreneau(listCren.get(--k));
		}
		
		groupes = new HashSet<Groupe>(listG);
		prestations = new HashSet<Prestation>(listPres);
		creneaux = new HashSet<Creneau>(listCren);
	}

	/**
	 * Enregistrement du r�sultat (persistance des liens �tablis).
	 */
	private void enregistrerResultat() {
		PrestationGes controller = new PrestationGes();
		try {
			for (Prestation prestation : prestations) {
			
//				prestation.setNom("aaaa");
//				controller.edit(prestation.getId());
				System.out.println(prestation);
			}
		} catch (Exception ex) {
			Logger.getLogger(SolveurEDTCHOCO.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 * Retourne une variable enti�re qui correspond � la somme des variables
	 * d'un tableau � trois dimensions telle que : T[1][j][k] + T[2][j][k] + ...
	 * + T[n][j][k] o� les indices j et k sont pass�s en param�tres.
	 * 
	 * @param arg0
	 *            tableau � trois dimensions
	 * @param j
	 * @param k
	 * @return variable enti�re
	 */
	static private IntegerExpressionVariable sum3(IntegerVariable[][][] arg0,
			int j, int k) {
		IntegerVariable[] tab = new IntegerVariable[arg0.length];
		for (int i = 0; i < arg0.length; i++)
			tab[i] = arg0[i][j][k];
		return sum(tab);
	}

	@Override
	public String toString() {
		return "SolveurEDTCHOCO [m=" + m + ", s=" + s + ", Q="
				+ Arrays.toString(Q) + ", U=" + Arrays.toString(U) + ", T="
				+ Arrays.toString(T) + "]";
	}

}