package javaFile;

public class activityInfo {
	public String billId;
	public String billName;
	public String description;
	public String payerId;
	public String userId;
	public String amount;
	public String totalAmount;
	public String photoPath;
	public boolean flag;
	
	public activityInfo(){
		super();
	}
	
	public void setDescription(String d){
		this.description = d;
	}
	public void setName(String n){
		this.billName = n;
	}
	
	public void setId(String i){
		this.billId = i;
	}
	
	public void setPayer(String p){
		this.payerId = p;
	}
	
	public void setAmount(String a){
		this.amount = a;
	}
	
	public void setTotal(String a){
		this.totalAmount = a;
	}
	public void setPath(String p){
		this.photoPath = p;
	}
	
	public void setFlag(boolean f){
		this.flag = f;
	}
}
