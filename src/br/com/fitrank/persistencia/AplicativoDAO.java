package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Aplicativo;
import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.modelo.Ranking;
import br.com.fitrank.modelo.apresentacao.AplicativoTela;
import br.com.fitrank.modelo.apresentacao.RankingPessoaTela;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.DateConversor;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;

public class AplicativoDAO {

	private Connection conexao;

	public AplicativoDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}

	public Aplicativo adicionaAplicativo(Aplicativo aplicativo) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO aplicativo (" 
				+ "id_aplicativo, "
				+ "nome, "
				+ "url_site "
				+ ") VALUES (?,?,?)";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			int i = 0;

			preparedStatement.setString(++i, aplicativo.getId_aplicativo());
			preparedStatement.setString(++i, aplicativo.getNome());
			preparedStatement.setString(++i, aplicativo.getUrl_site());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaAplicativo | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return aplicativo;
	}
	
	public ArrayList<Aplicativo> adicionaAplicativos(ArrayList<Aplicativo> aplicativos) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO aplicativo (" 
				+ "id_aplicativo, "
				+ "nome, "
				+ "url_site "
				+ ") VALUES (?,?,?)";
		
		for (int i = 0; i < (aplicativos.size() - 1); i++) {
			insertTableSQL += ", (?,?,?)";
		}

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			int i = 0;
			
			for (Aplicativo aplicativo : aplicativos) {
				preparedStatement.setString(++i, aplicativo.getId_aplicativo());
				preparedStatement.setString(++i, aplicativo.getNome());
				preparedStatement.setString(++i, aplicativo.getUrl_site());
			}
			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaAplicativos | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return aplicativos;
	}
	
	public Aplicativo atualizaAplicativo(Aplicativo aplicativo)
			throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "update aplicativo set " 
				+ "nome = ? "
				+ "url_site = ? "
				+ "where id_aplicativo = ? ";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			int i = 0;

			preparedStatement.setString(++i, aplicativo.getNome());
			preparedStatement.setString(++i, aplicativo.getUrl_site());
			preparedStatement.setString(++i, aplicativo.getId_aplicativo());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("atualizaAplicativo | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return aplicativo;
	}

	public Aplicativo leAplicativo(String id_aplicativo) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Aplicativo aplicativo = null;

		String selectTableSQL = "SELECT "
				+ "id_aplicativo, "
				+ "nome, "
				+ "url_site "
				+ "FROM aplicativo "
				+ "where id_aplicativo = ?";

		try {

			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, id_aplicativo);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			
			
			while (rs.next()) {
				aplicativo = new Aplicativo();
				aplicativo.setId_aplicativo(rs.getString("id_aplicativo"));
				aplicativo.setNome(rs.getString("nome"));
				aplicativo.setUrl_site(rs.getString("url_site"));
			}

		} catch (SQLException e) {

			Logger.insertLog("leAplicativo | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return aplicativo;
	}
	
	public ArrayList<Aplicativo> leListaAplicativos(ArrayList<Aplicativo> aplicativos)
			throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ArrayList<Aplicativo> aplicativosReturn = null; 

		String selectTableSQL = "SELECT "
				+ "id_aplicativo, "
				+ "nome, "
				+ "url_site "
				+ "FROM aplicativo "
				+ "where id_aplicativo IN ( ?";
		
		for (int i = 0; i < (aplicativos.size() - 1); i++) {
			selectTableSQL += ", ?";
		}		
		
		selectTableSQL += ");";
		
		try {

			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			
			int i = 0;

			for (Aplicativo aplicativo : aplicativos) {
				preparedStatement.setString(++i, aplicativo.getId_aplicativo());
			}

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();
			
			aplicativosReturn = new ArrayList<Aplicativo>();
			
			while (rs.next()) {
				Aplicativo aplicativo = new Aplicativo();
				
				aplicativo.setId_aplicativo(rs.getString("id_aplicativo"));
				aplicativo.setNome(rs.getString("nome"));
				aplicativo.setUrl_site(rs.getString("url_site"));
				
				aplicativosReturn.add(aplicativo);

			}

		} catch (SQLException e) {

			Logger.insertLog("leListaAplicativos | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return aplicativosReturn;
	}
	
	public List<AplicativoTela> listaAplicativosUsuarioNoRanking(Configuracao configuracao, RankingPessoaTela rankingPessoaTela, Ranking ranking) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		List<AplicativoTela> listaAplicativoTela = new ArrayList<AplicativoTela>();
		
		Timestamp dataInicialTimestamp = null;
		Timestamp dataFinalTimestamp = null;
		
		if(ConstantesFitRank.DIA.equalsIgnoreCase(configuracao.getIntervaloData())){
			dataInicialTimestamp = ranking.getData_ranking();
			dataFinalTimestamp = ranking.getData_ranking();
		} else if(ConstantesFitRank.SEMANA.equalsIgnoreCase(configuracao.getIntervaloData())){
			dataInicialTimestamp =  DateConversor.getPreviousWeekFromSqlTimestamp(ranking.getData_ranking());
			dataFinalTimestamp = ranking.getData_ranking();
		} else if(ConstantesFitRank.MES.equalsIgnoreCase(configuracao.getIntervaloData())){
			dataInicialTimestamp =  DateConversor.getPreviousMonthFromSqlTimestamp(ranking.getData_ranking());
			dataFinalTimestamp = ranking.getData_ranking();
		} else if(ConstantesFitRank.ANO.equalsIgnoreCase(configuracao.getIntervaloData())){
			dataInicialTimestamp = DateConversor.getPreviousYearFromSqlTimestamp(ranking.getData_ranking());
			dataFinalTimestamp = ranking.getData_ranking();
		}
		
		if (dataInicialTimestamp != null) {
			dataInicialTimestamp = DateConversor.removeTimestampHourPart(dataInicialTimestamp);
		}
		
		//Nao foi possivel utilizar parametros do preparedStatement nesta consulta!!!
		String selectTableSQL = "SELECT pf.id_app,												\n"
							+	"       a.nome,													\n"
							+	"       COUNT(pf.id_publicacao) atividades						\n"
							+	"  FROM post_fitness pf,										\n"
							+	"       pessoa p,												\n"
							+	"       aplicativo a											\n"
							+	" WHERE p.id_usuario = '"+rankingPessoaTela.getId_pessoa()+"'										\n"
							+	"   AND p.id_usuario = pf.id_pessoa																	\n"
							+	"   AND pf.id_app = a.id_aplicativo																	\n";
		if(!ConstantesFitRank.MODALIDADE_TUDO.equals(configuracao.getModalidade())) {
			selectTableSQL  +=  "   AND pf.modalidade = '"+configuracao.getModalidade()+"'											\n";
		}
		if(!ConstantesFitRank.SEMPRE.equalsIgnoreCase(configuracao.getIntervaloData())) {
			selectTableSQL  +=	"   AND pf.data_publicacao  												\n"
//							+	"                    BETWEEN str_to_date('"+dataInicial+"', '%d/%m/%Y') 							\n"
//			  				+	"                        AND str_to_date('"+dataFinal+"', '%d/%m/%Y'))	\n";
							+	"                    BETWEEN '"+dataInicialTimestamp+"' 							\n"
							+	"                        AND '"+dataFinalTimestamp+"'	\n";
		}
			selectTableSQL  +=	"GROUP BY pf.id_app "
							+ 	"ORDER BY atividades desc";
	
		try {
			dbConnection = conexao;
			selectTableSQL = selectTableSQL.replace("\t", "");
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			ResultSet rs = preparedStatement.executeQuery();
			AplicativoTela aplicativoTela;

			while (rs.next()) {
				aplicativoTela = new AplicativoTela();
				aplicativoTela.setId_aplicativo(rs.getString("id_app"));
				aplicativoTela.setNome(rs.getString("nome"));
				aplicativoTela.setQuantidadeAtividades(rs.getInt("atividades"));
				listaAplicativoTela.add(aplicativoTela);
			}
	
		} catch (SQLException e) {
	
			Logger.insertLog("listaAplicativosUsuarioNoRanking | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return listaAplicativoTela;
	}
	
	// public boolean removeAplicativoFromId(Aplicativo aplicativo)
	// throws SQLException {
	//
	// Connection dbConnection = null;
	// PreparedStatement preparedStatement = null;
	//
	// String deleteSQL = "DELETE from aplicativo WHERE id_aplicativo = ?";
	//
	// try {
	// dbConnection = conexao;
	// preparedStatement = dbConnection.prepareStatement(deleteSQL);
	// preparedStatement.setInt(++i, aplicativo.getIdAplicativo());
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
