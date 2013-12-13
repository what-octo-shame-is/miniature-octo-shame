package TestControllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Prestation;

@ManagedBean(name="controllers")
@ApplicationScoped
public class Controllers {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;
	private Services services;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private String id;
	private List<Enseignant> listENS;
	private List<Enseignant> enseigants;
	private Groupe nouvGroupe = new Groupe();
	private Groupe suppGroupe = new Groupe();
	private Groupe modifGroupe = new Groupe();
	private List<Enseignant> listeEns = new ArrayList<Enseignant>();
	private Enseignant nouvEns = new Enseignant();
	private Enseignant suppEns = new Enseignant();
	private Enseignant modifEns = new Enseignant();
	private String selectedEnseignant;

	private UIData tableEns;
	private Enseignant suppEns2 = new Enseignant();
	List<Prestation> listPrestation = new ArrayList<Prestation>();
	private List<Groupe> listeGroupe = new ArrayList<Groupe>();

	
	public Controllers(Services services) {
		super();
		this.services = services;

		listeGroupe = new ArrayList<Groupe>(services.getList());
		listeEns = new ArrayList<Enseignant>(services.getListEnseigants());
		listPrestation = new ArrayList<Prestation>();
	}
	public String GetNomEnseignantByID()
	{
		return  services.GetNomEnseignantByID(selectedEnseignant);
		
		
	}
	public String NomEnseignant()
	{
		return services.NomEnseignant(selectedEnseignant);
	}

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random
																	// day of
																	// month

		return date.getTime();
	}

	public Date getInitialDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY,
				calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public ScheduleModel getEventModel() {
		return affecterPrestation(services );
	}

	private Calendar today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar;
	}
	
	
	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
//		listPrestation = new ArrayList<Prestation>(services.getListPrestation());
//		listeGroupe = new ArrayList<Groupe>(services.getList());
//		if(listeGroupe.g){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Event moved", "Day delta:" + event.getDayDelta()
							+ ", Minute delta:" + event.getMinuteDelta());

			addMessage(message);

//		}
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private Date numeroJour(int numero) {
		int numeroSeance = numero % 4;
		int position = 0;
		Calendar maDate = new java.util.GregorianCalendar();
		maDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		switch (numeroSeance) {
		case 0:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 4);
			
			break;

		case 1:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 9);

			break;
		case 2:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 11);
		
			break;

		case 3:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 2);
	
			break;
		case 4:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.MONTH, Calendar.HOUR, Calendar.MINUTE, 4, 30);
	
			break;
		case 5:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 9);

			break;
		case 6:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 11);
			
			break;
		case 7:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 2);

			break;
		case 8:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 4);

			break;
		case 9:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 9);

			break;
		case 10:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 11);

			break;
		case 11:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 2);

			break;
		case 12:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 4);
		
			break;
		case 13:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 9);
	
			break;
		case 14:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 11);

			break;
		case 15:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 2);

			break;
		case 16:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 4);

			break;
		case 17:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 9);

			break;
		case 18:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 11);

			break;
		case 19:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 2);

			break;
		case 20:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 4);

			break;
		case 21:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 9);

			break;
		case 22:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 11);

			break;

		default:
			break;
		}
		maDate.add(Calendar.DATE, numeroSeance == 0 ? (numero / 4) - 1
				+ position : (numero / 4) + position);
		return maDate.getTime();
	}

	public ScheduleModel affecterPrestation(Services serviceAdmin) {
		eventModel = new DefaultScheduleModel();

		List<Prestation> listPrestation = new ArrayList<Prestation>(
				serviceAdmin.getPrestationByEnseignant(selectedEnseignant));

		for (Prestation p : listPrestation) {
			String id = p.getCodeCl();
			eventModel.addEvent(new DefaultScheduleEvent(p.getEnseignant().getNom()
					+ "\n"
					+ p.getModule().getDesignation()
					+ "\n"
					+ serviceAdmin.GetNomGroupeByID(id)
					+ "\n"
					+ p.getCreneau(), numeroJour(p.getCreneau()
					.getNumero()), numeroJour(p.getCreneau().getNumero())));
		}
		return eventModel;
	}
	
	public void selectEnseignantListener(AjaxBehaviorEvent  event){
		System.out.println("Selected : "+selectedEnseignant);
	}

	/********************* getter & setter ****************/

	public List<Groupe> getListeGroupe() {
		return listeGroupe;
	}

	public void setListeGroupe(List<Groupe> listeGroupe) {
		this.listeGroupe = listeGroupe;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Enseignant> getListENS() {
		return listENS;
	}

	public void setListENS(List<Enseignant> listENS) {
		this.listENS = listENS;
	}

	public List<Enseignant> getEnseigants() {
		return enseigants;
	}

	public void setEnseigants(List<Enseignant> enseigants) {
		this.enseigants = enseigants;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public Groupe getNouvGroupe() {
		return nouvGroupe;
	}

	public void setNouvGroupe(Groupe nouvGroupe) {
		this.nouvGroupe = nouvGroupe;
	}

	public Groupe getSuppGroupe() {
		return suppGroupe;
	}

	public void setSuppGroupe(Groupe suppGroupe) {
		this.suppGroupe = suppGroupe;
	}

	public Groupe getModifGroupe() {
		return modifGroupe;
	}

	public void setModifGroupe(Groupe modifGroupe) {
		this.modifGroupe = modifGroupe;
	}

	public List<Enseignant> getListeEns() {
		return listeEns;
	}

	public void setListeEns(List<Enseignant> listeEns) {
		this.listeEns = listeEns;
	}

	public Enseignant getNouvEns() {
		return nouvEns;
	}

	public void setNouvEns(Enseignant nouvEns) {
		this.nouvEns = nouvEns;
	}

	public String getSelectedEnseignant() {
		return selectedEnseignant;
	}



	public void setSelectedEnseignant(String selectedEnseignant) {
		this.selectedEnseignant = selectedEnseignant;
	}

	public Enseignant getSuppEns() {
		return suppEns;
	}

	public void setSuppEns(Enseignant suppEns) {
		this.suppEns = suppEns;
	}

	public Enseignant getModifEns() {
		return modifEns;
	}

	public void setModifEns(Enseignant modifEns) {
		this.modifEns = modifEns;
	}

	public UIData getTableEns() {
		return tableEns;
	}

	public void setTableEns(UIData tableEns) {
		this.tableEns = tableEns;
	}

	public Enseignant getSuppEns2() {
		return suppEns2;
	}

	public void setSuppEns2(Enseignant suppEns2) {
		this.suppEns2 = suppEns2;
	}

}