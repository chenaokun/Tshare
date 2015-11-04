package javaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class payToSettle extends HttpServlet {
	 static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	 static DynamoDB dynamoDB;
	 static String groupId="";
 protected void doGet(HttpServletRequest request, 
     HttpServletResponse response) throws ServletException, IOException 
   {
	 groupInfo group=(groupInfo)request.getSession().getAttribute("curr_group");
	 groupId=group.groupId;
	 
	 client.setRegion(Region.getRegion(Regions.US_WEST_2));
	 
	 // reading the user input    
	   response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Integer amount=Integer.parseInt(request.getParameter("amount"));
		Integer before=Integer.parseInt(request.getParameter("before"));
		String receiver=request.getParameter("receiver");
		User u=(User)request.getSession().getAttribute("userInfo");
		String userId=u.Id;
		Integer after=before-amount;
		System.out.println(after);
		
		Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();

		updateItems.put("balance", new AttributeValueUpdate().withValue(new AttributeValue(Integer.toString(after))).withAction("PUT"));
		
		Map<String, AttributeValue> itemKeys = new HashMap<String, AttributeValue>();
		itemKeys.put("userId", new AttributeValue(userId));
		itemKeys.put("groupId", new AttributeValue(groupId));

		UpdateItemRequest updateItemRequest = new UpdateItemRequest()
		                                          .withTableName("currentBalance")
		                                          .withKey(itemKeys)
		                                          .withAttributeUpdates(updateItems);

		client.updateItem(updateItemRequest);
		
		//get receiver's balance from DB
		String tableName="currentBalance";
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tableName);
		Item item = table.getItem("groupId", groupId, "userId", receiver); 
		String balance=removeQuo.remove(item.getJSON("balance"));
		Integer receiverBefore=Integer.parseInt(balance);
		Integer receiverAfter=receiverBefore+amount;
		updateItems.put("balance", new AttributeValueUpdate().withValue(new AttributeValue(Integer.toString(receiverAfter))).withAction("PUT"));
		System.out.println("updating "+receiver+"'s balance: "+ Integer.toString(receiverAfter));
		itemKeys.put("userId", new AttributeValue(receiver));
		updateItemRequest
        .withTableName("currentBalance")
        .withKey(itemKeys)
        .withAttributeUpdates(updateItems);
		client.updateItem(updateItemRequest);
		
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
	         QueryResult queryResult = client.query(queryRequest);
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
	         response.sendRedirect("/Tshare-test2/jsp/Settle-up.jsp");
	     } catch (Exception e) {
	         System.err.println("Failed to fetch item in " + tableName);
	         System.err.println(e.getMessage());
	     }
 }

 
   public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
