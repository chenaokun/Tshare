<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign-up Page</title>
<!-- <link href="css/bootstrap.css" rel="stylesheet" type="text/css"> -->
<link href="../css/bootstrap-3.3.4.css" rel="stylesheet" type="text/css">
<link href="../css/Sign-in.css" rel="stylesheet" type="text/css">
<link href="../css/siderbar.css" rel="stylesheet" type="text/css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
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
  		<h2>Sign Up</h2>
  		<p>&nbsp;</p>
	</div>
	
	<div class="signin">
		<form action="../signUp" method="get" >
  			<div class="h5">*User Name</div>
  			<input name="usrname" type="text" placeholder="mymail@mail.com"><br>
  			<div class="h5">*Password</div>
   			<input type="password" name="password1"><br>
   			<div class="h5">*Re-enter Password</div>
   			<input type="password" name="password2"><br>   	   			
  			<div class="h5">*Full Name</div>
  			<input name="fullName" type="text"><br>
  			<div class="h5">Upload Photo</div>
  			<input type="file" name="img">
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
</body>
</html>
