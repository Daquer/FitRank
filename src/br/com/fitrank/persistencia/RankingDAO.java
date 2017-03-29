package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fitrank.modelo.Ranking;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;

import com.mysql.jdbc.Statement;


public class RankingDAO {
	
	private Connection conexao;

	public RankingDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}
	
	public Ranking adicionaRanking(Ranking ranking) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO ranking ("
				+ "id_configuracao, "
				+ "data_ranking"
				+ ") VALUES (?, ?)";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

			int i = 0;
			
			preparedStatement.setInt(++i, ranking.getId_configuracao());
			preparedStatement.setTimestamp(++i, ranking.getData_ranking()); 

			// execute insert SQL statement
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			
			int idRanking = ConstantesFitRank.INT_RESULTADO_INVALIDO;
			if(rs.next()){
				idRanking = rs.getInt(1);
			}
			ranking.setId_ranking(idRanking);

		} catch (SQLException e) {

			Logger.insertLog("adicionaRanking | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return ranking;
	}

	public Ranking atualizaRanking(Ranking ranking) throws SQLException {
	
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
	
		String insertTableSQL = "UPDATE ranking set "
				+ "id_configuracao= ?, "
				+ "data_ranking= ?"
				+ "where id_ranking = ?";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
	
			int i = 0;
			
			preparedStatement.setInt(++i, ranking.getId_configuracao());
			preparedStatement.setTimestamp(++i, ranking.getData_ranking());
			preparedStatement.setInt(++i, ranking.getId_ranking());
			
	
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
	
		} catch (SQLException e) {
	
			Logger.insertLog("atualizaRanking | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return ranking;
	}
	
	public Ranking leRanking(int idRanking) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Ranking ranking = new Ranking();
	
		String selectTableSQL = "SELECT  "
				+ "id_ranking, "
				+ "id_configuracao, "
				+ "data_ranking "
				+ "FROM ranking "
				+ "where id_ranking = ? ";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			preparedStatement.setInt(1, idRanking);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if ( rs.next() ) {
				
				ranking.setId_ranking(rs.getInt("id_ranking"));
				ranking.setId_configuracao(rs.getInt("id_configuracao"));
				ranking.setData_ranking(rs.getTimestamp("data_ranking"));
				
			}
	
		} catch (SQLException e) {
	
			Logger.insertLog("leRanking | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return ranking;
	}
}
