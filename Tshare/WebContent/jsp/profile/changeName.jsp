<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>changeName</title>
</head>
<body>
<div id="summary">
	<div style="text-align:center">
		<h4>Please Enter Your New User Name</h4>
			<form action="../../updateProfile?subject=name" method="post" >
  				<input name="username" type="text"><br>
  				<div>&nbsp;</div>  			
				<input type="submit" value="submit">	      		
			</form>	
	</div>
</div>	
</body>
</html>