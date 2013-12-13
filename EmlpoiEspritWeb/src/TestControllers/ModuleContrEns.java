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
import tn.esprit.edt.persistance.Module;

@ManagedBean(name="moduleContrEns")
@ApplicationScoped
public class ModuleContrEns {
	@EJB
	Services serviceAdmin;
	Set<Module> seMSG = new HashSet<Module>();
	@ManagedProperty(value = "#{controllerSchEns}")
	private ControllerSchEns controllerSchEns;
	@ManagedProperty(value = "#{controllerEnseignant}")
	private ControllerEnseignant controllerEnseignant;
	private Module suppModule = new Module();
	private Enseignant enseignant = new Enseignant();
	private String nomEnseignant;
	private String idModule;
	private List<Module> players = new ArrayList<Module>();
	private List<Module> selectedPlayers = new ArrayList<Module>();

	public ModuleContrEns() {
		super();

		selectedPlayers = new ArrayList<Module>();
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

	
	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
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

	public ControllerSchEns getControllerSchEns() {
		if (controllerSchEns == null) {
			long t1 = Calendar.getInstance().getTimeInMillis();
			System.out.print("Creating new controller ... ");
			controllerSchEns = new ControllerSchEns(serviceAdmin);
			if(controllerEnseignant==null) controllerEnseignant = new ControllerEnseignant();
			controllerSchEns.setControllerEnseignant(controllerEnseignant);
			long t2 = Calendar.getInstance().getTimeInMillis();
			System.out.println("done wihtin " + (t2 - t1) + " ms");
		}
		System.out.println("Returned current controllers instance");
		return controllerSchEns;
	}

	public void setControllerSchEns(ControllerSchEns controllerSchEns) {
		this.controllerSchEns = controllerSchEns;
	}

	public ControllerEnseignant getControllerEnseignant() {
		return controllerEnseignant;
	}

	public void setControllerEnseignant(ControllerEnseignant controllerEnseignant) {
		this.controllerEnseignant = controllerEnseignant;
	}

	


}
