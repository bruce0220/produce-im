package com.hzw.code.produce;


public class CommonUtils {
	
	public static String toCamel(String str){
		String s[] = str.split("_|-");
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<s.length;i++){
			if(i==0){
				buf.append(s[i]);
				continue;
			}
				
			buf.append(firstCharUp(s[i]));
		}
		return buf.toString();
	}
	
	public static String firstCharUp(String str){
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
}
