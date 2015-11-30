package javaFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class getActivity {
	static DynamoDB dynamoDB= get.dynamoDB;
	
	public static ArrayList<activityInfo> userActivity(String userId, String groupId){
		
		 List<Map<String, AttributeValue>> User_activity = new ArrayList<Map<String, AttributeValue>>();
		 Map<String, AttributeValue> lastKeyEvaluated = null;
		 int size = 0;
	     Map<String, AttributeValue> expressionAttributeValues = 
				    new HashMap<String, AttributeValue>();
	     
		 expressionAttributeValues.put(":g", new AttributeValue().withS(groupId));
		 expressionAttributeValues.put(":u", new AttributeValue().withS(userId));
	     //expressionAttributeValues.put(":id", new AttributeValue().withS(Id));
		  
		  try {
			  do{
				  ScanRequest scanRequest = new ScanRequest()
					        .withTableName("expense")
					        //.withFilterExpression("groupId = :g")
					        .withLimit(10)
					        .withFilterExpression("userId = :u and groupId = :g")
					        .withExpressionAttributeValues(expressionAttributeValues)
				  			.withExclusiveStartKey(lastKeyEvaluated);
				  			
					ScanResult result = get.client.scan(scanRequest);
					lastKeyEvaluated = result.getLastEvaluatedKey();
				  	List<Map<String, AttributeValue>> items = result.getItems();  
				  	for(Map<String, AttributeValue> item : items){
				  		User_activity.add(item);
				  	}
				  	size = User_activity.size();
			  } while(size < 10 && lastKeyEvaluated != null);
			   
		        Iterator<Map<String, AttributeValue>> itemsIter = User_activity.iterator();	
		        ArrayList<activityInfo> user_activity = new ArrayList(); 
		        //activityInfo curr_activity = new activityInfo(userId); 
		        while (itemsIter.hasNext()) {
		        	   Map<String, AttributeValue> currentItem = itemsIter.next();
		        	   Iterator<String> currentItemIter = currentItem.keySet().iterator();
		        	   activityInfo curr_activity = new activityInfo();
		        	   
		        	   while (currentItemIter.hasNext()) {
		        	       String attr = (String) currentItemIter.next();
		        	       if (attr.equals("billId") ) {
		        	        String billId = currentItem.get(attr).getS(); 
		        	        curr_activity.setId(billId);		        
		        	        //System.out.println("billId : "+billId);        	        	
		        	       } else if (attr.equals("billName") ) {
		        	    	   String billName = currentItem.get(attr).getS();   
		        	           //System.out.println("billName : "+billName);
		        	           curr_activity.setName(billName);		        	           
		        	       } else if (attr.equals("paidBy")) {
		        	    	   String payer = currentItem.get(attr).getS();   
		        	           //System.out.println("payer : "+payer);
		        	           curr_activity.setPayer(payer);
		        	       } else if (attr.equals("amount")) {
		        	    	   String amount = currentItem.get(attr).getS();   
		        	           //System.out.println("amount : "+amount);
		        	           curr_activity.setAmount(amount);
		        	       } else if (attr.equals("totalAmount")) {
		        	    	   String totalAmount = currentItem.get(attr).getS();   
		        	           //System.out.println("totalAmount : "+totalAmount);
		        	           curr_activity.setTotal(totalAmount);
		        	       }
		        	  		        	       
		        	   }  
		        	   user_activity.add(curr_activity);
		         }
		        return user_activity;
		     } catch (Exception e) {
		         System.err.println("Failed to fetch item in groupDescription");
		         System.err.println(e.getMessage());
		     }
		  return null;
	}
	
	public static ArrayList<activityInfo> allActivity(String userId, String groupId){
		 
		 List<Map<String, AttributeValue>> All_activity = new ArrayList<Map<String, AttributeValue>>();
		 Map<String, AttributeValue> lastKeyEvaluated = null;
		 int size = 0;
	     Map<String, AttributeValue> expressionAttributeValues = 
				    new HashMap<String, AttributeValue>();
	     
		 expressionAttributeValues.put(":g", new AttributeValue().withS(groupId));
		 //expressionAttributeValues.put(":u", new AttributeValue().withS(userId));
	     //expressionAttributeValues.put(":id", new AttributeValue().withS(Id));
		  
		  try {
			  	do{
				  ScanRequest scanRequest = new ScanRequest()
					        .withTableName("bill")
					        //.withFilterExpression("groupId = :g")
					        .withLimit(10)
					        .withFilterExpression("groupId = :g")
					        .withExpressionAttributeValues(expressionAttributeValues)
				  			.withExclusiveStartKey(lastKeyEvaluated);
				  			
					ScanResult result = get.client.scan(scanRequest);
					lastKeyEvaluated = result.getLastEvaluatedKey();
				  	List<Map<String, AttributeValue>> items = result.getItems();  
				  	for(Map<String, AttributeValue> item : items){
				  		All_activity.add(item);
				  	}
				  	size = All_activity.size();
			  	} while(size < 10 && lastKeyEvaluated != null);
			   
		        Iterator<Map<String, AttributeValue>> itemsIter = All_activity.iterator();	
		        ArrayList<activityInfo> all_activity = new ArrayList(); 
		        //activityInfo curr_activity = new activityInfo(userId); 
		        while (itemsIter.hasNext()) {
		        	   Map<String, AttributeValue> currentItem = itemsIter.next();
		        	   Iterator<String> currentItemIter = currentItem.keySet().iterator();
		        	   activityInfo curr_activity = new activityInfo();
		        	   
		        	   while (currentItemIter.hasNext()) {
		        	       String attr = (String) currentItemIter.next();
		        	       if (attr.equals("billId") ) {
		        	        String billId = currentItem.get(attr).getS(); 
		        	        curr_activity.setId(billId);		        
		        	        //System.out.println("billId : "+billId);        	        	
		        	       	        	           
		        	       } else if (attr.equals("description")) {
		        	    	   String description = currentItem.get(attr).getS();   
		        	           //System.out.println("description : "+description);
		        	           curr_activity.setDescription(description);
		        	       } else if (attr.equals("photoPath")) {
		        	    	   String photoPath = currentItem.get(attr).getS();   
		        	           //System.out.println("photoPath : "+photoPath);
		        	           curr_activity.setTotal(photoPath);
		        	       }
		        	  		        	       
		        	   }  
		        	   
		        	   all_activity.add(curr_activity);
		         }
		        
		        for(activityInfo ai : all_activity){
		        	String billId = ai.billId;		    		 
		    		 Map<String, AttributeValue> expressionAttributeValues2 = 
		    				    new HashMap<String, AttributeValue>();
		    		 expressionAttributeValues2.put(":b", new AttributeValue().withS(billId));
		    		 
		    		 /*get members in this bill*/
		    		 ScanRequest scanRequest2 = new ScanRequest()
		 			        .withTableName("expense")
		 			        .withFilterExpression("billId = :b")
		 			        .withExpressionAttributeValues(expressionAttributeValues2);
		 		  			
		 			 ScanResult result2 = get.client.scan(scanRequest2);
		 		     List<Map<String, AttributeValue>> items2 = result2.getItems(); 
		 			    
		 	         Iterator<Map<String, AttributeValue>> itemsIter2 = items2.iterator();	         
		 	         String members = "";
		 	         while (itemsIter2.hasNext()) {
		 	        	    Map<String, AttributeValue> currentItem = itemsIter2.next();
		 	        	    Iterator<String> currentItemIter = currentItem.keySet().iterator();
		 	        	   
		 	        	    while (currentItemIter.hasNext()) {
		 	        	        String attr = (String) currentItemIter.next();
		 	        	        if (attr.equals("userId") ) {
		 	        	        	String member = currentItem.get(attr).getS();   
		 	        	        	members +=member+";";		 	        	        	        	        	
		 	        	        }        	        
		 	        	    }   	    
		 	          }
		 	        
		 	         ai.setMembers(members);
		 	         
		 	         /*set amount, totalAmount, paidBy, billName */
		 	         Table table = dynamoDB.getTable("expense");
		    		 Item item = table.getItem("userId", userId, "billId", billId); 
		    		 if(item == null){
		    			 ai.flag = false;
		    		 }else{
		    			 ai.flag = true;
		    			 ai.amount = removeQuo.remove(item.getJSON("amount"));
		    			 ai.totalAmount = removeQuo.remove(item.getJSON("totalAmount"));
		    			 ai.payerId = removeQuo.remove(item.getJSON("paidBy"));
		    			 ai.billName = removeQuo.remove(item.getJSON("billName"));
		    		 }
		    		 
		    		 
		        }
		        return all_activity;
		        
		     } catch (Exception e) {
		         System.err.println("Failed to fetch item");
		         System.err.println(e.getMessage());
		     }
		  return null;
	}
	
	public static void main(String[] args){
		allActivity("yanghuizi", "1");
	}
}
