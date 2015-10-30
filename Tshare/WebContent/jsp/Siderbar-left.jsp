<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,javaFile.*"%>
 <% User us1=(User)session.getAttribute("userInfo");
 	//System.out.println("session received:"+us.Id);%>
 	
	<div class="grid_4 pull_8">
    	<div class="sidebarbox">
      		<h3>Hello! <%=us1.Id%></h3>
      		<ul>
        		<li><a href="#">My profile</a> </li>
        		<li><a href="#">Setting</a></li>
      		</ul>
    	</div>
   
   	 	<div class="simple" id = "test">
			<h4>Groups</h4>
			<ul id = "group">
    			<!--<li><a href="#">group1</a></li>
  				<li><a href="#">group2</a></li>
				<li><a href="#">group3</a></li>
				<li><a href="#">group4</a></li> -->
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
	   
<script>	
		//a.setAttribute(href, "#");
			for(var i=1; i<8; i++) {
				var li = document.createElement("li");
				var a = document.createElement("a");
				var text = document.createTextNode("Y763"+i);
				a.appendChild(text);
				a.setAttribute("href", "../addBill");
				a.setAttribute("onclick", "saveGroupName(text)");
				li.appendChild(a);	
				var element = document.getElementById("group");
				element.appendChild(li);
			}	
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
			function saveGroupName(name) {
				//alert(name);
				localStorage.setItem("groupname", name);
			}	
		
</script>
