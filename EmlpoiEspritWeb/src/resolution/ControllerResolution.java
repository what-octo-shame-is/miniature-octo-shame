package resolution;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
@ManagedBean
@SessionScoped
public class ControllerResolution {
	public void addError(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Sample error message",
								"PrimeFaces makes no mistakes"));
	}
}
