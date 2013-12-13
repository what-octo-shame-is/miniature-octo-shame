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

import tn.esprit.edt.persistance.Enseignant;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "controllerPDFEns")
@SessionScoped
public class ControllerPDFEns {

	@EJB
	Services service;
	private List<Enseignant> listeOfClients;
	private List<Enseignant> list;

	public List<Enseignant> getListeOfClients() {

		listeOfClients = new ArrayList<Enseignant>(service.getListEns());
		return listeOfClients;
	}

	public List<Enseignant> listEns() {
		return list = new ArrayList<Enseignant>(service.getListEns());
	}

	public void setListeOfClients(List<Enseignant> listeOfClients) {
		this.listeOfClients = listeOfClients;
	}

	JasperPrint jasperPrint;

	public void init() throws JRException, NamingException {
		System.out.println(listEns().size());
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
//				listEns());
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(service.getListEns());
		jasperPrint = JasperFillManager.fillReport("C:\\IREPORT\\report7.jasper",
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
