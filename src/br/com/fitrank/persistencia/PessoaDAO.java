package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Amizade;
import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.DateConversor;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;



public class PessoaDAO {

	private Connection conexao;

	public PessoaDAO() {
		
	}

	public Pessoa adicionaPessoa(Pessoa pessoa) throws SQLException {

		conexao = new JDBCFactory().getConnection();
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO pessoa ("
				+ "id_usuario, "
				+ "data_cadastro, "
				+ "nome, "
				+ "data_ultimo_login, "
				+ "data_ultima_atualizacao_runs, "
				+ "data_ultima_atualizacao_walks, "
				+ "data_ultima_atualizacao_bikes, "
				+ "rank_anual, "
				+ "genero, "
				+ "data_nascimento, "
				+ "url_foto "
				+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = conexao.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, pessoa.getId_usuario());
			preparedStatement.setTimestamp(++i, pessoa.getData_cadastro());
			preparedStatement.setString(++i, pessoa.getNome());
			preparedStatement.setTimestamp(++i, pessoa.getData_ultimo_login());
			preparedStatement.setTimestamp(++i, pessoa.getData_ultima_atualizacao_runs());
			preparedStatement.setTimestamp(++i, pessoa.getData_ultima_atualizacao_walks());
			preparedStatement.setTimestamp(++i, pessoa.getData_ultima_atualizacao_bikes());
			preparedStatement.setString(++i, pessoa.getRank_anual());
			preparedStatement.setString(++i, pessoa.getGenero());
			preparedStatement.setTimestamp(++i, pessoa.getData_nascimento());
			preparedStatement.setString(++i, pessoa.getUrl_foto());

			// execute insert SQL statement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaPessoa | " + e.getMessage() + "Id pessoa: " + pessoa.getId_usuario());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (conexao != null) {
				conexao.close();
			}

		}
		return pessoa;
	}
	
	public Pessoa atualizaPessoa(Pessoa pessoa, boolean proprioUsuario) throws SQLException {
	
		
		conexao = new JDBCFactory().getConnection();
		PreparedStatement preparedStatement = null;
	
		String updateTableSQL  	= "UPDATE pessoa set "
								+ "nome = ?, ";
			if(proprioUsuario){
				updateTableSQL += "data_ultimo_login = ?, ";
			}
		updateTableSQL 		   += "data_ultima_atualizacao_runs = ?, "
								+ "data_ultima_atualizacao_walks = ?, "
								+ "data_ultima_atualizacao_bikes = ?, "
								+ "rank_anual = ?, "
								+ "genero = ?, "
								+ "data_nascimento = ?, "
								+ "url_foto = ? "
								+ "WHERE id_usuario = ? ";
	
		try {
			preparedStatement = conexao.prepareStatement(updateTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, pessoa.getNome());
			if(proprioUsuario){
				preparedStatement.setTimestamp(++i, pessoa.getData_ultimo_login());
			}
			preparedStatement.setTimestamp(++i, pessoa.getData_ultima_atualizacao_runs());
			preparedStatement.setTimestamp(++i, pessoa.getData_ultima_atualizacao_walks());
			preparedStatement.setTimestamp(++i, pessoa.getData_ultima_atualizacao_bikes());
			preparedStatement.setString(++i, pessoa.getRank_anual());
			preparedStatement.setString(++i, pessoa.getGenero());
			preparedStatement.setTimestamp(++i, pessoa.getData_nascimento());
			preparedStatement.setString(++i, pessoa.getUrl_foto());
			
			preparedStatement.setString(++i, pessoa.getId_usuario());
	
			// execute insert SQL statement
			preparedStatement.executeUpdate();
	
		} catch (SQLException e) {
	
			Logger.insertLog("atualizaPessoa | " + e.getMessage() + " ID PESSOA: " + pessoa.getId_usuario() + " Nome:" + pessoa.getNome());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (conexao != null) {
				conexao.close();
			}
	
		}
		
		return pessoa;
	}

	public Pessoa lePessoa(String idPessoa) throws SQLException {
		
		conexao = new JDBCFactory().getConnection();
		PreparedStatement preparedStatement = null;
		Pessoa pessoa = null;

		String selectTableSQL = "SELECT "
				+ "id_usuario, "
				+ "data_cadastro, "
				+ "nome,"
				+ "data_ultimo_login, "
				+ "data_ultima_atualizacao_runs, "
				+ "data_ultima_atualizacao_walks, "
				+ "data_ultima_atualizacao_bikes, "
				+ "rank_anual, "
				+ "genero, "
				+ "data_nascimento, "
				+ "url_foto "
				+ "from pessoa "
				+ "where id_usuario = ?";
		
		try {
			preparedStatement = conexao.prepareStatement(selectTableSQL);
			
			preparedStatement.setString(1, idPessoa);
			ResultSet rs = preparedStatement.executeQuery();
			
			if ( rs.next() ) {
				pessoa = new Pessoa();
				pessoa.setId_usuario(rs.getString("id_usuario"));
				pessoa.setData_cadastro(rs.getTimestamp("data_cadastro"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setData_ultimo_login(rs.getTimestamp("data_ultimo_login"));
//				pessoa.setData_ultima_atualizacao_runs(rs.getDate("data_ultima_atualizacao_runs") ) );
//				pessoa.setData_ultima_atualizacao_walks(rs.getDate("data_ultima_atualizacao_walks") ) );
//				pessoa.setData_ultima_atualizacao_bikes(rs.getDate("data_ultima_atualizacao_bikes") ) );
				pessoa.setData_ultima_atualizacao_runs(rs.getTimestamp("data_ultima_atualizacao_runs") );
				pessoa.setData_ultima_atualizacao_walks(rs.getTimestamp("data_ultima_atualizacao_walks") );
				pessoa.setData_ultima_atualizacao_bikes(rs.getTimestamp("data_ultima_atualizacao_bikes") );
				pessoa.setRank_anual(rs.getString("rank_anual"));
				pessoa.setGenero(rs.getString("genero"));
				pessoa.setData_nascimento( rs.getTimestamp("data_nascimento") );
				pessoa.setUrl_foto(rs.getString("url_foto"));
			}
			
		} catch (SQLException e) {
			 
			Logger.insertLog("lePessoa | " + e.getMessage());
 
		} finally {
 
			if (preparedStatement != null) {
				preparedStatement.close();
			}
 
			if (conexao != null) {
				conexao.close();
			}
			
		}
		
		return pessoa;
	}
	
	public boolean removePessoaFromId(String userId) throws SQLException {
		conexao = new JDBCFactory().getConnection();

		PreparedStatement preparedStatement = null;
 
		String deleteSQL = "DELETE from pessoa WHERE id_usuario = ?";
 
		try {

			preparedStatement = conexao.prepareStatement(deleteSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, userId);
 
			// execute delete SQL stetement
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
 
			Logger.insertLog("removePessoaFromId | " + e.getMessage() + "id pessoa: " + userId);
			return false;
		} finally {
 
			if (preparedStatement != null) {
				preparedStatement.close();
			}
 
			if (conexao != null) {
				conexao.close();
			}
 
		}
	}
	
	public List<Pessoa> leTodasPessoas() throws SQLException {
		
		conexao = new JDBCFactory().getConnection();
		PreparedStatement preparedStatement = null;
		List<Pessoa> pessoas = new ArrayList<Pessoa>();

		String selectTableSQL = "SELECT "
				+ "id_usuario, "
				+ "data_cadastro, "
				+ "nome,"
				+ "data_ultimo_login, "
				+ "data_ultima_atualizacao_runs, "
				+ "data_ultima_atualizacao_walks, "
				+ "data_ultima_atualizacao_bikes, "
				+ "rank_anual, "
				+ "genero, "
				+ "data_nascimento, "
				+ "url_foto "
				+ "from pessoa";
		
		try {
			preparedStatement = conexao.prepareStatement(selectTableSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) {
				Pessoa pessoa = new Pessoa();
				pessoa.setId_usuario(rs.getString("id_usuario"));
				pessoa.setData_cadastro(rs.getTimestamp("data_cadastro"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setData_ultimo_login(rs.getTimestamp("data_ultimo_login"));
				pessoa.setData_ultima_atualizacao_runs(rs.getTimestamp("data_ultima_atualizacao_runs") );
				pessoa.setData_ultima_atualizacao_walks(rs.getTimestamp("data_ultima_atualizacao_walks") );
				pessoa.setData_ultima_atualizacao_bikes(rs.getTimestamp("data_ultima_atualizacao_bikes") );
				pessoa.setRank_anual(rs.getString("rank_anual"));
				pessoa.setGenero(rs.getString("genero"));
				pessoa.setData_nascimento(rs.getTimestamp("data_nascimento") );
				pessoa.setUrl_foto(rs.getString("url_foto"));
				
				pessoas.add(pessoa);
			}
			
		} catch (SQLException e) {
			 
			Logger.insertLog("leTodasPessoas | " + e.getMessage());
 
		} finally {
 
			if (preparedStatement != null) {
				preparedStatement.close();
			}
 
			if (conexao != null) {
				conexao.close();
			}
			
		}
		
		return pessoas;
	}
	
	public ArrayList<Amizade> listaPessoasMenosAtualizadas(ArrayList<Amizade> listaAmigos,
			int limiteAtualizacaoUsuarios, String colunaModalidade) throws SQLException {
		
		conexao = new JDBCFactory().getConnection();
		PreparedStatement preparedStatement = null;
		Amizade amizade;
		ArrayList<Amizade> listaAmizades = new ArrayList<Amizade>();
		String amigos = "";
		
		for( int i = 0; i < (listaAmigos.size() - 1); i++) {
			if( i == (listaAmigos.size() - 2)) {//Não usar vírgula no último elemento do IN
				amigos += "'" + listaAmigos.get(i).getId_amigo() + "'";
			} else {
				amigos += "'" + listaAmigos.get(i).getId_amigo() + "',";
			}
		}
		//Os amigos que tiverem um limite maior que o valor da constante (inicialmente meia hora) da ultima atualizacao serão atualizados
		String dataLimiteAtualizacaoUsuarios = DateConversor.getPreviousMinutesString(ConstantesFitRank.LIMITE_MINUTOS_ATUALIZACAO_USUARIOS);
		
		String selectTableSQL = "SELECT " 
				+ "id_usuario, " 
				+ "data_ultimo_login, "
				+ "data_ultima_atualizacao_runs, "
				+ "data_ultima_atualizacao_walks,"
				+ "data_ultima_atualizacao_bikes " 
				+ "from pessoa " 
				+ "where id_usuario IN (" + amigos + ") ";
		
				if ( colunaModalidade.equals("A") ){
					
					selectTableSQL += "AND ( data_ultima_atualizacao_runs < '" + dataLimiteAtualizacaoUsuarios + "' "
							+ "OR data_ultima_atualizacao_walks < '" + dataLimiteAtualizacaoUsuarios + "' "
							+ "OR data_ultima_atualizacao_bikes < '" + dataLimiteAtualizacaoUsuarios + "') "
							+ "ORDER BY data_ultima_atualizacao_runs, data_ultima_atualizacao_walks, data_ultima_atualizacao_bikes ASC ";
				} else {
					selectTableSQL += "AND " + colunaModalidade + " < '" + dataLimiteAtualizacaoUsuarios + "' " 
							+ "ORDER BY " + colunaModalidade + " ASC ";
				}
				
				
				selectTableSQL += "LIMIT " + limiteAtualizacaoUsuarios;

		try {
			
			preparedStatement = conexao.prepareStatement(selectTableSQL);

//			preparedStatement.setString(1, idUsuario);

			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				amizade = new Amizade();
				amizade.setId_amigo(rs.getString("id_usuario"));
				listaAmizades.add(amizade);
			}

		} catch (SQLException e) {

			Logger.insertLog("listaPessoasMenosAtualizadas | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (conexao != null) {
				conexao.close();
			}

		}

		return listaAmizades;
	
	}

}
