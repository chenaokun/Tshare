package javaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

public class deleteMember extends HttpServlet{
	static DynamoDB dynamoDB= get.dynamoDB;
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		String[] userList = request.getParameterValues("userList");
		groupInfo group=(groupInfo)request.getSession().getAttribute("curr_group");
		User user=(User)request.getSession().getAttribute("userInfo");
		ArrayList<groupInfo> groupList = (ArrayList<groupInfo>)request.getSession().getAttribute("groupInfo");
		String groupId=group.groupId;
		HashMap<String,String> members=(HashMap<String, String>) request.getSession().getAttribute("groupToMember");
		
		Table table = dynamoDB.getTable("currentBalance");
		boolean in=false;
		
		for(String s:userList){
			table.deleteItem("groupId", groupId,"userId",s);
			if(s.equals(user.Id))
				in=true;
			members.remove(s);
		}
		
		if(in){
			ArrayList<groupInfo> newList=new ArrayList<groupInfo>();
			for(groupInfo g:newList){
				if(!g.groupId.equals(groupId))
					newList.add(g);
			}
			request.getSession().setAttribute("groupInfo", newList);
		}
		else{
			request.getSession().setAttribute("groupToMember", members);
		}

		response.sendRedirect("/jsp/memberDeleted.jsp");
	}
}
