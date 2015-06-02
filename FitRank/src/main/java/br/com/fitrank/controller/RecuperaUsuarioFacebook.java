package br.com.fitrank.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fitrank.modelo.Usuario;
import br.com.fitrank.service.UsuarioServico;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;


//@WebServlet("/RecuperaUsuarioFacebook")
public class RecuperaUsuarioFacebook extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
    public RecuperaUsuarioFacebook() {
      
    }

   
  protected void inicia(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
//	  Document doc = Jsoup.connect("https://www.runtastic.com/en/users/jr-cefet/sport-sessions/355090258").get();
//	  Elements newsHeadlines = doc.select("#mp-itn b a");
	   
	   FacebookClient facebookClient = new DefaultFacebookClient(request.getParameter("token"));
	   User facebookUser = facebookClient.fetchObject("me", User.class);
	   Connection<User> friendsFB = facebookClient.fetchConnection("me/friends", User.class, Parameter.with("fields", "name, id, picture"));

//	   String friendsRuns = "";
//	   String runsJson = "{\"data\":[";
//	   String runsJson = "";
	   String[] profPicUrls = new String[friendsFB.getData().size()];
	   String[] ids = new String[friendsFB.getData().size()];
	   for (int i=0;i < friendsFB.getData().size(); i++) {
		  //String nome = friendsFB.getData().get(i).getName();
		  String id = friendsFB.getData().get(i).getId();
		  ids[i] = id;
		  profPicUrls[i] = friendsFB.getData().get(i).getPicture() != null ? friendsFB.getData().get(i).getPicture().getUrl() : "imagem/401.png" ;
		 
		  
		  //JsonObject runsConnection = facebookClient.fetchObject(id + "/fitness.runs", JsonObject.class);
		  
		  /*if (runsConnection.getJsonArray("data").length() != 0 ) {
			  for(int j=0;j < runsConnection.getJsonArray("data").length(); j++) {
				  
				  for(int k=0;k < runsConnection.getJsonArray("data").getJsonObject(j).names().length();k++) {
					  runsJson = runsJson + runsConnection.getJsonArray("data").getJsonObject(j).names().get(k) + "<br>";
				  }
				  runsJson = runsJson + "-------<br>";
//				  runsJson = runsJson + runsConnection.getJsonArray("data").getJsonObject(j).names();
//				  if(j == (runsConnection.getJsonArray("data").length() - 1)) {
//					  runsJson = runsJson + runsConnection.getJsonArray("data").getJsonObject(j);
//				  } else {
//					  runsJson = runsJson + runsConnection.getJsonArray("data").getJsonObject(j) + ",";
//				  }
			  }
		  }*/
	   }
//	   runsJson = runsJson + "]}";
	   
	   Usuario usuario = new Usuario();
	   
	   UsuarioServico usuarioServico = new UsuarioServico();
	   usuario = usuarioServico.persisteUsuarioServico(facebookUser);
       
	   
//	   request.setAttribute("nome_usuario", usuario.getNome());
//	   request.setAttribute("id_usuario", usuario.getId());
//	   request.setAttribute("email_usuario", usuario.getEmail());
//	   request.setAttribute("dtnasc_usuario", usuario.getDataNascimento());
	  /* request.setAttribute("friendsCount", friendsFB.getData().size());
	   request.setAttribute("friendsNames", friendsName);
	   request.setAttribute("myPosts", postsString);
	   */
	   request.setAttribute("token", request.getParameter("token"));
	   request.setAttribute("ids", ids);
	   request.setAttribute("profPicUrl", profPicUrls);
	   
	   
	   RequestDispatcher rd = request.getRequestDispatcher("/escolheUsuario.jsp");  
	   rd.forward(request,response);  
   }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}

}
