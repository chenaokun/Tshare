package javaFile;

import java.io.IOException;

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

public class settleUp extends HttpServlet { 
	
	 static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	 static DynamoDB dynamoDB;
	
  protected void doGet(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException 
  {
	  System.out.println("called");	 
	  User u=(User)request.getSession().getAttribute("userInfo");
	  String userId=u.Id;
	  System.out.println("userid="+userId);
	  client.setRegion(Region.getRegion(Regions.US_WEST_2));
	  dynamoDB = new DynamoDB(client);
	  //to be modified
	  String groupId="1";
	  
	  String tableName="currentBalance";
	  Table table = dynamoDB.getTable(tableName);
	  try {	         
	         Item item = table.getItem("groupId", groupId, "userId", userId); 
	         System.out.println(item.toString());
	         String balance=removeQuo.remove(item.getJSON("balance"));
	         request.getSession().setAttribute(userId+groupId+"balance", balance);
	         System.out.println("balance="+balance);
	 		 response.sendRedirect("/Tshare-test2/jsp/Settle-up.jsp");
	     } catch (Exception e) {
	         System.err.println("Failed to fetch item in " + tableName);
	         System.err.println(e.getMessage());
	     }
  }
  
  
}