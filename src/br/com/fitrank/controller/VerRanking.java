package br.com.fitrank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.modelo.Ranking;
import br.com.fitrank.modelo.RankingPessoa;
import br.com.fitrank.modelo.apresentacao.RankingPessoaTela;
import br.com.fitrank.service.AplicativoServico;
import br.com.fitrank.service.ConfiguracaoServico;
import br.com.fitrank.service.PessoaServico;
import br.com.fitrank.service.PostFitnessServico;
import br.com.fitrank.service.RankingPessoaServico;
import br.com.fitrank.service.RankingServico;
import br.com.fitrank.util.ConstantesFitRank;

/**
 * Servlet implementation class VerRanking
 */

public class VerRanking extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	RankingPessoaServico rankingPessoaServico = new RankingPessoaServico();
	
	String modalidade = null;
	String modo = null;  
	String turno = null;
	String periodo = null;
	String fav = null;
	String padrao = null;
	PostFitnessServico postFitnessServico = new PostFitnessServico();
	
    public VerRanking() {
    	
    }
    
    private void inicia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	List<RankingPessoa> listRankingPessoas = new ArrayList<RankingPessoa>();
    	ConfiguracaoServico configuracaoServico = new ConfiguracaoServico();
    	RankingServico rankingServico = new RankingServico();
    	RankingPessoaServico rankingPessoaServico = new RankingPessoaServico();
    	String nomeGeradorRank = "";
    	
    	int idRanking = request.getAttribute("idRanking") == null ? Integer.valueOf(request.getParameter("idRanking")) : (Integer) request.getAttribute("idRanking");
    	String isAjax = request.getParameter("ajax") == null ? "" : (String) request.getParameter("ajax");
    	
    	Ranking ranking = rankingServico.leRanking(idRanking);
	    Configuracao configuracao = configuracaoServico.leConfiguracaoPorId(ranking.getId_configuracao());
	    modalidade = configuracao.getModalidade();
    	modo = configuracao.getModo();
		periodo = configuracao.getIntervaloData();
    	
    	listRankingPessoas = rankingPessoaServico.listaRankingPessoaPorIdRanking(ranking.getId_ranking());
    	
    	//Recupera as configuracoes de pessoa, inclusive foto.
    	for (RankingPessoa rankingPessoa : listRankingPessoas) {
    		
    		PessoaServico pessoaServico = new PessoaServico();
    		
    		rankingPessoa.setPessoa( pessoaServico.lePessoaPorIdServico( rankingPessoa.getId_pessoa() ) );
    		
    		if (rankingPessoa.getPessoa().getId_usuario().equals(configuracao.getIdPessoa()) ) {
    			nomeGeradorRank = rankingPessoa.getPessoa().getNome();
    		}
		}
	    
    	request.setAttribute("geradorRank", nomeGeradorRank);
		request.setAttribute("modalidade", modalidade);
		request.setAttribute("modo", modo);
		request.setAttribute("periodo", periodo);
		
		if(isAjax.equals("S")) {	
			List<RankingPessoaTela> listaRankingPessoaTela = obtemListaAplicativosTela(listRankingPessoas, configuracao, ranking);
	    	postFitnessServico = new PostFitnessServico();
	    	
			String json = com.cedarsoftware.util.io.JsonWriter.objectToJson(listaRankingPessoaTela);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
			
    	} else {
    		
    		RequestDispatcher rd = request.getRequestDispatcher("ranking.jsp");
    		rd.forward(request, response);
    	}
    	
    }
    
    private String definePeriodo(String periodo) {
		switch(periodo) {
			case ConstantesFitRank.DIA:
				//Dia
				return "0";
			case ConstantesFitRank.SEMANA:
				//Semana
				return "1";
			case ConstantesFitRank.MES:
				//Mes
				return "2";
			case ConstantesFitRank.ANO:
				//Ano
				return "3";
			default:
				return periodo;
		}
		
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}
	
	private List<RankingPessoaTela> obtemListaAplicativosTela(List<RankingPessoa> listaRankingPessoa, Configuracao configuracaoRanking, Ranking ranking) {
    	List<RankingPessoaTela> listaRankingPessoaTela = new ArrayList<RankingPessoaTela>();
    	AplicativoServico aplicativoServico = new AplicativoServico();
		for (RankingPessoa rankingPessoa : listaRankingPessoa) {
			RankingPessoaTela rankingPessoaTela = new RankingPessoaTela(rankingPessoa);
			rankingPessoaTela.setListaAplicativosTela(aplicativoServico.listaAplicativosUsuarioNoRanking(configuracaoRanking, rankingPessoaTela, ranking));
			listaRankingPessoaTela.add(rankingPessoaTela);
		}
		return listaRankingPessoaTela;
	}
	
}
