package javaFile;
import com.amazonaws.services.dynamodbv2.document.Item;
//This class is used to store user info
public class User {
	public String Id;
	public String name;
	public String img;
	
	public User(Item user){
		this.Id=removeQuo.remove(user.getJSON("Id"));
		this.name=removeQuo.remove(user.getJSON("userName"));
		this.img=removeQuo.remove(user.getJSON("photoPath"));
	}
}
