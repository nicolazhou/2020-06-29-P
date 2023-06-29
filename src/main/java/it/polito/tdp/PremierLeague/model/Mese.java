package it.polito.tdp.PremierLeague.model;

public class Mese {

	private String mese;
	private int meseN;
	
	
	public Mese(String mese, int meseN) {
		super();
		this.mese = mese;
		this.meseN = meseN;
	}


	public String getMese() {
		return mese;
	}


	public void setMese(String mese) {
		this.mese = mese;
	}


	public int getMeseN() {
		return meseN;
	}


	public void setMeseN(int meseN) {
		this.meseN = meseN;
	}


	@Override
	public String toString() {
		return  mese;
	}
	
	
	
}
