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
<link rel="stylesheet" href="../styles/style.css" type="text/css" />
<link href="../css/bootstrap-3.3.4.css" rel="stylesheet" type="text/css">
<link href="../css/Sign-in.css" rel="stylesheet" type="text/css">
<link href="../css/siderbar.css" rel="stylesheet" type="text/css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<style>
       .container
        {
            position: absolute;
            top: 10%; left: 10%; right: 0; bottom: 0;
        }
        .action
        {
            width: 400px;
            height: 30px;
            margin: 10px 0;
        }
        .cropped>img
        {
            margin-right: 10px;
        }
    </style>
</head>

<body>


<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
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
			<!-- <img src="https://s3-us-west-2.amazonaws.com/tshareavatar/youdidut@gmail.comfda" height="42" width="42"> -->
			
  			<div class="h5">*User Name</div>
  			<input id="usrname" type="text" placeholder="mymail@mail.com"><br>
  			<div class="h5">*Password</div>
   			<input type="password" id="password1"><br>
   			<div class="h5">*Re-enter Password</div>
   			<input type="password" id="password2"><br>   	   			
  			<div class="h5">*Full Name</div>
  			<input id="fullName" type="text"><br>
  			<div class="h5">Upload Photo</div>
  			<div class="imageBox">
        <div class="thumbBox"></div>
        <div class="spinner" style="display: none">Loading...</div>
    </div>
    <div class="action">
        <input type="file" id="file" style="float:left; width: 250px">       
    </div>
    <div class="action">
        <input type="button" id="btnZoomIn" value=" + " style="float: left">
        <input type="button" id="btnZoomOut" value=" - " style="float: left">
    </div>
    <div class="cropped">

    </div>
  			<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		<script src="../js/cropbox.js"></script>
			
  			<div>&nbsp;</div>  			
			<input type="submit" id="btnCrop" class="submit" value="Submit" >		      		
		
	</div>
</div>



<div id="credit" class="container_12">
    	<p>&nbsp;</p>
    	<p> @Copyright Titan</p>
  	</div>
  	


<script type="text/javascript">
    $(window).load(function() {
        var options =
        {
            thumbBox: '.thumbBox',
            spinner: '.spinner',
            imgSrc: ''
        }
        var cropper = $('.imageBox').cropbox(options);
        var changed=false;

        $('#file').on('change', function(){
        	changed=true;
            var reader = new FileReader();
            reader.onload = function(e) {
                options.imgSrc = e.target.result;
                cropper = $('.imageBox').cropbox(options);
            }
            reader.readAsDataURL(this.files[0]);
            this.files = [];
        })
        $('#btnCrop').on('click', function(){
        	
            var usrname = $('#usrname').val();
            var password1 = $('#password1').val();
            var password2 = $('#password2').val();
            var fullName = $('#fullName').val();
            
             if(changed){        	
            	window.location = "../signUp?usrname="+usrname+"&password1="+password1+"&password2="+password2+"&fullName="+fullName+"&img=1";    
            	
            	var img = cropper.getDataURL();            	
            	sessionStorage.img=img; 
            	sessionStorage.usrname=usrname;
            	sessionStorage.haveImage=true;
             }
            else{
            	window.location = "../signUp?usrname="+usrname+"&password1="+password1+"&password2="+password2+"&fullName="+fullName+"&img=0";    
            	sessionStorage.haveImage=false;
            }	 
            
            
        })
        $('#btnZoomIn').on('click', function(){
            cropper.zoomIn();
        })
        $('#btnZoomOut').on('click', function(){
            cropper.zoomOut();
        })
    });
</script>
</body>
</html>
