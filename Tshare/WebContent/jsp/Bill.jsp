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
		ArrayList<activityInfo> activity = (ArrayList<activityInfo>)session.getAttribute("my_activity");
    	for(activityInfo a : activity) {
    		System.out.println(a.amount);
    		Double amount = Double.parseDouble(a.amount);
    		System.out.println(amount);
    		if(amount>0){%>
    			<div class="panel panel-default">
      				<div class="panel-heading">
        				<h4 class="panel-title">    				       				
          					
          					    <h5 style="text-align:left">BillName: <%=a.billName %> &nbsp;&nbsp;&nbsp;&nbsp;
          					    You owns $<%=amount %> to <%=a.payerId %></h5>          					
          				</h4>
      				</div>     				      				
      			</div>
    			
    		<%} else{%>
    			<div class="panel panel-default">
      				<div class="panel-heading">
        				<h4 class="panel-title">    				       				
          					
          					    <h5 style="text-align:left">BillName: <%=a.billName %> &nbsp;&nbsp;&nbsp;&nbsp;
          					    You spent $<%=a.totalAmount%></h5>          					
          				</h4>
      				</div>     				      				
      			</div>
    			
    		<%} %>    	
    <%} %>
	
	</div>
	</div>
</body>
</html>