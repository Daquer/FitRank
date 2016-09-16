package br.com.fitrank.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.jdbcjobstore.Constants;

import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.service.PessoaServico;
import br.com.fitrank.service.PostFitnessServico;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.Logger;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.json.JsonObject;

public class JobExtracao implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Logger.insertLog("---------------------------------------Job iniciado---------------------------------------");
		
//		InputStream input = null;
//	    Properties prop = new Properties();
	    PostFitnessServico postFitnessServico = new PostFitnessServico();
	    PessoaServico pessoaServico = new PessoaServico();
	    List<Pessoa> pessoas = new ArrayList<Pessoa>();
	    
//	    input = getClass().getClassLoader().getResourceAsStream("config.properties");
//	   
//	    try {
//			prop.load(input);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	    
	    pessoas = pessoaServico.leTodasPessoasServico();
	    
	    AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(ConstantesFitRank.ID_APP_FITRANK, ConstantesFitRank.app_secret);
	    
	    Logger.insertLog("Access token de aplicativo obtido");
	    
	    FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken());
	    
	    for(Pessoa pessoa: pessoas){
	    	
	    	JsonObject picture = facebookClient.fetchObject(pessoa.getId_usuario() + "/picture", JsonObject.class, Parameter.with("type", "normal"), Parameter.with("redirect", "false"));
	    	String url = picture.getJsonObject("data").getString("url");
	    
	    	if (!pessoa.getUrl_foto().equals(url)) {
	    		pessoa.setUrl_foto(url);
	    	
	    		pessoaServico.atualizaPessoaServico(pessoa, false);
	    	}
	    }
	    
	    
//	    postFitnessServico.lePostFitnessPorIdPessoa(idPessoa);
	    
	    Logger.insertLog("---------------------------------------Job finalizado---------------------------------------");
	}
}
