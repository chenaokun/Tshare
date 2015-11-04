package javaFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class run {
	
		public static void main(String[] arg)
		{
			Solution test=new Solution();		
			HashMap<String[], Double> map=new HashMap<String[], Double>();
			userBalance a=new userBalance("a", 20.0);
			userBalance b=new userBalance("b", -15.0);
			userBalance c=new userBalance("c", 5.0);
			userBalance d=new userBalance("d", -10.0);
			ArrayList<userBalance> list=new ArrayList<userBalance>();
			list.add(a);
			list.add(b);
			list.add(c);
			list.add(d);
			map=test.getSolution(list);
			for (Entry<String[], Double> entry : map.entrySet()) {
			    String[] key = entry.getKey();
			    Double value = entry.getValue();
			    System.out.println(key[0]+" pays "+key[1]+" $"+Double.toString(value));
			    
			}
		}
		
	 }
	 


