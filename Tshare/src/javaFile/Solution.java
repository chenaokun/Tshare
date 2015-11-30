package javaFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Solution {
	int maxGroupMember=100;
    public HashMap<String[], Double> getSolution(ArrayList<userBalance> ubList) {
    	sortMax max = new sortMax();
    	sortMin min = new sortMin();
    	PriorityQueue<userBalance> pqMax = new PriorityQueue<userBalance>(maxGroupMember, max);
    	PriorityQueue<userBalance> pqMin = new PriorityQueue<userBalance>(maxGroupMember, min);    	
    	userBalance ub=null;
    	userBalance ubSend=null;
    	userBalance ubReceive=null;
    	HashMap<String[], Double> plan=new HashMap<String[], Double>();
    	
    	double check=0;
    	
    	for(int i=0;i<ubList.size();i++){
    		ub=ubList.get(i);
    		System.out.println(ub.userId+" "+Double.toString(ub.balance));
    		
    		pqMax.add(ub);
    		pqMin.add(ub);
    		check+=ub.balance;
    	}
    	
    	if(check>=0.01){
    		System.out.println("ERROR IN DATABASE. Total balance within group is "+ check);
    		return plan;
    	}
    	
    	while(!pqMax.isEmpty()){
    		System.out.println("Solution: loop");
    		ubSend=pqMax.poll();
    		pqMin.remove(ubSend);
    		ubReceive=pqMin.poll();
    		pqMax.remove(ubReceive);
    		if(ubSend.balance+ubReceive.balance>0){    			
    			String[] transactionParties=new String[2];
    			transactionParties[0]=ubSend.userId;
    			transactionParties[1]=ubReceive.userId;
    			plan.put(transactionParties,Math.abs(ubReceive.balance));    			
    			ubSend.balance=ubSend.balance+ubReceive.balance;
    			ubReceive.balance=0.0;
    			
    		}
    		else{    			
    			String[] transactionParties=new String[2];
    			transactionParties[0]=ubSend.userId;
    			transactionParties[1]=ubReceive.userId;
    			plan.put(transactionParties,Math.abs(ubSend.balance));    			
    			ubReceive.balance=ubSend.balance+ubReceive.balance;
    			ubSend.balance=0.0;
    		}
    		if(Math.abs(ubSend.balance)>0.01){
    			pqMax.add(ubSend);
    			pqMin.add(ubSend);
    		}
    		
    		if(Math.abs(ubReceive.balance)>0.01)
    			pqMax.add(ubReceive);
    			pqMin.add(ubReceive);
    	}
    	return plan;
    	
        
    }
    
    static class sortMax implements Comparator<userBalance> {    	 
		public int compare(userBalance ub1, userBalance ub2) {
			return (int)(ub2.balance - ub1.balance);
		}
	}
    
    static class sortMin implements Comparator<userBalance> {    	 
		public int compare(userBalance ub1, userBalance ub2) {
			return (int)(ub1.balance - ub2.balance);
		}
	}
}

/*2 4 5 5 6 7 0 0 1 2 2    7012345     6570123    70123  23601  2456701   560123*/