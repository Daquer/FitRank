package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;


public class ConfiguracaoDAO {
	
	private Connection conexao;

	public ConfiguracaoDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}

	public Configuracao adicionaConfiguracao(Configuracao configuracao) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO configuracao ("
				+ "modalidade, "
				+ "modo, "
				+ "intervalo_data, "
				+ "favorito, "
				+ "id_pessoa"
				+ ") VALUES (?, ?, ?, ?, ?)";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
			
			String favorito = ConstantesFitRank.CHAR_NAO;
			
			if(configuracao.isFavorito()){
				favorito = ConstantesFitRank.CHAR_SIM;
			} 
			
			int i = 0;
			
			preparedStatement.setString(++i, configuracao.getModalidade());
			preparedStatement.setString(++i, configuracao.getModo());
			preparedStatement.setString(++i, configuracao.getIntervaloData());
			preparedStatement.setString(++i, favorito);
			preparedStatement.setString(++i, configuracao.getIdPessoa());

			int idConfiguracao = ConstantesFitRank.INT_RESULTADO_INVALIDO;
			// execute insert SQL statement
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if(rs.next()){
				idConfiguracao = rs.getInt(1);
			}
			configuracao.setIdConfiguracao(idConfiguracao);

		} catch (SQLException e) {

			Logger.insertLog("adicionaConfiguracao | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return configuracao;
	}
	
public Configuracao atualizaConfiguracao(Configuracao configuracao) throws SQLException {

		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String updateTableSQL  = "update configuracao set "
				+ "modalidade = ?, "
				+ "modo = ?, "
				+ "intervalo_data = ?, "
				+ "favorito = ?, "
				+ "id_pessoa = ? "
				+ "where id_configuracao = ?";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);
			
			String favorito = "N";
			
			if(configuracao.isFavorito()){
				favorito = "S";
			} 
			
			int i = 0;
			
			preparedStatement.setString(++i, configuracao.getModalidade());
			preparedStatement.setString(++i, configuracao.getModo());
			preparedStatement.setString(++i, configuracao.getIntervaloData());
			preparedStatement.setString(++i, favorito);
			preparedStatement.setString(++i, configuracao.getIdPessoa());
			preparedStatement.setInt(++i, configuracao.getIdConfiguracao());
			
			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("atualizaConfiguracao | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		
		return configuracao;
	}
	

	public Configuracao leConfiguracaoPorId(int idConfiguracao) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Configuracao configRetorno = null;
		
		String selectTableSQL = "SELECT "
				+ "id_configuracao, "
				+ "modo, "
				+ "modalidade, "
				+ "intervalo_data, "
				+ "favorito, "
				+ "id_pessoa "
				+ "FROM configuracao "
				+ "WHERE id_configuracao = ?";
		
		try {
			
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			
			preparedStatement.setInt(1, idConfiguracao);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				configRetorno = new Configuracao();
				configRetorno.setIdConfiguracao(rs.getInt("id_configuracao"));
				configRetorno.setModo(rs.getString("modo"));
				configRetorno.setModalidade(rs.getString("modalidade"));
				configRetorno.setIntervaloData(rs.getString("intervalo_data"));
				configRetorno.setFavorito(rs.getString("favorito").equals("S") ? true : false);
				configRetorno.setIdPessoa(rs.getString("id_pessoa"));

			}
		} catch (SQLException e) {
			 
			Logger.insertLog("leConfiguracaoPorId | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
			
		}
		
		return configRetorno;
	}
	
	public Configuracao leConfiguracaoPorPessoa(Configuracao configuracao, Boolean isFavorito) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Configuracao configRetorno = null;
	
		String selectTableSQL = "SELECT "
				+ "id_configuracao, "
				+ "modo, "
				+ "modalidade, "
				+ "intervalo_data, "
				+ "favorito, "
				+ "id_pessoa "
				+ "FROM configuracao "
				+ "WHERE id_pessoa = ? ";
		
		if(isFavorito){
			selectTableSQL += " AND favorito = ? ";
		} 
		
		try {
			
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			
			int i = 1;
			preparedStatement.setString(i++, configuracao.getIdPessoa());
			
			if(isFavorito){
				preparedStatement.setString(i++, ConstantesFitRank.CHAR_SIM);
			} 
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				configRetorno = new Configuracao();
				
				configRetorno.setIdConfiguracao(rs.getInt("id_configuracao"));
				configRetorno.setModo(rs.getString("modo"));
				configRetorno.setModalidade(rs.getString("modalidade"));
				configRetorno.setIntervaloData(rs.getString("intervalo_data"));
				configRetorno.setFavorito(rs.getString("favorito").equals("S") ? true : false);
				configRetorno.setIdPessoa(rs.getString("id_pessoa"));

			}
		} catch (SQLException e) {
			 
			Logger.insertLog("leConfiguracaoPorPessoa | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
			
		}
		
		return configRetorno;
	}

//	public boolean removeConfiguracaoFromId(Configuracao configuracao) throws SQLException {
//
//		Connection dbConnection = null;
//		PreparedStatement preparedStatement = null;
// 
//		String deleteSQL = "DELETE from configuracao WHERE id_configuracao = ?";
// 
//		try {
//			dbConnection = conexao;
//			preparedStatement = dbConnection.prepareStatement(deleteSQL);
//			preparedStatement.setInt(++i, configuracao.getIdConfiguracao());
// 
//			// execute delete SQL stetement
//			preparedStatement.executeUpdate();
//			return true;
//		} catch (SQLException e) {
// 
//			Logger.insertLog(e.getMessage());
//			return false;
//		} finally {
// 
//			if (preparedStatement != null) {
//				preparedStatement.close();
//			}
// 
//			if (dbConnection != null) {
//				dbConnection.close();
//			}
// 
//		}
//	}

	
}
