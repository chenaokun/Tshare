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
<link href="../css/bootstrap.css" rel="stylesheet" type="text/css">
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
  
    	<div class="navbar-header">     		
      		<a class="navbar-brand" href="#">Tshare</a>
      	</div>
    	<div class="collapse navbar-collapse" id="defaultNavbar1">
			<ul class="nav navbar-nav navbar-right">
  				<li><a href="register.jsp">Sign Up </a></li> 
			</ul>
    	</div>    

</nav>

<div id = "sign-box" class="container-fluid1">
	<div class="signin">
  		<p>&nbsp;</p>
  		<label class="col-sm-2 control-label"></label>
  		<div class="col-sm-12">
  			<h2>Sign In</h2>
  		</div>
  		<p>&nbsp;</p>
	</div>
	
	<div class="signin">
		<form action="../logIn" method="get" class="form-horizontal">
		<div class="my-form-group">
  			<label class="my-col-sm-3">User Name</label>
  			<div class="my-col-sm-3">
  				<input name="usrname" type="text" class="form-control"><br>
  			</div>
  		
  			<label class="my-col-sm-3">Password</label>
  			<div class="my-col-sm-3">
  				<input type="password" name="password" class="form-control"><br>
  			</div>
  		
  			<div class="col-sm-offset-2 my-col-sm-10">
		      <div id ="check" class="checkbox">
		        <label>
		          <input type="checkbox"> Remember me
		        </label>
		      </div>
		    </div>
		
  			<div class="col-sm-offset-2 my-col-sm-10">	
  				<div class="checkbox">
		        	<label>
		          		<input id ="check" type="submit" class="btn btn-default" value="Submit" >	
			
		       	 	</label>		
				</div>
			</div>
			</div>		
		</form>
	</div>
	
	<div id="credit" class="container_12">
    	<p>&nbsp;</p>
    	<p> @Copyright Titan</p>
	</div>
</div>



<script src="../js/jquery-1.11.2.min.js" type="text/javascript"></script>
<script src="../js/bootstrap-3.3.4.js" type="text/javascript"></script>
</body>
</html>
