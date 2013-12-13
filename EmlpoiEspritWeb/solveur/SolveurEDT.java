package gestionEdt.solveur;


import gestionedt.models.Creneau;
import gestionedt.models.Enseignant;
import gestionedt.models.Groupe;
import gestionedt.models.Module;
import gestionedt.models.Pool;
import gestionedt.models.Prestation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;

public abstract class SolveurEDT {

    protected Set<Enseignant> enseignants;
    protected Set<Module> modules;
    protected Set<Pool> pools;
    protected Set<Prestation> prestations;
    protected Set<Groupe> groupes;
    protected Set<Creneau> creneaux;

    /**
     * Constructeur.
     * @param enseignants
     * @param modules
     * @param pools
     * @param prestations
     * @param groupes
     * @param creneaux
     */
    public SolveurEDT(Set<Enseignant> enseignants,
                      Set<Module> modules,
                      Set<Pool> pools,
                      Set<Prestation> prestations,
                      Set<Groupe> groupes,
                      Set<Creneau> creneaux) {

        this.enseignants = enseignants;
        this.modules = modules;
        this.pools = pools;
        this.prestations = prestations;
        this.groupes = groupes;
        this.creneaux = creneaux;
    }

    /**
     * Tente de r�soudre le probl�me de l'emploi du temps avec les donn�es en
     * entr�e fournies au constructeur.
     * @return indique si une solution a �t� trouv�e ou non
     */
    public abstract boolean resoudre();

    /**
     * V�rifie que la solution (�ventuellement partielle) fix�e par la scolarit�
     * ne poss�de pas de conflit (exemple de conflit : deux prestations assur�es
     * par un m�me enseignant ont lieu en m�me temps, etc).
     * @return pile des conflits (vide si aucun)
     */
    public String[] verifier() {

        List<String> conflits = new ArrayList<String>();
        List<Pool> listPool = new ArrayList<Pool>(pools);
        /** VARIABLES DE TRAVAIL **/
        // liste des enseignants ajout�s au fur et � mesure
        // qui assurent une prestation durant un cr�neau donn�
        Set<Enseignant> listeEns = new HashSet<Enseignant>();
        // liste des groupes ajout�s au fur et � mesure
        // qui suivent une prestation durant un cr�neau donn�
        Set<Groupe> listeGrps = new HashSet<Groupe>();
        // tableau qui contiendra le nombre de prestations ayant lieu durant un
        // cr�neau donn� pour chaque pool auquel les prestations sont associ�es
        int[] nbPrestSimultParPool = new int[pools.size()];

        // pour chaque cr�neau, on v�rifie qu'il n'y a pas de conflit tel que :
        // - un enseignant ne peut pas assurer deux prestations dans un m�me cr�neau
        // - un groupe ne peut pas suivre deux prestations dans un m�me cr�neau
        // - un enseignant doit �tre disponible pour assurer une prestation
        // - un groupe doit �tre lui aussi disponible pour suivre une prestation
        // - le nombre max de prestations simultan�es par pool doit �tre respect�
        for(Creneau creneau : creneaux) {
            // initialisation des variables de travail
            listeEns.clear(); // remise � z�ro des liste d'enseignants
            listeGrps.clear(); // remise � z�ro des liste de groupes
            Arrays.fill(nbPrestSimultParPool, 0); // remise � 0 de chaque case du tableau

            // pour chaque prestation ayant lieu pendant ce cr�neau :
            for(Prestation prestation : creneau.getPrestations()) {
                // un enseignant ne peut pas assurer deux prestations dans un m�me cr�neau
                Enseignant ens = prestation.getEnseignant();
                if(listeEns.contains(ens))
                    conflits.add("L'enseignant " + ens.getNom() + " assure " +
                                 "plusieurs prestations le " + creneau + ".");
                listeEns.add(ens);
                // un enseignant doit �tre disponible pour assurer une prestation
                if(ens.estIndispo(creneau))
                    conflits.add("L'enseignant " + ens.getNom() + " est " +
                                 "indisponible le " + creneau + ".");
                // pour chaque groupe qui suit la prestation
                for(Groupe groupe : prestation.getGroupes()) {
                    // un groupe ne peut pas suivre deux prestations dans un m�me cr�neau
                    if(listeGrps.contains(groupe))
                        conflits.add("Le groupe '" + groupe.getLibelle() + "' suit " +
                                     "plusieurs prestations le " + creneau + ".");
                    listeGrps.add(groupe);
                    // un groupe doit �tre disponible pour suivre une prestation
                    if(groupe.estIndispo(creneau))
                        conflits.add("Le groupe '" + groupe.getLibelle() + "' est " +
                                     "indisponible le " + creneau + ".");
                }
                // mise � jour du nombre de prestations simultan�es pour chaque pool concern�
                
                for(Pool pool : prestation.getModule().getPools()) {
                    int i = listPool.indexOf(pool);
                    nbPrestSimultParPool[i]++;
                }
            }
            // le nombre max de prestations simultan�es par pool doit �tre respect�
            for(Pool pool : pools) {
                int i = listPool.indexOf(pool);
                if(nbPrestSimultParPool[i] > pool.getNbPrestMaxParPlage())
                    conflits.add("Le nombre de prestations ayant lieu le " +
                                 creneau + " d�passe le nombre autoris� " +
                                 "pour le pool " + pool.getLibelle() + ".");
            }
        }

        // pour chaque prestation, on v�rifie que l'effectif total ne d�passe
        // pas l'effectif max autoris� pour le module associ�
        for(Prestation prestation : prestations) {
            int effectifMax = prestation.getModule().getEffectifMax();
            int effectifTotal = 0;
            for(Groupe groupe : prestation.getGroupes())
                effectifTotal += groupe.getEffectif();
            if(effectifTotal > effectifMax)
                conflits.add("L'effectif total de la prestation "+prestation.getId()+
                        " d�passe l'effectif max autoris� pour le module " +
                        prestation.getModule().getLibelle()+".");
        }

        // pour chaque groupe, on v�rifie :
        for(Groupe groupe : groupes) {
            // - qu'il poss�de assez de cr�neaux disponibles
            //   pour suivre toutes les prestations auxquelles il est inscrit
            int nbIndisponibilites = groupe.getIndisponibilites().size();
            int nbDisponibilites = creneaux.size() - nbIndisponibilites;
            int nbPrestationsSuivies = groupe.getPrestationsSuivies().size();
            if(nbDisponibilites < nbPrestationsSuivies)
                conflits.add("Le groupe '"+groupe.getLibelle()+"' a trop de " +
                        "cr�neaux indisponibles pour suivre toutes ses prestations.");

            // - qu'il est affect� au plus une fois � une prestation
            //   d'un module dans lequel il est inscrit
            // - et qu'il est inscrit � tous les modules associ�es
            //   aux prestations auxquelles il est affect�
            Map<Module, Integer> nbPrestParModule = new HashMap<Module, Integer>(modules.size());
            for(Prestation prestation : groupe.getPrestationsSuivies()) {
                Module module = prestation.getModule();
                if( ! groupe.getModules().contains(module))
                    conflits.add("Le groupe '" + groupe + "' est affect� � une prestation " +
                            "du module '"+ module +"' auquel il n'est pas inscrit.");
                if(nbPrestParModule.containsKey(module)) {
                    if(nbPrestParModule.get(module) == 1) {
                        conflits.add("Le groupe '"+groupe.getLibelle()+"' est affect� � " +
                                     " plus d'une prestation pour le module '"+module+"'.");
                        nbPrestParModule.put(module, 2);
                    }
                }
                else
                    nbPrestParModule.put(module, 1);
            }
        }

        // pour chaque enseignant, on v�rifie qu'il poss�de assez de cr�neaux
        // disponibles pour assurer toutes ses prestations
        for(Enseignant enseignant : enseignants) {
            int nbIndisponibilites = enseignant.getIndisponibilites().size();
            int nbDisponibilites = creneaux.size() - nbIndisponibilites;
            int nbPrestationsAss = enseignant.getPrestationsAssurees().size();
            if(nbDisponibilites < nbPrestationsAss)
                conflits.add("L'enseignant '"+enseignant.getNom()+"' a trop de " +
                        "cr�neaux indisponibles pour assurer toutes ses prestations.");
        }

        return conflits.toArray(new String[0]);
    }

}