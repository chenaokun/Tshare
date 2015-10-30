<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
<%
	String groupName="groupName";
	String groupDescription="groupDescription";
	HashMap<String, String> groupToMember=(HashMap<String, String>)session.getAttribute("groupToMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>right bar</title>
</head>
<body>
<div class="grid_3 push_0">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h3 class="panel-title" align="center" ><%=groupName%></h3>
    </div>
    <div class="panel-body" align="center"><%=groupDescription%></div>
    <% for (Entry<String, String> entry : groupToMember.entrySet()) { 		
	    String member = entry.getValue();
	%>
	<div><%=member%></div><hr>
	<% } %>
  </div>
</div>
</body>
</html>