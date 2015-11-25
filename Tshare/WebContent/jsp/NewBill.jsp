<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
<%
	HashMap<String, String> groupToMember1=(HashMap<String, String>)session.getAttribute("groupToMember");
%>
 <% User uu=(User)session.getAttribute("userInfo");
   Boolean changed=false;
 	 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title> Add Bill </title>
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
<div id = "bill" class="container-fluid">  
	 
	<div style="text-align:center">  		
  		<h3>Add a new bill</h3>
	</div>
	
	
		<form action="../PutBillToDB" method="post" >
		<div class="grid_bill1">
  			<div class="h5">Bill Name: </div>
  			<input name="billName" class="form-control-bill" type="text"><br>
  			<div class="h5">You Paid: </div>
  			<input name="amount" class="form-control-bill" type="text"><br>
  			<div class="h5">
				<p class="h5">Add User:</p>
   			</div>
   			<% for (Entry<String, String> entry : groupToMember1.entrySet()) { 		
   				String Id	=	entry.getKey();
	    		String member = entry.getValue();
	    		String option=member+" ("+Id+")";
			%>			
   			<input type="checkbox" name="userList" value="<%=Id%>"> <%=option%><br>
  			<% } %>
  		
  	</div>
  	
  	<div class="grid_bill2">
   			<div class="h5">Description:</div>
   			<textarea class="form-control-bill"  name="description"></textarea>
  			<div class="h5">Upload Photo:</div>
  			<div>&nbsp;</div>
  			<input id="inputFileToLoad" name="img" type="file" onchange="encodeImageFileAsURL();" />  	
  			<div class="grid_5">  			 
  				<input type="submit" class="btn btn-info btn" value="Submit" >
			</div>		 			   				
	</div>
	  			
	</form>	   
</div>


<script type='text/javascript'>
function encodeImageFileAsURL(){
    var filesSelected = document.getElementById("inputFileToLoad").files;
    if (filesSelected.length > 0)
    {
        var fileToLoad = filesSelected[0];
        var fileReader = new FileReader();
		sessionStorage.uploaded=false;
        fileReader.onload = function(fileLoadedEvent) {
            var srcData = fileLoadedEvent.target.result; // <--- data: base64  
            sessionStorage.img=srcData;
            sessionStorage.uploaded="true";
        }
        fileReader.readAsDataURL(fileToLoad);
    }
}


</script>
<script src="../js/jquery-1.11.2.min.js" type="text/javascript"></script>
<script src="../js/bootstrap-3.3.4.js" type="text/javascript"></script>

</body>
</html>
