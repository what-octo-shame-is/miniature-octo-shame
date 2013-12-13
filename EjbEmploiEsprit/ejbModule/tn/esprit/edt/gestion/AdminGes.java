package tn.esprit.edt.gestion;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tn.esprit.edt.persistance.Admin;
import tn.esprit.edt.persistance.Groupe;




/**
 * Session Bean implementation class AdminGes
 */
@Stateless
@LocalBean
public class AdminGes implements AdminGesLocal {

	@PersistenceContext(unitName = "EDT_EJB")
	EntityManager em;

	@Override
	public void addAdmin(Admin m) {
		em.persist(m);
	}

	@Override
	public void updateAdmin(Admin m) {
		em.merge(m);
		
	}

	@Override
	public void deleteAdmin(int ID) {
		em.remove(em.find(Admin.class, ID));
		
	}

	@Override
	public Admin getAdminByID(int ID) {
		return em.find(Admin.class, ID);
	}

	@Override
	public List<Admin> getAllAdmin() {
		return em.createNamedQuery("findAllAdmin").getResultList();
	}
	
	public String authentifier(String login,String password) {
		String a="";
		List<Admin> adm=em.createNativeQuery("select * from ESP_ADMIN u where u.LOGIN='"+login+"' and u.PASSWORD='"+password+"'").getResultList();

		if(adm.size()==1){
		a="yess";
		}
		else{
			 a="noo";
		}

		return a;

		}
	public String GetNomAdmin(String Code){
		try {
			String strQ = "SELECT p FROM Admin AS p where p.login='"
					+ Code + "'  ";

			Admin set = (Admin) em.createQuery(strQ).getSingleResult();
			return set.getNomAdmin();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
}
