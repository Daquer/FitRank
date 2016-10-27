package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.modelo.Localizacao;
import br.com.fitrank.persistencia.LocalizacaoDAO;

public class LocalizacaoServico {
	private LocalizacaoDAO localizacaoDAO;
	
	public boolean adicionaLocalizacoes(List<Localizacao> listaLocalizacao) {

		this.localizacaoDAO = new LocalizacaoDAO();
 
		try {
			return localizacaoDAO.adicionaLocalizacoes(listaLocalizacao);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Localizacao> leLocalizacoesPorIdsCourse(List<Course> courses) {
		
		this.localizacaoDAO = new LocalizacaoDAO();
		 
		try {
			if( courses.isEmpty()){
				return new ArrayList<Localizacao>();
			}
			
			return localizacaoDAO.leLocalizacoesPorIdsCourse(courses);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
