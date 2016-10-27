package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.PostFitness;
import br.com.fitrank.persistencia.PostFitnessDAO;

public class PostFitnessServico {
	
	private PostFitnessDAO postFitnessDAO;
	
	public PostFitness adicionaPostFitnessServico(PostFitness postFitness) {
		
		this.postFitnessDAO = new PostFitnessDAO();
		
	    try {
	    	return postFitnessDAO.adicionaPostFitness(postFitness);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean adicionaListaPostFitnessServico(ArrayList<PostFitness> listaPostFitness) {
		
		this.postFitnessDAO = new PostFitnessDAO();
		
		try {
			return postFitnessDAO.adicionaListaPostFitness(listaPostFitness);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<PostFitness> lePostFitnessPorIdPessoa(String idPessoa) {
		this.postFitnessDAO = new PostFitnessDAO();
		
		try {
	    	return postFitnessDAO.lePostFitnessPorIdPessoa(idPessoa);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public String leModalidadeComMaisAtividades(String idPessoa) {
		
		try {
	    	return postFitnessDAO.leModalidadeComMaisAtividades(idPessoa);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public String obtemDataPostMaisRecente(String idPessoa) {
		this.postFitnessDAO = new PostFitnessDAO();
		
		try {
	    	return postFitnessDAO.obtemDataPostMaisRecente(idPessoa);
	    	
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}