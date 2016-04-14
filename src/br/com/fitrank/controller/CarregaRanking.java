package br.com.fitrank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fitrank.modelo.Amizade;
import br.com.fitrank.modelo.Aplicativo;
import br.com.fitrank.modelo.Configuracao;
import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.modelo.PostFitness;
import br.com.fitrank.modelo.Ranking;
import br.com.fitrank.modelo.RankingPessoa;
import br.com.fitrank.modelo.fb.PostFitness.PostFitnessFB;
import br.com.fitrank.persistencia.PessoaDAO;
import br.com.fitrank.service.AmizadeServico;
import br.com.fitrank.service.AplicativoServico;
import br.com.fitrank.service.ConfiguracaoServico;
import br.com.fitrank.service.PessoaServico;
import br.com.fitrank.service.PostFitnessServico;
import br.com.fitrank.service.RankingPessoaServico;
import br.com.fitrank.service.RankingServico;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.DateConversor;
import br.com.fitrank.util.PostFitnessUtil;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookGraphException;
import com.restfb.types.User;

/**
 * Servlet implementation class CarregaRanking
 */

public class CarregaRanking extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	AplicativoServico aplicativoServico = new AplicativoServico();

	ConfiguracaoServico configuracaoServico = new ConfiguracaoServico();

	PessoaServico pessoaServico = new PessoaServico();

	PostFitnessServico postFitnessServico = new PostFitnessServico();
	
	ArrayList<Aplicativo> aplicativos = new ArrayList<Aplicativo>();
	
	List<PostFitnessFB> postsFit = new ArrayList<PostFitnessFB>();
	
	ArrayList<Aplicativo> aplicativosNaoInserir = new ArrayList<Aplicativo>();
	
	AmizadeServico amizadeServico = new AmizadeServico();
	
	RankingPessoaServico rankingPessoaServico = new RankingPessoaServico();
	
	RankingServico rankingServico = new RankingServico();
	
	String modalidade = null;
	String modo = null;  
	String turno = null;
	String periodo = null;
	String fav = null;
	String padrao = null;
	
	
    public CarregaRanking() {
    	
    }
    
    private void inicia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	Date horainicio = new Date();
		RequestDispatcher rd = null;

		aplicativos.clear();
		postsFit.clear();
		aplicativosNaoInserir.clear();
		
		List<RankingPessoa> listRankingPessoas = new ArrayList<RankingPessoa>();
		
		String modalidade = request.getAttribute("modalidade") == null ? (String) request.getParameter("modalidade") : (String) request.getAttribute("modalidade");
		String modo = request.getAttribute("modo") == null ? (String) request.getParameter("modo") : (String) request.getAttribute("modo");
    	String periodo = request.getAttribute("periodo") == null ? (String) request.getParameter("periodo") : (String) request.getAttribute("periodo");
		   
    	FacebookClient facebookClient = new DefaultFacebookClient(request.getParameter("token"));
    	User facebookUser = facebookClient.fetchObject("me", User.class);
    	
    	//Atualiza��es feitas em toda e qualquer chamada de ranking
		Date ultimaAtualizacao = handleUltimaAtividade(modalidade, facebookClient, facebookUser);
		if (ultimaAtualizacao != null) {
			
			atualizaCorridasAmigos(facebookUser.getId(), modalidade, facebookClient, request);
		}
		
		Configuracao configuracaoRanking = new Configuracao();
    	configuracaoRanking.setIdPessoa(facebookUser.getId());
    	configuracaoRanking.setModalidade(modalidade);
		configuracaoRanking.setIntervaloData(periodo);
		configuracaoRanking.setFavorito(false);
		configuracaoRanking.setPadraoModalidade(false);
		configuracaoRanking.setModo(modo);
		
		listRankingPessoas = rankingPessoaServico.geraRanking(configuracaoRanking);
		
		configuracaoRanking = configuracaoServico.adicionaConfiguracao(configuracaoRanking);
		
		if(configuracaoRanking.getIdConfiguracao() != ConstantesFitRank.INT_RESULTADO_INVALIDO){
		
			Ranking ranking = new Ranking();
			ranking.setId_configuracao(configuracaoRanking.getIdConfiguracao());
			ranking = rankingServico.adicionaRanking(ranking);
			
			if(ranking.getId_ranking() != ConstantesFitRank.INT_RESULTADO_INVALIDO){
				rankingPessoaServico.gravaRankingPessoa(listRankingPessoas, ranking.getId_ranking());
			}
		}
		
		//Recupera as configura��es de pessoa, inclusive foto.
    	for (RankingPessoa rankingPessoa : listRankingPessoas) {
    		
    		PessoaServico pessoaServico = new PessoaServico();
    		
    		rankingPessoa.setPessoa( pessoaServico.lePessoaPorIdServico( rankingPessoa.getId_pessoa() ) );
		}

		request.setAttribute("token", (String) request.getParameter("token"));
		
		Date horaFim = new Date();
		System.out.println("\n\nTempo de processamento CarregaRanking: " + (horainicio.getTime() - horaFim.getTime())/1000 + " segundos.\n");

		
    	request.setAttribute("modalidade", modalidade);
		request.setAttribute("modo", modo);
		request.setAttribute("periodo", periodo);
		request.setAttribute("listaRanking", listRankingPessoas);
		
		request.setAttribute("token", (String) request.getParameter("token"));
		
		String json = com.cedarsoftware.util.io.JsonWriter.objectToJson(listRankingPessoas);
		
		response.addHeader("json", json);
		
		rd = request.getRequestDispatcher("ranking.jsp");
		
		rd.forward(request, response);
    	
    }
    
    private String defineModalidade(String modalidade) {
		switch (modalidade) {
			case ConstantesFitRank.MODALIDADE_CAMINHADA:
				return "walks";
	
			case ConstantesFitRank.MODALIDADE_CORRIDA:
				return "runs" ;
	
			case ConstantesFitRank.MODALIDADE_BICICLETA:
				return "bikes";
				
			case ConstantesFitRank.MODALIDADE_TUDO:
				return "all";
	
			default:
				return modalidade;
	
			}
	}
    
    private Date handleUltimaAtividade(String modalidade, FacebookClient facebookClient, User facebookUser) {
		Pessoa pessoa = new Pessoa();

		switch (modalidade) {
		case ConstantesFitRank.MODALIDADE_CAMINHADA:
			Date ultimoWalk = pessoaServico.lePessoaServico(facebookUser).getData_ultima_atualizacao_walks();
			pessoa = executaAtualizacao(ConstantesFitRank.MODALIDADE_CAMINHADA, facebookClient, facebookUser, ultimoWalk);
			ultimoWalk = pessoa.getData_ultima_atualizacao_walks();
			return ultimoWalk;
			
		case ConstantesFitRank.MODALIDADE_CORRIDA:
			Date ultimoRuns = pessoaServico.lePessoaServico(facebookUser).getData_ultima_atualizacao_runs();
			pessoa = executaAtualizacao(ConstantesFitRank.MODALIDADE_CORRIDA, facebookClient, facebookUser, ultimoRuns);
			ultimoRuns = pessoa.getData_ultima_atualizacao_runs();
			return ultimoRuns;
			
		case ConstantesFitRank.MODALIDADE_BICICLETA:
			Date ultimoBikes = pessoaServico.lePessoaServico(facebookUser).getData_ultima_atualizacao_bikes();
			pessoa = executaAtualizacao(ConstantesFitRank.MODALIDADE_BICICLETA, facebookClient, facebookUser, ultimoBikes);
			ultimoBikes = pessoa.getData_ultima_atualizacao_bikes();
			return ultimoBikes;
			
		case ConstantesFitRank.MODALIDADE_TUDO:
			pessoa = pessoaServico.lePessoaServico(facebookUser);
			pessoa = executaAtualizacao(ConstantesFitRank.MODALIDADE_CAMINHADA, facebookClient, facebookUser, pessoa.getData_ultima_atualizacao_walks());
			pessoa = executaAtualizacao(ConstantesFitRank.MODALIDADE_CORRIDA, facebookClient, facebookUser, pessoa.getData_ultima_atualizacao_runs());
			pessoa = executaAtualizacao(ConstantesFitRank.MODALIDADE_BICICLETA, facebookClient, facebookUser, pessoa.getData_ultima_atualizacao_bikes());
			return new Date();
			
		default:
			return null;
		}
	}
    
    private void atualizaCorridasAmigos(String idUsuario, String modalidade, FacebookClient facebookClient, HttpServletRequest request){
		AmizadeServico amizadeServico = new AmizadeServico();
		List<Amizade> amigos = amizadeServico.listaAmizades(idUsuario);
		
		for(Amizade amizade : amigos){
			try {
				User facebookUser = facebookClient.fetchObject(amizade.getId_amigo(), User.class);
				handleUltimaAtividade(modalidade, facebookClient, facebookUser);
			} catch (FacebookGraphException e) {
				pessoaServico.removePessoaFromIdServico(amizade.getId_amigo());
			}
		}
	}
    

    private Pessoa executaAtualizacao(String modalidade, FacebookClient facebookClient, User facebookUser, Date dataUltimaAtualizacao) {
		
		Connection<PostFitnessFB> listaFitConnection = facebookClient
				.fetchConnection(facebookUser.getId()+"/fitness." + defineModalidade(modalidade),
						PostFitnessFB.class, Parameter.with("limit", calculaLimiteDeBusca(dataUltimaAtualizacao)));
		
		postFitnessServico = new PostFitnessServico();
		ArrayList<PostFitness> postsSalvosNoBanco = (ArrayList<PostFitness>) postFitnessServico.lePostFitnessPorIdPessoa(facebookUser.getId());
		ArrayList<PostFitness> postsNaoInserir = new ArrayList<PostFitness>();
		
		verificaAplicativos(listaFitConnection);
		
		ArrayList<PostFitness> postsFit = new ArrayList<PostFitness>();

		for (PostFitnessFB postFit : listaFitConnection.getData()) {
			// Primeira atividade desta modalidade OU publicacoes ainda nao armazenadas
			if(dataUltimaAtualizacao == null || postFit.getPublishTime().after(dataUltimaAtualizacao)){
				// Adiciona aplicativo � Lista
				PostFitness postFitness = new PostFitness();
				postFitness.setId_publicacao(postFit.getId());
				postFitness.setId_pessoa(facebookUser.getId());
				postFitness.setId_app(postFit.getApplication().getId());
				postFitness.setData_publicacao(DateConversor.DateToString(postFit.getPublishTime()));
				postFitness.setUrl(postFit.getDataCourse().getCourse().getUrl());
				postFitness.setModalidade(modalidade);
				
				try {
	
					switch (postFit.getApplication().getId()) {
					case ConstantesFitRank.ID_APP_NIKE:
						postFitness.setDistancia_percorrida(PostFitnessUtil.getNikeDistance(postFit.getDataCourse().getCourse().getTitle()));
						postFitness.setDuracao(PostFitnessUtil.getNikeDuration(postFit.getStartTime(), postFit.getEndTime()));
						break;
					case ConstantesFitRank.ID_APP_RUNTASTIC:
					case ConstantesFitRank.ID_APP_RUNTASTIC_MOUNTAIN_BIKE:
						postFitness.setDistancia_percorrida(PostFitnessUtil.getRuntasticDistance(postFit.getDataCourse().getCourse().getTitle()));
						postFitness.setDuracao(PostFitnessUtil.getRuntasticDuration(postFit.getDataCourse().getCourse().getTitle()));
						break;
					case ConstantesFitRank.ID_APP_RUNKEEPER:
						postFitness.setDistancia_percorrida(PostFitnessUtil.getRunKeeperDistance(postFit.getDataCourse().getCourse().getTitle()));
						postFitness.setDuracao(PostFitnessUtil.getRunKeeperDuration(postFit.getDataCourse().getCourse().getTitle()));
						break;
					default:
						break;
					}
					
				} catch (NumberFormatException e) {
					continue;
				}
	
				postsFit.add(postFitness);
			}

		}
		
		for (PostFitness postFitness : postsFit) {
			for (PostFitness postSalvoNoBanco : postsSalvosNoBanco) {
				if(postFitness.getId_publicacao().equals(postSalvoNoBanco.getId_publicacao()) && postFitness.getId_pessoa().equals(postSalvoNoBanco.getId_pessoa())){
					postsNaoInserir.add(postFitness);
				}
			}
		}
		
		for(PostFitness postNaoInserir : postsNaoInserir) {
			postsFit.remove(postNaoInserir);
		}
		
		Pessoa pessoa = pessoaServico.lePessoaServico(facebookUser);
		
		postFitnessServico.adicionaListaPostFitnessServico(postsFit);

		switch (modalidade) {
		case ConstantesFitRank.MODALIDADE_CAMINHADA:
			pessoa.setData_ultima_atualizacao_walks(new Date());
			break;
		case ConstantesFitRank.MODALIDADE_CORRIDA:
			pessoa.setData_ultima_atualizacao_runs(new Date());
			break;
		case ConstantesFitRank.MODALIDADE_BICICLETA:
			pessoa.setData_ultima_atualizacao_bikes(new Date());
			break;
		default:
			break;

		}

		Pessoa pessoaReturn = pessoaServico.atualizaPessoaServico(pessoa, false);

		return pessoaReturn;
	}
    
	private String calculaLimiteDeBusca(Date ultimaAtualizacao) {
		Integer limit;
		if(null != ultimaAtualizacao){
			limit = DateConversor.getDaysDifference(new Date(), ultimaAtualizacao) * ConstantesFitRank.LIMITE_CORRIDAS_REALIZADAS_POR_DIA;
			limit = limit == 0 ? 1 : limit;
		} else {
			limit = ConstantesFitRank.LIMITE_MAX_RECUPERA_FB;
		}
		return limit.toString();
	}
	
	private void verificaAplicativos(Connection<PostFitnessFB> fitConnection) {
		for (PostFitnessFB postFit : fitConnection.getData()) {

			postsFit.add(postFit);

			// Adiciona aplicativo � Lista
			Aplicativo aplicativo = new Aplicativo();

			aplicativo.setId_aplicativo(postFit.getApplication().getId());
			aplicativo.setNome(postFit.getApplication().getName());

			if (!aplicativos.contains(aplicativo)) {
				aplicativos.add(aplicativo);
			}
		}

		// Insere aplicativos que est�o sendo utilizados pelo
		// usu�rio, no banco.
		if (aplicativos.size() > 1) {

			aplicativosNaoInserir = aplicativoServico
					.leListaAplicativosServico(aplicativos);

			if (aplicativosNaoInserir != null) {
				
				@SuppressWarnings("unchecked")
				ArrayList<Aplicativo> aplicativosAux = (ArrayList<Aplicativo>) aplicativos.clone();
				
				for(Aplicativo app :aplicativos) {
					for (Aplicativo appNaoInserir: aplicativosNaoInserir) {
						if( app.getId_aplicativo().equals(appNaoInserir.getId_aplicativo()) ){
							aplicativosAux.remove(app);
						}
					}	
				}
				
				aplicativos = aplicativosAux;
			}

			if (aplicativos.size() > 1)
				aplicativoServico.adicionaAplicativosServico(aplicativos);
		}

		if (aplicativos.size() == 1) {
			if (aplicativoServico.leAplicativoServico(aplicativos.get(0)
					.getId_aplicativo()) == null) {
				aplicativoServico.adicionaAplicativoServico(aplicativos.get(0));
			}
		}

	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}

}
