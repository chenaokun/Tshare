<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(path);
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign-In Page</title>
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
    <div class="collapse navbar-collapse" id="defaultNavbar1">
<ul class="nav navbar-nav navbar-right">
  <li><a href="register.jsp">Sign Up </a></li> 
</ul>
    </div>
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
  		<label class="col-sm-2 control-label"></label>
  		<div class="col-sm-10">
  		<h2>Sign In</h2>
  		</div>
  		<p>&nbsp;</p>
	</div>
	
	<div class="signin">
		<form action="../logIn" method="get" class="form-horizontal">
		<div class="form-group">
  			<label class="col-sm-2 control-label">User Name</label>
  			<div class="col-sm-10">
  			<input name="usrname" type="text" class="form-control"><br>
  			</div>
  		</div>	
  		<div class="form-group">
  			<label class="col-sm-2 control-label">Password</label>
  			<div class="col-sm-10">
  			<input type="password" name="password" class="form-control">
  			</div>
  		</div>
  		<div class="form-group">
  			<div class="col-sm-offset-2 col-sm-10">
		      <div class="checkbox">
		        <label>
		          <input type="checkbox"> Remember me
		        </label>
		      </div>
		    </div>
		 </div>
		 <div class="form-group">
  			<div class="col-sm-offset-2 col-sm-10">			
			<input type="submit" class="btn btn-default" value="Submit" >	
			</div>
		</div>		
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
