package javaFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Attribute;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

public class DynamoDBLock {
	
	static DynamoDB dynamoDB= get.dynamoDB;
	
	public static boolean AcquireLock(String groupID, String userID, String date, String action)
	//Acquire lock with the groupID, userID and accessTime in seconds after 1970. 
	//false for lock fails, true for successful lock
	{
		
		System.out.println("To acquire lock");
		System.out.println("groupID: "+groupID);
		
		boolean lock = true;			
		//false for lock not exist, true for lock exists
		
		
		get.client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(get.client);
		Table table = dynamoDB.getTable("locks");
		
		QuerySpec spec1 = new QuerySpec()
			    .withHashKey("groupID", groupID)
			    .withConsistentRead(true);
		
		ItemCollection<QueryOutcome> items = table.query(spec1);
		
		Iterator<Item> iterator = items.iterator();
		if(!iterator.hasNext()) lock = false;
		if(lock != false) return false;
		if(lock == false)
		{
			Item newlock = new Item()
					.withPrimaryKey("groupID", groupID, "lockID", userID+date)
					.withString("date", date).withString("action", action);
			
			PutItemOutcome outcome = table.putItem(newlock);
			//Start a new query spec, using the former one will cause error
			QuerySpec spec2 = new QuerySpec()
				    .withHashKey("groupID", groupID)
				    .withConsistentRead(true);
			items = table.query(spec2);
			iterator = items.iterator();
			
			while (iterator.hasNext()) 
			{
				String getLockID = iterator.next().getJSON("lockID");
				String lockIDRetrived = getLockID.substring(1, getLockID.length()-1);
				//System.out.println("lockID: "+userID+" lockIDRetrived: "+lockIDRetrived);
				   if(!lockIDRetrived.equals(userID+date))
				   {
					   table.deleteItem("groupID", groupID, "lockID", userID+date);
					   //System.out.println("Lock Acquire Failed");
					   return false;
				   }
			}
			
			
		}
		
		
		//System.out.println("Lock Acquired");
		return true;
	}
	
	
	public static void ReleaseLock(String groupID, String userID, String date)
	//Delete the coordinate item in locks table to release lock
	{
		System.out.println("To Release Lock");
		get.client.setRegion(Region.getRegion(Regions.US_WEST_2));
		dynamoDB = new DynamoDB(get.client);
		Table table = dynamoDB.getTable("locks");
		
		DeleteItemOutcome deleteItemOutcome = 
				table.deleteItem("groupID", groupID, "lockID", userID+date);
		
		//System.out.println("Released Lock");
	}
	
	
	
	public static void ResolveDeadlock(String groupID, String date)
	//Resolt deadlock for groupID and date
	{
		Table table = dynamoDB.getTable("locks");
		QuerySpec spec = new QuerySpec()
			    .withHashKey("groupID", groupID)
			    .withConsistentRead(true);
		
		ItemCollection<QueryOutcome> items = table.query(spec);
		
		Iterator<Item> iterator = items.iterator();
		
		while (iterator.hasNext()) 
		{
			String getlockID = iterator.next().getJSON("lockID");
			String lockIDRetrived = getlockID.substring(1, getlockID.length()-1);
			DeleteItemOutcome deleteItemOutcome = 
					table.deleteItem("groupID", groupID, "lockID", lockIDRetrived);
			//System.out.println("Resolved deadlock");
			break;
			
		}
		
	}

}
