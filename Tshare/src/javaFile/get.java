package javaFile;

import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

//This class provides a public static DynamoDB variable with credentials read 
//from /AwsCredentials.properties, and set the region.
//This class is need to be initialized only when signing in or signing up.
public class get {
	static AWSCredentials credentials ;
	 static AmazonDynamoDB client;
	 static DynamoDB dynamoDB;
	 get() throws IOException {
		 //System.out.println("invoke method grouId is "+groupId);
		 
	  credentials = new PropertiesCredentials(
	             AmazonDynamoDB.class.getResourceAsStream("/AwsCredentials.properties"));
	  client =new AmazonDynamoDBClient(credentials);
	  client.setRegion(Region.getRegion(Regions.US_WEST_2));
	  dynamoDB = new DynamoDB(client);	  
	 }
}
