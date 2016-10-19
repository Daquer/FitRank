package br.com.fitrank.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.service.CourseServico;
import br.com.fitrank.service.PessoaServico;
import br.com.fitrank.service.PostFitnessServico;
import br.com.fitrank.util.ConstantesFitRank;
import br.com.fitrank.util.Logger;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

public class JobExtracao implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Logger.insertLog("---------------------------------------Job iniciado---------------------------------------");
		
//		InputStream input = null;
//	    Properties prop = new Properties();
	    PostFitnessServico postFitnessServico = new PostFitnessServico();
	    CourseServico courseServico = new CourseServico();
	    PessoaServico pessoaServico = new PessoaServico();
	    List<Pessoa> pessoas = new ArrayList<Pessoa>();
	    List<Course> coursesPessoa = new ArrayList<Course>();
	    List<String> coursesRequest = new ArrayList<String>();
	    
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
	    	
	    	coursesPessoa = courseServico.leCoursePorIdPessoa(pessoa.getId_usuario());
	    	
	    	String coursesStr = "";
	    	int courseLimit = 50;
	    	
	    	Logger.insertLog("size--> " + coursesPessoa.size());
	    	for ( int i = 0; i < coursesPessoa.size(); i++) {
	    		Course course = coursesPessoa.get(i);
	    		
	    		
	    		if (i % 50 == 0){
	    			coursesStr = course.getId_course();
	    		} else {
	    			coursesStr += "," + course.getId_course();
	    		} 
//	    		coursesRequest.add(course.getId_course());
	    		Logger.insertLog("i--> " + i + " id--> " + course.getId_course());
	    	
	    		if (i == courseLimit - 1 || i == coursesPessoa.size() - 1){
			    	JsonObject jsonCourses = facebookClient.fetchObject("fitness.course", JsonObject.class, Parameter.with("ids", coursesStr));
			    	
//			    	ArrayList<JsonObject> jsonCoursesArray = jsonCourses.toJsonArray(jsonCourses.names());
			    	JsonArray jsonCoursesArray = jsonCourses.toJsonArray(jsonCourses.names());
			    	
			    	for( int j = 0; j < jsonCoursesArray.length(); j++){
			    		Logger.insertLog("j--> " + j);
			    		JsonObject jsonCourse = (JsonObject) jsonCoursesArray.get(j);
			    		
			    		try{
				    		JsonObject data = (JsonObject) jsonCourse.get("data");
				    		
				    		JsonObject distance = (JsonObject) data.get("distance");
				    		
				    		Integer distanceValueInt = 0;
				    		Double distanceValueDbl = 0.0;
				    		
				    		try{
				    			distanceValueDbl = (Double) distance.get("value");
				    		} catch(ClassCastException e) {
				    			distanceValueInt = (Integer) distance.get("value");
				    		}
				    		
				    		Integer calories = (Integer) data.get("calories");
				    		
				    		Double pace = (Double) data.get("pace");
				    		
			    		} catch (JsonException e) {// Exceção lançada normalmente para os casos onde uma das chaves não existe 
			    			//Do nothing
			    		}
			    		
//			    		JsonObject duration = (JsonObject) data.get("duration");
//			    		Integer durationValue = (Integer) duration.get("value");
			    		
			    	}
			    	
			    	String jsonCoursesStr = jsonCourses.toString();
			    	
			    	courseLimit += 50;
			    	coursesStr = "";
	    		}
	    	}
//	    	List<CourseFB>
	    	
//	    	JsonObject jsonCourses = facebookClient.fetchObjects(coursesRequest, JsonObject.class);
	    	
	    }
	    
	    
	    
	    
//	    postFitnessServico.lePostFitnessPorIdPessoa(idPessoa);
	    
	    Logger.insertLog("---------------------------------------Job finalizado---------------------------------------");
	}
}
