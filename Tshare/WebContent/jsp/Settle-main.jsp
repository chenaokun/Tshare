<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
 <% User us=(User)session.getAttribute("userInfo");
 	String userId=us.Id;
 	String groupId="1";
 	String balance=(String)session.getAttribute(userId+groupId+"balance"); 	
 	System.out.println("testtesttest");
 	HashMap<String[], Integer> optimized=(HashMap<String[], Integer>)session.getAttribute(userId+groupId+"optimized");
 	
 %>
 	
<div  id="bill">
	<div class="row">
    	<div style="height: 40px; border-width: 8px;" class="col-sm-3">You Owes  $<%=balance %></div>
    	<div class="col-sm-2"></div>
    	<div class="col-sm-3"></div>
    	<div class="col-sm-4"></div>
    	<p>&nbsp;</p>
	</div>
	<div class="row">
    	<div class="col-sm-3">Basic Plan</div>
    	<div class="col-sm-2"></div>
    	<div class="col-sm-3"></div>
    	<div class="col-sm-4"></div>
    	<hr>
	</div>
	<!--<%for(int i=0;i<2;i++) {%>-->
		<div class="row">
    		<div class="col-sm-3">Mary pays You</div>
    		<div class="col-sm-2">$40</div>
    		<div class="col-sm-3"> Amount paid:    </div>
    		<div class="col-sm-4">
      			<form id="form1" name="form1" method="post">
        			<input type="text" name="textfield" id="textfield" size="10">
        			<input class="btn btn-info btn-xs" type="submit" value="Submit">
      			</form>
    		</div>
    		<p>&nbsp;</p>
		</div>
	<!--<%} %>-->
	<div class="row">
    	<div class="col-sm-3">Optimized Plan</div>
    	<div class="col-sm-2"></div>
    	<div class="col-sm-3"></div>
    	<div class="col-sm-4"></div>
	</div>
	<hr>
	<% for (Entry<String[], Integer> entry : optimized.entrySet()) { 
		String[] key = entry.getKey();
		System.out.println(userId+" "+key[1]);
		if(!key[1].equals(userId)&&!key[0].equals(userId))
			continue;
	    int value = entry.getValue();
	    if(key[1].equals(userId)){%>
	<div class="row">
    	<div class="col-sm-3"><%=key[0]%> pays You</div>
    	<div class="col-sm-2"><%=Integer.toString(-1*value)%></div>
    	<div class="col-sm-3">Amount paid:</div>
    	<div class="col-sm-4">
    		<form id="form2" name="form2" method="post">
        		<input type="text" name="textfield" id="textfield2" size="10">
        		<input class="btn btn-info btn-xs" type="submit" value="Submit">
     		</form>
     	</div>
	</div> <% } else {%>
	<div class="row">
    	<div class="col-sm-3">You pay <%=key[1]%></div>
    	<div class="col-sm-2"><%=Integer.toString(value)%></div>
    	<div class="col-sm-3">Amount paid:</div>
    	<div class="col-sm-4">
    		<form id="form2" name="form2" method="post">
        		<input type="text" name="textfield" id="textfield2" size="10">
        		<input class="btn btn-info btn-xs" type="submit" value="Submit">
     		</form>
     	</div>
	</div> 
		<% } %>
	<% } %>
</div>