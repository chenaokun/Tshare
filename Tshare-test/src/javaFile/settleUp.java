package javaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

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
	         //System.out.println(item.toString());
	         String balance=removeQuo.remove(item.getJSON("balance"));
	         request.getSession().setAttribute(userId+groupId+"balance", balance);
	         //System.out.println("balance="+balance);
	         
	         
	         HashMap<String, Condition> keyConditions = new HashMap<String, Condition>();
	         keyConditions.put("groupId", new Condition().
	        		  withComparisonOperator(ComparisonOperator.EQ).
	        		  withAttributeValueList(new AttributeValue().withS("1")));
	         QueryRequest queryRequest = new QueryRequest().withTableName(tableName);
	         queryRequest.setKeyConditions(keyConditions);
	         QueryResult queryResult = client.query(queryRequest);
	         System.out.println(queryResult.toString());
	         List<Map<String, AttributeValue>> items = queryResult.getItems();
	         Iterator<Map<String, AttributeValue>> itemsIter = items.iterator();
	         String totalBalance = null;
	         String user=null;
	         ArrayList<userBalance> ubList=new ArrayList<userBalance>();
	         while (itemsIter.hasNext()) {
	        	    Map<String, AttributeValue> currentItem = itemsIter.next();
	        	    Iterator<String> currentItemIter = currentItem.keySet().iterator();
	        	   
	        	    while (currentItemIter.hasNext()) {
	        	        String attr = (String) currentItemIter.next();
	        	        if (attr.equals("balance") ) {
	        	            totalBalance = currentItem.get(attr).getS();	        	            
	        	        }
	        	        else if (attr.equals("userId") ) {
	        	        	user = currentItem.get(attr).getS();	        	            
	        	        }
	        	        
	        	    }
	        	    userBalance ub=new userBalance(user, Integer.parseInt(totalBalance));
        	        ubList.add(ub);
	        	}
	         HashMap<String[], Integer> plan;
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
  
  
}