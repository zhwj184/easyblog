package org.springweb.util;


public class StringUtil {

	public static String getOutputString(String input, int length){
		if(input == null || input.length() <= length){
			return input;
		}
		return input.substring(0, length) + "...";
	}
}
