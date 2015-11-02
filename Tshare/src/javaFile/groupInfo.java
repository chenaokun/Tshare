package javaFile;

public class groupInfo {
	public String groupId;
	public String groupName;
	public String description;
	
	public groupInfo(String id){
		this.groupId = id;
		
	}
	
	public void setDescription(String d){
		this.description = d;
	}
	public void setName(String n){
		this.groupName = n;
	}
}
