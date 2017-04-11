package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fitrank.modelo.Amizade;
import br.com.fitrank.persistencia.AmizadeDAO;
import br.com.fitrank.persistencia.PessoaDAO;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.DateConversor;

public class AmizadeServico {
	
	private AmizadeDAO amizadeDAO = new AmizadeDAO();
	private PessoaDAO pessoaDAO = new PessoaDAO();
	private Amizade amizade;
	
	public Amizade adicionaAmizadeServico(String idPessoa, String idAmigo) {
		
		amizade = new Amizade();
		
		amizade.setDataAmizade(DateConversor.getJavaSqlTimestamp(new Date()));
		
	    try {
	    	this.amizadeDAO = new AmizadeDAO();
	    	//cada amizade e registrada duas vezes no banco, uma para cada usuario
	    	//primeiro registro
	    	amizade.setIdPessoa(idAmigo);
			amizade.setId_amigo(idPessoa);
			amizadeDAO.adicionaAmizade(amizade);
	    	
			this.amizadeDAO = new AmizadeDAO();
			//segundo registro
	    	amizade.setIdPessoa(idPessoa);
			amizade.setId_amigo(idAmigo);
	    	
			return amizadeDAO.adicionaAmizade(amizade);
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	    
	}
	
	public ArrayList<Amizade> listaAmizades(String idPessoa){
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
	    	amizade.setIdPessoa(idPessoa);
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

	public List<Amizade> listaAmizadesMenosAtualizadas(String idPessoa, int limiteAtualizacaoUsuarios, String modalidade) {
		ArrayList<Amizade> listaAmigosEntrada;
		ArrayList<Amizade> listaAmigosSaida;
		
		this.pessoaDAO = new PessoaDAO();
		String colunaModalidade = "";
		
		colunaModalidade = setColunaModalidade(modalidade);
		
		try {
			listaAmigosEntrada = listaAmizades(idPessoa);
			
			listaAmigosSaida = (ArrayList<Amizade>) pessoaDAO.listaAmigosMenosAtualizados(listaAmigosEntrada, limiteAtualizacaoUsuarios, colunaModalidade);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return listaAmigosSaida;
	}

	private String setColunaModalidade(String modalidade) {
		switch(modalidade){
			case ConstantesFitRank.MODALIDADE_CAMINHADA:
				return "data_ultima_atualizacao_walks";
	
			case ConstantesFitRank.MODALIDADE_CORRIDA:
				return "data_ultima_atualizacao_runs" ;
	
			case ConstantesFitRank.MODALIDADE_BICICLETA:
				return "data_ultima_atualizacao_bikes";
		
			default:
				return modalidade;
		}
	}
}
