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
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
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


public class mainServlet extends HttpServlet {
	static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
    static DynamoDB dynamoDB;   
 protected void doPost(HttpServletRequest request, 
	      HttpServletResponse response) throws ServletException, IOException 
	       
	  {
	    // reading the user input      
	    response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String groupId=request.getParameter("groupId");
		String groupName=request.getParameter("groupName");
		System.out.println("POST Success! mainServlet");
		System.out.println("groupId: "+ groupId+" name:"+groupName);
		getGroup gg = new getGroup();
		
		User u=(User)request.getSession().getAttribute("userInfo");
		HashMap<String, String> groupBalance =new HashMap<String, String>();
		ArrayList<String> group=gg.getGroupSet(u.Id, groupBalance);
		ArrayList<groupInfo> all_groups = new ArrayList<groupInfo>();
		for(String g : group) {
			groupInfo gi = gg.searchGroup(g);
			all_groups.add(gi);
		}
		request.getSession().setAttribute("groupInfo", all_groups);
		
		
		for(groupInfo g: all_groups) {
			if((g.groupId).equals(groupId)){
				request.getSession().setAttribute("curr_group", g);
				break;
			}
		}
		
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		 dynamoDB = new DynamoDB(client);
		 Table table = dynamoDB.getTable("currentBalance");
		 ItemCollection<QueryOutcome> col = table.query("groupId",groupId);
		 ArrayList<String> memberList=new ArrayList<String>();
		 for (Item item: col) {
			 memberList.add(item.getJSON("userId"));
			 System.out.println(item.getJSON("userId"));
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
		 
		request.getSession().setAttribute("groupToMember",groupToMember);
		request.getSession().setAttribute("groupToImg",groupToImg);
		response.sendRedirect("/Tshare-test2/jsp/Main-page.jsp");
		
	  }

}

