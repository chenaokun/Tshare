package javaFile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
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
public class logIn extends HttpServlet { 
	
	static DynamoDB dynamoDB;
	
  protected void doGet(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException 
  {
    // reading the user input     
	  get ge=new get();
	  dynamoDB= get.dynamoDB;
	  response.setContentType("text/html");
	response.setCharacterEncoding("utf-8");
	String u=request.getParameter("usrname");
	String p=request.getParameter("password");
	/*System.out.println("username:"+u);
	System.out.println("input password:"+p);*/
	
	Item user=getPW(u,"Usr_info");
	if(user==null)
		response.sendRedirect("/jsp/sign_in.jsp");
	String pwDB=user.getJSON("password");
	if(pwDB!=null&&pwDB.length()>2&&p.equals(pwDB.substring(1, pwDB.length()-1))){			
		User StoredUser=new User(user);		
		request.getSession().setAttribute("userInfo", StoredUser);
		response.sendRedirect("/jsp/Welcome.jsp");
	}
	else response.sendRedirect("/jsp/sign_in.jsp");
	HashMap<String, String> groupBalance =new HashMap<String, String>();
	getGroup gg = new getGroup();
	ArrayList<String> group=gg.getGroupSet(u,groupBalance);
	ArrayList<groupInfo> all_groups = new ArrayList<groupInfo>();
	for(String g : group) {
		groupInfo gi = gg.searchGroup(g);
		all_groups.add(gi);
	}	
	request.getSession().setAttribute("groupInfo", all_groups);
	request.getSession().setAttribute("groupBalance", groupBalance);
  }
  
  private static Item getPW(String userId, String tableName){
	 
	  Table table = dynamoDB.getTable(tableName);
	  
	  try {

	         //System.out.println("getting table " + tableName);	        
	         Item item = table.getItem("Id", userId);    
	         String user_pass = item.toString();
	         //System.out.println(user_pass);
	         return item;

	     } catch (Exception e) {
	         System.err.println("Failed to get item in " + tableName);
	         System.err.println(e.getMessage());
	     }
	    return null;
  }
  
}