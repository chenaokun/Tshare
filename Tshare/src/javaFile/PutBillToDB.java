package javaFile;

import java.io.IOException;
import java.util.Date;

import java.io.*;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.Request;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Servlet implementation class AddBill
 */
public class PutBillToDB extends HttpServlet {

	static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	static DynamoDB dynamoDB;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String billTotalAmt = request.getParameter("amount");
		String billName = request.getParameter("billName");
		String billDesc = request.getParameter("description");
		String[] userList = request.getParameterValues("userList");
		
		System.out.print("billAmt:"+billTotalAmt);
		System.out.print("billName:"+billName);
		System.out.print("billDesc:"+billDesc);
		System.out.print("userList:"+userList);
		
		int length = userList.length;
		double billAmtDouble = Double.parseDouble(billTotalAmt)/length;
		String billAmt = String.valueOf(billAmtDouble);
		//Test Data
		/*String billAmt = "123";
		String billName = "Test Bill";
		String billDesc = "Test Bill Description";
		String[] userList = {"Ann ann@mail.com", "Kate kate@mail.com", "Jack jack@mail.com"};*/
		String groupId = "33892";
		
		User StoredUser = (User) request.getSession().getAttribute("userInfo");
		String paidUserId = StoredUser.Id;
		System.out.println("paidUserId:"+paidUserId);
		
		/*String groupId = request.getSession().getAttribute("groupId").toString();
		System.out.print("groupId:"+groupId);*/
		
		//String memberGroup = request.getSession().getAttribute("UserAdd").toString();
		String billImg = "image SRC";
		
		
		Date date = new Date();
		String dateStr = date.toString();
		
		System.out.println("To add attribute to bill form");
		AddBillForm(dateStr, paidUserId, billDesc, billImg);
		
		for( String userId : userList)
		{
			
			System.out.println("To add bill");
			AddExpense(dateStr, billName, billTotalAmt, billAmt, groupId, userId, paidUserId);
			UpdateBalance(userId, groupId, billAmt, paidUserId);
			System.out.println("Bill add for "+userId);			
			
		}
		
		//Update the new balance
		//payer first, then everyone
		UpdateBalance(paidUserId, groupId, billTotalAmt);
		
		
		response.sendRedirect("/Tshare-test2/jsp/Main-page.jsp");
	}
	
	
	
	protected static void AddExpense(String date, String billName,
			String billTotalAmt, String billAmt, String groupId, String userId, String paidUserId)
	{		
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("expense");
		String key = userId ;
		
		Item item = new Item().withPrimaryKey("userId",userId,"time",date)
				.withString("groupId", groupId)
				.withString("billName", billName)
				.withString("totalAmount", billTotalAmt)
				.withString("amount", billAmt)
				.withString("paidBy", paidUserId);
		System.out.println("To put into expense table");
		PutItemOutcome outcome = table.putItem(item);
	}
	
	protected static void AddBillForm(String date, String userId, String billDesc, String billImg)
	{
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("bill");
		Item item = new Item()
				.withPrimaryKey("userId", userId, "time", date)
				.withString("description", billDesc)
				.withString("photoPath",billImg);
		System.out.println("To put into bill table");
		PutItemOutcome outcome = table.putItem(item);
		
	}
	
	protected static Item FindObject(
			String tableName, String primaryKey, String primaryValue, String rangeKey, String rangeValue)
	{
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tableName);
		
		try {

	         System.out.println("getting table " + tableName);	        
	         Item item = table.getItem(primaryKey, primaryValue, rangeKey, rangeValue);    
	         String user_pass = item.toString();
	         System.out.println(user_pass);
	         return item;

	     } catch (Exception e) {
	         System.err.println("Failed look for item in " + tableName);
	         System.err.println(e.getMessage());
	     }
		return null;
	}
	
	protected static void UpdateBalance(
			String userId, String groupId,  String billAmt, String payer)
	{
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("currentBalance");
		Item item;
		String newBalance = null;
		try
		{
			item = table.getItem("userId",userId, "groupId", groupId);
			String balance = item.getJSON("balance");
			newBalance = 
					String.valueOf(Double.parseDouble(balance.substring(1, balance.length()-1))+Double.parseDouble(billAmt));
			
		} catch (Exception e){
			System.err.println(e.getMessage());
			newBalance = billAmt;
			System.out.println("no previous record found, create a new one");
		}
				
		
		Item newItem = new Item()
				.withPrimaryKey("userId",userId,"groupId",groupId)
				.withString("balance", newBalance);
		PutItemOutcome outcome = table.putItem(newItem);
		
		
	}
	
	protected static void UpdateBalance(
			String payer, String groupId, String totalBillAmt)
	{
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("currentBalance");
		Item item;
		String newBalance = null;
		
		try
		{
			item = table.getItem("groupId", groupId,"userId", payer);
			String balance = item.getJSON("balance");
			newBalance = 
					String.valueOf(Double.parseDouble(balance.substring(1, balance.length()-1))-Double.parseDouble(totalBillAmt));
			System.out.println("balance"+newBalance);
			
		} catch (Exception e){
			System.err.println(e.getMessage());
			newBalance = String.valueOf(0-Double.parseDouble(totalBillAmt));
			System.out.println("no previous record found, create a new one");
		}
		
		Item newItem = new Item()
				.withPrimaryKey("groupId",groupId, "userId",payer)
				.withString("balance", newBalance);
		PutItemOutcome outcome = table.putItem(newItem);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
