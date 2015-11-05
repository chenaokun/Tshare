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
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;

public class updateProfile extends HttpServlet{
	static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	static DynamoDB dynamoDB;
	
	protected void doGet(HttpServletRequest request, 
		     HttpServletResponse response) throws ServletException, IOException 
	{
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(client);
		String tableName="Usr_info";
		User user=(User)request.getSession().getAttribute("userInfo");
		String userId=user.Id;		
		String subject=request.getParameter("subject");
		
		System.out.println("request is to change: "+subject);
		
		if(subject.equals("name")){			
			String value=request.getParameter("username");
			System.out.println("new name to update: "+value);
			updateTable(tableName, userId, "userName", value);
			user.name=value;
			request.getSession().setAttribute("userInfo", user);
		}
		else if(subject.equals("password")){
			String password1=request.getParameter("password1");
			String password2=request.getParameter("password2");
			if(!password1.equals(password2)){
				response.sendRedirect("/Tshare-test2/jsp/profile/passwordError.jsp");
				return;
			}
			updateTable(tableName, userId, "password", password1);			
		}
		response.sendRedirect("/Tshare-test2/jsp/profile/updateSuccess.jsp");		
	}
	
	void updateTable(String tableName, String userId, String attribute, String value){
		Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();

		updateItems.put(attribute, new AttributeValueUpdate().withValue(new AttributeValue(value)).withAction("PUT"));
		
		Map<String, AttributeValue> itemKeys = new HashMap<String, AttributeValue>();
		itemKeys.put("Id", new AttributeValue(userId));
		

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
