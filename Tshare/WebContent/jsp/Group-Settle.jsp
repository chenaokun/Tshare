<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="../js/jquery-1.11.2.min.js"></script>
<div class="grid_8 push_4">
		<div id="pageshadow">
			<div id="header"> 
				<div class="headerlinks grid_8">
  					<a href="#">About Us | </a> 
                    <a href="#">Group Info</a> 
				</div>
				<div class="title grid_8">
                	<a id = "gg" href="#"></a>
                    <script>
                        	var element = document.getElementById("gg");
							var text = document.createTextNode(localStorage.getItem("groupname"));
							element.appendChild(text);
                    </script>
                </div>
			</div>
            
            <div id="nav">
				<ul class="sf-menu">
    				<li class="first-item"> <a href="#" style="border-left:none;">Home</a> </li>
    				<li> <a href="#a">Activity</a>
      					<ul>
        					<li> <a href="Main-page.jsp">All activity</a> </li>
        					<li class="current"> <a href="#">My Activity</a>
          						<ul>
            						<li class="current"><a href="#">This Year</a></li>
           	 						<li><a href="#aba">This Month</a></li>
          						</ul>
        					</li>
        				
      					</ul>
    				</li>
    				<li> <a href="AddBill.jsp">Add Bill</a></li>
        			<li> <a href="../settleUp">Settle Up</a></li>
    			</ul>
			</div>
			<div id="includedContent">
				<%@ include file="Settle-main.jsp" %>
			</div>
			<!-- <script> 
    			$(function(){
      				$("#includedContent").load("Settle-main.jsp"); 
    			});
    		</script>  -->
		</div>   
	</div>