package TestControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Prestation;

@ManagedBean(name = "controllerSchEns", eager = true)
@SessionScoped
public class ControllerSchEns implements Serializable {
	@ManagedProperty(value = "#{controllerEns}")
	private ControllerEnseignant controllerEns;
	private String selectedEns;
	private ScheduleModel eventModel;
	@EJB
	private Services services;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private List<Enseignant> listENS;
	private Enseignant nouvEns = new Enseignant();
	private List<Enseignant> enseigants;
	private String login;
	private String password;
	private List<Enseignant> listeEns = new ArrayList<Enseignant>();
	private Enseignant enseignant = new Enseignant();

	private Enseignant suppEns2 = new Enseignant();
	private List<Prestation> listPrestation = new ArrayList<Prestation>();

	public ControllerSchEns() {
		System.out.println("Creating ControllerSchEns");
           listeEns = new ArrayList<Enseignant>();
	}
	public String NomEnseignant() {
		return services.GetNomEnseignant(selectedEns);
	}
	

	public String IDEnseignantByLogin() {
		return services.GetNomEnseignantByID(selectedEns);
	}
	
	public String authentif() {

		System.out.println(services.authentifierEns(selectedEns, password));
		 String msg= services.authentifierEns(selectedEns, password);
		 services.authentifierEns(selectedEns, password);
		if (msg.equals("yesEns")) {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("islogged", true);
		}
     return msg;

	}

	public ControllerSchEns(Services services) {
		this.services = services;
		listeEns = new ArrayList<Enseignant>(services.getListEnseigants());
		setListPrestation(new ArrayList<Prestation>());
	}

	public void selectEnsListener(AjaxBehaviorEvent event) {
		System.out.println("Selected : " + selectedEns);
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
		return affecterPrestation(services);
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

	public ScheduleModel affecterPrestation(Services serviceAdmin) {
		if (controllerEns == null)
			System.err.println("Null Enseignant Controller");
		eventModel = new DefaultScheduleModel();

		List<Prestation> listPrestation = new ArrayList<Prestation>(
				serviceAdmin.getPrestationByEnseignant(IDEnseignantByLogin()));

		for (Prestation p : listPrestation) {
			eventModel.addEvent(new DefaultScheduleEvent(p.getModule()
					.getDesignation()
					+ "\n"
					+ p.getEnseignant().getNom()
					+ "\n"
					+ p.getCreneau()
					+ "\n"
					+ p.getCreneau().getNumHeure(), numeroJour(p.getCreneau()
					.getNumero()), numeroJour(p.getCreneau().getNumero())));

		}
		return eventModel;
	}

	/********************* getter & setter ****************/

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
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

	public List<Enseignant> getListeEns() {
		
		return listeEns= new ArrayList<Enseignant>(services.getListEnseigants());
	}

	public void setListeEns(List<Enseignant> listeEns) {
		this.listeEns = listeEns;
	}

	public Enseignant getSuppEns2() {
		return suppEns2;
	}

	public void setSuppEns2(Enseignant suppEns2) {
		this.suppEns2 = suppEns2;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public List<Prestation> getListPrestation() {
		listPrestation = new ArrayList<Prestation>(services.getListPrestation());
		return listPrestation;
	}

	public void setListPrestation(List<Prestation> listPrestation) {
		this.listPrestation = listPrestation;
	}

	public String getSelectedEns() {
		return selectedEns;
	}

	public void setSelectedEns(String selectedEns) {
		this.selectedEns = selectedEns;
	}

	public ControllerEnseignant getControllerEnseignant() {
		return controllerEns;
	}

	public void setControllerEnseignant(
			ControllerEnseignant controllerEnseignant) {
		this.controllerEns = controllerEnseignant;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public ControllerEnseignant getControllerEns() {
		return controllerEns;
	}
	public void setControllerEns(ControllerEnseignant controllerEns) {
		this.controllerEns = controllerEns;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Enseignant getNouvEns() {
		return nouvEns;
	}
	public void setNouvEns(Enseignant nouvEns) {
		this.nouvEns = nouvEns;
	}
	
}
