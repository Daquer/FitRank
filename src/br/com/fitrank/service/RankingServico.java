package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.Date;

import br.com.fitrank.modelo.Ranking;
import br.com.fitrank.persistencia.RankingDAO;
import br.com.fitrank.util.DateConversor;
import br.com.fitrank.util.Logger;

public class RankingServico {
	
	private RankingDAO rankingDAO;
	
	public Ranking adicionaRanking(Ranking ranking) {
		rankingDAO = new RankingDAO();
		ranking.setData_ranking(DateConversor.getJavaSqlTimestamp(new Date()));
		
		try {
			ranking = rankingDAO.adicionaRanking(ranking);
		} catch (SQLException e) {
			Logger.insertLog("Erro ao salvar Ranking.");
			e.printStackTrace();
		}
		
		return ranking;
	}
	
	public Ranking leRanking(int idRanking) {
		rankingDAO = new RankingDAO();
		Ranking ranking;
		
		try {
			ranking = rankingDAO.leRanking(idRanking);
		} catch (SQLException e) {
			Logger.insertLog("Erro ao ler Ranking.");
			Logger.insertLog("id_ranking = " + idRanking);
			e.printStackTrace();
			ranking = new Ranking();
		}
		
		return ranking;
	}

}
