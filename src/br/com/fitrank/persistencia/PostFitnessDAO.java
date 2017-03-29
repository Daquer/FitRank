package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.PostFitness;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;


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
				+ "modalidade, "
				+ "id_app, "
				+ "distancia_percorrida, "
				+ "duracao, "
				+ "data_publicacao, "
				+ "url"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, postFitness.getId_publicacao());
			preparedStatement.setString(++i, postFitness.getId_pessoa());
			preparedStatement.setString(++i, postFitness.getModalidade());
			preparedStatement.setString(++i, postFitness.getId_app());
			preparedStatement.setDouble(++i, postFitness.getDistancia_percorrida());
			preparedStatement.setDouble(++i, postFitness.getDuracao());
			preparedStatement.setTimestamp(++i, postFitness.getData_publicacao());
			preparedStatement.setString(++i, postFitness.getUrl());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaPostFitness | " + e.getMessage());

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
	
	public boolean adicionaListaPostFitness(List<PostFitness> listaPostFitness) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		boolean isSucess = true;
		
		String insertTableSQL = "INSERT INTO post_fitness ("
				+ "id_publicacao, "
				+ "id_pessoa, "
				+ "modalidade, "
				+ "id_app, "
				+ "distancia_percorrida, "
				+ "duracao, "
				+ "data_publicacao, "
				+ "url"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		
		for (int aux = 0; aux < (listaPostFitness.size() - 1); aux++) {
			insertTableSQL +=  ", (?, ?, ?, ?, ?, ?, ?, ?)";
			
		}	
		
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			for (PostFitness postFitness : listaPostFitness) {
			
				preparedStatement.setString(++i, postFitness.getId_publicacao());
				preparedStatement.setString(++i, postFitness.getId_pessoa());
				preparedStatement.setString(++i, postFitness.getModalidade());
				preparedStatement.setString(++i, postFitness.getId_app());
				preparedStatement.setDouble(++i, postFitness.getDistancia_percorrida());
				preparedStatement.setDouble(++i, postFitness.getDuracao());
				preparedStatement.setTimestamp(++i, postFitness.getData_publicacao());
				preparedStatement.setString(++i, postFitness.getUrl());
			}
			// execute insert SQL statement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog( "adicionaListaPostFitness | " + e.getMessage());
			isSucess = false;
			
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		
		return isSucess;
	}

	public PostFitness atualizaPostFitness(PostFitness postFitness) throws SQLException {
	
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
	
		String insertTableSQL = "UPDATE post_fitness set "
				+ "id_pessoa= ?, "
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
			preparedStatement.setString(++i, postFitness.getId_app());
			preparedStatement.setDouble(++i, postFitness.getDistancia_percorrida());
			preparedStatement.setDouble(++i, postFitness.getDuracao());
			preparedStatement.setTimestamp(++i, postFitness.getData_publicacao());
			preparedStatement.setString(++i, postFitness.getUrl());
			preparedStatement.setString(++i, postFitness.getModalidade());
			preparedStatement.setString(++i, postFitness.getId_publicacao());
	
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
	
		} catch (SQLException e) {
	
			Logger.insertLog("atualizaPostFitness | " + e.getMessage());
	
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
	
	public List<PostFitness> lePostFitnessPorIdPessoa(String idPessoa) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		List<PostFitness> listaPostFitness = new ArrayList<PostFitness>();
	
		String selectTableSQL = "select "
				+ "id_publicacao, "
				+ "id_pessoa, "
				+ "id_app, "
				+ "distancia_percorrida, "
				+ "duracao, "
				+ "data_publicacao, "
				+ "url, "
				+ "modalidade "
				+ "from post_fitness "
				+ "where id_pessoa = ?;";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			preparedStatement.setString(1, idPessoa);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) {
				PostFitness postFitness = new PostFitness();
				postFitness.setId_publicacao(rs.getString("id_publicacao"));
				postFitness.setId_pessoa(rs.getString("id_pessoa"));
				postFitness.setId_app(rs.getString("id_app"));
				postFitness.setDistancia_percorrida(rs.getDouble("distancia_percorrida"));
				postFitness.setDuracao(rs.getDouble("duracao"));
				postFitness.setData_publicacao(rs.getTimestamp("data_publicacao"));
				postFitness.setUrl(rs.getString("url"));
				postFitness.setModalidade(rs.getString("modalidade"));
				
				listaPostFitness.add(postFitness);
			}
	
		} catch (SQLException e) {
	
			Logger.insertLog("lePostFitnessPorIdPessoa | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return listaPostFitness;
	}
	
	public String leModalidadeComMaisAtividades(String idPessoa) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		
		String retorno = "";
		
		String selectTableSQL = 
		"select * from "
		+ "(select count(*) as R from post_fitness where id_pessoa = ? and modalidade = 'R') runs,"
		+ "(select count(*) as W from post_fitness where id_pessoa = ? and modalidade = 'W') walks,"
		+ "(select count(*) as B from post_fitness where id_pessoa = ? and modalidade = 'B') bikes";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			preparedStatement.setString(1, idPessoa);
			preparedStatement.setString(2, idPessoa);
			preparedStatement.setString(3, idPessoa);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) {
				
				String strRuns = rs.getString("R");
				String strWalks = rs.getString("W");
				String strBikes = rs.getString("B");
				
				int runs = Integer.parseInt(strRuns);
				int walks = Integer.parseInt(strWalks);
				int bikes = Integer.parseInt(strBikes);
				
				int max = Math.max(runs, Math.max(walks, bikes));
					
				if (runs == max) {
					retorno = "runs";
				} else if (walks == max) {
					retorno = "walks";
				} else if (bikes == max) {
					retorno = "bikes";
				}
				
			}
	
		} catch (SQLException e) {
	
			Logger.insertLog("leModalidadeComMaisAtividades | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		
		return retorno;
	}
	
	public String obtemDataPostMaisRecente(String idPessoa) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		String dataPostMaisRecente = "";
	
		String selectTableSQL = "SELECT str_to_date(pf.data_publicacao, '%d/%m/%Y') dat, "
							  + "		  pf.data_publicacao "
							  + "  FROM post_fitness pf "
							  + " WHERE pf.id_pessoa = ? "
							  + " ORDER BY dat DESC "
							  + " LIMIT 1 ";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			preparedStatement.setString(1, idPessoa);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				dataPostMaisRecente = rs.getString("data_publicacao");
			}
	
		} catch (SQLException e) {
	
			Logger.insertLog("obtemDataPostMaisRecente | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return dataPostMaisRecente;
	}
}

