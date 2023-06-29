package it.polito.tdp.PremierLeague.model;

public class Coppia {

	private Match match1;
	private Match match2;
	
	private int peso;

	public Coppia(Match match1, Match match2, int peso) {
		super();
		this.match1 = match1;
		this.match2 = match2;
		this.peso = peso;
	}

	public Match getMatch1() {
		return match1;
	}

	public Match getMatch2() {
		return match2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return match1 + " - " + match2 + " (" + peso + ")";
	}
	
	
	
	
}
