<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
<%
	HashMap<String, String> groupToMember1=(HashMap<String, String>)session.getAttribute("groupToMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title> Add Bill </title>
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
<div id = "sign-box" class="container-fluid">  
	 <p>&nbsp;</p>
	<div>
  		<p>&nbsp;</p>
  		<h2>Add a new bill</h2>
	</div>
	
	<div>
		<form action="../PutBillToDB" method="post" >
  			<div class="h5">Bill Name: </div>
  			<input name="billName" type="text"><br>
  			<div class="h5">You Paid: </div>
  			<input name="amount" type="text"><br>
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
   			<div class="h5">Description:</div>
   			<textarea class="form-control" rows="5" cols="3" name="description"></textarea>
  			<div class="h5">Upload Photo</div>
  			<input type="file" name="img">
  			<div>&nbsp;</div>  			
			<input type="submit" class="submit" value="Submit" >	      		
		</form>
	</div>
</div>

<script src="../js/jquery-1.11.2.min.js" type="text/javascript"></script>
<script src="../js/bootstrap-3.3.4.js" type="text/javascript"></script>
</body>
</html>
