package tn.esprit.emploi.modification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import tn.esprit.edt.gestion.ServicesRemote;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Prestation;

@ManagedBean("controllerModification")
@SessionScoped
public class ControllerModification {

	/*********************** Declaration *****************************/
	@EJB
	ServicesRemote service;
	private List<Prestation> prestations;
	private List<Creneau> creneaux;
	private List<Groupe> groupes;
	private List<Enseignant> enseignants;
	private List<Creneau> cren;
    private Set<Creneau> c1;
    private Set<Creneau> c2 ;
   private String msg ="";
	public ControllerModification() {
		super();
		prestations = new ArrayList<Prestation>();
		creneaux = new ArrayList<Creneau>();
		enseignants = new ArrayList<Enseignant>();
		groupes = new ArrayList<Groupe>();
		cren = new ArrayList<Creneau>();
		 c1 = new HashSet<Creneau>();
		 c2 = new HashSet<Creneau>();
		

	}
	public void addInfo(ActionEvent actionEvent) {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"affectation terminée avec succée", msg ));  
    } 

	/**************************** Methodes **********************************/

	
//	   private Integer progress;  
//	   
//	    public Integer getProgress() {  
//	        if(progress == null)  
//	            progress = 0;  
//	        else {  
//	            progress = progress + (int)(Math.random() * 35);  
//	              
//	            if(progress > 100)  
//	                progress = 100;  
//	        }  
//	          
//	        return progress;  
//	    }  
//	  
//	    public void setProgress(Integer progress) {  
//	        this.progress = progress;  
//	    }  
//	      
//	    public void onComplete() {  
//	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));  
//	    }  
	
	
	
	
	
//	public String Affecter() {
//		prestations = new ArrayList<Prestation>(service.getListPrestation());
//
//		for (Prestation prestation : prestations) {
//			boolean a = true;
//			boolean b = true;
//
//			creneaux = new ArrayList<Creneau>(service.getAllCreneaux());
//			for (Creneau creneau : creneaux) {
//				// System.out.println("creneau");
//				groupes = new ArrayList<Groupe>(service.getList());
//				for (Groupe groupe : groupes) {
//					// System.out.println("groupe");
//
//					cren = new ArrayList<Creneau>(
//							service.getListCreneauxGroupe(groupe));
//
//					for (Creneau c : cren) {
//						// System.out.println("creneau2");
//						if (c.getNumero() == (creneau.getNumero())) {
//							a = false;
//						} else {
//							enseignants = new ArrayList<Enseignant>(
//									service.getListEnseigants());
//							for (Enseignant enseignant : enseignants) {
//								// System.out.println("enseignant");
//
//								List<Creneau> crenea = new ArrayList<Creneau>(
//										service.getCreneauxEns(enseignant));
//								for (Creneau ce : crenea) {
//
//									if (ce.getNumero() == creneau.getNumero()) {
//
//										b = false;
//									}
//								}
//							}
//						}
//
//					}
//					if ((a = true) && (b = true)) {
//						// System.out.println("nmrCreneau" +
//						// prestation.getCreneau().getNumero());
//						// service.SetCreneauAuPrestation(prestation, creneau);
//						prestation.setCreneau(creneau);
//						System.out.println("affecté");
//					}
//				}
//			}
//
//		}
//		return "oki";
//	}

	public String AffecterCrenauToPrestation() {
		creneaux = new ArrayList<Creneau>(service.getAllCreneaux());
		prestations = new ArrayList<Prestation>(service.getListPrestation());
		enseignants = new ArrayList<Enseignant>(service.getListEnseigants());
		groupes = new ArrayList<Groupe>(service.getList());
	   

		
		boolean var1 = true;
		boolean var2 = true;
		boolean var3 = false;
		
		for (Prestation prestation : prestations) {
			
			var3 = false;
			var1= true;
			var2= true;

			Enseignant enseignant = prestation.getEnseignant();
			Groupe gr = service.findGroupeById(prestation.getCodeCl());
			
			

			
			for (Creneau creneau : creneaux) {
				var1=true;
				var2=true;
				List<Prestation> listPrestation = new ArrayList<Prestation>(service.getPrestationByEnseignant(enseignant.getId()));
				List<Creneau> listcreneauEns = new ArrayList<Creneau>();
				for (Prestation prest : listPrestation) {
					if ((prest.getCreneau())!= null) {
						listcreneauEns.add(prest.getCreneau());
					}
					
				}
				List<Prestation> listPres = new ArrayList<Prestation>(service.getPrestationByGroupe(prestation.getCodeCl()));
				List<Creneau> listcreneauGr = new ArrayList<Creneau>();
				for (Prestation prest : listPres) {
					if ((prest.getCreneau())!= null) {
						listcreneauGr.add(prest.getCreneau());
					}}

					for (Creneau creneauens : listcreneauEns) {
						if (creneau.getNumero() == creneauens.getNumero()) {
							var1 = false;
//							System.out.println("var1 =" +var1 +"pour creneau "+creneau.getNumero());
						}
					}
					for (Creneau creneaug : listcreneauGr) {
						if (creneau.getNumero() == creneaug.getNumero()) {
							var1 = false;
//							System.out.println("var2 =" +var2 +"pour creneau "+creneau.getNumero());
						}
					}

					if (var1 == true & var2 == true
							& prestation.getCreneau() == null & var3 == false) {
//						System.out.println("var1 =" +var1 +"var 2 "+var2 + "var3" + var3);
						var3 = true;
						service.addCrenauToPrestation(
								prestation.getCodeModule(),
								prestation.getCodeCl(),
								prestation.getAnneeDeb(),
								prestation.getNumSemestre(),
								creneau.getNumero());
				
//						
//					 c1 = new HashSet<Creneau>();
//					
//					c1.add(creneau);
//					gr.setIndisponibilites(c1);
//				    c2 = new HashSet<Creneau>();
//					
//					c2.add(creneau);
//					enseignant.setCreneauCollection(c2);
//					prestations = new ArrayList<Prestation>(service.getListPrestation());
//					
						
					}
				} 
			}
		msg="l'affectation est terminée" 
				;
		return "Toutes les prestations  ont été placées sur l'emploi du temps";

	}

	public List<Prestation> getPrestations() {
		return prestations;
	}

	public void setPrestations(List<Prestation> prestations) {
		this.prestations = prestations;
	}

	public List<Creneau> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<Creneau> creneaux) {
		this.creneaux = creneaux;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public List<Enseignant> getEnseignants() {
		return enseignants;
	}

	public void setEnseignants(List<Enseignant> enseignants) {
		this.enseignants = enseignants;
	}

	public List<Creneau> getCren() {
		return cren;
	}

	public void setCren(List<Creneau> cren) {
		this.cren = cren;
	}

	public Set<Creneau> getC1() {
		return c1;
	}

	public void setC1(Set<Creneau> c1) {
		this.c1 = c1;
	}

	public Set<Creneau> getC2() {
		return c2;
	}

	public void setC2(Set<Creneau> c2) {
		this.c2 = c2;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	
	
	
	
	
	
	
	

//	public String Reset() {
//		Creneau c = service.findCreneau(1);
//
//		for (Prestation prestation : prestations) {
//
//			service.addCrenauToPrestation(prestation.getCodeModule(),
//					prestation.getCodeCl(), prestation.getAnneeDeb(),
//					prestation.getNumSemestre(), c.getNumero());
//			System.out.println(prestation.getCreneau().getNumero());
//		}
//		return "ok";
//	}

}
