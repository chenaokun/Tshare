<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
 <% User userP=(User)session.getAttribute("userInfo");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>updateSuccess</title>
</head>
<body>

<div id="summary">
	<h4>Your profile has been successfully updated!</h4>
	<div class="row">
    	<div class="col-sm-3">User ID</div>
    	<div class="col-sm-2"><%=userP.Id %></div>
    	<div class="col-sm-2"></div>    
	</div>
	<hr>
	<div class="row">
    	<div class="col-sm-3">User Name</div>
    	<div class="col-sm-2"><%=userP.name %></div>
    	<div class="col-sm-2">
	    	<form action="updateName.jsp" method="get">
	    	<button >Edit</button>
	    	</form>
    	</div>
    
	</div>
	<hr>
	<div class="row">
    	<div class="col-sm-3">Password</div>
    	<div class="col-sm-2">******</div>
    	<div class="col-sm-2">
	    	<form action="updatePassword.jsp" method="get">
	    	<button>Edit</button>
	    	</form>
    	</div>
   	</div>
   	<hr>
	<div class="row">
    	<div class="col-sm-3">Photo</div>
    	<%if(!userP.img.equals("0")) {    	%>
    	<div class="col-sm-2"><img src="https://s3-us-west-2.amazonaws.com/tshareavatar/<%=userP.Id+userP.img%>" height="42" width="42"></div>  
    	  <%} else{%>
    	<div class="col-sm-2"><img src="../../images/avatar.jpg" height="42" width="42"></div>    
    	  <%} %>
    	<div class="col-sm-2">
	    	<form action="updateImg.jsp" method="get">
	    	<button>Edit</button>
	    	</form>
    	</div>    
	</div>	
	<hr>
</div>	
</body>
</html>