package com.disney.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;

public class FileUtil {
	/*
	 * Java文件操作 获取文件扩展名
	 *
	 *  Created on: 2011-8-2
	 *      Author: blueeagle
	 */
	public static String getExtensionName(String filename) { 
		if ((filename != null) && (filename.length() > 0)) { 
			int dot = filename.lastIndexOf('.'); 
			if ((dot >-1) && (dot < (filename.length() - 1))) { 
				return filename.substring(dot + 1); 
			} 
		} 
		return filename; 
	} 
	/*
	 * Java文件操作 获取不带扩展名的文件名
	 *
	 *  Created on: 2011-8-2
	 *      Author: blueeagle
	 */
	public static String getFileNameNoEx(String filename) { 
		if ((filename != null) && (filename.length() > 0)) { 
			int dot = filename.lastIndexOf('.'); 
			if ((dot >-1) && (dot < (filename.length()))) { 
				return filename.substring(0, dot); 
			} 
		} 
		return filename; 
	} 
	
	public static void main(String[] args){
		try {
			String json = readFile("E:/t-data.json");
			List<LinkedHashMap<String, Object>> l = JsonUtil.readJson2List(json);
			System.out.println(l.size());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * 将文本文件中的内容读入到buffer中
     * @param buffer buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 读取文本文件内容
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return sb.toString();
    }
	
}
