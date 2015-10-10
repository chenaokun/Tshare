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






import java.io.PrintWriter;
public class logIn extends HttpServlet { 
	
	 static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	 static DynamoDB dynamoDB;
	
  protected void doGet(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException 
  {
    // reading the user input      
    response.setContentType("text/html");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	String u=request.getParameter("usrname");
	String p=request.getParameter("password");
	System.out.println("username:"+u);
	System.out.println("input password:"+p);
	
	Item user=getPW(u,"Usr_info");
	String pwDB=user.getJSON("password");
	System.out.println("valid password:"+pwDB);
	if(pwDB!=null&&pwDB.length()>2&&p.equals(pwDB.substring(1, pwDB.length()-1))){
		//request.getRequestDispatcher("/jsp/Welcome.jsp").forward(request, response);
		User StoredUser=new User(user);
		request.getSession().setAttribute("userInfo", StoredUser);
		response.sendRedirect("/Tshare-test2/jsp/Welcome.jsp");
	}
	else response.sendRedirect("/Tshare-test2/jsp/sign_in.jsp");
  }
  
  private static Item getPW(String userID, String tableName){
	  client.setRegion(Region.getRegion(Regions.US_WEST_2));
	  dynamoDB = new DynamoDB(client);
	  Table table = dynamoDB.getTable(tableName);
	  
	  try {

	         System.out.println("getting table " + tableName);	        
	         Item item = table.getItem("Id", userID);    
	         String user_pass = item.toString();
	         System.out.println(user_pass);
	         return item;

	     } catch (Exception e) {
	         System.err.println("Failed to create item in " + tableName);
	         System.err.println(e.getMessage());
	     }
	    return null;
  }
}