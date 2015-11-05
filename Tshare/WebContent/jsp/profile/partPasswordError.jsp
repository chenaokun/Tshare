<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>changeName</title>
</head>
<body>
<div id="bill">
	<div>Passwords do not match.</div>
	<div>Please Enter Your New Password</div>
	<form action="../../updateProfile?subject=password" method="post" >
  			<input name="password1" type="password"><br>
  			<div>Re-enter</div>
  			<input name="password2" type="password"><br>
  			<div>&nbsp;</div>  			
			<input type="submit" value="submit">	      		
		</form>	
</div>	
</body>
</html>