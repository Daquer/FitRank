package br.com.fitrank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.fitrank.modelo.PostFitnessFB;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;

//@WebServlet("/RecuperaFitness")
public class RecuperaFitness extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RecuperaFitness() {
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}
	
    protected void inicia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	FacebookClient facebookClient = new DefaultFacebookClient(request.getParameter("token"));
    	String categoria = (String) request.getParameter("categoria");
    	String jsoupParam = (String) request.getParameter("jsoup");
    	String idRun = (String) request.getParameter("idRun");
    	
    	RequestDispatcher rd = request.getRequestDispatcher("/404.jsp");
    	if(idRun != null && !idRun.equals("")) {
    		getSingleRun(request, facebookClient, idRun);
    		rd = request.getRequestDispatcher("/mostraCorridas.jsp");
    	}
    	
		if(categoria != null && categoria.equals("runs")) {
//    			setRunAttributesGraphApi(facebookClient, request);
			setRuns(facebookClient, request);
			rd = request.getRequestDispatcher("/mostraCorridas.jsp");
		}
    	if(jsoupParam != null && jsoupParam.equals("true")) {
    		setRunAttributesJsoup(facebookClient, request);
    		rd = request.getRequestDispatcher("/mostraCorridas.jsp");
    	}
		request.setAttribute("token", request.getParameter("token"));
		rd.forward(request,response); 
    }
    


	private void getSingleRun(HttpServletRequest request, FacebookClient facebookClient, String idRun) {
		PostFitnessFB run = facebookClient.fetchObject(idRun, PostFitnessFB.class);
		
		setRunAttributesGraphApi(request, run);
	}


	private void setRuns(FacebookClient facebookClient,
			HttpServletRequest request) {
		Connection<PostFitnessFB> runsConnection = facebookClient.fetchConnection(request.getParameter("id") + "/fitness.runs", PostFitnessFB.class, Parameter.with("limit", "5"));
		List<PostFitnessFB> postsFit = new ArrayList<PostFitnessFB>();
		
		for(PostFitnessFB postFit : runsConnection.getData()) {
			postsFit.add(postFit);
		}
		
		request.setAttribute("runs", postsFit);
	}

	private void setRunAttributesJsoup(FacebookClient facebookClient, HttpServletRequest request) {
		// TODO Auto-generated method stub
    	PostFitnessFB run = facebookClient.fetchObject(request.getParameter("id"), PostFitnessFB.class);
//    	PostFitness run = runsConnection.getData().get(0);
    	
    	String appName = run.getApplication().getName();
    	String courseUrl = run.getDataCourse().getCourse().getUrl();
    	
    	try {
			Document doc = Jsoup.connect(courseUrl).get();
			
			
			if(appName.equals("Runtastic.com")) {
				String distance = doc.select(".value.distance").text();
				String duration = doc.select(".value.duration").text();
				String avgPace = doc.select(".value.avg_pace").text();
				String elevationGain = doc.select(".value.elevation_gain").text();
				String calories = doc.select(".value.calories").text();
				String heart_rate = doc.select(".value.heart_rate").text();
				String maxHeartRate = doc.select(".additional.value.max_heart_rate").text();
				String weather = doc.select(".add_info_icons>div:nth-of-type(1)").attr("title");
				String celsiusDegrees = doc.select(".add_info_icons>p").text();
				String placeKind = doc.select(".add_info_icons>div:nth-of-type(2)").attr("title");
				String evaluation = doc.select(".add_info_icons>div:nth-of-type(3)").attr("title");
				String avgSpeed = doc.select(".row.avg_speed>.value").text();
				
				String redLineHeartRate = doc.select(".zone.red_line>.value>.distance").text();
				String redLineHeartRateDuration = doc.select(".zone.red_line>.value>.duration").text();
				
				String anaerobicHeartRate = doc.select(".zone.anaerobic>.value>.distance").text();
				String anaerobicHeartRateDuration = doc.select(".zone.anaerobic>.value>.duration").text();
				
				String aerobicHeartRate = doc.select(".zone.aerobic>.value>.distance").text();
				String aerobicHeartRateDuration = doc.select(".zone.aerobic>.value>.duration").text();
				
				String fatBurningHeartRate = doc.select(".zone.fat_burning>.value>.distance").text();
				String fatBurningHeartRateDuration = doc.select(".zone.fat_burning>.value>.duration").text();
				
				String easyHeartRate = doc.select(".zone.easy>.value>.distance").text();
				String easyHeartRateDuration = doc.select(".zone.easy>.value>.duration").text();
				
				String noZoneHeartRate = doc.select(".zone.no_zone>.value>.distance").text();
				String noZoneHeartRateDuration = doc.select(".zone.no_zone>.value>.duration").text();
				
				
				request.setAttribute("jsoupExtraction",
				"distance -> " + distance + "<br />" + 
				"duration -> " + duration + "<br />" + 
				"avgPace -> " + avgPace + "<br />" + 
				"elevationGain -> " + elevationGain + "<br />" + 
				"calories -> " + calories + "<br />" + 
				"heart_rate -> " + heart_rate + "<br />" + 
				"maxHeartRate -> " + maxHeartRate + "<br />" + 
				"weather -> " + weather + "<br />" + 
				"celsiusDegrees -> " + celsiusDegrees + "<br />" + 
				"placeKind -> " + placeKind + "<br />" + 
				"evaluation -> " + evaluation + "<br />" + 
				"avgSpeed -> " + avgSpeed + "<br />" + 
				"redLineHeartRate -> " + redLineHeartRate + "<br />" + 
				"redLineHeartRateDuration -> " + redLineHeartRateDuration + "<br />" + 
				"anaerobicHeartRate -> " + anaerobicHeartRate + "<br />" + 
				"anaerobicHeartRateDuration -> " + anaerobicHeartRateDuration + "<br />" + 
				"aerobicHeartRate -> " + aerobicHeartRate + "<br />" + 
				"aerobicHeartRateDuration -> " + aerobicHeartRateDuration + "<br />" + 
				"fatBurningHeartRate -> " + fatBurningHeartRate + "<br />" + 
				"fatBurningHeartRateDuration -> " + fatBurningHeartRateDuration + "<br />" + 
				"easyHeartRate -> " + easyHeartRate + "<br />" + 
				"easyHeartRateDuration -> " + easyHeartRateDuration + "<br />" + 
				"noZoneHeartRate -> " + noZoneHeartRate + "<br />" + 
				"noZoneHeartRateDuration -> " + noZoneHeartRateDuration
				);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}


	private void setRunAttributesGraphApi(HttpServletRequest request, PostFitnessFB run) {
		
		request.setAttribute("oRunString", run.toString());
		request.setAttribute("courseUrl", run.getDataCourse().getCourse().getUrl());
		request.setAttribute("appName", run.getApplication().getName());
		request.setAttribute("attribution", run.getAttribution());
		request.setAttribute("caption", run.getCaption());
		request.setAttribute("commentsCount", run.getComments().getTotalCount().toString());
		request.setAttribute("createdTime", run.getCreatedTime());
		request.setAttribute("description", run.getDescription());
		request.setAttribute("endTime", run.getEndTime().toString());
		request.setAttribute("profileNameFrom", run.getFrom().getName());
		request.setAttribute("icon", run.getIcon());
		request.setAttribute("fbObjectId", run.getId());
		request.setAttribute("likesCount", run.getLikesCount().toString());
		request.setAttribute("link", run.getLink());
		request.setAttribute("message", run.getMessage());
		request.setAttribute("messageTags", run.getMessageTags().toString());
		request.setAttribute("metadata", run.getMetadata() == null ? "null" : run.getMetadata().toString());
		request.setAttribute("postName", run.getName());
		request.setAttribute("objectId", run.getObjectId());
		request.setAttribute("picture", run.getPicture());
		request.setAttribute("place", run.getPlace());
		request.setAttribute("privacy", run.getPrivacy());
		request.setAttribute("properties", run.getProperties().toString());
		request.setAttribute("publishTime", run.getPublishTime().toString());
		request.setAttribute("sharesCount", run.getSharesCount().toString());
		request.setAttribute("source", run.getSource());
		request.setAttribute("startTime", run.getStartTime().toString());
		request.setAttribute("statusType", run.getStatusType());
		request.setAttribute("story", run.getStory());
		request.setAttribute("to", run.getTo().toString());
		request.setAttribute("postType", run.getType());
		request.setAttribute("updatedTime", run.getUpdatedTime() == null ? "null": run.getUpdatedTime().toString());
		request.setAttribute("withTags", run.getWithTags().toString());
		request.setAttribute("noFeedStory", run.isNoFeedStory() ? "true" : "false");
	}

	

}
