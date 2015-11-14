<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>register_success</title>
<link href="../css/bootstrap-3.3.4.css" rel="stylesheet" type="text/css">
<link href="../css/Sign-in.css" rel="stylesheet" type="text/css">
<link href="../css/siderbar.css" rel="stylesheet" type="text/css">
</head>
<body>
	
	<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#defaultNavbar1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
      <a class="navbar-brand" href="#">Tshare</a></div>
    
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="defaultNavbar1">
<ul class="nav navbar-nav navbar-right">
  <li></li>
  <li>      </li>
  <li></li>
</ul>
    </div>
</div>
    <div>
    </div>    
  <!-- /.container-fluid -->
</nav>
<div id = "sign-box" class="container-fluid">
	<div class="signin">
  		<p>&nbsp;</p>
  		<h2>Welcome!</h2>
  		<p>&nbsp;</p>
	</div>
	
	<div class="signin">
		<h5>You have successfully signed up. Please log in.</h5>
		<p>&nbsp;</p>
		<form action="../logIn" method="get" >
  			<div class="h5">User Name</div>
  			<input name="usrname" type="text"><br>
  			<div class="h5">
				<p class="h5">Password</p>
   			</div>
  			<input type="password" name="password"><br>
  			<div>&nbsp;</div>  			
			<input type="submit" class="submit" value="Submit" >	      		
		</form>
	</div>
</div>
<div id="credit" class="container_12">
    	<p>&nbsp;</p>
    	<p> @Copyright Titan</p>
  	</div>
<script src="../js/jquery-1.11.2.min.js" type="text/javascript"></script>
<script src="../js/bootstrap-3.3.4.js" type="text/javascript"></script>
<script>


function saveImg(){
	
	if(sessionStorage.haveImage){
	usrname=sessionStorage.usrname;
	img=sessionStorage.img;
	var ajax = new XMLHttpRequest();
	var postData = "canvasData="+encodeURIComponent(img);		    
	ajax.open("POST",'../uploadToS3?usrname='+usrname,true);
	ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	ajax.send(postData); 
	}
}

window.onload = saveImg;
</script>
</body>
</html>