package br.com.fitrank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.service.ConfiguracaoServico;
import br.com.fitrank.util.ConstantesFitRank;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

/**
 * Servlet implementation class SalvaConfiguracao
 */
//@WebServlet("/SalvaConfiguracao")
public class SalvaConfiguracao extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConfiguracaoServico configuracaoServico = new ConfiguracaoServico(); 
	Configuracao configuracao = new Configuracao();
	
	String modalidade = null;
	String modo = null;  
	String turno = null;
	String periodo = null;
	String fav = null;
	String padrao = null;
	
    public SalvaConfiguracao() {
    	super();
    }
    
    private void inicia(HttpServletRequest request, HttpServletResponse response) {
    	
    	PrintWriter out;
    	
    	FacebookClient facebookClient = new DefaultFacebookClient(request.getParameter("token"), Version.LATEST);
    	
    	User facebookUser = facebookClient.fetchObject("me", User.class);
    	
    	modalidade = (String) request.getParameter("modalidade");
    	modo = (String) request.getParameter("modo");  
    	turno = (String) request.getParameter("turno");
    	periodo = (String) request.getParameter("periodo");
    	fav = (String) request.getParameter("config");
    	
    	String userID = (String) facebookUser.getId();
    	Boolean isValido = isParametrosValidos(modalidade, modo, turno, periodo, fav);
    	String mensagem = "";
    	
    	if (isValido) {
        	
        	configuracao.setModalidade(modalidade);
        	configuracao.setModo(modo);
        	configuracao.setDiaNoite(turno);
        	configuracao.setIntervaloData(periodo);
        	configuracao.setFavorito(fav.equals("S") ? true : false);
        	configuracao.setIdPessoa(userID);
        	
        	if (fav.equals("S")) {
        		Configuracao configFav = configuracaoServico.leConfiguracaoFavorita(userID);
    	    	if (configFav == null) {
    	    		configuracaoServico.adicionaConfiguracao(configuracao);
    	    	} else {
    	    		configuracao.setIdConfiguracao(configFav.getIdConfiguracao());
    	    		configuracaoServico.atualizaConfiguracao(configuracao);
    	    	}
        	} else if (padrao.equals("S")) {
        		Configuracao configPadrao = configuracaoServico.leConfiguracaoPadraoModalidade(userID, modalidade);
        		if (configPadrao == null) {
    	    		configuracaoServico.adicionaConfiguracao(configuracao);
    	    	} else {
    	    		configuracao.setIdConfiguracao(configPadrao.getIdConfiguracao());
    	    		configuracaoServico.atualizaConfiguracao(configuracao);
    	    	}
        	}
        	mensagem = "Ranking favorito salvo com sucesso";
        	
    	} else {
    		//ocorreu algum erro
    		mensagem = "Ocorreu algum erro ao gerar o ranking favorito";
    	}
    	response.addHeader("msg", mensagem);
		response.setContentType("text/html;charset=UTF-8");

		try {
			out = response.getWriter();
			out.println(mensagem);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
	private boolean isParametrosValidos(String modalidade, String modo, String turno, String periodo, String fav) {
		
		switch(modalidade) {
			case ConstantesFitRank.MODALIDADE_BICICLETA:
			case ConstantesFitRank.MODALIDADE_CAMINHADA:
			case ConstantesFitRank.MODALIDADE_CORRIDA:
			case ConstantesFitRank.MODALIDADE_TUDO:
				break;
			default:
				return false;
		}
		
		switch(modo) {
			case ConstantesFitRank.VELOCIDADE_MEDIA:
			case ConstantesFitRank.DISTANCIA:
			case ConstantesFitRank.QUANTIDADE:
				break;
			default:
				return false;
		}
		
		switch(periodo) {
			case ConstantesFitRank.DIA:
			case ConstantesFitRank.MES:
			case ConstantesFitRank.SEMANA:
			case ConstantesFitRank.ANO:
			case ConstantesFitRank.SEMPRE:
				break;
			default:
				return false;
		}
		
		switch(fav) {
			case "Favorito":
				this.fav = "S";
				break;
			case "N":
				this.fav = "N";
				break;
			default:
				return false;
		}
		
		return true;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}
	
}
