 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
 <% User uu=(User)session.getAttribute("userInfo");
 	Integer version=Integer.parseInt(uu.img);
 %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Sign-up Page</title>
<!-- <link href="css/bootstrap.css" rel="stylesheet" type="text/css"> -->
<link rel="stylesheet" href="../../styles/style.css" type="text/css" />
<link href="../../css/bootstrap-3.3.4.css" rel="stylesheet" type="text/css">
<link href="../../css/siderbar.css" rel="stylesheet" type="text/css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<style>
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
<script src="../../js/cropbox.js"></script>

<div id = "sign-box" class="container-fluid">
	
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
  			
			
  			<div>&nbsp;</div>  			
			<input type="submit" id="btnCrop" class="submit" value="Submit" >		      		
		
	</div>


  	

<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="../../js/cropbox.js"></script>
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
            var usrname = "<%=uu.Id%>";
             if(changed){      
            	var img = cropper.getDataURL();            	
             	sessionStorage.img=img; 
             	sessionStorage.usrname=usrname;
            	sessionStorage.version= <%=version%>+1;
           		var f = document.createElement("form");
           		f.setAttribute('method',"post")
           		f.setAttribute('action',"../../updateProfile?subject=img");
           		f.submit();             		  
             	saveImg();
             }
            else{
            	alert("Please select a photo to upload.");
            }	 
            
            
        })
        $('#btnZoomIn').on('click', function(){
            cropper.zoomIn();
        })
        $('#btnZoomOut').on('click', function(){
            cropper.zoomOut();
        })
    });
    
    function saveImg(){
    	usrname=sessionStorage.usrname;
    	img=sessionStorage.img;
    	version=sessionStorage.version;
    	var ajax = new XMLHttpRequest();
    	var postData = "canvasData="+encodeURIComponent(img);		
    	
    	ajax.open("POST",'../../deleteAndUpload?usrname='+usrname+'&version='+version,true);
    	ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    	
    	ajax.send(postData); 
    	
    }
</script>
</body>
</html>
