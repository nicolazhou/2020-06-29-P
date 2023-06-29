package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	
	private List<Mese> mesi;
	
	private Graph<Match, DefaultWeightedEdge> grafo;
	
	private List<Match> vertici;
	private Map<Integer, Match> matchIdMap;
	
	
	// Ricorsione
	private List<Match> soluzione;
	private int pesoMax;
	
	
	public Model() {
		
		this.dao = new PremierLeagueDAO();
		
		this.mesi = new ArrayList<>();
		
	}
	
	
	public void creaGrafo(int min, int mese) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
	
		this.vertici = new ArrayList<>(this.dao.getVertici(mese));
		this.matchIdMap = new HashMap<>();
		
		for(Match m : this.vertici) {
			
			this.matchIdMap.put(m.getMatchID(), m);
			
		}
		
		
		// Vertici:
		Graphs.addAllVertices(this.grafo, this.vertici);

		
		
		// Archi
		for(Match m1 : this.grafo.vertexSet()) {
			for(Match m2 : this.grafo.vertexSet()) {
				
				if(!m1.equals(m2)) {
					
					DefaultWeightedEdge e = this.grafo.getEdge(m1, m2);
					
					if(e == null) {
						
						
						int peso = this.dao.getPeso(min, m1, m2);
						
						if(peso > 0) {
							
							Graphs.addEdgeWithVertices(this.grafo, m1, m2, peso);
							
						}
						
					}

					
				}
				
			}
			
			
		}
		
	}
	
	
	public int getNNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public boolean isGrafoLoaded() {
		
		if(this.grafo == null)
			return false;
		
		return true;
	}
	
	
	public List<Coppia> getConnessioneMax() {
		
		List<Coppia> result = new ArrayList<>();
		
		int max = -1;
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			
			int peso = (int) this.grafo.getEdgeWeight(e);
			
			
			if(peso > max) {
				
				Coppia c = new Coppia(this.grafo.getEdgeTarget(e), this.grafo.getEdgeSource(e), peso);
				
				result = new ArrayList<>();
				
				result.add(c);
				
				max = peso;
				
				
			} else if(peso == max) {
				
				Coppia c = new Coppia(this.grafo.getEdgeTarget(e), this.grafo.getEdgeSource(e), peso);
				
				result.add(c);
			}
			
		}
		
		return result;
		
		
	}
	
	
	public List<Mese> getMesi() {
		
		List<Mese> result = new ArrayList<>();
		
		
		result.add(new Mese("Gennaio", 1));
		result.add(new Mese("Febbraio", 2));
		result.add(new Mese("Marzo", 3));
		result.add(new Mese("Aprile", 4));
		result.add(new Mese("Maggio", 5));
		result.add(new Mese("Giugno", 6));
		result.add(new Mese("Luglio", 7));
		result.add(new Mese("Agosto", 8));
		result.add(new Mese("Settembre", 9));
		result.add(new Mese("Ottobre", 10));
		result.add(new Mese("Novembre", 11));
		result.add(new Mese("Dicembre", 12));
		
		return result;
		
	}
	
	public List<Match> getVertici() {
		
		return this.vertici;
		
	}
	
	
	
	public List<Match> trovaCammino(Match partenza, Match arrivo) {
		
		// Inizializzazione
		List<Match> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		this.soluzione = new ArrayList<>();
		
		
		// Ricorsione
		cerca(parziale, arrivo);
		
		
		System.out.println("soluzione: " + soluzione);
		
		
		return this.soluzione;
	}


	private void cerca(List<Match> parziale, Match arrivo) {
		// TODO Auto-generated method stub
		
		Match ultimo = parziale.get(parziale.size()-1);
		// Condizione di terminazione
		if(ultimo.equals(arrivo)) {
			
			// Controllo se è di peso massimo
			if(sommaPesi(parziale) > sommaPesi(this.soluzione))
				this.soluzione = new ArrayList<>(parziale);
			
			return;
			
		}
		
		
		// Continua
		List<Match> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		for(Match m : vicini) {
			
			if(!parziale.contains(m)) { // Non contiene lo stesso match
				
				int id1 = m.getTeamHomeID();
				int id2 = m.getTeamAwayID();
				
				int uId1 = ultimo.getTeamHomeID();
				int uId2 = ultimo.getTeamAwayID();
				
				if((id1 != uId1 && id1 != uId2) || (id2 != uId1 && id2 != uId2)) { // Non squadre arco di prima
					
					parziale.add(m);
					
					cerca(parziale, arrivo);
					
					parziale.remove(parziale.size()-1);
					
				}
				
				
				
			}
			
			
		}
		
		
	}


	public int sommaPesi(List<Match> parziale) {
		// TODO Auto-generated method stub
		
		int somma = 0;
		
		for(int i = 0; i < parziale.size()-1; i++) {
			
			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i), parziale.get(i+1));
			
			int peso = (int) this.grafo.getEdgeWeight(e);
			
			somma += peso;
			
			
		}
		
		return somma;
	}
	
	
}
