package javaFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class signUp extends HttpServlet{
	static AmazonDynamoDB client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
	 static DynamoDB dynamoDB;
	 
	 protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		 client.setRegion(Region.getRegion(Regions.US_WEST_2));
		 dynamoDB = new DynamoDB(client);
		 String tableName="Usr_info";
		 Table table = dynamoDB.getTable(tableName);
		 String userId=request.getParameter("usrname");
		 String password1=request.getParameter("password1");
		 String password2=request.getParameter("password2");
		 String fullName=request.getParameter("fullName");
		 String img=request.getParameter("img");
		 boolean errors[]=new boolean[3];
		 for(int i=0;i<errors.length;i++)
			 errors[i]=false;
		 //test if left blank
		 if(password1!=null&&password2!=null&&userId!=null&&fullName!=null&&password1.length()!=0&&password2.length()!=0&&userId.length()!=0&&fullName.length()!=0)
			 errors[0]=false;
		 else errors[0]=true;
		 
		 if(errors[0]){
			 request.getSession().setAttribute("registerErrors", errors);
			 response.sendRedirect("/Tshare-test2/jsp/register_error.jsp");
			 return;
		 }
		 
		 //test if userId has been already taken
		 try {
	         System.out.println("getting table " + tableName);	        
	         Item item = table.getItem("Id", userId);    
	         if(item!=null)
	        	 errors[1]=true;
	         else errors[1]=false;
	     } catch (Exception e) {
	         System.err.println("Failed to create item in " + tableName);
	         System.err.println(e.getMessage());
	     }
		 
		 //test if two passwords are the same
		 if(password1.equals(password2))
			 errors[2]=false;
		 else errors[2]=true;
		 
		 if(errors[1]||errors[2]){
			 request.getSession().setAttribute("registerErrors", errors);
			 response.sendRedirect("/Tshare-test2/jsp/register_error.jsp");
			 return;
		 }
		 
		 Item item = new Item()
		    .withPrimaryKey("Id", userId)
		    .withString("password", password1)
		    .withString("userName", fullName)
		    .withString("photoPath", img);
		
		 PutItemOutcome outcome = table.putItem(item);	 	 
		 
			
		 response.sendRedirect("/Tshare-test2/jsp/register_success.jsp");
		 
	}
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			this.doGet(request, response);
		}
		
}
