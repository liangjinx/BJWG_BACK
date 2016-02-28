package com.bjwg.back.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @version 创建时间：2015-9-7 下午04:36:13
 * @Modified By:Administrator
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class ConsoleUtil {

	public static void println(Object o){
		
		System.out.println(o);
	}
	public static void println(String s){
		
		System.out.println(s);
	}
	public static void println(List list){
		
		System.out.println(list);
	}
	public static void println(Map<String,Object> map){
		
		for (Map.Entry<String, Object> m : map.entrySet()) {
			
			System.out.println("map中的值：" + m.getKey() + " : " +m.getValue());
		}
	}
	public static void println(int i){
		
		System.out.println(i);
	}
	public static void println(Date date){
		
		System.out.println(date);
	}
	public static void println(Long l){
		
		System.out.println(l);
	}
	
}
