package javaFile;



import com.amazonaws.services.dynamodbv2.document.Item;

public class User {
	public String Id;
	public String email;
	public String photo;
	
	public User(Item user){
		this.Id=removeQuo.remove(user.getJSON("Id"));
	}
}