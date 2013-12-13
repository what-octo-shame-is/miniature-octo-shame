package TestControllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.omg.CORBA.BAD_PARAM;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Prestation;
@ManagedBean(name="controllerDisponibilite")
@SessionScoped
public class ControllerDisponibilite {
	@EJB
	Services services;
	private List<Creneau> ListCreneau ;
	private List<Enseignant> listEnseignant;
	private String selectedEnseignant;
	private 	List<Prestation> prestations;
	private Integer selectedCreneau;
	private String selectedGroupe;
	private Enseignant enseignant;
    private List<Groupe> groupes;
	private Groupe groupe;
	private Boolean msg ;
	private String msg2;
	private String msgGr;
	private Creneau cre;
	public ControllerDisponibilite(){
		listEnseignant= new ArrayList<Enseignant>();
		ListCreneau = new ArrayList<Creneau>();
		setGroupes(new ArrayList<Groupe>());
		prestations= new ArrayList<Prestation>();
	}
	public boolean DispnibleEnseignants(){
		Integer	creneau= selectedCreneau;
		String id=selectedEnseignant;
		msg2= "l'enseignant est disponible pour ce creneau";
		boolean disp=true;
		prestations = new ArrayList<Prestation>(services.getPrestationByEnseignant(id));
		
		for (Prestation prestation : prestations) {
			
			if (prestation.getCreneau().getNumero()==creneau) {
				disp=false;
		    msg2="l'enseignant n'est pas disponible pour ce creneau" ;
			}
		}
		System.out.println(disp);
		msg=disp ;
		return disp;	
		
			
	}
	
	
	
	
    public boolean DispnibleGroupe(){
	  Integer	creneau= selectedCreneau;
	  String id=selectedGroupe;
	  msgGr= "le groupe est disponible pour ce creneau";
		boolean disp=true;
		List<Prestation> prestations = new ArrayList<Prestation>(services.getPrestationByGroupe(id));
		
		for (Prestation prestation : prestations) {
			
			if (prestation.getCreneau().getNumero()==creneau) {
				disp=false;
				msgGr= "le groupe n'est pas disponible pour ce creneau";
			}
		}
		return disp;
			
	}







public String getMsgGr() {
	return msgGr;
}
public void setMsgGr(String msgGr) {
	this.msgGr = msgGr;
}
public void selectEnseignantListener(AjaxBehaviorEvent  event){
	System.out.println("Selected : "+selectedEnseignant);
}

public void selectCreneauListener(AjaxBehaviorEvent  event){
	System.out.println("Selected : "+selectedCreneau);
}
public void selectGroupeListener(AjaxBehaviorEvent  event){
	System.out.println("Selected : "+selectedGroupe);
}
public List<Creneau> getListCreneau() {
	ListCreneau= new ArrayList<Creneau>(services.getAllCreneaux());
	return ListCreneau;
}
public void setListCreneau(List<Creneau> listCreneau) {
	ListCreneau = listCreneau;
}
public List<Enseignant> getListEnseignant() {
	listEnseignant= new ArrayList<Enseignant>(services.getListEns());
	return listEnseignant;
}
public void setListEnseignant(List<Enseignant> listEnseignant) {
	this.listEnseignant = listEnseignant;
}
public String getSelectedEnseignant() {
	return selectedEnseignant;
}
public void setSelectedEnseignant(String selectedEnseignant) {
	this.selectedEnseignant = selectedEnseignant;
}
public Integer getSelectedCreneau() {
	return selectedCreneau;
}
public void setSelectedCreneau(Integer selectedCreneau) {
	this.selectedCreneau = selectedCreneau;
}
public Enseignant getEnseignant() {
	return enseignant;
}
public void setEnseignant(Enseignant enseignant) {
	this.enseignant = enseignant;
}
public Creneau getCreneau() {
	return cre;
}
public void setCreneau(Creneau cre) {
	this.cre = cre;
}
public Groupe getGroupe() {
	return groupe;
}
public void setGroupe(Groupe groupe) {
	this.groupe = groupe;
}
public Creneau getCre() {
	return cre;
}
public void setCre(Creneau cre) {
	this.cre = cre;
}
public List<Prestation> getPrestations() {
	prestations= new ArrayList<Prestation>(services.getListPrestation());
	return prestations;
}
public void setPrestations(List<Prestation> prestations) {
	this.prestations = prestations;
}
public String getSelectedGroupe() {
	return selectedGroupe;
}
public void setSelectedGroupe(String selectedGroupe) {
	this.selectedGroupe = selectedGroupe;
}
public Boolean getMsg() {
	return msg;
}
public void setMsg(Boolean msg) {
	this.msg = msg;
}
public String getMsg2() {
	return msg2;
}
public void setMsg2(String msg2) {
	this.msg2 = msg2;
}
public List<Groupe> getGroupes() {
	groupes= new ArrayList<Groupe>(services.getList());
	return groupes;
}
public void setGroupes(List<Groupe> groupes) {
	this.groupes = groupes;
}
	
	

}
