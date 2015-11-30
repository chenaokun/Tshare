package javaFile;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class addMember extends HttpServlet{
	static DynamoDB dynamoDB= get.dynamoDB;
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		String curPath=request.getParameter("curPath");
		String list=request.getParameter("list");
		groupInfo group=(groupInfo) request.getSession().getAttribute("curr_group");
		String groupId=group.groupId;
		HashMap<String,String> members=(HashMap<String, String>) request.getSession().getAttribute("groupToMember");
		HashMap<String,String> groupToImg=(HashMap<String, String>) request.getSession().getAttribute("groupToImg");
		System.out.println("addMember.java: "+groupId);
		
	    Table table= dynamoDB.getTable("currentBalance");
	    String[] member = list.split(";");
	    for(int i=0; i < member.length; i++){
       	 Item item = new Item()
                    .withPrimaryKey("groupId", groupId,"userId", member[i])
                    .withString("balance", "0");
       	 table.putItem(item);       	 
        }
	    
	    	table = dynamoDB.getTable("Usr_info");
	    	for(String Id: member){
	    		 Item item = table.getItem("Id", Id); 
				 String name=item.getJSON("userName");
				 members.put(Id, removeQuo.remove(name));
				 String img=item.getJSON("photoPath");
				 groupToImg.put(Id, removeQuo.remove(img));
			 }
	    	request.getSession().setAttribute("groupToImg", groupToImg);
	    	request.getSession().setAttribute("groupToMember", members);
	    	response.sendRedirect(curPath);
	    
	    
	    
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
	
}
