package it.polito.tdp.anagrammi;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.anagrammi.DAO.AnagrammiDAO;
import it.polito.tdp.anagrammi.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtErrati;

    @FXML
    private TextArea txtCorretti;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnAnagrammi;

    @FXML
    private TextField txtAnagrammi;

    @FXML
    void doAnagrammi(ActionEvent event) {
    	txtCorretti.clear();
    	txtErrati.clear();
    	
    	Set[] anagrammi = null;
    	String parola = txtAnagrammi.getText();
    	try {
			anagrammi = modello.getAnagrammi(parola);
		} catch (Exception e) {
			txtCorretti.setText("Errore: "+e.getMessage());
			//e.printStackTrace();
			return;
		}
    	
    	try {
    		txtCorretti.setText("Trovati "+anagrammi[0].size()+" anagrammi esistenti:\n"+stampaSet(anagrammi[0]));
    	}
    	catch (Exception e) {
    		txtCorretti.setText("Nessun anagramma corretto trovato!");
		}
    	try {
    		txtErrati.setText("Trovati "+anagrammi[1].size()+" anagrammi non esistenti:\n"+stampaSet(anagrammi[1]));
    	}
    	catch (NullPointerException e) {
    		txtErrati.setText("Parola troppo lunga:\nImpossibile calcolare anagrammi errati!\n("+(modello.getNumeroAnagrammi(parola)[1]-anagrammi[0].size())/*+"/"+modello.getNumeroAnagrammi(parola)[0]*/+")");
		}
    	catch (Exception e) {
    		txtErrati.setText("Nessun anagramma errato trovato!");
		}
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtAnagrammi.clear();
    	txtCorretti.clear();
    	txtErrati.clear();
    }

    private String stampaSet (Set <String> elenco) throws Exception {
    	if (elenco.isEmpty())
    		throw new Exception();
    	String ritorno = "";
    	for (String s : elenco) {
    		ritorno+= s+"\n";
    	}
    	return ritorno;
    }
    
    @FXML
    void initialize() {
        assert txtErrati != null : "fx:id=\"txtErrati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorretti != null : "fx:id=\"txtCorretti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnagrammi != null : "fx:id=\"btnAnagrammi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnagrammi != null : "fx:id=\"txtAnagrammi\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    Model modello;
	public void setModel (Model modello) {
		this.modello = modello;		
	}
}
