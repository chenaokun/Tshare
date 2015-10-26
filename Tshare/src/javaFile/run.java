package javaFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class run {
	
		public static void main(String[] arg)
		{
			Solution test=new Solution();		
			HashMap<String[], Integer> map=new HashMap<String[], Integer>();
			userBalance a=new userBalance("a", 20);
			userBalance b=new userBalance("b", -15);
			userBalance c=new userBalance("c", 5);
			userBalance d=new userBalance("d", -10);
			ArrayList<userBalance> list=new ArrayList<userBalance>();
			list.add(a);
			list.add(b);
			list.add(c);
			list.add(d);
			map=test.getSolution(list);
			for (Entry<String[], Integer> entry : map.entrySet()) {
			    String[] key = entry.getKey();
			    int value = entry.getValue();
			    System.out.println(key[0]+" pays "+key[1]+" $"+Integer.toString(value));
			    
			}
		}
		
	 }
	 


