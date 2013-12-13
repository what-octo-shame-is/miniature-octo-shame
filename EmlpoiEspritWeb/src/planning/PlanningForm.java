package planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Prestation;
import tn.esprit.edt.persistance.PrestationPK;


public class PlanningForm {
	private Set<Groupe> groupes;

	private Set<Enseignant> enseignants;

	private Set<Groupe> groupesSel;

	private Set<Enseignant> enseignantsSel;

	private EDT planning;

	private Set<Prestation> prestationCasees;

	private Set<Prestation> prestationsNonCasees;

	private Map<Integer, String> classNamesPrest;

	private int numCreneau;

	private int idPrestation;

	private int idPrestARemplacer;
	
	/** Énumération des différents types de messages. */
	public enum TypeMessage {
		INFO, SUCCES, ERREUR, AVERTISSEMENT
	}

	private TypeMessage typeMessage;

	private String titreMessages;

	private Set<String> messages;

	private boolean resolu;

	/**
     *
     */
	public PlanningForm() {
		super();
		this.classNamesPrest = new HashMap<Integer, String>();
		// instanciation d'un EDT vide
		this.planning = this.new EDT();
		this.resolu = false;
	}

	public boolean isResolu() {
		return resolu;
	}

	public void setResolu(boolean resolu) {
		this.resolu = resolu;
	}

	public Set<String> getMessages() {
		return messages;
	}

	public void setMessages(Set<String> messages) {
		this.messages = messages;
	}

	public String getTitreMessages() {
		return titreMessages;
	}

	public void setTitreMessages(String titreMessages) {
		this.titreMessages = titreMessages;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}

	/**
	 * Retourne la classe (attribut HTML utilisé par les CSS) du type de message
	 * sélectionné.
	 * 
	 * @return
	 */
	public String getClassCSSTypeMessage() {
		TypeMessage type = getTypeMessage();
		if (type == null)
			return "";
		switch (type) {
		case SUCCES:
			return "succes";
		case INFO:
			return "info";
		case AVERTISSEMENT:
			return "avertissement";
		case ERREUR:
		default:
			return "erreur";
		}
	}

	public void setTypeMessage(TypeMessage typeMessage) {
		this.typeMessage = typeMessage;
	}

	public int getIdPrestation() {
		return idPrestation;
	}

	public void setIdPrestation(int idPrestation) {
		this.idPrestation = idPrestation;
	}

	public int getIdPrestARemplacer() {
		return idPrestARemplacer;
	}

	public void setIdPrestARemplacer(int idPrestARemplacer) {
		this.idPrestARemplacer = idPrestARemplacer;
	}

	public int getNumCreneau() {
		return numCreneau;
	}

	public void setNumCreneau(int numCreneau) {
		this.numCreneau = numCreneau;
	}

	public Set<Prestation> getPrestationCasees() {
		return prestationCasees;
	}

	public void setPrestationCasees(Set<Prestation> prestationCasees) {
		this.prestationCasees = prestationCasees;
		for (Prestation p : this.prestationCasees)
			this.classNamesPrest.put(Integer.parseInt(p.getCodeCl()), getClassName(p));
		
	}

	/**
	 * Construit pour une prestation une chaîne qui représentera l'ensemble des
	 * classes de l'élément HTML représentant la prestation (cet ensemble de
	 * classes ("classes" dans le sens CSS du terme) sera utilisé pour
	 * déterminer quelle prestation pourra être déposée ou non à tel ou tel
	 * endroit).
	 * 
	 * @param prestation
	 *            la prestation dont on veut l'attribut HTML "class"
	 * @return une partie de l'attribut "class" de l'élément HTML représentant
	 *         la prestation
	 */
	private String getClassName(Prestation prestation) {
		if (prestation == null)
			return "";
		String className = "";
		className += ("enseignant" + prestation.getEnseignant().getId());
		for (Groupe groupe : prestation.getGroupes())
			className += (" groupe" + groupe.getId());
		return className;
	}

	public Set<Prestation> getPrestationsNonCasees() {
		return prestationsNonCasees;
	}

	public void setPrestationsNonCasees(Set<Prestation> prestationsNonCasees) {
		this.prestationsNonCasees = prestationsNonCasees;
		for (Prestation p : this.prestationsNonCasees)
			this.classNamesPrest.put(Integer.parseInt(p.getPrestationPK().toString()), getClassName(p));
	}

	/**
	 * Retourne l'ensemble des noms de classes (au sens CSS) pour les éléments
	 * HTML de chaque prestations (casées et non casées). Ces classes sont
	 * utilisées pour déterminer quelle prestation pourra être déposée ou non à
	 * tel ou tel endroit.
	 * 
	 * @return table de hachage dont la clé est un id de prestation et la valeur
	 *         sa "class"
	 */
	public Map<Integer, String> getClassNamesPrest() {
		return classNamesPrest;
	}

	public void setClassNamesPrest(Map<Integer, String> classNamesPrest) {
		this.classNamesPrest = classNamesPrest;
	}

	public Set<Enseignant> getEnseignants() {
		return enseignants;
	}

	public void setEnseignants(Set<Enseignant> enseignants) {
		this.enseignants = enseignants;
	}

	public Set<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(Set<Groupe> groupes) {
		this.groupes = groupes;
	}

	public Set<Enseignant> getEnseignantsSel() {
		return enseignantsSel;
	}

	public void setEnseignantsSel(Set<Enseignant> enseignantsSel) {
		this.enseignantsSel = enseignantsSel;
	}

	public Set<Groupe> getGroupesSel() {
		return groupesSel;
	}

	public void setGroupesSel(Set<Groupe> groupesSel) {
		this.groupesSel = groupesSel;
	}

	public int getNbGroupesSel() {
		return (groupesSel != null ? groupesSel.size() : 0);
	}

	public int getNbEnseignantsSel() {
		return (enseignantsSel != null ? enseignantsSel.size() : 0);
	}

	public int getNbEntitesSel() {
		return getNbGroupesSel() + getNbEnseignantsSel();
	}

	public EDT getPlanning() {
		return planning;
	}

	public void setPlanning(EDT planning) {
		this.planning = planning;
	}

	public class EDT {

		private Map<Integer, CaseCreneau> cases;

		public EDT() {
			int nbCases = Creneau.NB_JOURS * Creneau.NB_CRENEAUX_PAR_JOUR;
			this.cases = new HashMap<Integer, CaseCreneau>(nbCases);
			for (int i = 1; i <= nbCases; i++)
				this.cases.put(i, this.new CaseCreneau());
		}

		public CaseCreneau getCase(int numCreneau) {
			return cases.get(numCreneau);
		}

		public void fusionnerSousCases() {
			for (CaseCreneau c : this.cases.values())
				c.fusionner();
		}

		public class CaseCreneau {

			private Set<SousCasePrestation> sousCases;

			public CaseCreneau() {
				int nbSsCases = PlanningForm.this.getNbEntitesSel();
				this.sousCases = new HashSet<SousCasePrestation>(nbSsCases);
				for (int i = 0; i < nbSsCases; i++)
					this.sousCases.add(this.new SousCasePrestation(i));
			}

			public Set<SousCasePrestation> getSousCases() {
				return sousCases;
			}

			public void fusionner() {
				int i = this.sousCases.size() - 1;
				List<SousCasePrestation> list = new ArrayList<SousCasePrestation>(this.sousCases);
				while (i > 0) {
					SousCasePrestation ssCaseCourante = list.get(i);
					SousCasePrestation ssCasePrec = list.get(i - 1);
					if (ssCaseCourante.getPrestation() != null
							&& ssCaseCourante.getPrestation().equals(
									ssCasePrec.getPrestation())) {
						int nbColonnes = ssCasePrec.getNbColonnes()
								+ ssCaseCourante.getNbColonnes();
						ssCasePrec.setNbColonnes(nbColonnes);
						ssCaseCourante.setNbColonnes(0);
					}
					i--;
				}
			}

			public int getNbSousCasesFusionnees() {
				int nb = 0;
				for (SousCasePrestation sc : this.sousCases)
					if (sc.getNbColonnes() > 0)
						nb++;
				return nb;
			}

			public class SousCasePrestation {

				private int num; // commence à 0
				private int nbColonnes;
				private Prestation prestation;

				public SousCasePrestation(int num) {
					this.num = num;
					this.prestation = null;
					this.nbColonnes = 1;
				}

				public int getNbColonnes() {
					return nbColonnes;
				}

				public void setNbColonnes(int nbColonnes) {
					this.nbColonnes = nbColonnes;
				}

				public Prestation getPrestation() {
					return prestation;
				}

				public void setPrestation(Prestation prestation) {
					this.prestation = prestation;
				}

				/**
				 * Construit pour une sous-case une chaîne qui représentera
				 * l'ensemble des classes de l'élément HTML représentant la
				 * sous-case (cet ensemble de classes ("classes" au sens CSS du
				 * terme) sera utilisé pour déterminer quelle prestation pourra
				 * être déposée ou non dessus).
				 * 
				 * @return une partie de l'attribut "class" de l'élément HTML
				 *         représentant la sous-case
				 */
				public String getClassName() {
					// note : la gestion des sous-cases fusionnées rend la
					// méthode
					// un peu complexe, puisqu'il faut tenir compte qu'une
					// prestation
					// peut être contenue dans plusieurs sous-cases (celles-ci
					// seront
					// fusionnées à l'affichage)
					String className = "";
					CaseCreneau caseParent = CaseCreneau.this;
					int i = this.num;
					SousCasePrestation ssCaseSuivante;
					do {
						// note : ici on suppose que ce sont les groupes
						// sélectionnés
						// qui sont utilisés en premier pour remplir les
						// sous-cases
						List<Groupe> listG = new ArrayList<Groupe>(PlanningForm.this.getGroupesSel());
						if (i < PlanningForm.this.getNbGroupesSel()) {
							//badeltha get(id)=string ---> integer
							int idGrp = Integer.parseInt(listG.get(i).getId());
							className += " groupe" + idGrp;
						} else { // if(num >=
									// PlanningForm.this.getNbGroupesSel()) {
							int indEnsSel = i
									- PlanningForm.this.getNbGroupesSel();
							int idEns =  Integer.parseInt(listG.get(indEnsSel).getId());
							className += " enseignant" + idEns;
						}
						i++;
						if (i >= caseParent.getSousCases().size())
							ssCaseSuivante = null;
						else{
							List<SousCasePrestation> listSous = 
								new ArrayList<PlanningForm.EDT.CaseCreneau.SousCasePrestation>(caseParent.getSousCases());
							ssCaseSuivante = listSous.get(i);
						}
							

					} while (ssCaseSuivante != null
							&& ssCaseSuivante.getNbColonnes() == 0);

					return className;
				}

			}

		}

	}


}
