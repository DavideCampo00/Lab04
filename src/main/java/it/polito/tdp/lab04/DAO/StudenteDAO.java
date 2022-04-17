package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	public Studente getStudenteByMatricola(Integer matricola) {
		final String sql = "SELECT s.matricola,s.cognome,s.nome,s.CDS "
				+ "FROM studente s "
				+ "WHERE s.matricola=?";
		
		Studente studente=null; 

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				studente=new Studente(rs.getInt("matricola"),rs.getString("cognome"),rs.getString("nome"), rs.getString("CDS"));

			
				
					
			}
			st.close();
			rs.close();
			conn.close();
			
			
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore studenteDAO");
			
			
		}
		return studente;
	}
	
	public List<Studente> getTuttiGliStudenti() {

		final String sql = "SELECT * FROM studente";

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Integer matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String CDS=rs.getString("CDS");

				studenti.add(new Studente(matricola,nome,cognome,CDS));
				
			}

			conn.close();
			
			return studenti;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<Corso> getCorsiFrequentatiByMatricola(Integer matricola) {
		final String sql = "SELECT c.codins, c.crediti,c.nome,c.pd "
				+ "FROM corso c, iscrizione i "
				+ "WHERE i.matricola=? AND c.codins=i.codins";
		
		List<Corso> elencoCorsi=new ArrayList<Corso>();	 

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				elencoCorsi.add(new Corso(codins,numeroCrediti,nome,periodoDidattico));
				
					
			}
			st.close();
			rs.close();
			conn.close();
			
			return elencoCorsi;
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {

		final String sql = "SELECT * FROM iscrizione where codins=? and matricola=?";
		boolean returnValue = false;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());

			ResultSet rs = st.executeQuery();

			if (rs.next())
				returnValue = true;

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

		return returnValue;
	}

}
