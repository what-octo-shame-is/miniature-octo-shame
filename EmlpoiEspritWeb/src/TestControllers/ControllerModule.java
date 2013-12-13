package TestControllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;

import tn.esprit.edt.gestion.Services;

import tn.esprit.edt.persistance.Module;

@ManagedBean(name="controllerModule")
@SessionScoped
public class ControllerModule {
	@EJB
	Services serviceModule;

	private List<Module> listeModule = new ArrayList<Module>();
	private Module nouvModule = new Module();
	private Module suppModule = new Module();
	private Module modifModule = new Module();
	private UIData tableModule;
	private Module suppAbs2 = new Module();

	



	public ControllerModule() {
		super();
		listeModule = new ArrayList<Module>();
	}

	

	public String actualiser() {
		nouvModule = new Module();
		return "add";
	}



	public Services getServiceModule() {
		return serviceModule;
	}



	public void setServiceModule(Services serviceModule) {
		this.serviceModule = serviceModule;
	}



	public List<Module> getListeModule() {
		listeModule = new ArrayList<Module>(serviceModule.getListModule());
		return listeModule;
	}



	public void setListeModule(List<Module> listeModule) {
		this.listeModule = listeModule;
	}



	public Module getNouvModule() {
		return nouvModule;
	}



	public void setNouvModule(Module nouvModule) {
		this.nouvModule = nouvModule;
	}



	public Module getSuppModule() {
		return suppModule;
	}



	public void setSuppModule(Module suppModule) {
		this.suppModule = suppModule;
	}



	public Module getModifModule() {
		return modifModule;
	}



	public void setModifModule(Module modifModule) {
		this.modifModule = modifModule;
	}



	public UIData getTableModule() {
		return tableModule;
	}



	public void setTableModule(UIData tableModule) {
		this.tableModule = tableModule;
	}



	public Module getSuppAbs2() {
		return suppAbs2;
	}



	public void setSuppAbs2(Module suppAbs2) {
		this.suppAbs2 = suppAbs2;
	}

	



}
