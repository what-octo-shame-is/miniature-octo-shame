package TestControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Creneau;
import tn.esprit.edt.persistance.Enseignant;
import tn.esprit.edt.persistance.Module;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "controllerPDFcr")
@SessionScoped
public class ControllerPDFcr {

	@EJB
	Services service;
	private List<Creneau> listeOfClients;
	private List<Creneau> list;

	public List<Creneau> getListeOfClients() {

		listeOfClients = new ArrayList<Creneau>(service.getAllCreneaux());
		return listeOfClients;
	}

	public List<Creneau> listEns() {
		return list = new ArrayList<Creneau>(service.getAllCreneaux());
	}

	public void setListeOfClients(List<Creneau> listeOfClients) {
		this.listeOfClients = listeOfClients;
	}

	JasperPrint jasperPrint;

	public void init() throws JRException, NamingException {
		System.out.println(listEns().size());
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
//				listEns());
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(service.getAllCreneaux());
		jasperPrint = JasperFillManager.fillReport("C:\\IREPORT\\report4.jasper",
				new HashMap(), beanCollectionDataSource);
	}

	public String PDF() throws JRException, IOException, NamingException {
		init();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=report.pdf");
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);

		return "pdf";
	}

}
