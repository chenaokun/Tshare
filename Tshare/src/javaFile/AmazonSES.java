package javaFile;

import java.io.IOException;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AmazonSES {
	static final String FROM = "youdidut@gmail.com";  // Replace with your "From" address. This address must be verified.
    static String TO = ""; // Replace with a "To" address. If your account is still in the
                                                      // sandbox, this address must be verified.
    static String BODY = "";
    static String SUBJECT = "";
    static final String part1="<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"
    		+"<html xmlns='http://www.w3.org/1999/xhtml'><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />"
    		+"<title>Demystifying Email Design</title><meta name='viewport' content='width=device-width, initial-scale=1.0'/>"
    		+"</head><body style='margin: 0; padding: 0;'><table align='center' border='0' cellpadding='0' cellspacing='0' width='600' style='border: 1px solid #cccccc; border-collapse: collapse;'><tr><td>"
    		+"<table align='center' border='0' cellpadding='0' cellspacing='0' width='600' style='border-collapse: collapse;'>"
    		+"<tr><td bgcolor='#70bbd9' style='padding:15px 15px 15px 15px;'>"
    		+"<table border='0' cellpadding='0' cellspacing='0' width='100%'><tr>"
    		+"<td  align='center' style='color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;' width='75%'>"
    		+"Tshare Update</td></table></td></tr></table></td></tr><tr><td bgcolor='#ffffff' style='padding: 40px 30px 40px 30px;'>"
    		+"<table border='0' cellpadding='0' cellspacing='0' width='100%'><tr>"
    		+"<td  align='center' style='color: #70bbd9; font-family: Arial, sans-serif; font-size: 14px;' width='75%'>";
    static final String part2="</td></table></tr></table></body></html>";
    
    public static void sendNotice(String[] t, String b, String s, String except) throws IOException {    	
                
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{TO});
        
        BODY=b;
        SUBJECT=s;
        // Create the subject and body of the message.
        Content subject = new Content().withData(SUBJECT);
        //Content textContent = new Content().withData(BODY); 
        Content htmlContent = new Content().withData(part1+BODY+part2);
        // Create a message with the specified subject and body.
        Message msg = new Message().withSubject(subject);
        Body body = new Body().withHtml(htmlContent);//.withText(textContent)
        msg.setBody(body);
        
        for(int i=0;i<t.length;i++){
        	if(except.equals(t[i]))
        		continue;
        	TO=t[i];
        	System.out.println("\nReceiver is: "+TO);  	      	
	        Destination des=new Destination().withToAddresses(TO);	              
	        
	        // Assemble the email.
	        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(msg).withDestination(des);
	        
	        try
	        {        
	            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
	        
	            // Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials. 
	            // Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials 
	            // using the default credential provider chain. The first place the chain looks for the credentials is in environment variables 
	            // AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
	            // For more information, see http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html
	            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();
	               
	            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox 
	            // status, sending limits, and Amazon SES identity-related settings are specific to a given AWS 
	            // region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using 
	            // the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1 
	            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html 
	            Region REGION = Region.getRegion(Regions.US_WEST_2);
	            client.setRegion(REGION);
	       
	            // Send the email.
	            client.sendEmail(request);  
	            System.out.println("Email sent!");
	        }
	        catch (Exception ex) 
	        {
	            System.out.println("The email was not sent.");
	            System.out.println("Error message: " + ex.getMessage());
	        }
        }
    }
}
