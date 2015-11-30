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
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
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
	static DynamoDB dynamoDB= get.dynamoDB;
	 
  public groupInfo searchGroup(String groupId) {
		 //System.out.println("invoke method grouId is "+groupId);
		
	     
	     Map<String, AttributeValue> expressionAttributeValues = 
				    new HashMap<String, AttributeValue>();
		  expressionAttributeValues.put(":g", new AttributeValue().withS(groupId));
		  
		  try {
			  ScanRequest scanRequest = new ScanRequest()
				        .withTableName("groupDescription")
				        .withFilterExpression("groupId = :g")
				        .withExpressionAttributeValues(expressionAttributeValues);
			  			
				ScanResult result = get.client.scan(scanRequest);
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

  public ArrayList<String> getGroupSet(String userId, HashMap<String, String> groupBalance){
	  
	  //Table table = dynamoDB.getTable(tableName);
	  Map<String, AttributeValue> expressionAttributeValues = 
			    new HashMap<String, AttributeValue>();
	  expressionAttributeValues.put(":u", new AttributeValue().withS(userId));
	  
	  try {
		  ScanRequest scanRequest = new ScanRequest()
			        .withTableName("currentBalance")
			        .withFilterExpression("userId = :u")
			        .withExpressionAttributeValues(expressionAttributeValues);
		  			
			    ScanResult result = get.client.scan(scanRequest);
		  		List<Map<String, AttributeValue>> items = result.getItems(); 
			    
	         Iterator<Map<String, AttributeValue>> itemsIter = items.iterator();	         
	         ArrayList<String> groups=new ArrayList<String>();
	         while (itemsIter.hasNext()) {
	        	    Map<String, AttributeValue> currentItem = itemsIter.next();
	        	    Iterator<String> currentItemIter = currentItem.keySet().iterator();
	        	    String groupId="";
	        	    String balance="";
	        	    while (currentItemIter.hasNext()) {
	        	        String attr = (String) currentItemIter.next();        	        
	        	        
	        	        if (attr.equals("groupId") ) {
	        	        	String curr_group = currentItem.get(attr).getS();   
	        	        	groups.add(curr_group);
	        	        	groupId=currentItem.get(attr).getS();   	        	
	        	        }    
	        	        else if(attr.equals("balance")){
	        	        	balance=currentItem.get(attr).getS();
	        	        }
	        	    }
	        	    groupBalance.put(groupId, balance);
	          }
	         return groups;

	     } catch (Exception e) {
	         System.err.println("Failed to fetch item in currentBalance");
	         System.err.println(e.getMessage());
	     }
	    return null;
  }
  
  public HashMap<String,String> getGroupMember(String groupId){
	  
		 Table table = dynamoDB.getTable("currentBalance");
		 ItemCollection<QueryOutcome> col = table.query("groupId",groupId);
		 ArrayList<String> memberList=new ArrayList<String>();
		 for (Item item: col) {
			 memberList.add(item.getJSON("userId"));
			 //System.out.println(item.getJSON("userId"));
		 }
		 HashMap<String,String> groupToMember=new HashMap<String,String>();
		 HashMap<String,String> groupToImg=new HashMap<String,String>();
		 table = dynamoDB.getTable("Usr_info");
		 Item item;
		 for(String Id: memberList){
			 Id=removeQuo.remove(Id);
			 item = table.getItem("Id", Id);
			 String name=item.getJSON("userName");
			 groupToMember.put(Id, removeQuo.remove(name));
			 String img=item.getJSON("photoPath");
			 groupToImg.put(Id, removeQuo.remove(img));
		 }
		 
		 return groupToMember;
		 
  }
}

