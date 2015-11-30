<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%>
<% 
    HashMap<String, String> gb=(HashMap<String, String>)session.getAttribute("groupBalance");
	groupInfo curr_group = (groupInfo)session.getAttribute("curr_group");
	String balance= gb.get(curr_group.groupId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>group home page</title>
</head>

<div id="pageshadow">
	<div id ="bill" style="text-align: center">
		<h3 >&nbsp;&nbsp;<%=curr_group.description %></h3>
		
		<hr>
		<h3>&nbsp;&nbsp;Your balance in current group is <%=balance %></h3>
		<hr>	
	</div>
</div>

</html>