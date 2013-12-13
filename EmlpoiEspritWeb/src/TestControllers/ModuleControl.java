package TestControllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;

import org.primefaces.event.DragDropEvent;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.gestion.ServicesRemote;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Module;

@ManagedBean(name = "moduleControl")
@ApplicationScoped
public class ModuleControl {
	@EJB
	Services serviceAdmin;
	Set<Module> seMSG = new HashSet<Module>();
	
	@ManagedProperty(value = "#{controllersG}")
	private ControllersGr controllersGr;
	List<Module> list = new ArrayList<Module>(seMSG);
	private Set<Module> listeModule = new HashSet<Module>();
	private Module nouv = new Module();
	private Module supp = new Module();
	private Module modif = new Module();
	private UIData table;
	private Module suppModule = new Module();
	private Enseignant enseignant = new Enseignant();
	private String nomEnseignant;
	private String idModule;
	private List<Module> players = new ArrayList<Module>();
	private List<Module> selectedPlayers = new ArrayList<Module>();
	List<Module> liste = new ArrayList<Module>();

	public ModuleControl() {
		super();

		selectedPlayers = new ArrayList<Module>();
		listeModule = new HashSet<Module>();

	}

	public void onDrop(DragDropEvent event) {
		Module player = (Module) event.getData();

		selectedPlayers.add(player);

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(player.getDesignation() + " added",
						"Position:" + event.getDropId()));
	}

	public List<Enseignant> getListEnseignant() {
		Set<Enseignant> setEns = new HashSet<Enseignant>(
				serviceAdmin.getListEns());
		List<Enseignant> listEnseignant = new ArrayList<Enseignant>(setEns);
		return listEnseignant;
	}

	/*************** getter & setter ***********/

	public ServicesRemote getServiceAdmin() {
		return serviceAdmin;
	}

	public void setServiceAdmin(Services serviceAdmin) {
		this.serviceAdmin = serviceAdmin;
	}

	public Set<Module> getListeModule() {
		return listeModule;
	}

	public void setListeModule(Set<Module> listeModule) {
		this.listeModule = listeModule;
	}

	public Module getNouv() {
		return nouv;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public void setNouv(Module nouv) {
		this.nouv = nouv;
	}

	public Module getSupp() {
		return supp;
	}

	public void setSupp(Module supp) {
		this.supp = supp;
	}

	public Module getModif() {
		return modif;
	}

	public void setModif(Module modif) {
		this.modif = modif;
	}

	public UIData getTable() {
		return table;
	}

	public void setTable(UIData table) {
		this.table = table;
	}

	public Module getSuppModule() {
		return suppModule;
	}

	public void setSuppModule(Module suppModule) {
		this.suppModule = suppModule;
	}

	public Set<Module> getSeMSG() {
		return seMSG;
	}

	public List<Module> getPlayers() {
		players = new ArrayList<Module>(serviceAdmin.getListModule());
		return players;
	}

	public void setPlayers(List<Module> players) {
		this.players = players;
	}

	public void setSeMSG(Set<Module> seMSG) {
		this.seMSG = seMSG;
	}

	public List<Module> getList() {
		return list;
	}

	public void setList(List<Module> list) {
		this.list = list;
	}

	public String getNomEnseignant() {
		return nomEnseignant;
	}

	public void setNomEnseignant(String nomEnseignant) {
		this.nomEnseignant = nomEnseignant;
	}

	public String getIdModule() {
		return idModule;
	}

	public void setIdModule(String idModule) {
		this.idModule = idModule;
	}

	public List<Module> getSelectedPlayers() {
		return selectedPlayers;
	}

	public void setSelectedPlayers(List<Module> selectedPlayers) {
		this.selectedPlayers = selectedPlayers;
	}

	public ControllersGr getControllersGr() {
		if(controllersGr==null){
			long t1 = Calendar.getInstance().getTimeInMillis();
			System.out.print("Creating new controller ... ");
			controllersGr = new ControllersGr(serviceAdmin);
			long t2 = Calendar.getInstance().getTimeInMillis();
			System.out.println("done wihtin "+(t2-t1)+" ms");
		}
		System.out.println("Returned current controllers instance");
		return controllersGr;
	}

	public void setControllersGr(ControllersGr controllersGr) {
		this.controllersGr = controllersGr;
	}

	public List<Module> getListe() {
		return liste;
	}

	public void setListe(List<Module> liste) {
		this.liste = liste;
	}



}
