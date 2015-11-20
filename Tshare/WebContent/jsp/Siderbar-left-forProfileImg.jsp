<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
    
<!-- <head>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
</head> -->
<head><meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- <script src="/Tshare-test2/js/jquery-1.11.2.min.js" type="text/javascript"></script> -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>-->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
 

<style type="text/css"> 

 #menu {
    float: left;
    margin: 0;
    width:80%;
}   
 #menu1 {
    float: left;
    margin: 0;
    width:20%;
}     
    </style>
</head>

 <% User us1=(User)session.getAttribute("userInfo");
    ArrayList<groupInfo> group2 = (ArrayList<groupInfo>)session.getAttribute("groupInfo");
 %>
 	
	<div class="grid_4 pull_8">
    	<div class="sidebarbox">
      		<h3>Hello! <%=us1.name%><%if(!us1.img.equals("0")) {    	%>
    			<img src="https://s3-us-west-2.amazonaws.com/tshareavatar/<%=us1.Id+us1.img%>" height="42" width="42" align="right">  
    	  		<%} else{%>
    		<img src="../../images/avatar.jpg" height="42" width="42" align="right">   
    	  <%} %></h3>
      		
      		<ul>
        		<li><a href="/Tshare-test2/jsp/profile/Profile-Main.jsp">My profile</a> </li>
        		<li><a href="#">Setting</a></li>
      		</ul>
    	</div>
   	 	<div class="simple" id = "test">
			<h4>Groups 
				<a data-toggle="modal" data-target="#myaddGroup" data-rel="popup" data-position-to="window" 
				data-transition="fade" class="btn btn-info btn-xs">Add group</a> 
			</h4>
			<ul id = "group">
    			<!--<li><a href="#">group1</a></li>
  				<li><a href="#">group2</a></li>
				<li><a href="#">group3</a></li>
				<li><a href="#">group4</a></li> -->
			<%
			for(groupInfo g : group2) {
				String gn = g.groupName;%>
				<li id="menu">
				<a onclick="saveGroupName(this)" groupName="<%=g.groupName%>" groupId="<%=g.groupId%>"><%=g.groupName%></a></li>	
				<li id="menu1"> <span title="Delete This Group"  data-toggle="tooltip" data-placement="top"><button  type="button" class="btn btn-info btn-xs" data-toggle="modal" data-target="#deleteGroup" data-id="<%=g.groupId%>" data-name="<%=g.groupName%>">x</button></span>
				
				<%-- <button type="button" data-toggle="modal" data-target="#myaddMember" data-rel="popup" data-position-to="window" 
				data-transition="fade" class="btn btn-info btn-xs" data-id=<%=g.groupId%> data-name=<%=g.groupName%>>Add member</button> --%></li>
			<%}%>
			
    		</ul>
		   
    		<h4>Friends</h4>
			<ul id = "friend">
    			<!--<li><a href="#">friend1</a></li>
  				<li><a href="#">friend2</a></li>
				<li><a href="#">friend</a></li>
				<li><a href="#">friend</a></li> -->
    		</ul>
		</div>
	</div> 
	<div  class="modal fade"  id="myaddGroup" role="dialog">
    	<div class="modal-dialog">   
      		<div class="modal-content">
        		<div class="modal-header">
          			<button type="button" class="close" data-dismiss="modal">&times;</button>
          			<h4 class="modal-title">Add a new group</h4>
        		</div>
        		<div class="modal-body">
          			<div class="form-group">
          			    <form action="../GroupServlet" method="get">Group Name:    
							<textarea name="groupName" class="form-control" rows="1"></textarea>
							<br>
							<div>Add Group Member: </div> 
							<textarea name="memberList"class="form-control" rows="1"></textarea>
							<div>Group Description: </div> 
							<br>
							<textarea name="groupDescription"class="form-control" rows="1"></textarea>
							<br>
							<p>Please use ; as separator of group member email addresses.</p>						
							<div>
    							<p align="right">
    							<button type="submit" class="btn btn-info">Submit</button></p>
							</div>
						</form>
					</div>
        		</div>
        		<div class="modal-footer">
          			<p>Tshare</p>
        		</div>
      		</div>
      
    	</div>
  	</div>
  	
	
  	
  	 		
  <div class="modal fade" id="deleteGroup">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
        <p>Do you really want to delete this group?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
        <button type="button" class="btn btn-primary" onclick="javascript:deleteThisGroup()">Yes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

	
	<script>
	
	$('#deleteGroup').on('show.bs.modal', function (event) {
		  var button = $(event.relatedTarget); // Button that triggered the modal
		  id = button.data('id'); // Extract info from data-* attributes		  
		  sessionStorage.id=id;
		  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		  var name = button.data('name'); 
		  sessionStorage.name=name;
		  var modal = $(this);
		  modal.find('.modal-title').text(name);
		  modal.find('.modal-body input').val(id);
		})
		
	
	
	$(document).ready(function(){
	    $('[data-toggle="tooltip"]').tooltip();   
	});
	
	function deleteThisGroup(){
		var f = document.createElement("form");
		f.setAttribute('method',"post")
		f.setAttribute('action',"../deleteGroup?groupId="+sessionStorage.id+"&curPath="+window.location.href);
		f.submit();
	}
	</script>
			
	<script>
	
		for(var i=1; i<8; i++) {
			var li = document.createElement("li");
			var a = document.createElement("a");
			var text = document.createTextNode("friend"+i);
			a.appendChild(text);
			a.setAttribute("href", "#");
			li.appendChild(a);	
			var element = document.getElementById("friend");
			element.appendChild(li);
		}
		
		function saveGroupName(a) {
			var name = a.getAttribute("groupName");
			var Id = a.getAttribute("groupId");			
			localStorage.setItem("groupname", name);
			var postFormStr = "<form id='hidden_form' method='POST' action='/Tshare-test2/main-page'>\n";
			postFormStr += "<input type='hidden' name='groupId' value='" + Id + "'></input>";
			postFormStr += "<input type='hidden' name='groupName' value='" + name + "'></input>";
			postFormStr += "</form>";
			var formElement = $(postFormStr);
			$('#group').append(formElement);
			//var element = document.getElementById("group");		   	    
    	    $(formElement).submit();
						
		}
		
	</script>
