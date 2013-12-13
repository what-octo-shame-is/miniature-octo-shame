package tn.esprit.edt.gestion;

import java.util.List;

import javax.ejb.Local;

import tn.esprit.edt.persistance.Admin;



@Local
public interface AdminGesLocal {
	public void addAdmin(Admin m);

	public void updateAdmin(Admin m);

	public void deleteAdmin(int ID);

	public Admin getAdminByID(int ID);

	public List<Admin> getAllAdmin();
	public String authentifier(String login,String password) ;
	public String GetNomAdmin(String Code);

}
