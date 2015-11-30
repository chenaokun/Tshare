package javaFile;

import java.io.IOException;
import java.util.ArrayList;
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
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class addBill extends HttpServlet{	 
	 static DynamoDB dynamoDB= get.dynamoDB;
	 
	 protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		groupInfo group=(groupInfo)request.getSession().getAttribute("curr_group");
		String groupId=group.groupId;	
		 Table table = dynamoDB.getTable("currentBalance");
		 ItemCollection<QueryOutcome> col = table.query("groupId","1");
		 ArrayList<String> memberList=new ArrayList<String>();
		 for (Item item: col) {
			 memberList.add(item.getJSON("userId"));
			 System.out.println(item.getJSON("userId"));
		 }
		 HashMap<String,String> groupToMember=new HashMap<String,String>();
		 table = dynamoDB.getTable("Usr_info");
		 Item item;
		 for(String Id: memberList){
			 Id=removeQuo.remove(Id);
			 item = table.getItem("Id", Id);
			 String name=item.getJSON("userName");
			 groupToMember.put(Id, removeQuo.remove(name));
		 }
		 request.getSession().setAttribute("groupToMember", groupToMember);
		 response.sendRedirect("/jsp/AddBill.jsp");
	}
}
