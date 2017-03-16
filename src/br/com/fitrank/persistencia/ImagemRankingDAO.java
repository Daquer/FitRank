package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fitrank.modelo.ImagemRanking;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;


public class ImagemRankingDAO {
	
	private Connection conexao;

	public ImagemRankingDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}
	
	public ImagemRanking adicionaImagemRanking(ImagemRanking imagemRanking) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO imagem_ranking ("
				+ "id_ranking, "
				+ "imagem"
				+ ") VALUES (?, ?)";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			int i = 0;
			
			preparedStatement.setInt(++i, imagemRanking.getId_ranking());
			preparedStatement.setBlob(++i, imagemRanking.getImagem()); 

			// execute insert SQL statement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaImagemRanking | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return imagemRanking;
	}
	
	public ImagemRanking leImagemRanking(int idRanking) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ImagemRanking imagemRanking = null;
	
		String selectTableSQL = "SELECT  "
				+ "id_ranking, "
				+ "imagem "
				+ "FROM imagem_ranking "
				+ "where id_ranking = ? ";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			preparedStatement.setInt(1, idRanking);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				imagemRanking = new ImagemRanking();
				imagemRanking.setId_ranking(rs.getInt("id_ranking"));
				imagemRanking.setImagem(rs.getBlob("imagem"));
				
			}
	
		} catch (SQLException e) {
	
			Logger.insertLog("leImagemRanking | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return imagemRanking;
	}
}
