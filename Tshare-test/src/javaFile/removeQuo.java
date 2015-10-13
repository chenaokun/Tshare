package javaFile;

public class removeQuo {
	public static String remove(String s){
		if(s==null||s.length()<2)
			return s;
		return s.substring(1,s.length()-1);
	}
}
