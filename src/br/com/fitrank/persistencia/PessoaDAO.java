package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.restfb.types.User;

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
				+ "genero, "
				+ "data_nascimento, "
				+ "url_foto, "
				+ "url_perfil "
				+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = conexao.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, pessoa.getIdUsuario());
			preparedStatement.setTimestamp(++i, pessoa.getDataCadastro());
			preparedStatement.setString(++i, pessoa.getNome());
			preparedStatement.setTimestamp(++i, pessoa.getDataUltimoLogin());
			preparedStatement.setTimestamp(++i, pessoa.getDataUltimaAtualizacaoRuns());
			preparedStatement.setTimestamp(++i, pessoa.getDataUltimaAtualizacaoWalks());
			preparedStatement.setTimestamp(++i, pessoa.getDataUltimaAtualizacaoBikes());
			preparedStatement.setString(++i, pessoa.getGenero());
			preparedStatement.setTimestamp(++i, pessoa.getDataNascimento());
			preparedStatement.setString(++i, pessoa.getUrlFoto());
			preparedStatement.setString(++i, pessoa.getUrlPerfil());

			// execute insert SQL statement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaPessoa | " + e.getMessage() + "Id pessoa: " + pessoa.getIdUsuario());

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
	
	public void atualizaUrlPerfilPessoas(List<User> listaUsuarios) throws SQLException {
		
		String pessoasIn = "";
		String pessoasCase = "";
		
		conexao = new JDBCFactory().getConnection();
		PreparedStatement preparedStatement = null;
		
		for( int i = 0; i <= (listaUsuarios.size() - 1); i++) {
			if( i == (listaUsuarios.size() - 1)) {//N�o usar v�rgula no �ltimo elemento do IN
				pessoasCase = "WHEN id_usuario = ? THEN ? ";
				pessoasIn += "?";
			} else {
				pessoasCase = "WHEN id_usuario = ? THEN ? ";
				pessoasIn += "?,";
			}
		}
		
		String updateTableSQL  	= "UPDATE pessoa set "
								+ "url_perfil = (CASE " + pessoasCase + " END) "
								+ "WHERE id_usuario IN ( " + pessoasIn + " )" ;
	
		try {
			preparedStatement = conexao.prepareStatement(updateTableSQL);
			
			int i = 0;
			
			for(User usuarioFb : listaUsuarios){ 
				preparedStatement.setString(++i, usuarioFb.getId());
				
				if(usuarioFb.getLink() != null) {
					preparedStatement.setString(++i, usuarioFb.getLink());
				}
			}
			
			for(User usuarioFb : listaUsuarios) {
				preparedStatement.setString(++i, usuarioFb.getId());
			}
			
			// execute insert SQL statement
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
	
			Logger.insertLog("atualizaPessoas | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (conexao != null) {
				conexao.close();
			}
	
		}
		
//		return pessoa;
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
								+ "genero = ?, "
								+ "data_nascimento = ?, "
								+ "url_foto = ?, "
								+ "url_perfil = ? "
								+ "WHERE id_usuario = ? ";
	
		try {
			preparedStatement = conexao.prepareStatement(updateTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, pessoa.getNome());
			if(proprioUsuario){
				preparedStatement.setTimestamp(++i, pessoa.getDataUltimoLogin());
			}
			preparedStatement.setTimestamp(++i, pessoa.getDataUltimaAtualizacaoRuns());
			preparedStatement.setTimestamp(++i, pessoa.getDataUltimaAtualizacaoWalks());
			preparedStatement.setTimestamp(++i, pessoa.getDataUltimaAtualizacaoBikes());
			preparedStatement.setString(++i, pessoa.getGenero());
			preparedStatement.setTimestamp(++i, pessoa.getDataNascimento());
			preparedStatement.setString(++i, pessoa.getUrlFoto());
			preparedStatement.setString(++i, pessoa.getUrlPerfil());
			
			preparedStatement.setString(++i, pessoa.getIdUsuario());
	
			// execute insert SQL statement
			preparedStatement.executeUpdate();
	
		} catch (SQLException e) {
	
			Logger.insertLog("atualizaPessoa | " + e.getMessage() + " ID PESSOA: " + pessoa.getIdUsuario() + " Nome:" + pessoa.getNome());
	
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
				+ "genero, "
				+ "data_nascimento, "
				+ "url_foto, "
				+ "url_perfil "
				+ "from pessoa "
				+ "where id_usuario = ?";
		
		try {
			preparedStatement = conexao.prepareStatement(selectTableSQL);
			
			preparedStatement.setString(1, idPessoa);
			ResultSet rs = preparedStatement.executeQuery();
			
			if ( rs.next() ) {
				pessoa = new Pessoa();
				pessoa.setIdUsuario(rs.getString("id_usuario"));
				pessoa.setData_cadastro(rs.getTimestamp("data_cadastro"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setData_ultimo_login(rs.getTimestamp("data_ultimo_login"));
//				pessoa.setData_ultima_atualizacao_runs(rs.getDate("data_ultima_atualizacao_runs") ) );
//				pessoa.setData_ultima_atualizacao_walks(rs.getDate("data_ultima_atualizacao_walks") ) );
//				pessoa.setData_ultima_atualizacao_bikes(rs.getDate("data_ultima_atualizacao_bikes") ) );
				pessoa.setDataUltimaAtualizacaoRuns(rs.getTimestamp("data_ultima_atualizacao_runs") );
				pessoa.setDataUltimaAtualizacaoWalks(rs.getTimestamp("data_ultima_atualizacao_walks") );
				pessoa.setDataUltimaAtualizacaoBikes(rs.getTimestamp("data_ultima_atualizacao_bikes") );
				pessoa.setGenero(rs.getString("genero"));
				pessoa.setDataNascimento( rs.getTimestamp("data_nascimento") );
				pessoa.setUrlFoto(rs.getString("url_foto"));
				pessoa.setUrlPerfil(rs.getString("url_perfil"));
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
				+ "genero, "
				+ "data_nascimento, "
				+ "url_foto, "
				+ "url_perfil "
				+ "from pessoa";
		
		try {
			preparedStatement = conexao.prepareStatement(selectTableSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) {
				Pessoa pessoa = new Pessoa();
				pessoa.setIdUsuario(rs.getString("id_usuario"));
				pessoa.setData_cadastro(rs.getTimestamp("data_cadastro"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setData_ultimo_login(rs.getTimestamp("data_ultimo_login"));
				pessoa.setDataUltimaAtualizacaoRuns(rs.getTimestamp("data_ultima_atualizacao_runs") );
				pessoa.setDataUltimaAtualizacaoWalks(rs.getTimestamp("data_ultima_atualizacao_walks") );
				pessoa.setDataUltimaAtualizacaoBikes(rs.getTimestamp("data_ultima_atualizacao_bikes") );
				pessoa.setGenero(rs.getString("genero"));
				pessoa.setDataNascimento(rs.getTimestamp("data_nascimento") );
				pessoa.setUrlFoto(rs.getString("url_foto"));
				pessoa.setUrlPerfil(rs.getString("url_perfil"));
				
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
	
	public ArrayList<Amizade> listaAmigosMenosAtualizados(ArrayList<Amizade> listaAmigos,
			int limiteAtualizacaoUsuarios, String colunaModalidade) throws SQLException {
		
		conexao = new JDBCFactory().getConnection();
		PreparedStatement preparedStatement = null;
		Amizade amizade;
		ArrayList<Amizade> listaAmizades = new ArrayList<Amizade>();
		String amigos = "";
		
		if(listaAmigos.size() == 0) {
			return listaAmigos;
		}
		
		for( int i = 0; i <= (listaAmigos.size() - 1); i++) {
			if( i == (listaAmigos.size() - 1)) {//N�o usar v�rgula no �ltimo elemento do IN
				amigos += "'" + listaAmigos.get(i).getIdAmigo() + "'";
			} else {
				amigos += "'" + listaAmigos.get(i).getIdAmigo() + "',";
			}
		}
		//Os amigos que tiverem um limite maior que o valor da constante (inicialmente meia hora) da ultima atualizacao ser�o atualizados
		Timestamp dataLimiteAtualizacaoUsuarios = DateConversor.getPreviousMinutesTimestamp(ConstantesFitRank.LIMITE_MINUTOS_ATUALIZACAO_USUARIOS);
		
		String selectTableSQL = "SELECT " 
				+ "id_usuario, " 
				+ "data_ultimo_login, "
				+ "data_ultima_atualizacao_runs, "
				+ "data_ultima_atualizacao_walks,"
				+ "data_ultima_atualizacao_bikes " 
				+ "from pessoa " 
				+ "where id_usuario IN (" + amigos + ") AND ";
		
				if ( colunaModalidade.equals("A") ){
					selectTableSQL += "( data_ultima_atualizacao_runs < '" + dataLimiteAtualizacaoUsuarios + "' "
							+ "OR data_ultima_atualizacao_runs IS NULL "
							+ "OR data_ultima_atualizacao_walks < '" + dataLimiteAtualizacaoUsuarios + "' "
							+ "OR data_ultima_atualizacao_walks IS NULL "
							+ "OR data_ultima_atualizacao_bikes < '" + dataLimiteAtualizacaoUsuarios + "' "
							+ "OR data_ultima_atualizacao_bikes IS NULL ) "
							+ "ORDER BY data_ultima_atualizacao_runs, data_ultima_atualizacao_walks, data_ultima_atualizacao_bikes ASC ";
				} else {
					selectTableSQL += "( " + colunaModalidade + " < '" + dataLimiteAtualizacaoUsuarios + "' "
							+ "OR " + colunaModalidade + " IS NULL ) "
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
