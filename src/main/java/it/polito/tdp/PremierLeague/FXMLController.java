/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Coppia;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Mese;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Mese> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	
    	List<Coppia> coppie = this.model.getConnessioneMax();
    	
    	this.txtResult.appendText("Coppie con connessione massima: \n\n");
    	
    	for(Coppia coppia : coppie) {
    		
    		this.txtResult.appendText(coppia + "\n");
    		
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	String input = this.txtMinuti.getText();
    	
    	Integer min = 0;
    	
    	try {
    		
    		min = Integer.parseInt(input);
    		
    	} catch(NumberFormatException e) {
    		
    		this.txtResult.appendText("Inserisci un valore numerico ai minuti");
    		return;
    		
    	}
    	
    	Mese mese = this.cmbMese.getValue();
    	
    	if(mese == null) {
    		
    		this.txtResult.appendText("Scegli un mese!");
    		return;
    		
    	}
    	
    	
    	this.model.creaGrafo(min, mese.getMeseN());
    	
    	
    	this.txtResult.appendText("Grafo creato! \n");
    	this.txtResult.appendText("# Vertici: " + this.model.getNNodes() + "\n");
    	this.txtResult.appendText("# Archi: " + this.model.getNArchi() + "\n");
        
    	this.cmbM1.getItems().clear();
    	this.cmbM1.getItems().addAll(this.model.getVertici());
    	
    	this.cmbM2.getItems().clear();
    	this.cmbM2.getItems().addAll(this.model.getVertici());
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	Match match1 = this.cmbM1.getValue();
    	
    	if(match1 == null) {
    		
    		this.txtResult.appendText("Scegli match 1!");
    		return;
    		
    	}
    	
    	Match match2 = this.cmbM2.getValue();
    	
    	if(match2 == null) {
    		
    		this.txtResult.appendText("Scegli match 2!");
    		return;
    		
    	}
    	
    	List<Match> cammino = this.model.trovaCammino(match1, match2);
    	
    	
    	this.txtResult.appendText("Cammino di peso: " + this.model.sommaPesi(cammino) + "\n\n") ;
    	
    	for(Match m : cammino) {
    		
    		this.txtResult.appendText(m + "\n");
    		
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
  
    	
    	this.cmbMese.getItems().addAll(this.model.getMesi());
    	
    }
    
    
}
