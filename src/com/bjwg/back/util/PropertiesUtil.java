package com.bjwg.back.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
/**
 * 配置文件读取工具类
 *
 */
public class PropertiesUtil {
	
	private static PropertiesUtil manager = null;
	private static Object managerLock = new Object();
	private Object propertiesLock = new Object();
	private static String RULE_CONFIG_FILE = "/rule.properties";
	private Properties properties = null;
	

	public static PropertiesUtil getInstance() {
		if (manager == null) {
			synchronized (managerLock) {
				if (manager == null) {
					manager = new PropertiesUtil();
				}
			}
		}
		return manager;
	}

	private PropertiesUtil() {
	}

	public static String getProperty(String name) {
		return getInstance()._getProperty(name);
	}

	private String _getProperty(String name) {
		initProperty();
		String property = properties.getProperty(name);
		if (property == null) {
			return "";
		}
		try {
			property = new String(property.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return property.trim();
	}

	@SuppressWarnings("rawtypes")
	public static Enumeration propertyNames() {
		return getInstance()._propertyNames();
	}

	@SuppressWarnings("rawtypes")
	private Enumeration _propertyNames() {
		initProperty();
		return properties.propertyNames();
	}

	private void initProperty() {
		if (properties == null) {
			synchronized (propertiesLock) {
				if (properties == null) {
					loadProperties();
				}
			}
		}
	}

	/**
	 * 默认路径
	 */
	private void loadProperties() {
		properties = new Properties();
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream(RULE_CONFIG_FILE);
			properties.load(in);
		} catch (Exception e) {
			System.err.println("Error reading conf properties in PropertyManager.loadProps() " + e);
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 提供配置文件路径
	 * 
	 * @param filePath
	 * @return
	 */
	public Properties loadProperties(String filePath) {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream(filePath);
			properties.load(in);
		} catch (Exception e) {
			System.err.println("Error reading conf properties in PropertiesUtil.loadProperties() " + e);
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return properties;
	}
	/**
	 * 将属性值获取为int型
	 * @param str 属性名
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static int getint( String str){
		try {
			return Integer.parseInt(getInstance().getProperty(str));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
