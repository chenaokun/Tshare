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
<link href="../css/bootstrap.css" rel="stylesheet" type="text/css">
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

<script src="../js/cropbox.js"></script>

<nav class="navbar navbar-default">
  
    	<div class="navbar-header">     		
      		<a class="navbar-brand" href="#">Tshare</a>
      	</div>
    	<div class="collapse navbar-collapse" id="defaultNavbar1">
			<ul class="nav navbar-nav navbar-right">
  				<li><a href="sign_in.jsp">Sign In </a></li> 
			</ul>
    	</div>    

</nav>

<div id = "sign-box" class="container-fluid">
	<div class="signin">
  		<p>&nbsp;</p>
  		<label class="col-sm-2 control-label"></label>
  		<div class="col-sm-12">
  		<h2>Sign Up</h2>
  		</div>
  		<p>&nbsp;</p>
	</div>
	
	<div class="signin">
			<!-- <img src="https://s3-us-west-2.amazonaws.com/tshareavatar/youdidut@gmail.comfda" height="42" width="42"> -->
		<div class="my-form-group">	
  			<label class="my-col-sm-3">*User Name</label>
  			<div class="my-col-sm-3">
  				<input class="form-control" id="usrname" type="text" placeholder="mymail@mail.com"><br>
  			</div>
  			
  			<label class="my-col-sm-3">*Password</label>
   			<div class="my-col-sm-3">
   				<input class="form-control" type="password" id="password1"><br>
   			</div>
   		
   			<label class="my-col-sm-3">*Re-enter Password</label>
   			<div class="my-col-sm-3">
   				<input class="form-control" type="password" id="password2"><br> 
   			</div>
   			  	   			
  			<label class="my-col-sm-3">*Full Name</label>
  			<div class="my-col-sm-3">
  				<input class="form-control" id="fullName" type="text"><br>
  			</div>
  			
  			<label class="my-col-sm-3">Upload Photo</label>
  			<div class="my-col-sm-3">
  				<div class="imageBox">
        			<div class="thumbBox"></div>
       				 <div class="spinner" style="display: none">Loading...</div>
        		</div>
    		</div>
    		
    		<label class="my-col-sm-3"></label>
    		<div class="my-col-sm-3">
    			<div class="action"> 
        			<input type="file" id="file" style="float:left; width: 250px">       
    			</div>
   			</div>
   			
    		<div class="col-sm-offset-4 my-col-sm-10">
    			<div class="action">
        			<input type="button" id="btnZoomIn" value="  +  " class="btn btn-default">        
        			<input type="button" id="btnZoomOut" value="  -  " class="btn btn-default">
    			</div>
    		</div>
  		 </div>	
  				
  			<div class="col-sm-offset-5-plus my-col-sm-10">
  				<div class="checkbox">	
  					<label>
						<input type="submit" id="btnCrop" class="btn btn-default"  value="Submit" >
					</label>
				</div>		      		
			</div>
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
