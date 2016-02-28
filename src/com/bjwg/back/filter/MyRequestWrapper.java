package com.bjwg.back.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.bjwg.back.util.StringUtils;


/**
 * @author Administrator
 * @version 创建时间：2015-3-16 上午10:41:24
 * @Modified By:Administrator
 * Version: 
 * 类说明：过滤器
 */

public class MyRequestWrapper extends HttpServletRequestWrapper{
	
	public MyRequestWrapper(HttpServletRequest request) { 
		super(request); 
	} 
	
	@Override
    public String getParameter(String name) {
		// 返回值之前 先进行过滤
        return StringUtils.filterDangerString(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        // 返回值之前 先进行过滤
        String[] values = super.getParameterValues(name);
        if (values == null) 
    		return null; 
        int count = values.length; 
        String[] trimResults = new String[count]; 
        for (int i = 0; i < count; i++) { 
        	trimResults[i] = StringUtils.filterDangerString(values[i].trim()); 
        }
        return trimResults;
    }
}
