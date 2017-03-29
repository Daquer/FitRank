<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="br.com.fitrank.modelo.RankingPessoa" %>
	<%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="java.text.DecimalFormat" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
<!-- 		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta property="og:image" content="http://eic.cefet-rj.br/app/FitRank/ShareImg?id=<%= request.getParameter("idRanking") %>" />
<!-- 		<meta property="og:image:width" content="711"> -->
<!-- 		<meta property="og:image:height" content="315"> -->
		<meta property="og:image:width" content="671">
		<meta property="og:image:height" content="250">
		<meta property="og:image:type" content="image/png">
		<meta property="fb:app_id" content="749336888463283">
		<meta property="og:locale" content="pt_BR">
		<meta property="og:url" content="http://eic.cefet-rj.br/app/FitRank/VerRanking?idRanking=<%= request.getParameter("idRanking") %>" />
		<meta property="og:site_name" content="FitRank">
		<meta property="og:type" content="website" />
		<meta property="og:title" content="Ranking de Atividades Físicas de <%= (String) request.getAttribute("geradorRank") %> e amigos" />
		<meta property="og:description" content="Junte-se a <%= (String) request.getAttribute("geradorRank") %> neste jogo junto com seus amigos em busca de uma melhor qualidade de vida." />
		<meta name="theme-color" content="#6f3d94" />
		<title>Ranking - <%= (String) request.getAttribute("geradorRank") == null ?  "" : (String) request.getAttribute("geradorRank")%></title>
		<link rel="apple-touch-icon" sizes="60x60" href="/apple-touch-icon.png">
		<link rel="icon" type="image/png" href="/favicon-32x32.png" sizes="32x32">
		<link rel="icon" type="image/png" href="/favicon-16x16.png" sizes="16x16">
		<link rel="manifest" href="/manifest.json">
		<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">
		<meta name="apple-mobile-web-app-title" content="FitRank">
		<meta name="application-name" content="FitRank">
		<meta name="theme-color" content="#ffffff">
		<script type="text/javascript" src="js/jquery-1.11.2.js"></script>
		<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
		<script src="http://connect.facebook.net/pt_BR/all.js"></script>
<!-- 		<script src="js/introjs/intro.js" type="text/javascript"></script>	 -->
		<script src="js/hmtl2canvas/html2canvas.js" type="text/javascript"></script>
<!-- 		<script src="js/wheel-menu-master/jquery.wheelmenu.js" type="text/javascript"></script>	 -->
		<script type="text/javascript">
		
			var periodo = {
				"D":"Dia", 
				"S":"Semana",
				"M":"Mês", 
				"A":"Ano",
				"T":"Sempre"
			};
			
			var modo = {
				"D" : "Distancia",
				"V" : "Velocidade",
				"Q" : "Quantidade"
			};
			
			var modoMedidas = {
				"D" : "km",
				"V" : "km/h",
				"Q" : "Atividades"
			};
			
			var modoDescricao = {
				"D" : "Distância",
				"V" : "Velocidade Média",
				"Q" : "Quantidade"
			};
			
			var modalidade = {
				"R": "runs",
				"W": "walks",
				"B": "bikes",
				"A": "all"
			};
			
			var modalidadeDescricao = {
				"R": "Corrida",
				"W": "Caminhada",
				"B": "Ciclismo",
				"A": "Misto"
			};
			
			var configs = {
				"F": "Favorito",
				"R": "S",
				"D": "Descadastrar"
			};
			
			var configsDesc = {
				"C": "Configurações",
				"F": "Favorito",
				"R": "Recarregar atividades",
				"D": "Descadastrar"
			};
			
			var dadosAjax = {};
			
<%-- 			var token = '<%=(String) request.getParameter("token")%>'; --%>
			var token = '<%=(String) request.getAttribute("token")%>';
<%-- 			var ultimaPublicacao = '<%=(String) request.getAttribute("dataPostMaisRecente")%>'; --%>
			var ultimaPublicacao;
<%-- 			var idUsuario = '<%=(String) request.getAttribute("id")%>'; --%>
<%-- 			var json =  JSON.parse('<%=(String) response.getHeader("json")%>'); --%>
			var json;
			var debug;
			var modalidadeRequest = '<%=(String) request.getAttribute("modalidade")%>';
			var modoRequest = '<%=(String) request.getAttribute("modo")%>';
			var periodoRequest = '<%=(String) request.getAttribute("periodo")%>';
			
			//substituir por scriptlet java
// 			var idRanking = json["@items"][0]["id_ranking"]; //pega o id do ranking a partir do primeiro usuário, pois este sempre existirá
			var idRanking = <%=(String) request.getParameter("idRanking")%>;
			var verRanking = location.pathname.indexOf("VerRanking") !== -1 ? true : false; 
			var clickDisabled = false;
// 			function startTutorial() {
// 				var intro = introJs();
// 	 	          intro.setOptions({
// 	 	            steps: [
// 	 	              { 
// 	 	                intro: "Olá! Vimos que este é o seu primeiro login. Vamos à algumas instruções."
// 	 	              },
// 	 	              {
// 	 	                element: document.querySelector('.siteHeader'),
// 	 	                intro: "Aqui é o centro de configurações do FitRank. Vamos aos detalhes..."
// 	 	              },
// 	 	              {
// 	 	                element: document.querySelector('img.fav'),
// 	 	                intro: "Clique aqui quando quiser compartilhar com seus amigos do Facebook o ranking que você gerou."
// 		 	          },
// 	 	              {
// 	 	                element: document.querySelector(".modalidade:not(.opcao)"),
// 	 	                intro: "Aqui você escolhe entre as opções de modalidade que são Corrida, Caminhada e Ciclismo."
// 		 	          },
// 		 	          {
// 		 	            element: document.querySelector(".modo:not(.opcao)"),
// 		 	            intro: "Aqui você escolhe entre os modos que são Distância Percorida, Velocidade média e Quantidade de Atividades."
// 			 	      },
// 			 	      {
// 			 	      	element: document.querySelector(".periodo:not(.opcao)"),
// 			 	        intro: "Aqui você escolhe o período período desejado para o ranking até 1 ano."
// 				 	  }	
	 	              
// 	 	            ]
// 	 	          });
// 	 	          intro.start();
// 	 	          intro.onafterchange(function(targetElement) {
// 	 	        	  //alert("after new step");
// 	 	        	});
	 	        	  
	 	          
// // 				introJs().start();
// 			}
			
			function menuBubbleAnimationPrepare() {
			
				$(".menu").click(function(e) {
			   		if (clickDisabled)
			            return;
			   		
			   		clickDisabled = true;
			   		
			   		var itemClicado = $(this);
			   		
			   		var toHide = 0; 
			   		
			   		$(".menu").not($(this)).children(".opcao").each(function(index, el) {
			    		
			    		if($(el).css("display") !== 'none') {
			    			toHide++;
			    		}
					});
			   		
			   		var espera = toHide * (125 + 120) + ($(this).children(".opcao").length) * (125 + 120);
			   		
			   		setTimeout(function() {
			   			clickDisabled = false;
			   		}, espera);
			   		
			   		var time = 125;
			   		
			   		//Esconde as opcoes que estão aparecendo
		   			$(".menu").not($(this)).children(".opcao").each(function(index, el){
			    		
			    		if($(el).css("display") !== 'none') {
							setTimeout(function() {
								$(el).toggle('slide', {direction : 'down', duration: 120});
							}, time);
							
							time += 125;
			    		}
					});
			   		
		   			var mainDescriptionButtons = $(".menu").children(":not(.opcao)").children("[class*='chosen']");
			    	
		   			var spanDescChosen = $(this).children(":not(.opcao)").children("[class*='chosen']");

		   			//Esconde as descricoes que estão aparecendo
		   			$(mainDescriptionButtons).each(function(index, el){
		   				var isElEqualChosen =  $(spanDescChosen).attr('class') === $(el).attr('class'); 
		   				var isPeriod = $(el).hasClass("chosenPeriod");
		   				
		   				if ( !isElEqualChosen && $(el).css("display") !== "none" && !isPeriod) {
		   					
		   					$(this).toggle('slide', {direction : 'down', duration: 120});

		   				} else if( isElEqualChosen && $(spanDescChosen).css("display") !== "none" && !isPeriod) {
		   				
		   					$(spanDescChosen).toggle('slide', {direction : 'down', duration: 120});
		   					
		   					$(".headerContent").animate({
								marginBottom : "0"
							},120);
		   				} else if(isElEqualChosen && $(spanDescChosen).css("display") === "none") {
		   					$(spanDescChosen).toggle('slide', {direction : 'down', duration: 120});
		   					
		   					if(!$(spanDescChosen).hasClass("mainConfig")) {
			   					$(".headerContent").animate({
									marginBottom : "20px"
								},120);
		   					}
		   					
		   					
		   				}
		   				
		   				if (isElEqualChosen) {

							var marginTop =  $(".headerContent").css("margin-top");
							
							if ($(".periodo.opcao").first().css("display") == "none"  && isPeriod){
			   					$(".headerContent, .configWrapper").animate({
									marginTop : "50px"
								},120);
			   					
			   					if(!$(spanDescChosen).hasClass("mainConfig")) {
				   					$(".headerContent").animate({
										marginBottom : "0"
									},120);
			   					}
							} else {
								$(".headerContent, .configWrapper").animate({
									marginTop : "0"
								},120);
							}
		   				}
		   				
		   				
		   			});
		   			
// 		   			$(spanClassesChosen).toggle('slide', {direction : 'down', duration: 120});
		   			
// 		   			setTimeout(function() {
						
// //							if( !$(spanTextChosen).hasClass("chosenPeriod")) {
							
// //							}
						
// 						if($(".headerContent").css("margin-bottom") == 0 || $(".headerContent").css("margin-bottom") == '0px') { 
						
// 							$(".headerContent").animate({
// 								marginBottom : "20px"
// 							},120);
							
							
// 						} else {
// 							$(".headerContent").animate({
// 								marginBottom : "0"
// 							},120);
// 						}
						
// 					}, time);
					
// 					time += 125;
			   		
// 					if(!$(this).hasClass("periodoWrapper")){
						
// 						var spanTextChosen = $(this).children(":not(.opcao)").children("[class*='chosen']");
						
// 						if (spanTextChosen.css("display") === 'none' ) {
// 							spanTextChosen
// 							hiddened = $(this).children(".opcao");
							
// 						} else {
							
// 							hiddened = $(this).children(".opcao").get().reverse();
							
// 						}
// 					}
					
					//circulos de opcoes
			   		var hiddened;
					
					if ($(this).children(".opcao").css("display") === 'none' ) {
						hiddened = $(this).children(".opcao");
					} else {
						hiddened = $(this).children(".opcao").get().reverse();
					}
					
					
					$(hiddened).each(function(index, el){
						
						setTimeout(function() {
							$(el).toggle('slide', {direction : 'down', duration: 120});
							
						}, time);
						
						time += 125;
					});
					
				});
			   	
			   	$(".opcao").click(function() {
			   		var element = $(this);
			   		
			   		dadosAjax['token'] = token;
			   		dadosAjax['ajax'] = 'S';
			   		dadosAjax[element.attr('data-ref')] = element.children().attr('data-ref');
			   		dadosAjax[element.parent().siblings(".menu").first().children(":not(.opcao)").attr('data-ref')] = element.parent().siblings(".menu").first().children(":not(.opcao)").children(".bgSmall").attr('data-ref');
			   		dadosAjax[element.parent().siblings(".menu").eq(1).children(":not(.opcao)").attr('data-ref')] = element.parent().siblings(".menu").eq(1).children(":not(.opcao)").children(".bgSmall").attr('data-ref');
			   		dadosAjax[element.parent().siblings(".menu").eq(2).children(":not(.opcao)").attr('data-ref')] = element.parent().siblings(".menu").eq(2).children(":not(.opcao)").children(".bgSmall").attr('data-ref');
				   	//Troca os valores das propriedades de dados para os aceitos pelo controller.
			   		dadosAjax = prepareProperties(dadosAjax);
			   	
			   		if (dadosAjax['config'] == configs['F'] ) {
			   			
			   			$.ajax({
				   			url: location.origin + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "SalvaConfiguracao",
				   			data: dadosAjax,
				   			method: 'get',
				   			success: function( data, textStatus, jqXHR){
// 				   				$(".tableRank>tbody>.rankingLine").remove();

//					   				json = JSON.parse(data.responseText);
//									alert(data);
//					   				json = JSON.parse(jqXHR.getResponseHeader('json'));
				   				
// 				   				competidores = json["@items"];
				   				
// 				   				geraRanking(competidores, dadosAjax.modo);
				   			}
				   				
				   		});
				   		
			   		} else if (dadosAjax['config'] == configs['R']) {
			   			
			   			$.ajax({
				   			url: location.origin + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "CarregaRanking",
				   			data: dadosAjax,
				   			method: 'get',
				   			success: function( data, textStatus, jqXHR){
// 				   				$(".tableRank>tbody>.rankingLine").remove();
//					   				alert(jqXHR.getResponseHeader('msg'));
//					   				json = JSON.parse(jqXHR.getResponseHeader('json'));
				   				
// 				   				competidores = json["@items"];
				   				
// 				   				geraRanking(competidores, dadosAjax.modo);
				   			}
				   				
				   		});
			   		
			   		} else if(dadosAjax['config'] == configs['D']){
			   			descadastrar();//retira todas as permissões do usuário concedidas ao FitRank 	
			   		} else {
			   			
						var menu = element.siblings(":not(.opcao)").children(".bgSmall");
				   		
				   		var opcao = element.children();
				   		
				   		var menuDataRef = menu.attr("data-ref");
				   		
				   		var menuText = menu.siblings(".capsula").text();
				   		
				   		var opcaoDataRef = opcao.attr("data-ref");
				   		
				   		var opcaoText= opcao.children(".capsula").text();
	
				   		menu.removeClass(menuDataRef);
				   		
				   		opcao.removeClass(opcaoDataRef);
				   		
				   		menu.addClass(opcaoDataRef);
				   		
				   		menu.siblings(".capsula").text(opcaoText);
				   		
				   		menu.attr("data-ref", opcaoDataRef);
				   		
				   		opcao.addClass(menuDataRef);
				   		
				   		opcao.children(".capsula").text(menuText);
				   		
				   		opcao.attr("data-ref", menuDataRef);
	
				   		//para o caso de mudança de periodo
				   		menu.siblings(".chosenPeriod").text(opcaoDataRef);
				   		
				   		opcao.children(".descOpcao").text(menuDataRef);
				   		
				   		
	// 					if (opcaoDataRef == 'Semana') {
	// 						menu.siblings(".chosenPeriod").css("right", "-56px");
	// 			   		} else {
	// 			   			menu.siblings(".chosenPeriod").css("right", "-25px");
	// 			   		}
				   		
				   		
				   		$.ajax({
				   			url: location.origin + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "CarregaRanking",
				   			data: dadosAjax,
				   			method: 'get',
//					   			success: function( data, textStatus, jqXHR){
							complete: function(data, jqXHR, textStatus) {
				   				$(".tableRank>tbody>.rankingLine").remove();
				   				
				   				json = JSON.parse(data.responseText);
				   				
//					   				json = JSON.parse(jqXHR.getResponseHeader('json'));
				   				
				   				competidores = json["@items"];
				   				
				   				idRanking = competidores[0]["id_ranking"]; 
				   				
				   				geraRanking(competidores, dadosAjax.modo);
				   				
				   				appBubbleAnimationPrepare();
				   			}
				   				
				   		});
			   		}
			   		
			   		
			   		
			   	});	
			}
			   	
			function appBubbleAnimationPrepare() {
// 				$(".wheel-button").wheelmenu({
// 					  trigger: "click", // Can be "click" or "hover". Default: "click"
// 					  animation: "fly", // Entrance animation. Can be "fade" or "fly". Default: "fade"
// 					  animationSpeed: "fast", // Entrance animation speed. Can be "instant", "fast", "medium", or "slow". Default: "medium"
// 					  angle: "all", // Angle which the menu will appear. Can be "all", "N", "NE", "E", "SE", "S", "SW", "W", "NW", or even array [0, 360]. Default: "all" or [0, 360]
// 				});	
// 				return false;
				
				$(".moreAppsWrapper").click(function(e) {
			   		if (clickDisabled)
			            return;
			   		
			   		clickDisabled = true;
			   		
			   		var itemClicado = $(this);
			   		
			   		var toHide = 0; 
			   		
			   		$(this).not($(this)).children(".appOpcao").each(function(index, el) {
			    		if($(el).css("display") !== 'none') {
			    			toHide++;
			    		}
					});
			   		
			   		var espera = toHide * (125 + 120) + ($(this).children(".appOpcao").length) * (125 + 120);
			   		
			   		setTimeout(function() {
			   			clickDisabled = false;
			   		}, espera);
			   		
			   		var time = 125;
			   		
					//circulos de opcoes
			   		var hiddened;
					
					if ($(this).children(".appOpcao").css("display") === 'none' ) {
						hiddened = $(this).children(".appOpcao");
					} else {
						hiddened = $(this).children(".appOpcao").get().reverse();
					}
					
					
					$(hiddened).each(function(index, el){
						
						setTimeout(function() {
							$(el).toggle('slide', {direction : 'down', duration: 120});
							
						}, time);
						
						time += 125;
					});
					
				});
			}
			
			function showMsg(msg) {
				$('.error').text(msg).css("display", "").fadeIn(400);
			}
			
			function ajaxRanking() {
					   		
		   		dadosAjax['token'] = token;
		   		dadosAjax['ajax'] = 'S';
		   		dadosAjax['modo'] = modoRequest;
		   		dadosAjax['modalidade'] = modalidadeRequest;
		   		dadosAjax['periodo'] = periodoRequest;
	// 				   		dadosAjax[element.parent().siblings(".menu").eq(2).children(":not(.opcao)").attr('data-ref')] = element.parent().siblings(".menu").eq(2).children(":not(.opcao)").children(".bgSmall").attr('data-ref');
					   		
			   	$.ajax({
		   			url: location.origin + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "CarregaRanking",
		   			data: dadosAjax,
		   			method: 'get',
	//				   			success: function( data, textStatus, jqXHR){
					complete: function(data, jqXHR, textStatus) {
		   				$(".tableRank>tbody>.rankingLine").remove();
		   				
		   				debug = data.responseText;
		   				
		   				json = JSON.parse(data.responseText);
		   				
					   	ultimaPublicacao = data.getResponseHeader('dataPostMaisRecente');
					   	
						$("<span class='capsula descOpcaoConfig' style='font-size: 16px;' title='Última Atividade: " + ultimaPublicacao +"'></span>").text("Última Atividade: " + ultimaPublicacao).appendTo(".Recarregar.atividades");
						
		   				competidores = json["@items"];
		   				
		   				idRanking = competidores[0]["id_ranking"]; 
		   				
		   				geraRanking(competidores, dadosAjax.modo);
		   				
		   				appBubbleAnimationPrepare();
		   			}
		   				
		   		});
		   	}

			
			$(document).ready(function(){
// 				startTutorial();
				
			    $(document).ajaxStart(function () {
			        $("#loading").show();
			    }).ajaxStop(function () {
			        $("#loading").hide();
			    }).ajaxSuccess(function(e, data){
			    	if (data.getResponseHeader('msg')){
			    		showMsg(data.getResponseHeader('msg'));
	   				}
			    }).ajaxError(function (e, data) {
			    	if (data.getResponseHeader('msg')){
			    		showMsg(data.getResponseHeader('msg'));
	   				}
			    	$("#loading").hide();
			    });
			    
			    if ("<%= request.getAttribute("errorDescription") %>" != "null") {
					showMsg("<%= request.getAttribute("errorDescription") %>");	
				}
			    
			    if (verRanking) {
			    	$(".share").css("display","none");
			    	$(".config").css("display","none");
			    	$(".chosenModalidade,.chosenModo,.chosenPeriod").not(".descOpcao").css("display","block");
			    }
			    
				preparaConfiguracao();
// 			    preparaRanking();
			    
			    if (!verRanking) {
				    ajaxRanking();
			    	fixTitle();
			    	menuBubbleAnimationPrepare();
				} else {//VerRanking else
					dadosAjax['ajax'] = 'S';
					dadosAjax['idRanking'] = idRanking;
					dadosAjax['modo'] = modoRequest; 

					$.ajax({
			   			url: location.origin + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "VerRanking",
			   			data: dadosAjax,
			   			method: 'get',
//				   			success: function( data, textStatus, jqXHR){
						complete: function(data, jqXHR, textStatus) {
							$(".tableRank>tbody>.rankingLine").remove();
			   				
			   				json = JSON.parse(data.responseText);
							
			   				competidores = json["@items"];
			   				
			   				geraRanking(competidores, dadosAjax.modo);
			   				
			   				appBubbleAnimationPrepare();
			   			}
			   				
			   		});
			    }
			   	
			});
			
			//primeira execucao
			function preparaRanking() {		
				
				competidores = json["@items"];

				var modo = $(".modoWrapper").children(":not(.opcao)").children(".bgSmall").attr('data-ref').substring(0,1);
				
				geraRanking(competidores, modo);
				
			}
			
			function geraRanking(competidores, modoParam) {
				for(index in competidores){
   					var competidor = competidores[index];
   					
   					$(".tableRank>tbody").append("<tr class='rankingLine'></tr>");

   					var rankingLine = $( $(".rankingLine")[index] );
					
   					//Posição no Ranking, imagem de perfil e nome de perfil
   					rankingLine.append("<td class='colocacao'></td>");
   					rankingLine.append("<td class='profileImg'><a><img class='perfil' align='middle' ></a></td>");
   					
   					rankingLine.append("<td class='profileName'><a><span></span></a></td>");
   					
   					//Coluna de medidas do Ranking, definidos em configuração
   					rankingLine.append("<td class='measure'></td>");
   					
   					rankingLine.children(".measure").append("<span class='spanEmphasized'></span><br>");
   					rankingLine.children(".measure").append("<span class='not_emphasized secondSpan'><div class='circle bgTiny'></div></span><br>");
   					rankingLine.children(".measure").append("<span class='not_emphasized thirdSpan'><div class='circle bgTiny'></div></span>");
//    					rankingLine.children(".measure").append("<span class='not_emphasized appSpan'><div class='fitApp bgTiny' data-ref='Runtastic Mountain Bike'></div></span>");
   					
   					//Atribuição de valores aos elementos criados acima.
   					rankingLine.children(".colocacao").text(competidor.colocacao);
   					var profileImgTd = rankingLine.children(".profileImg").children("a").attr("href", "http://www.facebook.com/" + competidor.id_pessoa).attr("target", "_blank").children("img").attr("data-id_pessoa", competidor.id_pessoa);
   					profileImgTd.attr("src", competidor.pessoa.url_foto === null ? 'imagem/default_photo.png' : testImage(competidor.pessoa.url_foto, competidor.id_pessoa));
   					rankingLine.children(".profileName").children("a").attr("href", "http://www.facebook.com/" + competidor.id_pessoa).attr("target", "_blank").children("span").attr("data-id_pessoa", competidor.id_pessoa).text(competidor.pessoa.nome);
   					
   					//Resultado			   					
   					var resultadoLine = rankingLine.children(".measure").children("span");
   					
   					$(".modoTableHeader").text(modoDescricao[modoParam]);
   					
   					switch(modoParam){
   						case "V":
   							$(".secondSpan>div").addClass(modo["D"]);
   							$(".thirdSpan>div").addClass(modo["Q"]);
   							resultadoLine.first().text(competidor.resultado.toFixed(2) + " " + modoMedidas[modoParam] );
		   					$( resultadoLine[1] ).children("div").first().after(modoDescricao["D"] + " : " + competidor.distancia_percorrida.toFixed(2) + " " + modoMedidas["D"] );
		   					$( resultadoLine[2] ).children("div").first().after(modoDescricao["Q"] + " : " + competidor.quantidade_corridas + " " + modoMedidas["Q"] );
		   					break;
   						case "D" :
   							$(".secondSpan>div").addClass(modo["V"]);
   							$(".thirdSpan>div").addClass(modo["Q"]);
   							resultadoLine.first().text(competidor.resultado.toFixed(2) + " " + modoMedidas[modoParam] );
   							$( resultadoLine[1] ).children("div").first().after(modoDescricao["V"] + " : " + competidor.velocidade_media.toFixed(2) + " " + modoMedidas["V"] );
   							$( resultadoLine[2] ).children("div").first().after(modoDescricao["Q"] + " : " + competidor.quantidade_corridas + " " + modoMedidas["Q"] );
		   					break;
   						case "Q":
   							$(".secondSpan>div").addClass(modo["V"]);
   							$(".thirdSpan>div").addClass(modo["D"]);
   							resultadoLine.first().text(competidor.resultado + " " + modoMedidas[modoParam] );
   							$( resultadoLine[1] ).children("div").first().after(modoDescricao["V"] + " : " + competidor.velocidade_media.toFixed(2) + " " + modoMedidas["V"] );
   							$( resultadoLine[2] ).children("div").first().after(modoDescricao["D"] + " : " + competidor.distancia_percorrida.toFixed(2) + " " + modoMedidas["D"] );
   							break;
   					}
   					
   					var leftPos = 5;
   					var countApp = 1;
   					var maxAppsWithoutOverflow = 6;
   					var alturaOpcaoApp = 65; 
   					
   					if (competidor.listaAplicativosTela['@items'] !== undefined) {
						var itemsLength = competidor.listaAplicativosTela['@items'].length;
						var hiddenItemsLength = itemsLength - (maxAppsWithoutOverflow - 1);
						if (competidor.listaAplicativosTela['@items'] > maxAppsWithoutOverflow) {
							var hiddenItemsArray = competidor.listaAplicativosTela['@items'].slice(5, itemsLength);
   						}
   					}
   					
   					for (appTelaIndex in competidor.listaAplicativosTela['@items'] ) {
   						var appTela = competidor.listaAplicativosTela['@items'][appTelaIndex];
   						var appTelaNomeEscaped = escape(appTela.nome);
						
						
						if (itemsLength <= maxAppsWithoutOverflow) {
							rankingLine.children(".measure").append("<span class='not_emphasized appSpan' style='left:" + leftPos + "px'>" +
	   								"<div class='fitApp bgTiny' style='background-image: url(imagem/" + appTelaNomeEscaped +".png)' title='"+ appTela.nome +"'></div><span style='padding-left:40px;'>" + appTela.quantidadeAtividades + "</span></span>");
						} else {//Criar "dropdown" para evitar overflow
							if(countApp < maxAppsWithoutOverflow ) {
	   							rankingLine.children(".measure").append("<span class='not_emphasized appSpan' style='left:" + leftPos + "px'>" +
	   								"<div class='fitApp bgTiny' style='background-image: url(imagem/" + appTelaNomeEscaped +".png)' title='"+ appTela.nome +"'></div><span style='padding-left:40px;'>" + appTela.quantidadeAtividades + "</span></span>");

	   						
	   						} else if ( countApp >= maxAppsWithoutOverflow ){//Aplicativos no badge para evitar overflow
	   							if (countApp == maxAppsWithoutOverflow){ 
	   								rankingLine.children(".measure").append("<span class='not_emphasized appSpan' style='left:" + leftPos + "px'>" +
	   	   									"<div class='fitApp bgTiny moreAppsWrapper' style='background-image: url(imagem/ic_add_circle_black_24dp_2x.png)' title='+ " + hiddenItemsLength + " apps'></div><span class='badge'>" + hiddenItemsLength + "</span></span>");
// 		   							rankingLine.children(".measure").append("<span class='not_emphasized appSpan' style='left:" + 
// 		   									leftPos + "px'><div class='fitApp bgTiny' ><a href='#wheel" + appTelaIndex + "' class='wheel-button ne'>" +
// 		   										"<div style='background-image: url(imagem/ic_add_circle_black_24dp_2x.png)' title='testeTooltip'></div></a><ul id='wheel" + appTelaIndex + "' class='wheel'></ul><span class='badge'>" + hiddenItemsLength + "</span></span>");
	   							}
	   									
	   						  
// 									$("<li class='item'><a href='#home'><div class='fitApp bgTiny' style='background-image: url(imagem/" + appTelaNomeEscaped +".png)' title='"+ appTela.nome +"'></div></span></a></li>").appendTo("#wheel" + appTelaIndex);
   									//Adiciona os apps
   									
									$("<div class='fitApp bgTiny' style='background-image: url(imagem/" + appTelaNomeEscaped +".png)' data-id_pessoa = '" + competidor.id_pessoa + "' title='"+ appTela.nome +"'></div></span>")
									.appendTo( 
										$("<div></div>").appendTo(".moreAppsWrapper").addClass("appOpcao")
										.css("display", "none")
										.css("bottom" , alturaOpcaoApp)
									);
   									
									$("[title='" + appTela.nome + "'][data-id_pessoa=" + competidor.id_pessoa + "]").parent().append("<span class='fitOpcao'>" + appTela.quantidadeAtividades + "</span>");
   									
   									
   									alturaOpcaoApp += $(".opcao").height() * 2;
	   						}
						}
						
   						
   						
   						leftPos += 65;
   						countApp += 1;
   					}
   					
   				}	
   			
			}
			
			function genRankShare() {
				competidores = json["@items"];

				var modoParam = $(".modoWrapper").children(":not(.opcao)").children(".bgSmall").attr('data-ref').substring(0,1);
				
				$("body").append("<div class='rankShare'>");
				$(".rankShare").append("<table class='tableRankShare'>");
				$(".tableRankShare").append("<tbody>");
// 				$(".tableRankShare>tbody").append("<tr><th></th><th></th><th class='modoTableHeader'></th></tr>");
				
				for(index in competidores){
					
					if(index == 5) {
						break;
					}
					
   					var competidor = competidores[index];
   					
   					$(".tableRankShare>tbody").append("<tr class='rankingLine share'></tr>");

   					var rankingLine = $( $(".rankingLine")[competidores.length + parseInt(index)] );
					
   					//Posição no Ranking, imagem de perfil e nome de perfil
   					rankingLine.append("<td class='colocacao'></td>");
//    					rankingLine.append("<td class='profileImg'><a><img align='middle' ></a></td>");
   					
   					rankingLine.append("<td class='profileName'><a><span></span></a></td>");
   					
   					//Coluna de medidas do Ranking, definidos em configuração
   					rankingLine.append("<td class='measure share'></td>");
   					
   					rankingLine.children(".measure").append("<span class='spanEmphasized'></span><br>");
//    					rankingLine.children(".measure").append("<span class='not_emphasized secondSpan'><div class='circle bgTiny'></div></span><br>");
//    					rankingLine.children(".measure").append("<span class='not_emphasized thirdSpan'><div class='circle bgTiny'></div></span>");
//    					rankingLine.children(".measure").append("<span class='not_emphasized appSpan'><div class='fitApp bgTiny' data-ref='Runtastic Mountain Bike'></div></span>");
   					
   					//Atribuição de valores aos elementos criados acima.
   					rankingLine.children(".colocacao").text(competidor.colocacao);
//    					rankingLine.children(".profileImg").children("a").attr("href", "http://www.facebook.com/" + competidor.id_pessoa).attr("target", "_blank").children("img").attr("data-id_pessoa", competidor.id_pessoa).attr("src", competidor.pessoa.url_foto === null ? 'imagem/default_photo.png' : competidor.pessoa.url_foto );
   					rankingLine.children(".profileName").children("a").attr("href", "http://www.facebook.com/" + competidor.id_pessoa).attr("target", "_blank").children("span").attr("data-id_pessoa", competidor.id_pessoa).text(competidor.pessoa.nome);
   					
   					//Resultado			   					
   					var resultadoLine = rankingLine.children(".measure").children("span");
   					
   					//$(".modoTableHeader").text(modoDescricao[modoParam]);
   					
   					switch(modoParam){
   						case "V":
   							$(".secondSpan>div").addClass(modo["D"]);
   							$(".thirdSpan>div").addClass(modo["Q"]);
   							resultadoLine.first().text(competidor.resultado.toFixed(2) + " " + modoMedidas[modoParam] );
		   					$( resultadoLine[1] ).children("div").first().after(modoDescricao["D"] + " : " + competidor.distancia_percorrida.toFixed(2) + " " + modoMedidas["D"] );
		   					$( resultadoLine[2] ).children("div").first().after(modoDescricao["Q"] + " : " + competidor.quantidade_corridas + " " + modoMedidas["Q"] );
		   					break;
   						case "D" :
   							$(".secondSpan>div").addClass(modo["V"]);
   							$(".thirdSpan>div").addClass(modo["Q"]);
   							resultadoLine.first().text(competidor.resultado.toFixed(2) + " " + modoMedidas[modoParam] );
   							$( resultadoLine[1] ).children("div").first().after(modoDescricao["V"] + " : " + competidor.velocidade_media.toFixed(2) + " " + modoMedidas["V"] );
   							$( resultadoLine[2] ).children("div").first().after(modoDescricao["Q"] + " : " + competidor.quantidade_corridas + " " + modoMedidas["Q"] );
		   					break;
   						case "Q":
   							$(".secondSpan>div").addClass(modo["V"]);
   							$(".thirdSpan>div").addClass(modo["D"]);
   							resultadoLine.first().text(competidor.resultado + " " + modoMedidas[modoParam] );
   							$( resultadoLine[1] ).children("div").first().after(modoDescricao["V"] + " : " + competidor.velocidade_media.toFixed(2) + " " + modoMedidas["V"] );
   							$( resultadoLine[2] ).children("div").first().after(modoDescricao["D"] + " : " + competidor.distancia_percorrida.toFixed(2) + " " + modoMedidas["D"] );
   							break;
   					}
   					
   				}	
			}
			
			function prepareProperties(obj) {

				for (prop in obj) {
					
					switch(prop) {
						case 'modalidade' :
							for(mod in modalidade) {
								if( obj[prop] == modalidade[mod]) {
									obj[prop] = mod;
								}
							}
							break;
						case 'modo' :
							for(m in modo) {
								if( obj[prop] == modo[m]) {
									obj[prop] = m;
								}
							}
							break;
						case 'periodo' :
							for(per in periodo) {
								if( obj[prop] == periodo[per] && isNaN(per)) {
									obj[prop] = per;
								}
							}
							break;
							
						case 'config' :
							if( obj[prop] == 'Recarregar atividades') {
								obj[prop] = 'S';
							}
							
							break;
							
					}
					
				}
				
				
				return obj;
			}
			
			function preparaConfiguracao() {
				
				//modalidade
				$("<div></div>").appendTo(".modalidade").addClass(modalidade['<%=(String) request.getAttribute("modalidade")%>'].valueOf())
									.addClass("bgSmall")
									.attr("data-ref", modalidade["<%=(String) request.getAttribute("modalidade")%>"].valueOf());
				
				var alturaModalidade = $(".modalidade").height() * 2 - 10;
				
				$.map(modalidade, function(value, index) { 
					if( value != modalidade['<%=(String) request.getAttribute("modalidade")%>']) {
						var currModalidadeDescricao = modalidadeDescricao[index];
						
						//Adiciona as opcoes
						$("<span class='capsula descOpcaoModalidade'></span>").text(currModalidadeDescricao).appendTo(
								$("<div></div>").addClass(value).addClass('bgTiny').attr('data-ref', value).appendTo( 
									$("<div></div>").appendTo(".modalidadeWrapper").addClass("circle modalidade ranking smallTile opcao").attr('data-ref', 'modalidade').css("display", "none").css("bottom" , alturaModalidade).css("left", $(".modalidade").height())
								)
						);
						
						alturaModalidade += $(".opcao").height() * 2 + 15; 
					
					} else {
						$(".chosenModalidade").text(modalidadeDescricao['<%=(String) request.getAttribute("modalidade")%>']);
					}
				});
				
				//modo
				$("<div></div>").appendTo(".modo").addClass(modo['<%=(String) request.getAttribute("modo")%>'].valueOf())
									.addClass("bgSmall")
									.attr("data-ref", modo["<%=(String) request.getAttribute("modo")%>"].valueOf());
				
				var alturaModo = $(".modo").height() * 2 - 10;
				
				$.map(modo, function(value, index) { 
					if( value != modo['<%=(String) request.getAttribute("modo")%>']) {
						var currModoDescricao = modoDescricao[index]; 
						//Adiciona as opcoes
						$("<span class='capsula descOpcaoModo'></span>").text(currModoDescricao).appendTo(
							$("<div></div>").addClass(value).addClass('bgTiny').attr('data-ref', value).appendTo( 
								$("<div></div>").appendTo(".modoWrapper").addClass("circle modo ranking smallTile opcao").attr('data-ref', 'modo').css("display", "none").css("bottom" , alturaModo).css("left", $(".modo").height())
							)
						);
					
						alturaModo += $(".opcao").height() * 2 + 15;
					} else {
						$(".chosenModo").text(modoDescricao['<%=(String) request.getAttribute("modo")%>']);
					}
				});
				
				//periodo
				$("<div></div>").prependTo(".periodo").addClass("calendario").addClass("bgSmall")
							.attr("data-ref", periodo["<%=(String) request.getAttribute("periodo")%>"].valueOf());
				
				var alturaPeriodo = $(".periodo").height() * 2 - 10;
				
				$(".chosenPeriod").text(periodo['<%=(String) request.getAttribute("periodo")%>']);

<%-- 				if (periodo['<%=(String) request.getAttribute("periodo")%>'] == 'Semana') { --%>
// 					$(".chosenPeriod").css("right", "-56px");
// 		   		} else {
// 		   			$(".chosenPeriod").css("right", "-25px");
// 		   		}
				
				
				$.map(periodo, function(value, index) { 
					var currPeriodo = periodo[index];	
				
					if(isNaN(index) && value !== periodo['<%=(String) request.getAttribute("periodo")%>'] ) {
						
						//Adiciona as opcoes
						$("<span class='capsula chosenPeriod descOpcao'></span>").text(currPeriodo).appendTo(
								$("<div></div>").addClass(value).addClass('calendario bgTiny').attr('data-ref', value).appendTo( 
									$("<div></div>").appendTo(".periodoWrapper").addClass("circle periodo ranking smallTile opcao").attr('data-ref', 'periodo').css("display", "none").css("bottom" , alturaPeriodo).css("left", $(".periodo").height())
							)
						);
						
						alturaPeriodo += $(".opcao").height() * 2 + 15;
					}
				});
				
				//Configuracao
				$("<div></div>").appendTo(".config").addClass("bgSmall gearIcon").attr("data-ref", "config");
				
				var alturaConfig = $(".config").height() * 2 - 10;
				
				$.map(configsDesc, function(value, index) { 
					if( value != configsDesc['C']) {
						var currConfigDescricao = configsDesc[index];
						
						//Adiciona as opcoes
						$("<span class='capsula descOpcaoConfig'></span>").text(currConfigDescricao).appendTo(
								$("<div></div>").addClass(value).addClass('bgTiny').attr('data-ref', value).appendTo( 
									$("<div></div>").appendTo(".configWrapper").addClass("circle config ranking smallTile opcao").attr('data-ref', 'config').css("display", "none").css("bottom" , alturaConfig).css("left", $(".config").height())
								)
						);
						
						alturaConfig += $(".opcao").height() * 2 + 15; 
					
					
					} else {
						$(".mainConfig").text(configsDesc['C']);
					}
					
// 					if (value == configsDesc['R']) {
// 						$("<span class='capsula descOpcaoConfig' style='font-size: 16px;' title='Última Atividade: " + ultimaPublicacao +"'></span>").text("Última Atividade: " + ultimaPublicacao).appendTo(".Recarregar.atividades");
// 					}
				});
				
				
			} 
			
			//TODO Tratar User code #17
			function fixTitle() {
				if( token != "null") {
					FB.api('/me', 'GET',
						{ 
							"fields" : "name", 
							"access_token" : token
						},
						function(response) {
							if (response.name && $("title").text().split(" - ")[1] == "") {
								$("title").text("Ranking - " + response.name);
							}
						}
					);
				}
			}
			
			function descadastrar(){
				if( token != "null") {
					FB.api('/me/permissions', 'DELETE',
						{ 
							"access_token" : token
						},
						function(response) {
							if (!response || response.error) {
								showMsg("Ocorreu algum erro desconhecido.");
							  } else {
							    alert('Você foi descadastrado com sucesso do FitRank');
							    window.location = "http://eic.cefet-rj.br/app/FitRank/";
							  }
						}
					);
				}
			}
// 			function renderLastUpdate(competidores) {
// 				var myId = getOwnId();
// 				switch($('.chosenModalidade~[data-ref]').attr('data-ref')) {
// 					case 'all':
// 						break;
// 					case 'runs':
// 						break;
// 					case 'walks':
// 						break;
// 					case 'bikes':
// 						break;
// 					default:
// 						return false;
// 				}
// 				var propModalidade =   
					
// 				for(index in competidores)
				
// 			}

			function testImage(url, idPessoa, timeout) {
			    timeout = timeout || 5000;
			    var timedOut = false, timer;
			    var img = new Image();
			    
			    img.onerror = img.onabort = function() {
			        if (!timedOut) {
			            clearTimeout(timer);
			            
			        }
			        
			        
			        if( token != "null") {
				        FB.api(
	 					  '/' + idPessoa + '/picture',
	 					  'GET',
	 					  { 
	 						"type" : "normal", 
	 					  	"access_token" : token
						  },
	 					  function(response) {
							  $("[data-id_pessoa = " + idPessoa + "]").attr("src",response.data.url);
							  
							  var data = {};
							  data["id"] = idPessoa;
							  data["url"] = response.data.url;
							  
							  jQuery.ajax({
								    url: location.origin + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "AtualizaFotoPessoa",
								    data: data,
									async: false,
								    type: 'POST',
								    success: function(data){
								    	//Foto Atualizada
								    }
								});
	 					  }
	 					);
			        } else {
			        	$("[data-id_pessoa = " + idPessoa + "]").attr("src", "imagem/default_photo.png");
			        }
			    };
			    
			    img.onload = function() {
			        if (!timedOut) {
			            clearTimeout(timer);
			        }
			        $("[data-id_pessoa = " + idPessoa + "]").attr("src", url);
			    };
			    
			    img.src = url;
			    
			    timer = setTimeout(function() {
			        timedOut = true;
			        // reset .src to invalid URL so it stops previous
			        // loading, but doesn't trigger new load
			        img.src = "//!!!!/test.jpg";
			    }, timeout); 
			}// Code at http://stackoverflow.com/questions/9714525/javascript-image-url-verify/9714891#9714891
// 			function buscaInformacoesPerfil(element) {
// 				var idUsuario = $(element).attr("data-id_pessoa");
				
// 				FB.api(
// 					  '/' + idUsuario + '/picture',
// 					  'GET',
// 					  { "type" : "normal", 
<%-- 						"access_token" : '<%=(String) request.getParameter("token")%>'}, --%>
// 					  function(response) {
// 						  $(element).attr("src",response.data.url)
// 					  }
// 					);
				
// 				FB.api(
// 					  '/' + idUsuario ,
// 					  'GET',
// 					  { "fields" : "name", 
<%-- 						"access_token" : '<%=(String) request.getParameter("token")%>'}, --%>
// 					  function(response) {
// 						  $($(element).next("span")[0]).text(response.name);
// 					  }
// 					);
// 			}
			
// 			function buscaNomePerfil(element) {
// 				var elemento = $(element).parent().next().children()[0];
// 				var idUsuario =  $(elemento).attr("data-id_pessoa");
				
// 				FB.api(
// 					  '/' + idUsuario ,
// 					  'GET',
// 					  { "fieds" : "name", 
<%-- 						"access_token" : '<%=(String) request.getParameter("token")%>'}, --%>
// 					  function(response) {
// 						  $(elemento).text(response.name);
// 					  }
// 					);
// 			}
			
			function compartilhar() {
				var authWindow = window.open('about:blank', "fb_share", "width=500, height=500");
				
				genRankShare();
				
				html2canvas($(".tableRankShare"), {
				  logging: true,
				  onrendered: function(canvas) {
				    var data = {};
				    var image = new Image();
					image.src = canvas.toDataURL("image/png");
					$(".rankShare").remove();
// 					$(".tableRankShare>tbody>tr").first().remove();
				    data["img64"] = image.src;
				    data["idRanking"] = idRanking;  
					
					jQuery.ajax({
					    url: location.origin + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "UploadImg",
					    data: data,
						async: false,
					    type: 'POST',
					    success: function(data){
					    	authWindow.location.replace("https://www.facebook.com/dialog/share?app_id=749336888463283&display=popup&href=http://eic.cefet-rj.br/app/FitRank/VerRanking?idRanking=" + idRanking + "&redirect_uri=http://eic.cefet-rj.br/app/FitRank/self_close.jsp&caption=eic.cefet-rj.br/app/FitRank/","fb_share", "width=500, height=500");
					    }
					});
					
				  }
				
				});
			}
		</script>
		<link rel="stylesheet" type="text/css" href="./style/css/FitRank.css">
<!-- 		<link rel="stylesheet" type="text/css" href="./js/introjs/introjs.css"> -->
<!-- 		<link rel="stylesheet" type="text/css" href="./js/wheel-menu-master/wheelmenu.css"> -->
	</head>

	<body>
		<div class="wrapper">
			<div class="preheader">
				<div class='error' style='display:none;' onclick="$(this).fadeOut(400);"></div>
			</div>
			<div class="content">
				<div class="headerContent rankingHeader">
					<div class="siteHeader" data-step="1" data-intro="ola">
						<div>
							<span class="logo" onClick="window.location = './'" style="cursor: pointer;"> 
								FitRank
							</span>
							<div class="share" onclick="compartilhar()"> 
								<img class="share" src="imagem/social24.png" style="border-radius: 50%;background-color: rgb(191, 230, 231);" />
							</div>
						<div class="modalidadeWrapper menu">
							<div class="circle modalidade ranking smallTile" data-ref="modalidade">
								<span class="capsula chosenModalidade"></span>
							</div>
						</div>
						
						<div class="modoWrapper menu">
							<div class="circle modo ranking smallTile " data-ref="modo">
								<span class="capsula chosenModo"></span>
							</div>
						</div>
	
						<div class="periodoWrapper menu">
							<div class="circle periodo ranking smallTile" data-ref="periodo">
	  	 						<span class="capsula chosenPeriod"></span>
	  	 					</div>
						</div>
						<div class="configWrapper menu">
							<div class="circle config ranking smallTile" data-ref="config">
								<span class="capsula chosen mainConfig"></span>
	  	 					</div>
						</div>
					</div>
					</div>
					<div class="footer">
						<a target="_blank" href="creditoImagens.jsp" style="position: relative;top: 45px;left: 25px;">Autoria das imagens</a>
					</div>
				</div>
				<div class="ranks">
					<table class="tableRank">
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th class="modoTableHeader"></th>
					</table>
				</div>
			</div>
		</div>
		<div id="loading" style="display:none;background-image:"></div>
	</body>
</html>