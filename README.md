Read Me
This document introduces the steps to deploy Tshare on Elastic Beanstalk.
1. Fill in your AWS credentials in file "Tshare\Tshare\src\AwsCredentials.properties". Give this IAM "AdministratorAccess" permission.
 
2. Export Tshare project from Eclipse as a .war file.
 
3. Set up 6 tables in DynamoDB. The table schemas are as below:
Table1 name: Usr_info  hashkey:Id
Table2 name: groupDescription hashkey:groupId
Table3 name: expense hashkey:billId, rangekey:userId
Table4 name: currentBalance hashkey: groupId, rangeKey: userId
Table5 name: bill hashkey:billId
Note that you don't have to provide other attributes that are not key attributes when building up the tables.

4. Create a new application on Elastic Beanstalk. Choose the proper Tomcat version and Java version (we use Tomcat 7 and Java 7 in our test). Make sure you are aware of the JRE version of your project and it has the same Java version in its facet. Upload the .war file during the configuration process. 
 
5. After the environment is launched, go to "Configuration-> Load Balancing ". Check the Session stickiness option and set Cookie expiration to 1000 seconds.
 
6. You may configure other options in Elastic Beanstalk based on your need. Also it is advised to configure proper provisioned read/write capacity in DynamoDB for each table.

7. Visit Tshare website to sign up, sign in, etc.


