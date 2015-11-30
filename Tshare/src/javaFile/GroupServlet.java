package javaFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;


public class GroupServlet extends HttpServlet {

	static DynamoDB dynamoDB= get.dynamoDB;
 
 protected void doGet(HttpServletRequest request, 
	      HttpServletResponse response) throws ServletException, IOException 
	  {
	    // reading the user input      
	    response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String g=request.getParameter("groupName");
		String m=request.getParameter("memberList");
		String d=request.getParameter("groupDescription");
		User u=(User)request.getSession().getAttribute("userInfo");
		String userId = u.Id;
		/*System.out.println("groupName:"+g);
		System.out.println("member:"+m);
		System.out.println("user:"+userId);*/
		if(g!="" && m!=""){
			LoadGroup(g, m, d, userId);
			//System.out.println("add group successfully!");
			
		}else{
			//System.out.println("please complete the form");
		}
		getGroup gg = new getGroup();
		HashMap<String, String> groupBalance =new HashMap<String, String>();
		ArrayList<String> group=gg.getGroupSet(u.Id, groupBalance);
		ArrayList<groupInfo> all_groups = new ArrayList<groupInfo>();
		for(String tempgroup : group) {
			groupInfo gi = gg.searchGroup(tempgroup);
			all_groups.add(gi);
		}
		request.getSession().setAttribute("groupInfo", all_groups);
		response.sendRedirect("/jsp/Welcome.jsp");
		
	  }
 
 /*public static void main(String[] args) throws Exception {

	 
     try {            	 
         // Parameter1: table name // Parameter2: reads per second //
         // Parameter3: writes per second // Parameter4/5: hash key and type
         // Parameter6/7: range key and type (if applicable)
    	 createDB();
         createTable(group, 10L, 5L, "Id", "N");
         LoadUsers(group);
      
     } catch (Exception e) {
         System.err.println("Program failed:");
         System.err.println(e.getMessage());
     }
     System.out.println("Success.");
 }*/

 private static void createTable(
     String tableName, long readCapacityUnits, long writeCapacityUnits, 
     String hashKeyName, String hashKeyType, 
     String rangeKeyName, String rangeKeyType) {

     try {

         ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
         keySchema.add(new KeySchemaElement()
             .withAttributeName(hashKeyName)
             .withKeyType(KeyType.HASH));
         
         ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
         attributeDefinitions.add(new AttributeDefinition()
             .withAttributeName(hashKeyName)
             .withAttributeType(hashKeyType));

         if (rangeKeyName != null) {
             keySchema.add(new KeySchemaElement()
                 .withAttributeName(rangeKeyName)
                 .withKeyType(KeyType.RANGE));
             attributeDefinitions.add(new AttributeDefinition()
                 .withAttributeName(rangeKeyName)
                 .withAttributeType(rangeKeyType));
         }

         CreateTableRequest request = new CreateTableRequest()
                 .withTableName(tableName)
                 .withKeySchema(keySchema)
                 .withProvisionedThroughput( new ProvisionedThroughput()
                     .withReadCapacityUnits(readCapacityUnits)
                     .withWriteCapacityUnits(writeCapacityUnits));

         request.setAttributeDefinitions(attributeDefinitions);
         
         AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
    	 client.setRegion(Region.getRegion(Regions.US_WEST_2));

         //System.out.println("Issuing CreateTable request for " + tableName);
         Table table = dynamoDB.createTable(request);
         //System.out.println("Waiting for " + tableName+ " to be created...this may take a while...");
         table.waitForActive();

     } catch (Exception e) {
         System.err.println("CreateTable request failed for " + tableName);
         System.err.println(e.getMessage());
     }
 }

 private static void LoadGroup(String groupName, String memberList, String description, String user) {
	 
     Table table1 = dynamoDB.getTable("groupDescription");
     Table table2 = dynamoDB.getTable("currentBalance");
     Date date = new Date();
     String[] member = memberList.split(";");
     String groupId = user+date.toString();
     
     try {

         //System.out.println("Adding data to groupDecription & currentBalance");

         Item item1 = new Item()
             .withPrimaryKey("groupId", groupId)
             .withString("groupName", groupName)
             .withString("description", description);
         
         Item item2 = new Item()
                 .withPrimaryKey("groupId", groupId, "userId", user)
                 .withString("balance", "0");
         
         for(int i=0; i < member.length; i++){
        	 Item item3 = new Item()
                     .withPrimaryKey("groupId", groupId,"userId", member[i])
                     .withString("balance", "0");
        	 table2.putItem(item3);
         }
             
         table1.putItem(item1);
         table2.putItem(item2);        

     } catch (Exception e) {
         System.err.println("Failed to create item in ");
         System.err.println(e.getMessage());
     }

 }
 
 public groupInfo searchGroup(String groupId) {
	 
     Table table = dynamoDB.getTable("groupDescription");
     //Table table2 = dynamoDB.getTable("currentBalance");
     //Date date = new Date();
     
     Map<String, AttributeValue> expressionAttributeValues = 
			    new HashMap<String, AttributeValue>();
	  expressionAttributeValues.put(":g", new AttributeValue().withS(groupId));
	  
	  try {
		  ScanRequest scanRequest = new ScanRequest()
			        .withTableName("groupDescription")
			        .withFilterExpression("groupId = :u")
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

}

