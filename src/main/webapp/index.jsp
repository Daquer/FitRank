<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="theme-color" content="#6f3d94" />
<meta property="og:url"           content="http://eic.cefet-rj.br/app/FitRank/" />
<meta property="og:type"          content="website" />
<meta property="og:title"         content="FitRank" />
<meta property="og:description"   content="Um novo meio de interação para pessoas que praticam atividades físicas.O FitRank reúne atividades de diferentes apps gerar rankings com seus amigos!" />
<meta property="og:image"         content="http://eic.cefet-rj.br/app/FitRank/icon-730x250.png" />
<meta property="og:image:width"	  content="805">
<meta property="og:image:height"  content="250">
<meta property="fb:app_id" content="749336888463283">
<title>FitRank</title>
<link rel="stylesheet" type="text/css" href="./style/css/FitRank.css">
<link rel="apple-touch-icon" sizes="60x60" href="/apple-touch-icon.png">
<link rel="icon" type="image/png" href="/favicon-32x32.png" sizes="32x32">
<link rel="icon" type="image/png" href="/favicon-16x16.png" sizes="16x16">
<link rel="manifest" href="/manifest.json">
<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">
<meta name="apple-mobile-web-app-title" content="FitRank">
<meta name="application-name" content="FitRank">
<meta name="theme-color" content="#ffffff">
<script src="./js/jquery-1.11.2.js"></script>
<script src="http://connect.facebook.net/pt_BR/all.js"></script>
<script src="//use.typekit.net/srs6muz.js" async=""></script>
<script>
	$(document).ready(function() {
			if("<%= request.getAttribute("errorDescription") %>" != "null") {
				$('.error').text("<%= request.getAttribute("errorDescription") %>").fadeIn(400);
// 				.delay(3000).fadeOut(400);	
			} 
	
			FB.init({
				appId : '749336888463283', //Id do aplicativo ()
				status : true, // verifica status do login
				cookie : true, // habilita cookies para permitir acesso via servidor
				xfbml : true, // habilita parser XFBML
				version : 'v2.8'
			});

			$('#entra').on("click",
				function entra() {
					// 				FB.login(function(){
	
					/*FB.login(function(response) {
					alert(response.status);
					}, {scope: 'email,user_likes,user_actions.fitness'} );*/
					var token = "";
					FB.getLoginStatus(function(response) {
								
						if (response.status === 'connected') {
	
							token = response.authResponse.accessToken;
							
// 							window.location = location.origin
// 									+ location.pathname
// 									+ "InitUser?token="
// 									+ token;
							
							$("#token").val(token);
							$("#index").val("S");
							
							$("#formSubmit").submit();

							// 					        }else if(response.status === 'not_authorized'){

							// 					          alert("Não autorizado");

						} else {

							FB.login(function(response) {
									if (response.authResponse) {
										token = response.authResponse.accessToken;
										
										$("#token").val(token);
										$("#index").val("S");
										
										$("#formSubmit").submit();
									
// 										alert(response.session);
// 										alert("entrou");
// 										$(
// 												'#fb_login_form')
// 												.submit();
										
											token = response.authResponse.accessToken;
											 
// 											window.location = location.origin
// 													+ location.pathname
// 													+ "InitUser?token="
// 													+ token;
											
											$("#token").val(token);
											
											$("#formSubmit").submit();

										
									} else {
										console
												.log("O usuário não permitiu acesso aos dados!");
									}

// 									if (response.status == "connected"
// 											&& response.authResponse) {
// 										token = response.authResponse.accessToken;
// 										window.location = location.origin
// 												+ location.pathname
// 												+ "InitUser?token="
// 												+ token;

// 										$("#token").val(token);
										
// 										$("#formSubmit").submit();
// 									}

								},
								{
									scope : 'email,user_friends,user_actions.fitness'
								});//user_birthday
							// 					        	if(!popup) { 
							// 									   //an alert in this example
							// 									   alert('Parece que seu navegador está bloqueando o popup para autorizar a nossa conexão com o Facebook. \nPara continuar será necessário desabilitar o bloqueio.');
							// 									}
						}
					});
	
				});
		});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="preheader"></div>
		<div class="content">
			<div class="headerContent">
				<div class="siteHeader">
					<div>
						<div class="logo">FitRank</div>
					</div>
					<div>
					
						<p class="text" style="text-align: center;">Para socializar as suas atividades físicas usando o FitRank </p>
						<p class="text" style="text-align: center;">é necessário se logar com a conta do Facebook.</p>
						<img src="imagem/FB_Login.png" id="entra" style="cursor: pointer; margin: 0 auto; margin-top: 35px; display: block; border: none; width: 288px; height: 62px;" />
						<div class="fb-like" data-href="https://www.facebook.com/fitrank.go" style="margin: 0 auto; margin-top: 35px; display: block; width: 275px;" data-width="275" data-layout="standard" data-action="like" data-size="large" data-show-faces="true" data-share="true"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class='error' style='display:none' onclick="$(this).fadeOut(400);"></div>
		</div>
	</div>
	<div id="fb-root"></div>
	<form action="Main" id="formSubmit" style="display: none;" method="post">
    	<input type="hidden" id="token" name="token" />
	</form>
</body>
</html>
