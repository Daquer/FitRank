package br.com.fitrank.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.service.AmizadeServico;
import br.com.fitrank.service.ConfiguracaoServico;
import br.com.fitrank.service.PessoaServico;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.DateConversor;
import br.com.fitrank.util.Logger;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

public class InitUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	PessoaServico pessoaServico = new PessoaServico();
	AmizadeServico amizadeServico = new AmizadeServico();
	ConfiguracaoServico configuracaoServico = new ConfiguracaoServico();

	public InitUser() {

	}

	protected void inicia(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	   InputStream input = null;
	   Properties prop = new Properties();
	   String index	= "";
	   
	   input = getClass().getClassLoader().getResourceAsStream("config.properties");
	   
	   prop.load(input);
	   
	   try{
		   index = request.getParameter("index");
		   
		   Logger.insertLog("pre obtencao AppToken");
		   AccessToken accessToken = new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(ConstantesFitRank.ID_APP_FITRANK,
				   prop.getProperty("app_secret"), request.getParameter("token"));
		   Logger.insertLog("pos obtencao AppToken");
		   
		   String token = accessToken.getAccessToken();
		   
		   Logger.insertLog("pre obtencao UserToken");
		   FacebookClient facebookClient = new DefaultFacebookClient(token, Version.LATEST);
		   Logger.insertLog("pos obtencao UserToken");
		   
		   Logger.insertLog("pre fetchObject/me");
		   User facebookUser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "name, id, link"));
		   Logger.insertLog("pos fetchObject/me");
		   
		   Logger.insertLog("pre fetchObject/me/friends");
		   Connection<User> friendsFB = facebookClient.fetchConnection("me/friends", User.class, Parameter.with("fields", "name, id, link"));
		   Logger.insertLog("pos fetchObject/me/friends");
		   JsonObject picture = facebookClient.fetchObject("me/picture", JsonObject.class, Parameter.with("type", "normal"), Parameter.with("redirect", "false"));
		   
		   Pessoa pessoa = new Pessoa();
		   
		   if(facebookUser.getId()!=null && !facebookUser.getId().equals("")){
			   pessoa.setIdUsuario(facebookUser.getId());
		   }
			
		   if(facebookUser.getName()!=null){
			   pessoa.setNome(facebookUser.getName());
		   }
		   
		   //Sem permissão
		   if(facebookUser.getGender()!=null){
			   if(facebookUser.getGender().equalsIgnoreCase(ConstantesFitRank.FACEBOOK_FEMALE_GENDER)){
				   pessoa.setGenero(ConstantesFitRank.SEXO_FEMININO);
			   } else if(facebookUser.getGender().equalsIgnoreCase(ConstantesFitRank.FACEBOOK_MALE_GENDER)){
				   pessoa.setGenero(ConstantesFitRank.SEXO_MASCULINO);
			   }
		   }
		   
		   //Sem permissão
		   if(facebookUser.getBirthdayAsDate()!=null){
			   pessoa.setDataNascimento(DateConversor.getJavaSqlTimestamp(facebookUser.getBirthdayAsDate()));
		   }
		   
		   if( picture.getJsonObject("data").getString("url") != null){
			   pessoa.setUrlFoto( picture.getJsonObject("data").getString("url") );
		   }
		   
		   if( facebookUser.getLink() != null){
			   pessoa.setUrlPerfil(facebookUser.getLink());
		   }
		   
		   Pessoa usuarioExistente = pessoaServico.lePessoaServico(facebookUser);
		   
		   if (usuarioExistente == null ) {
			   pessoa = pessoaServico.adicionaPessoaServico(pessoa);
		   } else {
			   
			   pessoa.setDataUltimaAtualizacaoRuns(usuarioExistente.getDataUltimaAtualizacaoRuns());
			   pessoa.setDataUltimaAtualizacaoWalks(usuarioExistente.getDataUltimaAtualizacaoWalks());
			   pessoa.setDataUltimaAtualizacaoBikes(usuarioExistente.getDataUltimaAtualizacaoBikes());
			   pessoa = pessoaServico.atualizaPessoaServico(pessoa, true);
		   }
		   
		   for ( User friendFB : friendsFB.getData()) {
			  atualizaAmizadeUsuario(facebookUser, friendFB);
		   }
		   
		   Configuracao configuracao = null;
			configuracao = configuracaoServico
					.leConfiguracaoFavorita(facebookUser.getId());
			
			//Caso o usuário tenha um favorito cadastrado
			if (configuracao != null && configuracao.isFavorito() ) {
				
				request.setAttribute("modalidade", configuracao.getModalidade());
				request.setAttribute("modo", configuracao.getModo());
				request.setAttribute("periodo", configuracao.getIntervaloData());
	
			} else if (usuarioExistente == null) { //caso seja o primeiro login deste usuario
				request.setAttribute("modalidade", ConstantesFitRank.MODALIDADE_PADRAO);
			    request.setAttribute("modo", ConstantesFitRank.MODO_PADRAO);
			    request.setAttribute("periodo", ConstantesFitRank.PERIODO_PADRAO);
			} else { //caso o usuario NAO tenha um favorito cadastrado
			    request.setAttribute("modalidade", ConstantesFitRank.MODALIDADE_PRIMEIRO_LOGIN);
			    request.setAttribute("modo", ConstantesFitRank.MODO_PRIMEIRO_LOGIN);
			    request.setAttribute("periodo", ConstantesFitRank.PERIODO_PRIMEIRO_LOGIN);
			}
			
		   request.setAttribute("token", token);
		   
		   RequestDispatcher rd = request.getRequestDispatcher("/ranking.jsp");  
		   rd.forward(request,response);  
	   
	   } catch(Exception e) {
		   RequestDispatcher rd = null;
		   
		   if("S".equals(index)) {
			   rd = request.getRequestDispatcher("/index.jsp");
		   } else {
			   request.setAttribute("modalidade", ConstantesFitRank.MODALIDADE_PADRAO);
			   request.setAttribute("modo", ConstantesFitRank.MODO_PADRAO);
			   request.setAttribute("periodo", ConstantesFitRank.PERIODO_PADRAO);
			   rd = request.getRequestDispatcher("/ranking.jsp");
		   }
		   
		   Logger.insertLog("InitUser | " + e.getMessage());
		   
		   if(!e.getMessage().contains("(#17) User request limit reached")) {
			   request.setAttribute("errorDescription", e.getMessage());
		   }
		   
		   if(e.getMessage().contains("accessToken"))
			   Logger.insertLog("Index: " + index + " userToken recebido -> " + request.getParameter("token"));
		   
		   rd.forward(request,response);  
	   }
   }

	private void atualizaAmizadeUsuario(User facebookUser, User friendFB) {

		String idAmigo = friendFB.getId();

		if (amizadeServico.leAmizadeServico(facebookUser.getId(), idAmigo) == null) {
			amizadeServico
					.adicionaAmizadeServico(facebookUser.getId(), idAmigo);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}

}
