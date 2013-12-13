package tn.esprit.edt.gestion;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import tn.esprit.edt.persistance.Pool;

/**
 * Session Bean implementation class NbPrestRestantGes
 */
@Stateless
@LocalBean
public class NbPrestRestant implements NbPrestRestantLocal {

	 private Pool pool;
	    private int nbPrestRestant;

	    public NbPrestRestant(Pool pool, Long nbPrestTotal) {
	        this.pool = pool;
	        this.nbPrestRestant = pool.getNbPrestMaxParPlage() - nbPrestTotal.intValue();
	    }

	    public NbPrestRestant() {
			super();
			// TODO Auto-generated constructor stub
		}

		public int getNbPrestRestant() {
	        return nbPrestRestant;
	    }

	    public Pool getPool() {
	        return pool;

}}
