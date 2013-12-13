package Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import tn.esprit.edt.exceptions.PreexistingEntityException;
import tn.esprit.edt.gestion.ServicesRemote;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;
import tn.esprit.edt.persistance.Pool;
import tn.esprit.edt.persistance.Prestation;
import tn.esprit.edt.solveur.SolveurEDTCHOCO;



public class Main {

	/**
	 * @param args
	 * @throws Exception
	 * @throws PreexistingEntityException
	 */
	public static void main(String[] args) throws PreexistingEntityException,
			Exception {
		Context cnt = new InitialContext();

		ServicesRemote a = (ServicesRemote) cnt
				.lookup("EmlpoiEspritWeb//Services!tn.esprit.edt.gestion.ServicesRemote");
//		System.out.println(a.NomEnseignant("2"));
		//for(int i =0; i<= a.;i++)
		
		List<Groupe> groupes =new ArrayList<Groupe>(a.getList());
		for (Groupe groupe : groupes) {
			System.out.println("groupe :"+groupe.toString());		
		}
		System.out.println(a.getList());
		
		//		String Code="2";
//		System.out.println(a.GetNomAdmin(Code));
//		System.out.println(a.findIDbyNom("yos"));
		
//		String s = "haythem";
//		Enseignant Ens= a.findIDbyNom(s);
//		System.out.println(Ens.getId());
		
//		List<Prestation> p =new ArrayList<Prestation>(a.getPrestationByGroupe("1")) ;
//		System.out.println(p.size());
//		
//		Module m = new Module();
//		m.setId("2");
//		a.create(m);
//		Enseignant e = new Enseignant();
//		e.setId("2");
//		a.create(e);
//		Prestation p = new Prestation();
//		p.setAnneeDeb("1");
//		p.setCodeCl("1");
//		p.setCodeModule("1");
//		p.setNumSemestre(1);
//		p.setModule(m);
//		p.setEnseignant(e);
//		a.create(p);
		
		/************ solveuuuur *************/
//		 Set<Creneau> creneaux = new
//		 HashSet<Creneau>(a.findCreneauEntities());
//		
//		 Set<Enseignant> enseignants = new HashSet<Enseignant>(
//		 a.findEnseignantsAvecRel());
//		 Set<Module> modules = new HashSet<Module>(a.findModulesAvecRel());
//		
//		 Set<Pool> pools = new HashSet<Pool>(a.findPoolsAvecRel());
//		
//		 Set<Prestation> prestations = new HashSet<Prestation>(
//		 a.findPrestationsAvecRel());
//		
//		 Set<Groupe> groupes = new HashSet<Groupe>(a.findGroupesAvecRel());
//		
//		 SolveurEDTCHOCO s = new SolveurEDTCHOCO(enseignants, modules, pools,
//		 prestations, groupes, creneaux);
//		  s.verifier();
// 		 System.out.println(s.verifier());
//		
//
//		
//		
//		 System.out.println("boolean" +s.resoudre());
//
//			System.out.println(s.resoudre());
//		
//		
//		
		
		
//		boolean disp=true;
//		List<Prestation> prestations = new ArrayList<Prestation>(a.getPrestationByEnseignant("4"));
//		
//		for (Prestation prestation : prestations) {
//			
//			if (prestation.getCreneau().getNumero()==1) {
//				disp=false;
//			}
//		}
//			System.out.println("disp"+ disp);
		
//		List<Prestation> l = new ArrayList<Prestation>(a.getListPrestation());
//		for (Prestation prestation : l) {
//			System.out.println(prestation.getDescription());
//		}
		
		
		
		 }
} 

		

		/*************************** effectue ************/
//
//		List<Creneau> creneaux = new ArrayList<Creneau>(a.getAllCreneaux());
//		List<Prestation> prestations = new ArrayList<Prestation>(
//				a.getListPrestation());
//		List<Enseignant> enseignants = new ArrayList<Enseignant>(
//				a.getListEnseigants());
//		List<Groupe> groupes = new ArrayList<Groupe>(a.getList());
//		Set<Creneau> c1 = new HashSet<Creneau>();
//		Set<Creneau> c2 = new HashSet<Creneau>();
//
//		Integer nbSalle = 3;
//		boolean var1 = true;
//		boolean var2 = true;
//		boolean var3 = false;
//		for (Prestation prestation : prestations) {
//			var3 = false;
//
//			Enseignant enseignant = prestation.getEnseignant();
//			Groupe gr = a.findGroupeById(prestation.getCodeCl());
//			List<Creneau> creneausEns = new ArrayList<Creneau>(
//					enseignant.getCreneauCollection());
//			List<Creneau> creneausGr = new ArrayList<Creneau>(
//					gr.getIndisponibilites());
//
//			for (Creneau creneau : creneaux) {
//				// for (int j = 0; j < nbSalle; j++) {
//
//				for (Creneau creneauens : creneausEns) {
//					if (creneau == creneauens) {
//						var1 = false;
//						System.out.println("var1 =" + var1 + "pour creneau "
//								+ creneau.getNumero());
//					}
//				}
//				for (Creneau creneaug : creneausGr) {
//					if (creneau == creneaug) {
//						var2 = false;
//						System.out.println("var2 =" + var2 + "pour creneau "
//								+ creneau.getNumero());
//					}
//				}
//
//				if (var1 == true & var2 == true
//						& prestation.getCreneau() == null & var3 == false) {
//					var3 = true;
//					System.out.println("var1 =" + var1 + "var 2 " + var2
//							+ "var3" + var3);
//
//					a.addCrenauToPrestation(prestation.getCodeModule(),
//							prestation.getCodeCl(), prestation.getAnneeDeb(),
//							prestation.getNumSemestre(), creneau.getNumero());
//					
//					 c1 = new HashSet<Creneau>();
//						c1.clear();
//						c1.add(creneau);
//						gr.setIndisponibilites(c1);
//						System.out.println( gr.getLibelle() + "/n"+ gr.getIndisponibilites());
//					    c2 = new HashSet<Creneau>();
//						c2.clear();
//						c2.add(creneau);
//						enseignant.setCreneauCollection(c2);
//					
//					System.out.println("prestation"
//							+ prestation.getDescription() + "est affecté a "
//							+ creneau.getNumero());
//					
//					
//					
//					
//					
//					
//
//				}
//			}
//		}
//	}
//}

		
//		Creneau c = new Creneau();
//		c.setNumero(90);
//		Set<Creneau> creneauCollection = new HashSet<Creneau>();
//		creneauCollection.add(c);
//	Groupe g=	a.findGroupeById("1");
//		System.out.println(g.getLibelle());
//		g.setIndisponibilites(creneauCollection);
//		int  s = g.getIndisponibilites().size();
//		System.out.println(s);
//		for (int i = 0; i < g.getIndisponibilites().size(); i++) {
//			System.out.println("l'indisponibilité "+g.getIndisponibilites());
//		}
//		
//		Creneau c = new Creneau();
//		c.setNumero(7);
//		Enseignant enseignant = a.findEnseignantString("5");
//		System.out.println(enseignant.getNom());
//		a.SetInspo(c, enseignant);
//		System.out.println(""+ enseignant.getIndisponibilites());
//	
//		
		
//	Set<Creneau> c = a.getAllCreneaux();
//	System.out.println(c);
		
//		Enseignant ens = a.findEnseignantString("1");
//				System.out.println(ens.getNom());
//	Set<Prestation>  set=a.EnsCren(ens);
//	System.out.println(set);
//		
		
	/****************************************/
//List<Prestation> l = new ArrayList<Prestation>(a.getPrestationByGroupe("1"));
//
//for (Prestation prestation : l) {
//	System.out.println(prestation.getDescription());
//}
//	
//
//List<Prestation> list = new ArrayList<Prestation>(a.getPrestationByGroupe("1"));
//List<Creneau> l = new ArrayList<Creneau>();
//for (Prestation prestation : list) {
//	System.out.println(prestation.getCreneau().getNumero());
//	if( (prestation.getCreneau().getNumero())!= null)
//		l.add(prestation.getCreneau());
//}
//System.out.println(l);
//

////
//Enseignant s= new Enseignant();
//s=
//
		
	
