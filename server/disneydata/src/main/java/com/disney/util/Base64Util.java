package com.disney.util;

import java.io.IOException;

public class Base64Util {
	/**
	 * 编码
	 * @param bstr
	 * @return String
	 */
	@SuppressWarnings("restriction")
	private static String encode(byte[] bstr){
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解码
	 * @param str
	 * @return string
	 */
	@SuppressWarnings("restriction")
	private static byte[] decode(String str){
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer( str );
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	} 
	
	
	
	/**
	 * 编码
	 * @param bstr
	 * @return String
	 */
	public static String encodeString(String str){
		return encode(str.getBytes());
	}

	/**
	 * 解码
	 * @param str
	 * @return string
	 */
	public static String decodeString(String str){
		byte[] bytes = decode(str);
		return new String(bytes);
	} 
	
	public static void main(String[] args) {
		
		String test = "测试";
		
		String encode = encodeString(test);
		String decode = decodeString(encode);
				
		System.out.println(encode);
		System.out.println(decode);
		
		
	}
	
}
