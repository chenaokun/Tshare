package javaFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;

//This servlet is used to delete a bill in a group.
public class deleteBill extends HttpServlet{
	static DynamoDB dynamoDB= get.dynamoDB;
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		
		String billId=request.getParameter("billId");
		groupInfo g=(groupInfo)request.getSession().getAttribute("curr_group");
		String groupId = g.groupId;
		ArrayList<activityInfo> activity = (ArrayList<activityInfo>)request.getSession().getAttribute("all_activity");
		
		Table table1 = dynamoDB.getTable("expense");
		Table table2 = dynamoDB.getTable("bill");
		
		
		System.out.println("delete in group "+groupId+" "+billId);
		String members ="";
		String payerId = "";
		String totalAmount = "";
		String amount = "";
		for(activityInfo a : activity) {
			if(a.billId.equals(billId)){
				members = a.members;
				payerId = a.payerId;
				totalAmount = a.totalAmount;
				amount = a.amount;
				break;
			}				
		}
		Double numericAmt = Double.parseDouble(amount);
		if(numericAmt<0)
			numericAmt = 0-numericAmt;
		
		/*delete items from expense and bill tables && update table currentBalance*/
		String[] member = members.split(";");
		for(int j=0; j< member.length; j++) {
			System.out.println(member[j]);
			if(member[j].equals(payerId)){
				Double diff = numericAmt*(member.length-1);
				UpdateBalance(member[j], groupId, diff);
			} else {
				Double diff = 0-numericAmt;
				UpdateBalance(member[j], groupId, diff);
			}
			
			table1.deleteItem("userId", member[j], "billId", billId) ;
		}
		table2.deleteItem("groupId", groupId, "billId", billId) ;
				
		/*reset all_activity attribute*/
		User u=(User)request.getSession().getAttribute("userInfo");
		ArrayList<activityInfo> user_activity = getActivity.allActivity(u.Id, groupId);
		request.getSession().setAttribute("all_activity", user_activity);
		response.sendRedirect("/jsp/All_activity.jsp");	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
	
	protected static void UpdateBalance(
			String userId, String groupId, Double amount)
	{
		
		Table table = dynamoDB.getTable("currentBalance");
		Item item;
		String newBalance = null;
		
		try
		{
			item = table.getItem("groupId", groupId,"userId", userId);
			String balance = item.getJSON("balance");
			//System.out.println("balance before update: "+balance.substring(1, balance.length()-1));
			double newBalanceDouble = 
					Double.parseDouble(balance.substring(1, balance.length()-1))+amount;
			//System.out.println("balance before round: "+newBalanceDouble);
			
			BigDecimal newBalanceRound = new BigDecimal(newBalanceDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
			newBalance = String.valueOf(newBalanceRound);
			//System.out.println("balance"+newBalance);
			
		} catch (Exception e){
			System.err.println(e.getMessage());
			System.out.println("no previous record found, create a new one");
		}
		
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
}
