package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Amizade;
import br.com.fitrank.util.DateConversor;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;

public class AmizadeDAO {

	private Connection conexao;

	public AmizadeDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}

	public Amizade adicionaAmizade(Amizade amizade) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO amizade (" 
				+ "id_pessoa, "
				+ "id_amigo, " 
				+ "data_amizade" 
				+ ") VALUES (?, ?, ?)";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			int i = 0;

			preparedStatement.setString(++i, amizade.getId_pessoa());
			preparedStatement.setString(++i, amizade.getId_amigo());
			preparedStatement.setString(++i, DateConversor.DateToString(amizade.getData_amizade()));

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog( "adicionaAmizade | " + e.getMessage() +
							  "id pessoa : " + amizade.getId_pessoa() + 
							  "id_amigo : " + amizade.getId_amigo()
							  );

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return amizade;
	}

	public Amizade atualizaAmizade(Amizade amizade) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "update amizade set " 
				+ "id_amigo = ?, "
				+ "data_amizade = ? " 
				+ "where id_pessoa = ?";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			int i = 0;

			preparedStatement.setString(++i, amizade.getId_amigo());
			preparedStatement.setString(++i, DateConversor.DateToString(amizade.getData_amizade()));
			preparedStatement.setString(++i, amizade.getId_pessoa());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("atualizaAmizade | " +e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return amizade;
	}

	public List<Amizade> listaAmizades(String idPessoa) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Amizade amizade;
		ArrayList<Amizade> listaAmizades = new ArrayList<Amizade>();

		String selectTableSQL = "SELECT " 
				+ "id_pessoa, " 
				+ "id_amigo, "
				+ "data_amizade " 
				+ "from amizade " 
				+ "where id_pessoa = ?";

		try {

			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);

			preparedStatement.setString(1, idPessoa);

			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				amizade = new Amizade();
				amizade.setId_pessoa(rs.getString("id_pessoa"));
				amizade.setId_amigo(rs.getString("id_amigo"));
				amizade.setData_amizade(DateConversor.StringToDate(rs.getString("data_amizade")));
				listaAmizades.add(amizade);
			}

		} catch (SQLException e) {

			Logger.insertLog("listaAmizades | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return listaAmizades;
	}

	public Amizade leAmizade(Amizade amizade)
			throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Amizade amizadeResult = null;
		
		
		String selectTableSQL = "SELECT " 
				+ "id_pessoa, " 
				+ "id_amigo, "
				+ "data_amizade " 
				+ "from amizade "
				+ "where id_pessoa = ? and id_amigo = ?";

		try {

			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);

			preparedStatement.setString(1, amizade.getId_pessoa());
			preparedStatement.setString(2, amizade.getId_amigo());
			
			
			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				amizadeResult = new Amizade();
				amizadeResult.setId_pessoa(rs.getString("id_pessoa"));
				amizadeResult.setId_amigo(rs.getString("id_amigo"));
				amizadeResult.setData_amizade(DateConversor.StringToDate(rs.getString("data_amizade")));
			}

		} catch (SQLException e) {

			Logger.insertLog("leAmizade | " + e.getMessage());
			
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return amizadeResult;
	}
	//
	// public boolean removeAmizadeFromId(Amizade amizade) throws SQLException {
	//
	// Connection dbConnection = null;
	// PreparedStatement preparedStatement = null;
	//
	// String deleteSQL =
	// "DELETE from amizade WHERE id_pessoa = ? and id_amigo = ?";
	//
	// try {
	// dbConnection = conexao;
	// preparedStatement = dbConnection.prepareStatement(deleteSQL);
	//
	// preparedStatement.setString(++i, amizade.getIdPessoa());
	// preparedStatement.setString(++i, amizade.getIdAmigo());
	//
	// // execute delete SQL stetement
	// preparedStatement.executeUpdate();
	// return true;
	// } catch (SQLException e) {
	//
	// Logger.insertLog(e.getMessage());
	// return false;
	// } finally {
	//
	// if (preparedStatement != null) {
	// preparedStatement.close();
	// }
	//
	// if (dbConnection != null) {
	// dbConnection.close();
	// }
	//
	// }
	// }

}
