package com.bjwg.back.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 小工具类
 * 单例
 * @author :Kim  
 * @CreateDate : 2015-3-13 下午01:46:23 
 * @lastModified : 2015-3-13 下午01:46:23 
 * @version : 1.0
 * @jdk：1.6
 */
public class ToolKit {

	
	private static ToolKit instance;
	
	//缓存配置项
	private static Map<String, List<String>> configInfoMap;
	
	private static JdbcTemplate jdbcTemplate;
	
	private ToolKit(){
		
		configInfoMap = BaseConfigUtil.getConfigInfo();
	}
	
	public static ToolKit getInstance(){
		
		if(instance == null){
			try {
				
				instance = new ToolKit();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		return instance;
	}

	
	//获取缓存配置
	public String getSingleConfig(String name){
		try{
			
			return configInfoMap.get(name).get(0);
			
		}catch(Exception e){
			
			System.out.println("获取缓存配置 出错:"+e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 获取自增长序列
	 * @param name
	 * @return
	 */
	public int getNextKey(String name){
		
		jdbcTemplate = (JdbcTemplate)SpringFactory.getBean("jdbcTemplate");
		
		String sql = "select " + name + " from dual";
		
		return jdbcTemplate.queryForInt(sql);
		
	}
	
	
	
	public static void main(String[] args){
		ToolKit toolKit=ToolKit.getInstance();
		
		System.out.println(toolKit.getSingleConfig("shop_goods_path"));
		
	}

	
}
