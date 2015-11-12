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

public class activityServlet extends HttpServlet {
	static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	static DynamoDB dynamoDB;
	protected void doPost(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
		  {
		    // reading the user input      
		    response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			String activityOption=request.getParameter("activityOption");
			User u=(User)request.getSession().getAttribute("userInfo");
			groupInfo g=(groupInfo)request.getSession().getAttribute("curr_group");
			System.out.println("POST Success! actrivityServlet");
			System.out.println("option: "+ activityOption+" user: "+u.Id);
			
			if(activityOption.equals("a")){		
				ArrayList<activityInfo> user_activity = getActivity.allActivity(u.Id, g.groupId);
				request.getSession().setAttribute("all_activity", user_activity);
				response.sendRedirect("/Tshare-test2/jsp/All_activity.jsp");
			} else {
				ArrayList<activityInfo> user_activity = getActivity.userActivity(u.Id, g.groupId);
				request.getSession().setAttribute("my_activity", user_activity);
				response.sendRedirect("/Tshare-test2/jsp/My_activity.jsp");
			}		
		  }
}
