package br.com.fitrank.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.modelo.Localizacao;
import br.com.fitrank.modelo.Pessoa;
import br.com.fitrank.service.CourseServico;
import br.com.fitrank.service.PessoaServico;
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

	public void execute(JobExecutionContext arg0) throws JobExecutionException, NullPointerException {
		try {
			Logger.insertLog("---------------------------------------Job iniciado---------------------------------------");
			
	//		InputStream input = null;
	//	    Properties prop = new Properties();
		    CourseServico courseServico = new CourseServico();
		    PessoaServico pessoaServico = new PessoaServico();
		    List<Pessoa> pessoas = new ArrayList<Pessoa>();
		    List<Course> coursesPessoa = new ArrayList<Course>();
		    ArrayList<Course> coursesDB = new ArrayList<Course>();
		    ArrayList<Localizacao> localizacoesDB = new ArrayList<Localizacao>();
		    
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
		    
		    for(Pessoa pessoa: pessoas) {
		    	//Foto de perfil
		    	JsonObject picture = facebookClient.fetchObject(pessoa.getId_usuario() + "/picture", JsonObject.class, Parameter.with("type", "normal"), Parameter.with("redirect", "false"));
		    	String url = picture.getJsonObject("data").getString("url");
		    
		    	if (pessoa.getUrl_foto() == null || !pessoa.getUrl_foto().equals(url)) {
		    		pessoa.setUrl_foto(url);
		    	
		    		pessoaServico.atualizaPessoaServico(pessoa, false);
		    	}
		    	
		    	//Courses
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
		    		
		    		Logger.insertLog("i--> " + i + " id--> " + course.getId_course());
		    	
		    		if (i == courseLimit - 1 || i == coursesPessoa.size() - 1){
				    	JsonObject jsonCourses = facebookClient.fetchObject("fitness.course", JsonObject.class, Parameter.with("ids", coursesStr));
				    	jsonCourses.remove("fitness.course");
				    	JsonArray jsonCoursesArray = jsonCourses.toJsonArray(jsonCourses.names());
				    	
				    	for( int j = 0; j < jsonCoursesArray.length(); j++){
				    		Logger.insertLog("j--> " + j);
				    		JsonObject jsonCourse = jsonCoursesArray.getJsonObject(j);
				    		Double distanceValueDbl = 0.0;
				    		Integer distanceValueInt = 0;
				    		Integer calories = 0;
				    		Double latitude = 0.0;
				    		Double longitude = 0.0;
				    		int altitude = 0;
				    		JsonObject data = new JsonObject();
//				    		Double pace = 0.0; 
				    		String courseId = "";
				    		try{
				    			courseId = jsonCourse.getString("id"); 
				    			
				    			data = jsonCourse.getJsonObject("data");
					    		
					    		JsonObject distance = data.getJsonObject("distance");
					    		
					    		try{
					    			distanceValueDbl = (Double) distance.get("value");
					    		} catch(ClassCastException e) {
					    			distanceValueInt = (Integer) distance.get("value");
					    		}
					    		
					    		calories = data.getInt("calories");
					    		
					    		
					    		
				    		} catch (JsonException e) {// Exceção lançada normalmente para os casos onde uma das chaves não existe 
				    			//Do nothing
				    		} finally {
				    			Course courseDB = new Course();
				    			
				    			courseDB.setId_course(courseId);
				    			
				    			if (distanceValueDbl != 0.0) { 
				    				courseDB.setDistancia(Float.parseFloat(distanceValueDbl.toString()));
				    			} else {
				    				courseDB.setDistancia(Float.parseFloat(distanceValueInt.toString()));
				    			}
				    			
				    			courseDB.setCalorias(calories);
				    			
				    			coursesDB.add(courseDB);

				    			// Localizacao
				    			try{
					    			JsonArray jsonMetrics = data.getJsonArray("metrics");
						    		
						    		for(int k = 0; k < jsonMetrics.length(); k++) {
						    			JsonObject metric = jsonMetrics.getJsonObject(k);
						    			
						    			try{
							    			JsonObject location = metric.getJsonObject("location");
							    			
							    			latitude =  location.getDouble("latitude");
							    			longitude = location.getDouble("longitude");
							    			altitude = location.getInt("altitude");
						    			
						    			} catch(JsonException e){
							    			//Do nothing
						    			} finally {
							    			Localizacao localizacaoDB = new Localizacao();
							    			localizacaoDB.setLatitude(latitude);
							    			localizacaoDB.setLongitude(longitude);
							    			localizacaoDB.setAltitude(altitude);
							    			localizacaoDB.setId_course(courseId);
//							    			localizacaoDB.setRitmo(pace);
							    			
							    			localizacoesDB.add(localizacaoDB);
						    			}
						    		}
				    			} catch(JsonException e){
				    				//do nothing
				    			}
					    		
				    		}
				    		
				    	}
				    	
				    	courseLimit += 50;
				    	coursesStr = "";
		    		}
		    	}
//		    	JsonObject jsonCourses = facebookClient.fetchObjects(coursesRequest, JsonObject.class);
		    }
		    
		    courseServico.atualizaListaCourses(coursesDB);
		    
//		    LocalizacaoServico.atualizaLista();

		    Logger.insertLog("---------------------------------------Job finalizado---------------------------------------");
		} catch(Exception e) {
			Logger.insertLog(e.getMessage());
	    }
	}
}
