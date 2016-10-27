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
    	String isAjax = request.getParameter("ajax") == null ? "" : (String) request.getParameter("ajax");
    	RequestDispatcher rd;
    	Configuracao configuracao = new Configuracao();
    	
    	try {
    		int idRanking = request.getAttribute("idRanking") == null ? Integer.valueOf(request.getParameter("idRanking")) : (Integer) request.getAttribute("idRanking");
	    	
	    	Ranking ranking = rankingServico.leRanking(idRanking);
	    	
	    	if( ranking.getId_ranking() != 0 ) {
	    	
			    configuracao = configuracaoServico.leConfiguracaoPorId(ranking.getId_configuracao());
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
				
	    	} else {
	    		request.setAttribute("errorDescription", "Número de ranking inexistente.");
	    		response.addHeader("msg", "Número de ranking inexistente.");
				response.setContentType("text/html;charset=UTF-8");
				response.setStatus(500);
	    	}
	    	
	    	if(isAjax.equals("S")) {	
				List<RankingPessoaTela> listaRankingPessoaTela = obtemListaAplicativosTela(listRankingPessoas, configuracao, ranking);
		    	postFitnessServico = new PostFitnessServico();
		    	
				String json = com.cedarsoftware.util.io.JsonWriter.objectToJson(listaRankingPessoaTela);
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println(json);
				out.close();
				
	    	} else {
	    		
	    		rd = request.getRequestDispatcher("ranking.jsp");
	    		rd.forward(request, response);
	    	}
    	} catch(Exception e) {
    		if(isAjax.equals("S")){
				response.addHeader("msg", e.getMessage());
				response.setContentType("text/html;charset=UTF-8");
				response.setStatus(500);
	    	} else {
	    		request.setAttribute("errorDescription", e.getMessage());
	    		rd = request.getRequestDispatcher("/ranking.jsp");  
	    		rd.forward(request,response);
	    	}
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
