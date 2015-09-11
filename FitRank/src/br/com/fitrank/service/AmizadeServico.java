package br.com.fitrank.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.restfb.types.User;

import br.com.fitrank.modelo.Amizade;
import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.persistencia.AmizadeDAO;
import br.com.fitrank.persistencia.PessoaDAO;
import br.com.fitrank.util.ConstantesFitRank;

public class AmizadeServico {
	
	private AmizadeDAO amizadeDAO;
	private Amizade amizade;
	
	public Amizade adicionaAmizadeServico(String idPessoa, String idAmigo) {
		
		amizade = new Amizade();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat(ConstantesFitRank.FORMATO_DATA);
		String formattedDate = formatter.format(new Date());
		amizade.setData_amizade(formattedDate);
		
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
}
