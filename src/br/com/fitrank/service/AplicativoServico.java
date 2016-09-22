package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Aplicativo;
import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.modelo.Ranking;
import br.com.fitrank.modelo.apresentacao.AplicativoTela;
import br.com.fitrank.modelo.apresentacao.RankingPessoaTela;
import br.com.fitrank.persistencia.AplicativoDAO;


public class AplicativoServico {
	
	private AplicativoDAO aplicativoDAO;

	public Aplicativo adicionaAplicativoServico(Aplicativo aplicativo){
	
		this.aplicativoDAO = new AplicativoDAO();
		
	    try {
	    	return aplicativoDAO.adicionaAplicativo(aplicativo);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	    
	}
	public ArrayList<Aplicativo> adicionaAplicativosServico(ArrayList<Aplicativo> aplicativos){
		
			this.aplicativoDAO = new AplicativoDAO();
				
		    try {
				return aplicativoDAO.adicionaAplicativos(aplicativos);
			} catch (SQLException e) {
				
				e.printStackTrace();
				return null;
			}
		    
		}
	
	public Aplicativo leAplicativoServico(String id_aplicativo){
		
		this.aplicativoDAO = new AplicativoDAO();
		
	    try {
			return aplicativoDAO.leAplicativo(id_aplicativo);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Aplicativo> leListaAplicativosServico(ArrayList<Aplicativo> aplicativos){
		
		this.aplicativoDAO = new AplicativoDAO();
		
	    try {
			return aplicativoDAO.leListaAplicativos(aplicativos);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AplicativoTela> listaAplicativosUsuarioNoRanking(Configuracao configuracao, RankingPessoaTela rankingPessoaTela, Ranking ranking) {
		this.aplicativoDAO = new AplicativoDAO();
		
	    try {
			return aplicativoDAO.listaAplicativosUsuarioNoRanking(configuracao, rankingPessoaTela, ranking);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
