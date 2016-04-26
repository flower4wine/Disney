package com.disney.handler.jieshun.api;

/**
 * 其他工具类
 * @author 刘淦潮
 *
 */
final public class Util {
	
	/**
	 * 字节转16进制字符串
	 * @param bytes 字节数组
	 * @return 返回16进制的字符串
	 */
	public static String toHexString(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			buffer.append(String.format("%02X", bytes[i]));
		}
		return buffer.toString();
	}

}
