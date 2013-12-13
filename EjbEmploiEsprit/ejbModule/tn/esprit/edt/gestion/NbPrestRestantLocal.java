package tn.esprit.edt.gestion;

import javax.ejb.Local;

import tn.esprit.edt.persistance.Pool;

@Local
public interface NbPrestRestantLocal {
	
	public int getNbPrestRestant();

	
	public Pool getPool();
	

}
