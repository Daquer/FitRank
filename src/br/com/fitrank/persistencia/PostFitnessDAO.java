package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fitrank.modelo.PostFitness;
import br.com.fitrank.util.JDBCFactory;


public class PostFitnessDAO {
	
	private Connection conexao;

	public PostFitnessDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}
	
	public PostFitness adicionaPostFitness(PostFitness postFitness) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO post_fitness ("
				+ "id_publicacao, "
				+ "id_pessoa, "
				+ "data_inicio_corrida, "
				+ "data_fim_corrida, "
				+ "modalidade, "
				+ "id_app, "
				+ "distancia_percorrida, "
				+ "duracao, "
				+ "data_publicacao, "
				+ "url"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, postFitness.getId_publicacao());
			preparedStatement.setString(++i, postFitness.getId_pessoa());
			preparedStatement.setString(++i, postFitness.getData_inicio_corrida());
			preparedStatement.setString(++i, postFitness.getData_fim_corrida());
			preparedStatement.setString(++i, postFitness.getModalidade());
			preparedStatement.setInt(++i, postFitness.getId_app());
			preparedStatement.setFloat(++i, postFitness.getDistancia_percorrida());
			preparedStatement.setFloat(++i, postFitness.getDuracao());
			preparedStatement.setString(++i, postFitness.getData_publicacao());
			preparedStatement.setString(++i, postFitness.getUrl());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return postFitness;
	}

	public PostFitness atualizaPostFitness(PostFitness postFitness) throws SQLException {
	
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
	
		String insertTableSQL = "UPDATE post_fitness set "
				+ "id_pessoa= ?, "
				+ "data_inicio_corrida= ?, "
				+ "data_fim_corrida= ?, "
				+ "id_app= ?, "
				+ "distancia_percorrida= ?, "
				+ "duracao= ?, "
				+ "data_publicacao= ?, "
				+ "url = ?, "
				+ "modalidade = ?, "
				+ "where id_publicacao = ?;";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, postFitness.getId_pessoa());
			preparedStatement.setString(++i, postFitness.getData_inicio_corrida());
			preparedStatement.setString(++i, postFitness.getData_fim_corrida());
			preparedStatement.setInt(++i, postFitness.getId_app());
			preparedStatement.setFloat(++i, postFitness.getDistancia_percorrida());
			preparedStatement.setFloat(++i, postFitness.getDuracao());
			preparedStatement.setString(++i, postFitness.getData_publicacao());
			preparedStatement.setString(++i, postFitness.getUrl());
			preparedStatement.setString(++i, postFitness.getModalidade());
			preparedStatement.setString(++i, postFitness.getId_publicacao());
	
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
	
		} catch (SQLException e) {
	
			System.out.println(e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return postFitness;
	}
	
	public PostFitness lePostFitness(PostFitness postFitness) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
	
		String selectTableSQL = "select "
				+ "id_publicacao, "
				+ "id_pessoa, "
				+ "data_inicio_corrida, "
				+ "data_fim_corrida, "
				+ "id_app, "
				+ "distancia_percorrida, "
				+ "duracao, "
				+ "data_publicacao, "
				+ "url, "
				+ "modalidade, "
				+ "from post_fitness"
				+ "where id_publicacao = ?;";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			
			ResultSet rs = preparedStatement.executeQuery(selectTableSQL);
			
			preparedStatement.setString(1, postFitness.getId_publicacao());
			
			if ( rs.next() ) {
					
				postFitness.setId_publicacao(rs.getString("id_publicacao"));
				postFitness.setId_pessoa(rs.getString("id_pessoa"));
				postFitness.setData_inicio_corrida(rs.getString("data_inicio_corrida"));
				postFitness.setData_fim_corrida(rs.getString("data_fim_corrida"));
				postFitness.setId_app(rs.getInt("id_app"));
				postFitness.setDistancia_percorrida(rs.getFloat("distancia_percorrida"));
				postFitness.setDuracao(rs.getFloat("duracao"));
				postFitness.setData_publicacao(rs.getString("data_publicacao"));
				postFitness.setUrl(rs.getString("url"));
				postFitness.setModalidade(rs.getString("modalidade"));
				
			}
			
			
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
	
		} catch (SQLException e) {
	
			System.out.println(e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return postFitness;
	}
}