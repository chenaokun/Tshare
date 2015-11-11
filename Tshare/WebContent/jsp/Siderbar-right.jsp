<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*, java.util.Map.Entry"%> 	
<%
	String curPath = request.getContextPath();
	String groupName="groupName";
	String groupDescription="groupDescription";
	HashMap<String, String> groupToMember=(HashMap<String, String>)session.getAttribute("groupToMember");
	groupInfo current_group = (groupInfo)session.getAttribute("curr_group"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/scripts/jquery.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>

<title>right bar</title>
</head>
<body>
<div class="grid_3 push_0">
  <div class="panel panel-default">
    <div class="panel-heading">
    <div class="row">
      <div class="col-sm-8"><h3 class="panel-title" align="right" id="groupname"><%=current_group.groupName%></h3></div>
      <div class="col-sm-1" id="afterThis">
  		<input align="right" type="image" src="../images/edit.svg" id="editname" width="20" height="20">
	  </div>	  
    </div>
    <div class="panel-body" align="c"C:/Users/Di/Documents/GitHub/Tshare/Tshare"enter" id="groupdes"><%=current_group.description%></div>
    
    <div class="panel-body" align="center" id="groupdes"> Group Members
    <span title="addMember"  data-toggle="tooltip" data-placement="top"><button  data-toggle="modal" data-target="#addMember" data-id="<%=current_group.groupId%>" data-name="<%=current_group.groupName%>"><img src="../images/addMember.png" width="20" height="20"></button></span>
    </div>
    <% for (Entry<String, String> entry : groupToMember.entrySet()) { 		
	    String member = entry.getValue();
	%>
	<div class="panel-body" align="center" id="group-colon"><%=member%>
	
	</div><hr>
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



	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>	
<script>
function sendMessage(){
	var text=$('#list').val();
	var f = document.createElement("form");
	f.setAttribute('method',"post");
	
	f.setAttribute('action',"../addMember?groupId="+sessionStorage.id+"&list="+text);
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
</body>
</html>

