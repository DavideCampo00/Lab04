package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;

	public Model() {
		this.corsoDAO =new CorsoDAO();
		this.studenteDAO=new StudenteDAO();
	}
	public List<Corso> getTuttiICorsi(){
		return this.corsoDAO.getTuttiICorsi();
	}
	public Studente getStudenteByMatricola(Integer matricola){
		return this.studenteDAO.getStudenteByMatricola(matricola);
	}
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return this.corsoDAO.getStudentiIscrittiAlCorso(corso);
	}
	public Corso getCorso(String codins) {
		return this.corsoDAO.getCorso(codins);
	}
	public List<Studente> getTuttiGliStudenti(){
		return this.studenteDAO.getTuttiGliStudenti();
	}
	public List<Corso> getCorsiFrequentatiByMatricola(Integer matricola){
		return this.studenteDAO.getCorsiFrequentatiByMatricola(matricola);
	}
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return this.corsoDAO.inscriviStudenteACorso(studente, corso);
	}
	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {
		return this.studenteDAO.isStudenteIscrittoACorso(studente, corso);
	}

}
