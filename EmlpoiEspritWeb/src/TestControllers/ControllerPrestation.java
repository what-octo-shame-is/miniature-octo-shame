package TestControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Prestation;
import TestControllers.ControllerGroupe;;

@ManagedBean(name="controllerPrestation")
@SessionScoped
public class ControllerPrestation{
	
	
	
	
	@EJB
	Services servicePrestation;

	private List<Prestation> listePrestation = new ArrayList<Prestation>();
	/**
	 * 
	 */
	private List<List<Prestation>> listePrestationByGroupe = new ArrayList<List<Prestation>>();
	private List<Groupe> groupes;
//	ControllerGroupe controlleurGrp = new ControllerGroupe();

	
	private Prestation nouvPrestation = new Prestation();
	private Prestation suppPrestation = new Prestation();
	private Prestation modifPrestation = new Prestation();
	private UIData tablePrestation;
	private Prestation suppAbs2 = new Prestation();
	String idGrp;

	



	public ControllerPrestation() {
		super();
		listePrestation = new ArrayList<Prestation>();
	}

	

	public String actualiser() {
		nouvPrestation = new Prestation();
		return "add";
	}



	public Services getServicePrestation() {
		return servicePrestation;
	}



	public void setServicePrestation(Services servicePrestation) {
		this.servicePrestation = servicePrestation;
	}



	public List<Prestation> getListePrestation() {
		listePrestation= new ArrayList<Prestation>(servicePrestation.getListPrestation());
		return listePrestation;
	}



	public void setListePrestation(List<Prestation> listePrestation) {
		this.listePrestation = listePrestation;
	}



	public Prestation getNouvPrestation() {
		return nouvPrestation;
	}



	public void setNouvPrestation(Prestation nouvPrestation) {
		this.nouvPrestation = nouvPrestation;
	}



	public Prestation getSuppPrestation() {
		return suppPrestation;
	}



	public void setSuppPrestation(Prestation suppPrestation) {
		this.suppPrestation = suppPrestation;
	}



	public Prestation getModifPrestation() {
		return modifPrestation;
	}



	public void setModifPrestation(Prestation modifPrestation) {
		this.modifPrestation = modifPrestation;
	}



	public UIData getTablePrestation() {
		return tablePrestation;
	}



	public void setTablePrestation(UIData tablePrestation) {
		this.tablePrestation = tablePrestation;
	}



	public Prestation getSuppAbs2() {
		return suppAbs2;
	}



	public void setSuppAbs2(Prestation suppAbs2) {
		this.suppAbs2 = suppAbs2;
	}



	/**
	 * @return the listePrestationByGroupe
	 */
	public List<List<Prestation>> getListePrestationByGroupe() {
		groupes = new ArrayList<Groupe>(servicePrestation.getList());
		
		try{
			for (Groupe grp :groupes ) {
//				Prestation prestation = servicePrestation.getPrestationByGroupe(grp.getId());
				
				listePrestationByGroupe.add(new ArrayList<Prestation>(servicePrestation.getPrestationByGroupe(grp.getId())));
				//listePrestationByGroupe = new ArrayList<Prestation>(servicePrestation.getPrestationByGroupe(grp.getId()));
				
			}
		
		
		}catch(Exception e){
			
		}
		return listePrestationByGroupe;
		
	}



	public List<Groupe> getGroupes() {
		groupes = new ArrayList<Groupe>(servicePrestation.getList());
		return groupes;
	}



	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}



	/**
	 * @param listePrestationByGroupe the listePrestationByGroupe to set
	 */
	public void setListePrestationByGroupe(List<List<Prestation>> listePrestationByGroupe) {
		this.listePrestationByGroupe = listePrestationByGroupe;
	}


}
