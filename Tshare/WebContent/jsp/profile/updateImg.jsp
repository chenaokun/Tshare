<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome-page</title>
<!-- <script src="http://code.jquery.com/jquery-1.11.2.min.js" ></script>
<link href="../../css/bootstrap.css" rel="stylesheet">
<link href="../../css/siderbar.css" rel="stylesheet" type="text/css"> -->
<link href="http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz:regular,bold&amp;subset=latin" rel="stylesheet" type="text/css">
</head>

<body>
 	<%@ include file="../Header.html" %>
	
	<div id="content" class="container_12 lsb_grid_4">
		<div class="grid_8">
			<div id="pageshadow">
				<div id="header"> 
					<div class="title-wel">
                		<a>Update Profile Picture</a>
              		</div>
				</div>
			</div> 
			<%@ include file="changeImg.jsp" %>
		</div>
		<%@ include file="../Siderbar-left-forProfileImg.jsp" %> 
		
	</div>
	
	<%@ include file="../Footer.html" %>
</body>
</html>