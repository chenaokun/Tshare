<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bill activity</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
	
	<div id ="bill">
		<div class="panel-group" id="accordion">	
		<% 		
		ArrayList<activityInfo> activity = (ArrayList<activityInfo>)session.getAttribute("all_activity");
		int i=0;
    	for(activityInfo a : activity) {
    		//System.out.println(a.billId+" "+a.flag);
    		i++;
    		if(a.flag == false){%>
    			<div class="panel panel-default">
      				<div class="panel-heading">
        				<h4 class="panel-title">    				       				
          					<a data-toggle="collapse" data-parent="#accordion" >
          					    <h5 style="text-align:left">BillName: <%=a.billName %> &nbsp;&nbsp;&nbsp;&nbsp;You are not involved.&nbsp;&nbsp;
          					    	         							      					    	   					    	
          							<button  style="float:right;margin-top:-4px;" type="button" class="btn btn-info btn-xs" data-toggle="modal" 
          							data-target="#deleteBill" data-id="<%=a.billId%>">x</button>
          							
          							<button style="float:right;margin-top:-4px;margin-right:4px;" type="button" class="btn btn-info btn-xs" data-toggle="modal" 
          							data-target="#showreceipt" data-id="<%=a.photoPath%>"><img src="../images/img.png" width="20" height="20"></button>
          					    </h5>
          					</a>
          				</h4>
      				</div>
      				      				
      			</div>
      						
    		<%}else {
    			Double amount = Double.parseDouble(a.amount);
    			String[] member = (a.members).split(";");
        		
        		if(amount>0){%>
        			<div class="panel panel-default">
        				<div class="panel-heading">
        				<h4 class="panel-title">
          					<a data-toggle="collapse"  data-parent="#accordion" href=#<%=i %>>
          					    
          						<h5 style="text-align:left">BillName: <%=a.billName %>&nbsp;&nbsp;&nbsp;&nbsp; 
          							<%=a.payerId %> spent $<%=a.totalAmount %>; &nbsp; You owe $<%=amount %>&nbsp;&nbsp;
          							
          							<button  style="float:right;margin-top:-4px;" type="button" class="btn btn-info btn-xs" data-toggle="modal" data-target="#deleteBill" data-id="<%=a.billId%>">x</button>
          							<button style="float:right;margin-top:-4px;margin-right:4px;" type="button" class="btn btn-info btn-xs" data-toggle="modal" 
          							data-target="#showreceipt" data-id="<%=a.photoPath%>"><img src="../images/img.png" width="20" height="20"></button>
          						</h5>
          						
          					</a>
          					
       		 			</h4>
      					</div>
      				
      					<div id=<%=i %> class="panel-collapse collapse">
      					<%
      						for(int j=0; j< member.length; j++){
      							if(member[j].equals(a.payerId))
      								continue;
      					%>		
      							<li class="list-group-item"><%=member[j] %> owns <%=a.payerId %> $<%=a.amount %></li>     							
      					<%} %>	
      					           				
      					</div>
      				</div>
        			
				
    			<%} else{%>
    				<div class="panel panel-default">
        				<div class="panel-heading">
        				<div class="panel-title">
          					<a data-toggle="collapse"  data-parent="#accordion" href="#<%=i %>">  					    
          						<h5 style="text-align:left">BillName: <%=a.billName %>  
          							&nbsp;&nbsp;&nbsp;&nbsp; You spent $<%=a.totalAmount%> &nbsp;&nbsp;
          							
          							<button  style="float:right;margin-top:-4px;" type="button" class="btn btn-info btn-xs" data-toggle="modal" 
          							data-target="#deleteBill" data-id="<%=a.billId%>">x</button>
          							<button style="float:right;margin-top:-4px;margin-right:4px;" class="btn btn-info btn-xs" type="button"  data-toggle="modal" 
          							data-target="#showreceipt" data-path="<%=a.photoPath%>"><img src="../images/img.png" width="20" height="20"></button>
          						</h5>
          					</a>
       		 			</div>
      					</div>
      				
      					<div id=<%=i %> class="panel-collapse collapse">
        				<%
      						for(int j=0; j< member.length; j++){
      							if(member[j].equals(a.payerId))
      								continue;
      					%>		
      							<li class="list-group-item"><%=member[j] %> owes you $<%=a.amount.substring(1) %></li>     							
      					<%} %>        				
      					</div>
      				</div>
    				
    			<%} 
        	}   		   			  	
      }%>
			
		</div>
	</div>
	
	<div class="modal fade" id="deleteBill">
  		<div class="modal-dialog">
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        			<h4 class="modal-title"></h4>
      			</div>
      			<div class="modal-body">
        			<p>Do you really want to delete this bill?</p>
     	 		</div>
      			<div class="modal-footer">
        			<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
        			<button type="button" class="btn btn-primary" onclick="javascript:deleteThisBill()">Yes</button>
      			</div>
    		</div>
  		</div>
	</div>
	
	<div class="modal fade" id="showreceipt">
  		<div class="modal-dialog">
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        			<h4 class="modal-title"></h4>
      			</div>
      			<div class="modal-body">
        			<img>
     	 		</div>			
    		</div>
  		</div>
	</div>
	
	<script>
	
	$('#showreceipt').on('show.bs.modal', function (event) {
		  var button = $(event.relatedTarget); // Button that triggered the modal
		  var receipt = button.data('path'); // Extract info from data-* attributes		  		  
		  var modal = $(this);
		  //alert(receipt);
		  if(receipt == undefined){
			  modal.find(".modal-title").text("This bill has no picture");
			  modal.find("img").attr("src","../images/proj_no_photo.png");
			  modal.find("img").attr("width","500px");
			  modal.find("img").attr("length","200px");
		  }else{
			  modal.find(".modal-title").text("Bill Receipt");
			  modal.find("img").attr("src",receipt);
		  }	  
		  
		})
		
	$('#deleteBill').on('show.bs.modal', function (event) {
		  var button = $(event.relatedTarget); // Button that triggered the modal
		  bill = button.data('id'); // Extract info from data-* attributes		  
		  sessionStorage.bill=bill;
		  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		  
		  var modal = $(this);
		  modal.find('.modal-body input').val(id);
		})
		
	
	
	$(document).ready(function(){
	    $('[data-toggle="tooltip"]').tooltip();   
	});
	
	function deleteThisBill(){
		var groupId = localStorage.getItem("groupname");
		
		var postFormStr = "<form id='hidden_form' method='POST' action='../deleteBill'>\n";
		postFormStr += "<input type='hidden' name='groupId' value='" + groupId + "'></input>";
		postFormStr += "<input type='hidden' name='billId' value='" + sessionStorage.bill + "'></input>";
		postFormStr += "</form>";
		var formElement = $(postFormStr);
		$(formElement).submit();
		
	}
	</script>		
	