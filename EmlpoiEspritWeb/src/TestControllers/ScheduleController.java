package TestControllers;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Prestation;

@ManagedBean("scheduleController")
@SessionScoped
public class ScheduleController  {
	@EJB
	Services serviceAdmin;
	private ScheduleModel eventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	public ScheduleController() {
		
	affecterPrestation();
	}

	private void affecterPrestation() {
		List<Prestation> listPrestation = new ArrayList<Prestation>(
				serviceAdmin.getListPrestation());

		for (Prestation p : listPrestation) {
			eventModel.addEvent(new DefaultScheduleEvent(p.getModule()
					.getDesignation(), numeroJour(p.getCreneau().getNumero()),
					numeroJour(p.getCreneau().getNumero())));
		}
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
		return eventModel;
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

	private Date numeroJour(Integer numero) {
		int numeroSeance = numero % 4;
		int position = 0;
		Calendar maDate = new java.util.GregorianCalendar();
		maDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		switch (numeroSeance) {
		case 0:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 9);
			position = -7;
			break;
		case 1:
			maDate.set(Calendar.AM_PM, Calendar.AM);
			maDate.set(Calendar.HOUR, 10);
			position = 0;
			break;
		case 2:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 2);
			position = 7;
			break;
		case 3:
			maDate.set(Calendar.AM_PM, Calendar.PM);
			maDate.set(Calendar.HOUR, 3);
			position = 14;
			break;

		default:
			break;
		}
		maDate.add(Calendar.DATE, numeroSeance == 0 ? (numero / 4) - 1
				+ position : (numero / 4) + position);
		return maDate.getTime();
	}

	public Services getServiceAdmin() {
		return serviceAdmin;
	}

	public void setServiceAdmin(Services serviceAdmin) {
		this.serviceAdmin = serviceAdmin;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

}