package com.bjwg.back.listener;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.PageConstant.PageView;
import com.bjwg.back.util.EnumUtil;

/**
 * @author Carter
 * @version 创建时间：Sep 9, 2015 10:55:10 AM
 * @Modified By:Carter
 * Version: 1.0
 * jdk : 1.6
 * 类说明：由于在页面上不好引用常量, 这里将各常量初始化在app作用域中
 */
@WebListener
public class ConstantInitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("-- application const init... --");
		ServletContext servletContext = arg0.getServletContext();
		Map<Integer, String> map1 = null;
		Map<PageView, List<PageView>> map2 = null;
		try {
			map1 = EnumUtil.toStatusMsgMap(com.bjwg.back.base.constant.PageConstant.PageMsg.class);
			map2 = PageConstant.getViewMap();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		servletContext.setAttribute("NAV_CONST", map1);
		servletContext.setAttribute("NAV_CONST_VIEW", map2);
	}
}

