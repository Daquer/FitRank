<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.fitrank.modelo.PostFitnessFB" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Corridas</title>
</head>
<body>
<%
	String token = (String) request.getAttribute("token");

String courseUrl = (String) request.getAttribute("courseUrl");
String appName = (String) request.getAttribute("appName");
String caption = (String) request.getAttribute("caption");
String commentsCount = (String) request.getAttribute("commentsCount");
String createdTime = (String) request.getAttribute("createdTime");
String description = (String) request.getAttribute("description");
String endTime = (String) request.getAttribute("endTime");
String profileNameFrom = (String) request.getAttribute("profileNameFrom");
String icon = (String) request.getAttribute("icon");
String fbObjectId = (String) request.getAttribute("fbObjectId");
String likesCount = (String) request.getAttribute("likesCount");
String link = (String) request.getAttribute("link");
String message = (String) request.getAttribute("message");
String messageTags = (String) request.getAttribute("messageTags");
String metadata = (String) request.getAttribute("metadata");
String postName = (String) request.getAttribute("postName");
String objectId = (String) request.getAttribute("objectId");
String picture = (String) request.getAttribute("picture");
String place = (String) request.getAttribute("place");
String privacy = (String) request.getAttribute("privacy");
String properties = (String) request.getAttribute("properties");
String publishTime = (String) request.getAttribute("publishTime");
String sharesCount = (String) request.getAttribute("sharesCount");
String source = (String) request.getAttribute("source");
String startTime = (String) request.getAttribute("startTime");
String statusType = (String) request.getAttribute("statusType");
String story = (String) request.getAttribute("story");
String to = (String) request.getAttribute("to");
String postType = (String) request.getAttribute("postType");
String updatedTime = (String) request.getAttribute("updatedTime");
String withTags = (String) request.getAttribute("withTags");
String noFeedStory = (String) request.getAttribute("noFeedStory");

List<PostFitnessFB> postsFit = (ArrayList<PostFitnessFB>) request.getAttribute("runs");

String jsoupExtraction = (String) request.getAttribute("jsoupExtraction");

if(postsFit !=null) {
	for(int i = 0 ; i < postsFit.size(); i++) {
		out.println("<a href=\"RecuperaFitness?idRun=" + postsFit.get(i).getId() + "&token=" + token + "\" > Corrida " + (i + 1) + "</a><br />");
	}
} 
// String oRunString = (String) request.getAttribute("oRunString");

//
%>

<%-- Run object String: <br /><br /><%=oRunString%><br /><br /> --%>
<%if(postsFit ==null && jsoupExtraction==null) { %>
	<a href="<%=courseUrl%>"><%=courseUrl%></a><br /><br />
	<button onclick="window.location = 'RecuperaFitness?token=<%=token%>&jsoup=true&id=<%=fbObjectId%>'" >Jsoup this Course!</button><br /><br />
	<b>App: </b><%=appName%><br /><br /> 
	<b>Caption: </b><%=caption%><br /><br /> 
	<b>commentsCount: </b><%=commentsCount%><br /><br /> 
	<b>createdTime: </b><%=createdTime%><br /><br /> 
	<b>description: </b><%=description%><br /><br /> 
	<b>endTime: </b><%=endTime%><br /><br /> 
	<b>profileNameFrom: </b><%=profileNameFrom%><br /><br /> 
	<b>icon: </b><%=icon%><br /><br /> 
	<b>fbObjectId: </b><%=fbObjectId%><br /><br /> 
	<b>likesCount: </b><%=likesCount%><br /><br /> 
	<b>link: </b><%=link%><br /><br /> 
	<b>message: </b><%=message%><br /><br /> 
	<b>messageTags: </b><%=messageTags%><br /><br /> 
	<b>metadata: </b><%=metadata%><br /><br /> 
	<b>postName: </b><%=postName%><br /><br /> 
	<b>objectId: </b><%=objectId%><br /><br /> 
	<b>picture: </b><%=picture%><br /><br /> 
	<b>place: </b><%=place%><br /><br /> 
	<b>privacy: </b><%=privacy%><br /><br /> 
	<b>properties: </b><%=properties%><br /><br /> 
	<b>publishTime: </b><%=publishTime%><br /><br /> 
	<b>sharesCount: </b><%=sharesCount%><br /><br /> 
	<b>source: </b><%=source%><br /><br /> 
	<b>startTime: </b><%=startTime%><br /><br /> 
	<b>statusType: </b><%=statusType%><br /><br /> 
	<b>story: </b><%=story%><br /><br /> 
	<b>to: </b><%=to%><br /><br /> 
	<b>postType: </b><%=postType%><br /><br /> 
	<b>updatedTime: </b><%=updatedTime%><br /><br /> 
	<b>withTags: </b><%=withTags%><br /><br /> 
	<b>noFeedStory: </b><%=noFeedStory%><br /><br /> 
<%} else if (jsoupExtraction!=null) {%>
	<%=jsoupExtraction %>
<%} %>
</body>
</html>