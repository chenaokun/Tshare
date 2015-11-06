package javaFile;

import java.io.IOException;
import java.util.HashMap;
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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;

public class updateGroupInfo extends HttpServlet{
	static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	static DynamoDB dynamoDB;
	protected void doGet(HttpServletRequest request, 
		     HttpServletResponse response) throws ServletException, IOException 
	{
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		groupInfo group=(groupInfo)request.getSession().getAttribute("curr_group");
		String groupId=group.groupId;
		String description=request.getParameter("des");
		String name=request.getParameter("name");
		String tableName="groupDescription";
		String curPath=request.getParameter("curPath");
		System.out.println(name+description+groupId);
		updateTable(tableName, groupId, "groupName", name,"description", description);
		group.groupName=name;
		group.description=description;
		request.getSession().setAttribute("curr_group", group);
		response.sendRedirect(curPath);		
	
			
		}
	
	
	void updateTable(String tableName, String groupId, String attribute, String value, String attribute1, String value1){
		Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();

		updateItems.put(attribute, new AttributeValueUpdate().withValue(new AttributeValue(value)).withAction("PUT"));
		updateItems.put(attribute1, new AttributeValueUpdate().withValue(new AttributeValue(value1)).withAction("PUT"));
		
		Map<String, AttributeValue> itemKeys = new HashMap<String, AttributeValue>();
		itemKeys.put("groupId", new AttributeValue(groupId));
		

		UpdateItemRequest updateItemRequest = new UpdateItemRequest()
		                                          .withTableName(tableName)
		                                          .withKey(itemKeys)
		                                          .withAttributeUpdates(updateItems);

		client.updateItem(updateItemRequest);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
