package javaFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;

import javaFile.DynamoDBLock;

/**
 * Servlet implementation class AddBill
 */
public class PutBillToDB extends HttpServlet {

	static DynamoDB dynamoDB= get.dynamoDB;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		groupInfo group=(groupInfo)request.getSession().getAttribute("curr_group");
		String groupId=group.groupId;
		
		String billTotalAmt = request.getParameter("amount");
		String billName = request.getParameter("billName");
		String billDesc = request.getParameter("description");
		String[] userList = request.getParameterValues("userList");
		String img = request.getParameter("img");
		System.out.print("billAmt:"+billTotalAmt);
		System.out.print("billName:"+billName);
		System.out.print("billDesc:"+billDesc);
		System.out.println("userList:"+userList);
		System.out.println("img:"+img);
		
		int length = userList.length;
		double billAmtDouble = Double.parseDouble(billTotalAmt)/length;
		BigDecimal billAmtRounded = new BigDecimal(billAmtDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
		String billAmt = String.valueOf(billAmtRounded);
		
		User u=(User)request.getSession().getAttribute("userInfo");
		String userID=u.Id;
 		String body=userID+" paid "+billTotalAmt+" for "+ billName+". You owe "+userID+" "+billAmt+".";
		AmazonSES.sendNotice(userList, body, "New Bill Added at Tshare", userID);
		TimeUnit timeUnit = null;
		
		
		User StoredUser = (User) request.getSession().getAttribute("userInfo");
		String paidUserId = StoredUser.Id;
		System.out.println("paidUserId:"+paidUserId);
		
		
		Date date = new Date();
		long dateSec = date.getTime();
		String dateSecStr = Long.toString(dateSec);
		String dateStr = date.toString();
		request.getSession().setAttribute("billId",date+" "+paidUserId);
		String billImg = "";
		if(img!=null&&img.length()!=0)			
			billImg="https://s3-us-west-2.amazonaws.com/tsharebilling/"+date+" "+paidUserId;
		else 
			billImg="0";
		
		System.out.println("To add attribute to bill form");

		//Acquire the lock first
		boolean lock = false;
		int count = 0;
		while(!lock)
		{
			lock = DynamoDBLock.AcquireLock(groupId, userID, dateSecStr, "PutBill");
			if(lock == false)
			{
				//on acquiring lock failed
				count ++;
				Random randomGenerator = new Random();
				int randomInt = randomGenerator.nextInt(500);
				try {
					timeUnit.MILLISECONDS.sleep(randomInt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(count > 2000)
				{
					//resolve the possible deadlock situation, usually not to be used
					DynamoDBLock.ResolveDeadlock(groupId, dateSecStr);
				}
			}
		}
		
		//Add new item to the billForm 
		AddBillForm(dateStr, paidUserId, billDesc, billImg, groupId);//Add bill for the payer

		for( String userId : userList)
		{
			//Add new item to the expense of the payer and update their currentBalance
			//System.out.println("To add bill");
			AddExpense(dateStr, billName, billTotalAmt, billAmt, groupId, userId, paidUserId, length);
			UpdateBalance(userId, groupId, billAmt, paidUserId);
			//System.out.println("Bill add for "+userId);			
			
		}
		
		//update the currentBalance for the payer
		UpdateBalance(paidUserId, groupId, billAmt, length);
		
		DynamoDBLock.ReleaseLock(groupId, userID, dateSecStr);
		
		HashMap<String, String> groupBalance =new HashMap<String, String>();
		
		getGroup gg = new getGroup();
		gg.getGroupSet(userID,groupBalance);
		request.getSession().setAttribute("groupBalance", groupBalance);
		
		response.sendRedirect("/jsp/Main-page.jsp");
	}
	
	
	
	protected static void AddExpense(String date, String billName,
			String billTotalAmt, String billAmt, String groupId, String userId, String paidUserId, int memCnt)
	//Add new bill information into the expense table
	{		
		
		Table table = dynamoDB.getTable("expense");
		String key = userId ;
		
		if(userId.equals(paidUserId)) billAmt =
				String.valueOf(0-Double.parseDouble(billAmt)*((double)memCnt-1));
		String billId=date+" "+paidUserId;
		Item item = new Item().withPrimaryKey("billId",billId,"userId",userId)
				.withString("groupId", groupId)
				.withString("billName", billName)
				.withString("totalAmount", billTotalAmt)
				.withString("amount", billAmt)
				.withString("paidBy", paidUserId);
		System.out.println("To put into expense table");		
		PutItemOutcome outcome = table.putItem(item);
		
	}
	

	protected static void AddBillForm(String date, String userId, String billDesc, String billImg, String groupId )
	//Add new item to the bill table
	{
		
		Table table = dynamoDB.getTable("bill");
		Item item = new Item()
				.withPrimaryKey("billId", date+" "+userId)
				.withString("description", billDesc)
				.withString("photoPath",billImg)
				.withString("groupId", groupId);

		System.out.println("To put into bill table");
		PutItemOutcome outcome = table.putItem(item);
		
	}
	
	
	protected static void UpdateBalance(
			String userId, String groupId, String billAmt, String payer)
	//Update information for the currentbalance table
	//For all the people involved in the group, inculding the payer
	{
		
		Table table = dynamoDB.getTable("currentBalance");
		Item item;
		String newBalance = null;
		
		//Get the current balance information in the currentBalance table
		//If not success, create a new one
		try
		{
			item = table.getItem("userId",userId, "groupId", groupId);
			String balance = item.getJSON("balance");
			double newBalanceDouble = 
					Double.parseDouble(balance.substring(1, balance.length()-1))+Double.parseDouble(billAmt);
			BigDecimal newBalanceRound = new BigDecimal(newBalanceDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
			newBalance = 
					String.valueOf(newBalanceRound);
			
		} catch (Exception e){
			System.err.println(e.getMessage());
			newBalance = billAmt;
			System.out.println("no previous record found, create a new one");
		}
				
		//Update the currentBalance table
		Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();
			
		updateItems.put("balance", new AttributeValueUpdate().withValue(new AttributeValue(newBalance)).withAction("PUT"));
		
		Map<String, AttributeValue> itemKeys = new HashMap<String, AttributeValue>();
		itemKeys.put("userId", new AttributeValue(userId));
		itemKeys.put("groupId", new AttributeValue(groupId));
		
		UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName("currentBalance")
                .withKey(itemKeys)
                .withAttributeUpdates(updateItems);

		get.client.updateItem(updateItemRequest);
		
		
		
	}
	
	protected static void UpdateBalance(
			String payer, String groupId, String billAmt, int memCnt)
	//Update information for the currentbalance table
	//For the payer
	{
		
		Table table = dynamoDB.getTable("currentBalance");
		Item item;
		String newBalance = null;
		//Get the current balance information in the currentBalance table
		//If not success, create a new one
		try
		{
			item = table.getItem("groupId", groupId,"userId", payer);
			String balance = item.getJSON("balance");
			double newBalanceDouble = 
					Double.parseDouble(balance.substring(1, balance.length()-1))-Double.parseDouble(billAmt)*(double)memCnt;
			BigDecimal newBalanceRound = new BigDecimal(newBalanceDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
			newBalance = 
					String.valueOf(newBalanceRound);
			System.out.println("balance"+newBalance);
			
		} catch (Exception e){
			System.err.println(e.getMessage());
			newBalance = String.valueOf(new BigDecimal(0-Double.parseDouble(billAmt)*(double)memCnt).setScale(2, BigDecimal.ROUND_HALF_UP));
			System.out.println("no previous record found, create a new one");
		}
		
		Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();
		
		//Update the currentBalance table
		updateItems.put("balance", new AttributeValueUpdate().withValue(new AttributeValue(newBalance)).withAction("PUT"));
		
		Map<String, AttributeValue> itemKeys = new HashMap<String, AttributeValue>();
		itemKeys.put("userId", new AttributeValue(payer));
		itemKeys.put("groupId", new AttributeValue(groupId));
		
		UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName("currentBalance")
                .withKey(itemKeys)
                .withAttributeUpdates(updateItems);

		get.client.updateItem(updateItemRequest);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
