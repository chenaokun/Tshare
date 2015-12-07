package javaFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
//This class uploads avatars to S3 storage.
public class uploadToS3 extends HttpServlet{
	
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		AWSCredentials credentials = new PropertiesCredentials(
	             AmazonDynamoDB.class.getResourceAsStream("/AwsCredentials.properties"));
		String base64Data=request.getParameter("canvasData");
		byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((base64Data.substring(base64Data.indexOf(",")+1)).getBytes());
		System.out.println("uploadToS3");
		InputStream fis = new ByteArrayInputStream(bI);
		String usrname=request.getParameter("usrname");
		AmazonS3 s3 = new AmazonS3Client(credentials);
		Region usWest02 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest02);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(bI.length);
		metadata.setContentType("image/png");
		metadata.setCacheControl("public, max-age=31536000");
		s3.putObject("tshareavatar", usrname+"1", fis, metadata);
		s3.setObjectAcl("tshareavatar", usrname+"1", CannedAccessControlList.PublicReadWrite);
	}
		
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
