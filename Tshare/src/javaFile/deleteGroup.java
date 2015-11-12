package javaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;

public class deleteGroup extends HttpServlet{
	
	static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	static DynamoDB dynamoDB;
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		
		String groupId=request.getParameter("groupId");
		groupInfo group=(groupInfo) request.getSession().getAttribute("curr_group");
		ArrayList<groupInfo> groupList = (ArrayList<groupInfo>)request.getSession().getAttribute("groupInfo");
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		System.out.println("In deleteGroup.java: "+groupId);
		getGroup g=new getGroup();
		HashMap<String,String> memberList=g.getGroupMember(groupId);
		Table table = dynamoDB.getTable("currentBalance");
		for (Entry<String, String> entry : memberList.entrySet()) { 		
		    String member = entry.getKey();
		    System.out.println(groupId+" "+member);
			table.deleteItem("groupId", groupId, "userId", member) ;
		}
		table = dynamoDB.getTable("groupDescription");
		table.deleteItem("groupId", groupId);
		ArrayList<groupInfo> newList=new ArrayList<groupInfo>();
		for(groupInfo gi:groupList){
			if(!groupId.equals(gi.groupId))
				newList.add(gi);
		}
		request.getSession().setAttribute("groupInfo", newList);		
		
		response.sendRedirect("/Tshare-test2/jsp/groupDeleted.jsp");
		
		
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
