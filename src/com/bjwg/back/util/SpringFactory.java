package com.bjwg.back.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取spring容器，以访问容器中定义的其他bean
 * @author Administrator
 *
 */
public class SpringFactory implements ApplicationContextAware {

	// Spring应用上下文环境  
    private static ApplicationContext applicationContext; 
    
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */  
    public void setApplicationContext(ApplicationContext applicationContext) {  
    	SpringFactory.applicationContext = applicationContext;  
    }  
  
    /** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    /** 
     * 获取对象 
     * 这里重写了bean方法，起主要作用 
     * @param name 
     * @return Object 一个以所给名字注册的bean的实例 
     * @throws BeansException 
     */  
    public static Object getBean(String name) throws BeansException {  
    	
    	Object object =null;
    	
		try{
			
			object = applicationContext.getBean(name);
			
		}catch(Exception e){

			e.printStackTrace();
		}
		
		return object;
    }  

}
