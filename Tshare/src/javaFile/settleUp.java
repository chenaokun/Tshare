package javaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

//This servlet calls the Solution class to provide an optimized plan, and present it to users.
public class settleUp extends HttpServlet { 
	
	static DynamoDB dynamoDB= get.dynamoDB;
	 
  protected void doGet(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException 
  {
	  //System.out.println("called");	 
	  User u=(User)request.getSession().getAttribute("userInfo");
	  String userId=u.Id;
	  //System.out.println("userid="+userId);
	 
	  //to be modified
	  groupInfo group=(groupInfo)request.getSession().getAttribute("curr_group");
	  String groupId=group.groupId;
	  //int groupId = 1;
	  
	  String tableName="currentBalance";
	  Table table = dynamoDB.getTable(tableName);
	  try {	         
	         Item item = table.getItem("groupId", groupId, "userId", userId); 
	         System.out.println(item.toString());
	         String balance=removeQuo.remove(item.getJSON("balance"));
	         request.getSession().setAttribute(userId+groupId+"balance", balance);
	         //System.out.println("balance="+balance);
	         
	         
	         HashMap<String, Condition> keyConditions = new HashMap<String, Condition>();
	         keyConditions.put("groupId", new Condition().
	        		  withComparisonOperator(ComparisonOperator.EQ).
	        		  withAttributeValueList(new AttributeValue().withS(groupId)));
	         QueryRequest queryRequest = new QueryRequest().withTableName(tableName);
	         queryRequest.setKeyConditions(keyConditions);
	         
	         
	         //Add lock to protect Read
	         Date date = new Date();
	 		long dateSec = date.getTime();
	 		String dateSecStr = Long.toString(dateSec);
	 		String dateStr = date.toString();
	 		TimeUnit timeUnit = null;
	 		int count = 0;
	 		boolean lock = false;
	 		
	         while(!lock)
	 		{
	 			lock = DynamoDBLock.AcquireLock(groupId, userId, dateSecStr, "PutBill");
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
	 					//DynamoDBLock.ResolveDeadlock(groupId, dateSecStr);
	 				}
	 			}
	 		}
	         
	         QueryResult queryResult = get.client.query(queryRequest);
	         
	         //Release Lock
	         DynamoDBLock.ReleaseLock(groupId, userId, dateSecStr);
	         
	         
	         System.out.println(queryResult.toString()+" test");
	         List<Map<String, AttributeValue>> items = queryResult.getItems();
	         System.out.println("list!");
	         
	        
	         
	         Iterator<Map<String, AttributeValue>> itemsIter = items.iterator();
	         String totalBalance = null;
	         String user=null;
	         ArrayList<userBalance> ubList=new ArrayList<userBalance>();
	         while (itemsIter.hasNext()) {
	        	 	//System.out.println("iterator");
	        	 	
	        	    Map<String, AttributeValue> currentItem = itemsIter.next();
	        	    Iterator<String> currentItemIter = currentItem.keySet().iterator();
	        	   
	        	    while (currentItemIter.hasNext()) {
	        	        String attr = (String) currentItemIter.next();
	        	        if (attr.equals("balance") ) {
	        	            totalBalance = currentItem.get(attr).getS();
	        	            //System.out.println(totalBalance);
	        	            
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
	     } catch (Exception e) {
	         System.err.println("Failed to fetch item in " + tableName);
	         System.err.println(e.getMessage());
	     }
  }
  
  
}