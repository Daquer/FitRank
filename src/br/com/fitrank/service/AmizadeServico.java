package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fitrank.modelo.Amizade;
import br.com.fitrank.persistencia.AmizadeDAO;

public class AmizadeServico {
	
	private AmizadeDAO amizadeDAO = new AmizadeDAO();
	private Amizade amizade;
	
	public Amizade adicionaAmizadeServico(String idPessoa, String idAmigo) {
		
		amizade = new Amizade();
		
		
		amizade.setData_amizade(new Date());
		
	    try {
	    	this.amizadeDAO = new AmizadeDAO();
	    	//cada amizade e registrada duas vezes no banco, uma para cada usuario
	    	//primeiro registro
	    	amizade.setId_pessoa(idAmigo);
			amizade.setId_amigo(idPessoa);
			amizadeDAO.adicionaAmizade(amizade);
	    	
			this.amizadeDAO = new AmizadeDAO();
			//segundo registro
	    	amizade.setId_pessoa(idPessoa);
			amizade.setId_amigo(idAmigo);
	    	
			return amizadeDAO.adicionaAmizade(amizade);
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	    
	}
	
	public List<Amizade> listaAmizades(String idPessoa){
		ArrayList<Amizade> listaAmigos;
		try {
			listaAmigos = (ArrayList<Amizade>) amizadeDAO.listaAmizades(idPessoa);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return listaAmigos;
	}
	
	public Amizade leAmizadeServico(String idPessoa, String idAmigo){
		
		amizade = new Amizade();
		this.amizadeDAO = new AmizadeDAO();
		
	    try {
	    	
			//segundo registro
	    	amizade.setId_pessoa(idPessoa);
			amizade.setId_amigo(idAmigo);
	    	
			return amizadeDAO.leAmizade(amizade);
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

	public boolean desativaAmizade(String idUsuario, String id_amigo) {

		this.amizadeDAO = new AmizadeDAO();
		
		try {
			amizadeDAO.desativaAmizade(idUsuario,id_amigo);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
