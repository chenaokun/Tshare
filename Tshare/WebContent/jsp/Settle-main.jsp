<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
 <% User us=(User)session.getAttribute("userInfo");
 	String userId=us.Id;
 	groupInfo group1=(groupInfo)request.getSession().getAttribute("curr_group");
	String groupId=group1.groupId;
 	String balance=(String)session.getAttribute(userId+groupId+"balance"); 	
 	System.out.println("Settle-main:test");
 	HashMap<String[], Double> optimized=(HashMap<String[], Double>)session.getAttribute(userId+groupId+"optimized");
 	
 	
 %>
 	
<div  id="bill">
<table class="table">	
	<tr>
    	<td class="sum-body">Optimized Plan</td>
    	<td>     </td>
    	<td>     </td>
    	<td>     </td>
    </tr>
	<tr>
	<%if(Double.parseDouble(balance)>=0){ %>
    	<td class="sum-body">You Owe</td>
    	<td>     </td>
    	<td>     </td>
    	<td class="sum-body">$<%=balance %></td>
    <% } else {%>
    	<td class="sum-body">People Owe you</td>
    	<td>     </td>
    	<td>     </td>
    	<td class="sum-body">$<%= ((int)((-Double.parseDouble(balance))*100))/(double)100%></td>
    <%}%>
    </tr>	

		
    </table>
	<table class="table table-hover">
	<% for (Entry<String[], Double> entry : optimized.entrySet()) { 
		String[] key = entry.getKey();
		System.out.println(userId+" "+key[1]);
		Double value = entry.getValue();
		if(!key[1].equals(userId)&&!key[0].equals(userId)||(value==0.0))
			continue;	    
	    if(key[1].equals(userId)){%>
	
	<tr>
    	<td class="sum-body"><%=key[0]%> pays You</td>
    	<td class="sum-body"><%=  ((int)(value*100))/(double)100%></td>
    	<td class="sum-body">Amount paid:</td> 
    	<td>
    	<%-- <form class="form-inline"action="../payToSettle?receiver=<%=key[0]%>&beingPaid=1" method="post">
		  <div class="form-group">
		    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
		    <div class="input-group">
		      <div class="input-group-addon">$</div>
		      <input type="text" class="form-control" name="amount" id="exampleInputAmount" placeholder="Amount">
		      
		    </div>
		  </div>
		  <button type="submit" class="btn btn-primary">paid</button>
		</form> --%>
    		<form action="../payToSettle?receiver=<%=key[0]%>&beingPaid=1" method="post">  		
    		 	<input type="text" name="amount" id="textfield2" size="10">        		
        		<input class="btn btn-info btn-xs" type="submit" value="Submit"> 
     		</form> 
     	</td>
     	</tr> 	 <% } else {%>
     	
     	
     	
	<tr>
    	<td>You pay <%=key[1]%></td>
    	<td><%=Double.toString(value)%></td>
    	<td>Amount paid:</td>
    	<td>
    		<form action="../payToSettle?receiver=<%=key[1]%>&&beingPaid=0" method="post">
        		<input type="text" name="amount" id="textfield2" size="10">
        		<input class="btn btn-info btn-xs" type="submit" value="Submit">
     		</form>
     	</td>
     	</tr>
	
		<% } %>
	<% } %>
	</table>
</div>