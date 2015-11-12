<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bill activity</title>
</head>

<body>
	<div id="pageshadow">
	<div id ="bill">
	<% 		
    	System.out.println("Attention!!!!-------------");
		ArrayList<activityInfo> activity = (ArrayList<activityInfo>)session.getAttribute("all_activity");
    	for(activityInfo a : activity) {
    		System.out.println(a.flag);
    		if(a.flag == false){%>
    			<div class="row">
				<h5 style="text-align:center"><%=a.billName %></h5>
				<h5 style="text-align:center">You are not involved.</h5>  		   		
				<hr>
			</div>
    		<%}else {
    			Double amount = Double.parseDouble(a.amount);
        		System.out.println(amount);
        		if(amount>0){%>
        			<div class="row">
    				<h5 style="text-align:center"><%=a.billName %></h5>
    				<h5 style="text-align:center"><%=a.payerId %> spent $<%=a.totalAmount %>; You owns $<%=amount %></h5>  		   		
    				<hr>
				</div>
    			<%} else{%>
    				<div class="row">
    					<div style="text-align:center"><%=a.billName %></div>
    					<div style="text-align:center">You spent $<%=a.totalAmount%></div>
    					<hr>
					</div>
    			<%} 
        	}   		   			  	
      }%>
	
	</div>
	</div>
</body>
</html>