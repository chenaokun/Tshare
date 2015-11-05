<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
 <% //User u=(User)session.getAttribute("userInfo");
 	//System.out.println("session received:"+u.Id);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome-page</title>
<link href="../../css/bootstrap.css" rel="stylesheet">
<link href="../../css/siderbar.css" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz:regular,bold&amp;subset=latin" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="../Header.html" %>
	
	<div id="content" class="container_12 lsb_grid_4">
		<div class="grid_8 push_4">
			<%@ include file="changeName.jsp" %>
		</div>
		<%@ include file="../Siderbar-left.jsp" %>
	</div>
	
	<%@ include file="../Footer.html" %>
</body>
</html>