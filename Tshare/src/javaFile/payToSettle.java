package javaFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;

import javaFile.DynamoDBLock;

public class payToSettle extends HttpServlet {
	static DynamoDB dynamoDB= get.dynamoDB;
 protected void doGet(HttpServletRequest request, 
     HttpServletResponse response) throws ServletException, IOException 
   {
	 groupInfo group=(groupInfo)request.getSession().getAttribute("curr_group");
	 String groupId=group.groupId;
	 

	 Date date = new Date();
	 long dateSec = date.getTime();
	 String dateSecStr = Long.toString(dateSec);
	 String dateStr = date.toString();
	 TimeUnit timeUnit = null;
	 
	 
	 get.client.setRegion(Region.getRegion(Regions.US_WEST_2));
	 String tableName="currentBalance";
	 dynamoDB = new DynamoDB(get.client);
	 User u=(User)request.getSession().getAttribute("userInfo");
	 String userId=u.Id;
	 Table table = dynamoDB.getTable(tableName);
	   // reading the user input    
	 response.setContentType("text/html");
	 response.setCharacterEncoding("utf-8");
	 Double amount=Double.parseDouble(request.getParameter("amount"));
		
		
	 boolean lock = false;
	 int count = 0;
	 while(!lock)
	 {
		 lock = DynamoDBLock.AcquireLock(groupId, userId, dateSecStr, "Settle");
		 if(lock == false)
		 {
			 count ++;
			 Random randomGenerator = new Random();
			 int randomInt = randomGenerator.nextInt(500);
			 try 
			 {
				 timeUnit.MILLISECONDS.sleep(randomInt);
			 } catch (Exception e) {
					// TODO Auto-generated catch block
				 e.printStackTrace();
			 }
			 if(count > 2000)
			 {
				 DynamoDBLock.ResolveDeadlock(groupId, dateSecStr);
			 }
		 }
	 }

		
		
		Item item = table.getItem("groupId", groupId, "userId", userId); 
		String balance=removeQuo.remove(item.getJSON("balance"));
		Double before=Double.parseDouble(balance);		
		String receiver=request.getParameter("receiver");
		String beingPaid=request.getParameter("beingPaid");	
		if(beingPaid.equals("1"))
			amount*=-1;
		Double after=before-amount;
		System.out.println(after);
		BigDecimal afterRound = new BigDecimal(after).setScale(2, BigDecimal.ROUND_HALF_UP);
		Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();

		updateItems.put("balance", new AttributeValueUpdate().withValue(new AttributeValue(String.valueOf(afterRound))).withAction("PUT"));
		
		Map<String, AttributeValue> itemKeys = new HashMap<String, AttributeValue>();
		itemKeys.put("userId", new AttributeValue(userId));
		itemKeys.put("groupId", new AttributeValue(groupId));

		UpdateItemRequest updateItemRequest = new UpdateItemRequest()
		                                          .withTableName("currentBalance")
		                                          .withKey(itemKeys)
		                                          .withAttributeUpdates(updateItems);

		get.client.updateItem(updateItemRequest);
		
		//get receiver's balance from DB
		
		item = table.getItem("groupId", groupId, "userId", receiver); 
		balance=removeQuo.remove(item.getJSON("balance"));
		Double receiverBefore=Double.parseDouble(balance);
		Double receiverAfter=receiverBefore+amount;
		BigDecimal newBalanceRound = new BigDecimal(receiverAfter).setScale(2, BigDecimal.ROUND_HALF_UP);
		updateItems.put("balance", new AttributeValueUpdate().withValue(new AttributeValue(String.valueOf(newBalanceRound))).withAction("PUT"));
		System.out.println("updating "+receiver+"'s balance: "+ String.valueOf(newBalanceRound));
		itemKeys.put("userId", new AttributeValue(receiver));
		updateItemRequest
        .withTableName("currentBalance")
        .withKey(itemKeys)
        .withAttributeUpdates(updateItems);
		get.client.updateItem(updateItemRequest);
		
		//reload sessions for settle-up
		try {	         
	         item = table.getItem("groupId", groupId, "userId", userId); 
	         System.out.println(item.toString());
	         balance=removeQuo.remove(item.getJSON("balance"));
	         request.getSession().setAttribute(userId+groupId+"balance", balance);
	         //System.out.println("balance="+balance);
	         
	         
	         HashMap<String, Condition> keyConditions = new HashMap<String, Condition>();
	         keyConditions.put("groupId", new Condition().
	        		  withComparisonOperator(ComparisonOperator.EQ).
	        		  withAttributeValueList(new AttributeValue().withS(groupId)));
	         QueryRequest queryRequest = new QueryRequest().withTableName(tableName);
	         queryRequest.setKeyConditions(keyConditions);
	         QueryResult queryResult = get.client.query(queryRequest);
	         System.out.println(queryResult.toString()+" test");
	         List<Map<String, AttributeValue>> items = queryResult.getItems();
	         System.out.println("list!");
	         	         
	         Iterator<Map<String, AttributeValue>> itemsIter = items.iterator();
	         String totalBalance = null;
	         String user=null;
	         ArrayList<userBalance> ubList=new ArrayList<userBalance>();
	         while (itemsIter.hasNext()) {
	        	 	System.out.println("iterator");
	        	 	
	        	    Map<String, AttributeValue> currentItem = itemsIter.next();
	        	    Iterator<String> currentItemIter = currentItem.keySet().iterator();
	        	   
	        	    while (currentItemIter.hasNext()) {
	        	        String attr = (String) currentItemIter.next();
	        	        if (attr.equals("balance") ) {
	        	            totalBalance = currentItem.get(attr).getS();
	        	            System.out.println(totalBalance);
	        	            
	        	        }
	        	        else if (attr.equals("userId") ) {
	        	        	user = currentItem.get(attr).getS();	        	            
	        	        }
	        	        
	        	    }
	        	    userBalance ub=new userBalance(user, Double.parseDouble(totalBalance));
       	        ubList.add(ub);
	        	}
	         HashMap<String[], Double> plan;
	         Solution sol=new Solution();
	         plan=sol.getSolution(ubList);
	         request.getSession().setAttribute(userId+groupId+"optimized", plan);
	        
	         /*HashMap<String[], Integer> optimized=(HashMap<String[], Integer>)request.getSession().getAttribute(userId+groupId+"optimized");
	          for (Entry<String[], Integer> entry : optimized.entrySet()) {
			    String[] key = entry.getKey();
			    int value = entry.getValue();
			    System.out.println(key[0]+" pays "+key[1]+" $"+Integer.toString(value));				    
			}*/
	         response.sendRedirect("/jsp/Settle-up.jsp");
	         DynamoDBLock.ReleaseLock(groupId, userId, dateSecStr);
	     } catch (Exception e) {
	         System.err.println("Failed to fetch item in " + tableName);
	         System.err.println(e.getMessage());
	         DynamoDBLock.ReleaseLock(groupId, userId, dateSecStr);
	     }
		 
 }

 
   public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
