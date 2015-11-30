package javaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class updateSummary extends HttpServlet{	
	protected void doGet(HttpServletRequest request, 
		     HttpServletResponse response) throws ServletException, IOException 
	{
		User user=(User)request.getSession().getAttribute("userInfo");
		String u=user.Id;
		HashMap<String, String> groupBalance =new HashMap<String, String>();
		getGroup gg = new getGroup();
		ArrayList<String> group=gg.getGroupSet(u,groupBalance);
		request.getSession().setAttribute("groupBalance", groupBalance);
		response.sendRedirect("/jsp/Welcome.jsp");
	}
}
