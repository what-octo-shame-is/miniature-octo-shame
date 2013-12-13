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

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Groupe;
import tn.esprit.edt.persistance.Prestation;

@ManagedBean(name = "controllersGr")
@ApplicationScoped
public class ControllersGr {
	private ScheduleModel eventModel;
	private Services services;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private Groupe nouvGroupe = new Groupe();
	private Groupe suppGroupe = new Groupe();
	private Groupe modifGroupe = new Groupe();
	private String selectedGroupe;
	private UIData tableEns;

	List<Prestation> listPrestation = new ArrayList<Prestation>();
	private List<Groupe> listeGroupe = new ArrayList<Groupe>();

	public ControllersGr(Services services) {
		super();
		this.services = services;
		listeGroupe = new ArrayList<Groupe>();
		listPrestation = new ArrayList<Prestation>();
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
		return affecterPrestationGr(services);
	}
	 public String GetLibelleGroupe(){
		 return services.GetLibelleGroupeByID(selectedGroupe);
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
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event moved", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
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

	public ScheduleModel affecterPrestationGr(Services serviceAdmin) {
		eventModel = new DefaultScheduleModel();

		List<Prestation> listPrestation = new ArrayList<Prestation>(
				serviceAdmin.getPrestationByGroupe(selectedGroupe ));

		for (Prestation p : listPrestation) {
			String id = p.getCodeCl();
			eventModel.addEvent(new DefaultScheduleEvent(serviceAdmin.GetNomGroupeByID(id)
					+ "\n"
					+ p.getEnseignant().getNom()
					+ "\n"
					+ p.getModule().getDesignation()
					+ "\n"
					+ p.getCreneau(), numeroJour(p.getCreneau()
					.getNumero()), numeroJour(p.getCreneau().getNumero())));
		}
		return eventModel;
	}

	public void selectGroupeListener(AjaxBehaviorEvent event) {
		System.out.println("Selected : " + selectedGroupe);
	}

	/********************* getter & setter ****************/

	public List<Groupe> getListeGroupe() {
		listeGroupe = new ArrayList<Groupe>(services.getList());
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

	public String getSelectedGroupe() {
		return selectedGroupe;
	}

	public void setSelectedGroupe(String selectedGroupe) {
		this.selectedGroupe = selectedGroupe;
	}

	public UIData getTableEns() {
		return tableEns;
	}

	public void setTableEns(UIData tableEns) {
		this.tableEns = tableEns;
	}

}
