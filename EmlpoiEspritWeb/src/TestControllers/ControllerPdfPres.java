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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.persistance.Prestation;

@ManagedBean(name = "controllerPDFpres")
@SessionScoped
public class ControllerPdfPres {

	@EJB
	Services service;
	private List<Prestation> listeOfClients;
	private List<Prestation> list;

	public List<Prestation> getListeOfClients() {

		listeOfClients = new ArrayList<Prestation>(service.getListPrestation());
		return listeOfClients;
	}

	public List<Prestation> listEns() {
		return list = new ArrayList<Prestation>(service.getListPrestation());
	}

	public void setListeOfClients(List<Prestation> listeOfClients) {
		this.listeOfClients = listeOfClients;
	}

	JasperPrint jasperPrint;

	public void init() throws JRException, NamingException {
		System.out.println(listEns().size());
		// JRBeanCollectionDataSource beanCollectionDataSource = new
		// JRBeanCollectionDataSource(
		// listEns());
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				service.getListPrestation());
		jasperPrint = JasperFillManager.fillReport(
				"C:\\IREPORT\\report9.jasper", new HashMap(),
				beanCollectionDataSource);
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
