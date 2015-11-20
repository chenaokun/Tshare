package javaFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class uploadReceipt extends HttpServlet{
	
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException 
	{
		String base64Data=request.getParameter("canvasData");
		byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((base64Data.substring(base64Data.indexOf(",")+1)).getBytes());
		System.out.println("uploadReceiptToS3");
		InputStream fis = new ByteArrayInputStream(bI);
		String billId=(String)request.getSession().getAttribute("billId");
		AmazonS3 s3 = new AmazonS3Client();
		Region usWest02 = Region.getRegion(Regions.US_WEST_2);
		s3.setRegion(usWest02);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(bI.length);
		metadata.setContentType("image/png");
		metadata.setCacheControl("public, max-age=31536000");
		s3.putObject("tsharebilling", billId, fis, metadata);
		s3.setObjectAcl("tsharebilling", billId, CannedAccessControlList.PublicReadWrite);
	}
		
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
