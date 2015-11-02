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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class getGroup { 
	
	 static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	 static DynamoDB dynamoDB;
	 
  public groupInfo searchGroup(String groupId) {
		 System.out.println("invoke method grouId is "+groupId);
		 
		 client.setRegion(Region.getRegion(Regions.US_WEST_2));
		 dynamoDB = new DynamoDB(client);
	     
	     Map<String, AttributeValue> expressionAttributeValues = 
				    new HashMap<String, AttributeValue>();
		  expressionAttributeValues.put(":g", new AttributeValue().withS(groupId));
		  
		  try {
			  ScanRequest scanRequest = new ScanRequest()
				        .withTableName("groupDescription")
				        .withFilterExpression("groupId = :g")
				        .withExpressionAttributeValues(expressionAttributeValues);
			  			
				ScanResult result = client.scan(scanRequest);
			  	List<Map<String, AttributeValue>> items = result.getItems(); 
				    
		         Iterator<Map<String, AttributeValue>> itemsIter = items.iterator();	         
		         groupInfo curr_group = new groupInfo(groupId);
		         while (itemsIter.hasNext()) {
		        	    Map<String, AttributeValue> currentItem = itemsIter.next();
		        	    Iterator<String> currentItemIter = currentItem.keySet().iterator();
		        	    
		        	    
		        	    while (currentItemIter.hasNext()) {
		        	        String attr = (String) currentItemIter.next();
		        	        if (attr.equals("groupName") ) {
		        	        	String name = currentItem.get(attr).getS(); 
		        	        	curr_group.setName(name);
		        	        	
		        	        	//System.out.println("group : "+curr_group);        	        	
		        	        }else if (attr.equals("description") ) {
		        	        	String description = currentItem.get(attr).getS();   
		        	        	curr_group.setDescription(description);
		        	        	//System.out.println("group : "+curr_group);        	        	
		        	        }          	        
		        	    }  
		        	    return curr_group;
		          }
		     } catch (Exception e) {
		         System.err.println("Failed to fetch item in groupDescription");
		         System.err.println(e.getMessage());
		     }
		    return null;

	 }

  public ArrayList<String> getGroupSet(String userId){
	  client.setRegion(Region.getRegion(Regions.US_WEST_2));
	  dynamoDB = new DynamoDB(client);
	  //Table table = dynamoDB.getTable(tableName);
	  Map<String, AttributeValue> expressionAttributeValues = 
			    new HashMap<String, AttributeValue>();
	  expressionAttributeValues.put(":u", new AttributeValue().withS(userId));
	  
	  try {
		  ScanRequest scanRequest = new ScanRequest()
			        .withTableName("currentBalance")
			        .withFilterExpression("userId = :u")
			        .withExpressionAttributeValues(expressionAttributeValues);
		  			
			    ScanResult result = client.scan(scanRequest);
		  		List<Map<String, AttributeValue>> items = result.getItems(); 
			    
	         Iterator<Map<String, AttributeValue>> itemsIter = items.iterator();	         
	         ArrayList<String> groups=new ArrayList<String>();
	         while (itemsIter.hasNext()) {
	        	    Map<String, AttributeValue> currentItem = itemsIter.next();
	        	    Iterator<String> currentItemIter = currentItem.keySet().iterator();
	        	   
	        	    while (currentItemIter.hasNext()) {
	        	        String attr = (String) currentItemIter.next();
	        	        if (attr.equals("groupId") ) {
	        	        	String curr_group = currentItem.get(attr).getS();   
	        	        	groups.add(curr_group);
	        	        	//System.out.println("group : "+curr_group);        	        	
	        	        }        	        
	        	    }   	    
	          }
	         return groups;

	     } catch (Exception e) {
	         System.err.println("Failed to fetch item in currentBalance");
	         System.err.println(e.getMessage());
	     }
	    return null;
  }
}
