package TestControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;





import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Admin;


@ManagedBean(name ="controllerAdmin")
@SessionScoped
public class ControllerAdmin {

	@EJB
	Services serviceAdmin;

	
	EntityManager em;
	
	private List<Admin> listeAdminis;
	private Admin admin;
	private UIData tabletud;
	private String login;
	private String password;
	private String AdminSelected;

	public ControllerAdmin() {
		listeAdminis = new ArrayList<Admin>();
		admin = new Admin();
	}

	/**************************** Methodes **********************************/


	public String ajouterAdminweb() {
		serviceAdmin.addAdmin(admin);
		admin = new Admin();
		listeAdminis = serviceAdmin.getAllAdmin();
//		admin = new Administrateur();
		return "ajt";

	}
	
	public void selectAdminListener(AjaxBehaviorEvent  event){
		System.out.println("Selected : "+AdminSelected);
	}
	
	public String tNomAdmin()
	{
		String nom =  serviceAdmin.GetNomAdmin(AdminSelected);
		return nom ;
		
	}

	
	public String authentif() {

		System.out.println(serviceAdmin.authentifier(AdminSelected, password));
		 String msg= serviceAdmin.authentifier(AdminSelected, password);
		serviceAdmin.authentifier(AdminSelected, password);
		if (msg.equals("yess")   ) {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("islogged", true);
		}
     return msg;

	}
	public String seDeconnecter() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.removeAttribute("islogged");
		String navigationTo = "Acceuil.xhtml?faces-redirect=true";
		return navigationTo;
	}
   

	
	public void editEvent(int id) {	
		System.out.print(id);
		admin = serviceAdmin.getAdminByID(id);	
	}
	
	
	 public void edition(ActionEvent actionEvent) {
		 serviceAdmin.updateAdmin(admin);
	    }


	public String deleteAdminweb (Admin admin) {
		int id = admin.getId();
		serviceAdmin.deleteAdmin(id);
		listeAdminis = serviceAdmin.getAllAdmin();
		return "supp";
	}

	public Services getServiceAdmin() {
		return serviceAdmin;
	}

	public void setServiceAdmin(Services serviceAdmin) {
		this.serviceAdmin = serviceAdmin;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public List<Admin> getListeAdminis() {
		listeAdminis = serviceAdmin.getAllAdmin();
		return listeAdminis;
	}

	public void setListeAdminis(List<Admin> listeAdminis) {
		this.listeAdminis = listeAdminis;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public UIData getTabletud() {
		return tabletud;
	}

	public void setTabletud(UIData tabletud) {
		this.tabletud = tabletud;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminSelected() {
		return AdminSelected;
	}

	public void setAdminSelected(String adminSelected) {
		AdminSelected = adminSelected;
	}


	
}
