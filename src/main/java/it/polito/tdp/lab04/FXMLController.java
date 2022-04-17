/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.JobSettings;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbCorsi"
    private ComboBox<Corso> cmbCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="txtCheck"
    private CheckBox txtCheck; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void handleCercaIscrittiCorso(ActionEvent event) {
    	Corso c=cmbCorsi.getValue();
    	if(c==null || c.getCodins().equals("")) {
    		txtRisultato.setText("Nessun corso selezionato");
    		return;
    	}
    		
    	List<Studente> studenti=this.model.getStudentiIscrittiAlCorso(c);
    	for(Studente s:studenti) {
    		txtRisultato.appendText(s+"\n");
    	}
    }

    @FXML
    void handleIscrivi(ActionEvent event) {
    	txtRisultato.clear();

		try {

			if (txtMatricola.getText().isEmpty()) {
				txtRisultato.setText("Inserire una matricola.");
				return;
			}

			if (cmbCorsi.getValue() == null) {
				txtRisultato.setText("Selezionare un corso.");
				return;
			}

			// Prendo la matricola in input
			int matricola = Integer.parseInt(txtMatricola.getText());

			// mi basterebbe la matricola per fare l'iscrizione ma per completezza 
			// posso anche far apparire il Nome e Cognome dello studente nell'interfaccia
			Studente studente = model.getStudenteByMatricola(matricola);
			if (studente == null) {
				txtRisultato.appendText("Nessun risultato: matricola inesistente");
				return;
			}

			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());

			// Ottengo il nome del corso
			Corso corso = cmbCorsi.getValue();

			// Controllo se lo studente è già iscritto al corso
			if (model.isStudenteIscrittoACorso(studente, corso)) {
				txtRisultato.appendText("Studente già iscritto a questo corso");
				return;
			}
			// Prima di passare a rendere il tasto 'Iscrivi' realmente operativo 
			// con l'iscrizione, la versione 'Cerca' avrebbe fatto solo il successivo else.
			//}else {
			//	txtResult.appendText("Studente non iscritto a questo corso");
			//	return;
			//}

			///*
			// Iscrivo lo studente al corso.
			// Controllo che l'inserimento vada a buon fine
			if (!model.inscriviStudenteACorso(studente, corso)) {
				txtRisultato.appendText("Errore durante l'iscrizione al corso");
				return;
			} else {
				txtRisultato.appendText("Studente iscritto al corso!");
			}
			//*/

		} catch (NumberFormatException e) {
			txtRisultato.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtRisultato.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    	
    }

    @FXML
    void handleReset(ActionEvent event) {
    	cmbCorsi.getSelectionModel().clearSelection();
    	txtNome.clear();
    	txtCognome.clear();
    	txtMatricola.clear();
    	txtRisultato.clear();
    	txtCheck.setSelected(false);
    }

    @FXML
    void handlecercaCorsi(ActionEvent event) {
    	String matricola=txtMatricola.getText();
    	Integer matrNumerica;
    	try {
    		matrNumerica=Integer.parseInt(matricola);
    	}catch(NumberFormatException nfe) {
    		System.err.print(nfe);
    		nfe.printStackTrace();
    		txtRisultato.setText("Inserisci una matricola numerica");
    		return;
    		
    	}

        Studente s=this.model.getStudenteByMatricola(matrNumerica);
    	if(s==null) {
    		txtRisultato.setText("Studente non presente nel database");
    		return;
    	}
    	
    	try {
    		List<Corso> corsi=this.model.getCorsiFrequentatiByMatricola(matrNumerica);
    		if(corsi.size()==0) {
    			txtRisultato.setText("Studente non iscritto ad alcun corso");
    			return;
    		}
    		for(Corso c:corsi) {
    			txtRisultato.appendText(c+"\n");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		return;
    	}
    	
    	
    	
    	
    }

    @FXML
    void handleCheck(ActionEvent event) {
    	String matr=txtMatricola.getText();
    	Integer matrNumerica ;
    	try {
    		matrNumerica=Integer.parseInt(matr);
    		  		
    	}catch(NumberFormatException nfe) {
    		System.err.print(nfe);
    		nfe.printStackTrace();
    		txtRisultato.setText("Inserisci una matricola numerica");
    		return;
    		
    	}
    	if(matr.equals("")||matr==null) {
    		txtRisultato.setText("Non hai inserito una matricola"); 
    		return;   		
    	}
    	try {
    		Studente s=this.model.getStudenteByMatricola(matrNumerica);
        	txtNome.setText(s.getNome());
        	txtCognome.setText(s.getCognome());
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		txtRisultato.setText("Studente non presente nel database");
    	}
    	
    	

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	List<Corso> listaCorsi=this.model.getTuttiICorsi();
        for(Corso c:listaCorsi) {
        	cmbCorsi.getItems().add(c);
        }
        cmbCorsi.getItems().add(new Corso("",null,"",null));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCheck != null : "fx:id=\"txtCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    

}
