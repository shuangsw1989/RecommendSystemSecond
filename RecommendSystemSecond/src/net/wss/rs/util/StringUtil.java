package net.wss.rs.util;

import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断是不是数值字符串
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(isNumeric("1s"));
	}

}
