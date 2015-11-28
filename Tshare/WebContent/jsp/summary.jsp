<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
 <% HashMap<String, String> gb=(HashMap<String, String>)session.getAttribute("groupBalance");
 	Double sum=0.0;
 	for (Entry<String, String> entry : gb.entrySet()) { 
		String balance = entry.getValue();
		sum+=Double.parseDouble(balance);
 	}
 	ArrayList<groupInfo> group3 = (ArrayList<groupInfo>)session.getAttribute("groupInfo");
 %>
 	
<div  id="summary">
	<table class="table">	
		<div>
    		<h4>You total balance:&nbsp;&nbsp;$<%=sum%></h4>
    		<div>&nbsp;</div>
    		
    	</div>
    	
		<tr>
			<td>Details:</td>
			<td></td>
		</tr>		
    </table>
    
	<table class="table table-hover">
	<%for (groupInfo g:group3){
		String groupId=g.groupId;
		if(gb.containsKey(groupId)&&Double.parseDouble(gb.get(groupId))!=0.0){
	%>	
		<tr>
    		<td><%=g.groupName%></td>
    		<td></td>
    		<td></td>
    		<td>$<%=gb.get(groupId)%></td> 
   		</tr>  		
		<% } %>
	<% } %>
	</table>
</div>