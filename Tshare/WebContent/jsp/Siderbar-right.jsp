<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
<%
	String curPath = request.getContextPath();
	String groupName="groupName";
	String groupDescription="groupDescription";
	HashMap<String, String> groupToMember=(HashMap<String, String>)session.getAttribute("groupToMember");
	groupInfo current_group = (groupInfo)session.getAttribute("curr_group"); 
	HashMap<String, String> groupToImg=(HashMap<String, String>)session.getAttribute("groupToImg");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/scripts/jquery.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<style>
#button1 {

    background: transparent;
    border: none !important;
    font-size:0;
}
</style>
<title>right bar</title>
</head>

<div class="grid_3">
  <div class="panel2 panel-default2">
    <div class="panel-heading2">
    <div class="row">
      <div class="col-sm-8"><h3 class="panel-title2" align="right" id="groupname"><%=current_group.groupName%></h3></div>
      <div class="col-sm-1" id="afterThis">
  		<input align="right" type="image" src="../images/edit.svg" id="editname" width="20" height="20">
	  </div>	  
    </div>
    <div class="panel-body" align="center" id="groupdes"><%=current_group.description%></div>
    
    <div class="panel-body" align="center" id="groupdes"> Group Members</div>
    <div align="center">
    <span title="addMember"  data-toggle="tooltip" data-placement="top"><button id="button1"  data-toggle="modal" data-target="#addMember" ><img src="../images/addMember.png" width="20" height="20"></button></span>
    <span title="deleteMember"  data-toggle="tooltip" data-placement="top"><button id="button1" data-toggle="modal" data-target="#deleteMember"><img src="../images/deleteMember.png" width="25" height="25"></button></span>
    </div>
    <% for (Entry<String, String> entry : groupToMember.entrySet()) { 		
	    String member = entry.getValue();
	    String Id=entry.getKey();
	    String version=groupToImg.get(Id);
	%>
	<div class="panel-body" align="center" id="group-colon">
	<%if(!version.equals("0")) {    	%>
    			<img src="https://s3-us-west-2.amazonaws.com/tshareavatar/<%=Id+version%>" height="30" width="30" >  
    	  		<%} else{%>
    		<img src="../images/avatar.jpg" height="30" width="30" >   
    	  <%} %>
	<%=member%></div><hr>
	<% } %>
  </div>
  </div>
</div>


<div class="modal fade" id="addMember" tabindex="-1" role="dialog" aria-labelledby="addMemberLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="addMemberLabel"><%= current_group.groupName%></h4>
      </div>
      <div class="modal-body">
        <form action="javascript:sendMessage()">          
          <div class="form-group">
            <label for="message-text" class="control-label">Add new members:</label>
            <textarea class="form-control" id="list"></textarea>
          </div>
               
		<p>Please use ; as separator of group member email addresses.</p>
		<div>
			<p align="right">
			<button type="submit" class="btn btn-info">Submit</button></p>
		</div>	
		</form> 
      </div>
      <!-- <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="sendMessage()">Send message</button>
      </div> -->
    </div>
  </div>
  </div>


<div class="modal fade" id="deleteMember" tabindex="-1" role="dialog" aria-labelledby="deleteMemberLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="deleteMemberLabel"><%= current_group.groupName%></h4>
      </div>
      <div class="modal-body">
        <form action="../deleteMember" method="get">          
          <div class="form-group">
            <label for="message-text" class="control-label">Members to delete:</label>
            <div>
            <% for (Entry<String, String> entry : groupToMember.entrySet()) { 		
   				String Id	=	entry.getKey();
	    		String member = entry.getValue();
	    		String option=member+" ("+Id+")";
			%>			
   			<input type="checkbox" name="userList" value="<%=Id%>"> <%=option%><br>
  			<% } %>
  			</div>
          </div>
              
		
		<div>
			<p align="right">
			<button type="submit" class="btn btn-info">Submit</button></p>
		</div>	
		</form> 
      </div>
      <!-- <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="sendMessage()">Send message</button>
      </div> -->
    </div>
  </div>
  </div>


	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>	
<script>
function sendMessage(){
	var text=$('#list').val();
	var f = document.createElement("form");
	f.setAttribute('method',"post");
	
	f.setAttribute('action',"../addMember?list="+text+"&curPath="+window.location.href );
	f.submit();
}



$('#editname').click(function() {
	 var text = $('#groupname').text();
	 var input = $('<input id="attribute" size= "8" type="text" value="' + text + '" />');
	 $('#groupname').text('').append(input);
	 input.select();	 
	 
	 var text1 = $('#groupdes').text();
	 var input1 = $('<input id="attribute1" class="form-control" type="text" value="' + text1 + '" />');
	 $('#groupdes').text('').append(input1);
	 
	 var save = document.createElement("input");
	 save.type="image";
	 save.src="../images/save.png"
	 save.height=20;
	 save.width=20;
	 
	 var after = document.getElementById("afterThis");
	 after.appendChild(save);
	 
	 var editImage = document.getElementById("editname");
	 editImage.style.visibility = "hidden";
	 
	 save.onclick=function(){
		 var text = $('#attribute').val();
		   $('#groupname').text(text);
		   $('#attribute').remove(); 
		   var text1 = $('#attribute1').val();
		   $('#groupdes').text(text1);
		   $('#attribute1').remove(); 
		   
		   window.location = "../updateGroupInfo?des="+text1+"&name="+text+"&curPath="+window.location.href;    
			
		   }; 
	  
	});
	
</script>


