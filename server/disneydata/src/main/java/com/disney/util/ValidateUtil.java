package com.disney.util;

import java.util.regex.Pattern;

public class ValidateUtil {
	public static boolean isMobileNumber(String mobiles) {
		return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$")
				.matcher(mobiles).matches();
	}
/*	public static void main(String[] args){
		String phone = "13402141604";
		System.out.println(isMobileNumber(phone));
	}*/
	
}
